package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.Product;
import projects.acueductosapi.response.ProductoResponseRest;
import projects.acueductosapi.services.ProductoService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    public ProductoService productoService;

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<ProductoResponseRest> consultarProductos(@PathVariable int offset, @PathVariable int pageSize) {
        ResponseEntity<ProductoResponseRest> response = productoService.buscarProductos(offset, pageSize);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseRest> consultarProductoPorId(@PathVariable Integer id) {
        ResponseEntity<ProductoResponseRest> response = productoService.buscarPorId(id);
        return response;
    }

    @PostMapping("")
    public ResponseEntity<ProductoResponseRest> crearProducto(@RequestBody Product request) {
        ResponseEntity<ProductoResponseRest> response = productoService.crear(request);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseRest> actualizarProducto(@RequestBody Product request, @PathVariable Integer id) {
        ResponseEntity<ProductoResponseRest> response = productoService.actualizar(request, id);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseRest> eliminarProducto(@PathVariable Integer id) {
        ResponseEntity<ProductoResponseRest> response = productoService.eliminar(id);
        return response;
    }
}
