package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.User;
import projects.acueductosapi.response.UsuarioResponseRest;
import projects.acueductosapi.services.UsuarioService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    public UsuarioService userService;

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

    @PostMapping("")
    public ResponseEntity<UsuarioResponseRest> crearUsuario(@RequestBody User request) {
        ResponseEntity<UsuarioResponseRest> response = userService.crear(request);
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
