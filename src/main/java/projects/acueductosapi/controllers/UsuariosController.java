package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.User;
import projects.acueductosapi.response.TokenResponse;
import projects.acueductosapi.response.UsuarioResponseRest;
import projects.acueductosapi.services.Impl.JwtService;
import projects.acueductosapi.services.UsuarioService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    public UsuarioService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;


    @Autowired
    private JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<UsuarioResponseRest> consultarUsuarios() {
        ResponseEntity<UsuarioResponseRest> response = userService.buscarUsers();
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseRest> consultarUsuarioPorId(@PathVariable Integer id) {
        ResponseEntity<UsuarioResponseRest> response = userService.buscarPorId(id);
        return response;
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioResponseRest> buscarUsuarioPorUsername(@PathVariable String username) {
        ResponseEntity<UsuarioResponseRest> response = userService.buscarPorUsername(username);
        return response;
    }

    @PostMapping("")
    public ResponseEntity<?> crearUsuario(@RequestBody User request) {
        // Guardar la contraseña sin encriptar
        String passwordSinEncriptar = request.getPassword();

        ResponseEntity<UsuarioResponseRest> response = userService.crear(request);

        // Si el usuario se creó correctamente
        if (response.getStatusCode() == HttpStatus.OK) {
            // Autenticar al usuario recién creado con la contraseña sin encriptar
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), passwordSinEncriptar)
            );

            // Cargar los detalles del usuario
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Generar un token para el usuario
            final String jwt = jwtService.generateToken(userDetails);

            // Devolver el token en la respuesta
            return ResponseEntity.ok(new TokenResponse(jwt));
        }

        // Si la creación del usuario falló, devolver la respuesta del servicio
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseRest> actualizarUsuario(@RequestBody User request, @PathVariable Integer id) {
        ResponseEntity<UsuarioResponseRest> response = userService.actualizar(request, id);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseRest> eliminarUsuario(@PathVariable Integer id) {
        ResponseEntity<UsuarioResponseRest> response = userService.eliminar(id);
        return response;
    }
}
