package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.User;
import projects.acueductosapi.response.UsuarioResponseRest;

public interface UsuarioService {

    public ResponseEntity<UsuarioResponseRest> buscarUsers();



    public ResponseEntity<UsuarioResponseRest> buscarPorUsername(String username);

    public ResponseEntity<UsuarioResponseRest> buscarPorId(Integer id);


    public ResponseEntity<UsuarioResponseRest> crear(User user);


    public ResponseEntity<UsuarioResponseRest> actualizar(User user, Integer id);


    public ResponseEntity<UsuarioResponseRest> eliminar(Integer id);
}
