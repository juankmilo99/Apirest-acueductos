package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Authority;
import projects.acueductosapi.entities.User;
import projects.acueductosapi.repository.AuthorityRepository;
import projects.acueductosapi.repository.UsuarioRepository;
import projects.acueductosapi.response.UsuarioResponseRest;
import projects.acueductosapi.services.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioResponseRest> buscarUsers() {

        UsuarioResponseRest response = new UsuarioResponseRest();

        try {
            List<User> users = usuarioRepository.findAll();
            response.getUserResponse().setUsers(users);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar Usuarios");
            log.error("error al consultar usuarios: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioResponseRest> buscarPorId(Integer id){
        UsuarioResponseRest response = new UsuarioResponseRest();
        List<User> list = new ArrayList<>();

        try {
            Optional<User> users = usuarioRepository.findById(id);

            if (users.isPresent()) {
                list.add(users.get());
                response.getUserResponse().setUsers(list);
            } else {
                log.error("Error en consultar user");
                response.setMetadata("Respuesta nok", "-1", "user no encontrado");
                return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("Error en consultar user");
            response.setMetadata("Respuesta nok", "-1", "Error al consultar user");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK); //devuelve 200

    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioResponseRest> crear(User user) {
        log.info("Inicio metodo crear User");

        UsuarioResponseRest response = new UsuarioResponseRest();
        List<User> list = new ArrayList<>();

        try {
            // Encriptar la contraseña antes de guardarla
            String passwordEncriptada = encriptarPassword(user.getPassword());

            // Establecer la contraseña encriptada en el objeto User
            user.setPassword("{bcrypt}" +passwordEncriptada);
            user.setEnabled(true);

            User usuarioGuardado = usuarioRepository.save(user);

            if( usuarioGuardado != null) {

                // Crear una autoridad para el nuevo usuario
                Authority authority = new Authority();
                authority.setUser(usuarioGuardado);
                authority.setUsername(usuarioGuardado.getUsername());
                authority.setAuthority("ROLE_USER");

                // Guardar la autoridad en la base de datos
                authorityRepository.save(authority);
            } else {
                log.error("Error en grabar Usuario");
                response.setMetadata("Respuesta nok", "-1", "Usuario no guardado");
                return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch( Exception e) {
            log.error("Error en grabar Usuario ");
            response.setMetadata("Respuesta nok", "-1", "Error al grabar Usuario");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetadata("Respuesta ok", "00", "Usuario creado");
        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    // Método para encriptar la contraseña
    private String encriptarPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioResponseRest> actualizar(User user, Integer id) {

        log.info("Inicio metodo actualizar");

        UsuarioResponseRest response = new UsuarioResponseRest();
        List<User> list = new ArrayList<>();
        String passwordEncriptada = encriptarPassword(user.getPassword());

        // Establecer la contraseña encriptada en el objeto User
        user.setPassword("{bcrypt}" + passwordEncriptada);


        try {

            Optional<User> UsuarioBuscado = usuarioRepository.findById(id);

            if (UsuarioBuscado.isPresent()) {
                UsuarioBuscado.get().setUsername(user.getUsername());
                UsuarioBuscado.get().setDireccion(user.getDireccion());
                UsuarioBuscado.get().setCiudad(user.getCiudad());
                UsuarioBuscado.get().setEmail(user.getEmail());
                UsuarioBuscado.get().setPassword(user.getPassword());
                UsuarioBuscado.get().setEnabled(user.getEnabled());

                User UsuarioActualizar = usuarioRepository.save(UsuarioBuscado.get()); //actualizando

                if( UsuarioActualizar != null ) {
                    response.setMetadata("Respuesta ok", "00", "Usuario actualizado");
                    list.add(UsuarioActualizar);
                    response.getUserResponse().setUsers(list);
                } else {
                    log.error("error en actualizar Country");
                    response.setMetadata("Respuesta nok", "-1", "Usuario no actualizado");
                    return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.BAD_REQUEST);
                }


            } else {
                log.error("error en actualizar Usuario");
                response.setMetadata("Respuesta nok", "-1", "Usuario no actualizado");
                return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch ( Exception e) {
            log.error("error en actualizar Usuario", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Usuario no actualizado");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioResponseRest> eliminar(Integer id) {

        log.info("Inicio metodo eliminar usuario");

        UsuarioResponseRest response = new UsuarioResponseRest();

        try {

            //eliminamos el registro
            usuarioRepository.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "usuario eliminado");

        } catch (Exception e) {
            log.error("error en eliminar usario", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "usuario no eliminado");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK);

    }

}
