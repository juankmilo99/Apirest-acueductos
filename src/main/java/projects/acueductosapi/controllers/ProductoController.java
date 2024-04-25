package projects.acueductosapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projects.acueductosapi.entities.Product;
import projects.acueductosapi.response.ProductoResponseRest;
import projects.acueductosapi.services.ProductoService;

import java.io.IOException;


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
    public ResponseEntity<ProductoResponseRest> crearProducto(@RequestPart("product") String productStr, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productStr, Product.class);
        ResponseEntity<ProductoResponseRest> response = productoService.crear(product, imageFile);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseRest> actualizarProducto(@RequestBody Product request, @PathVariable Integer id) {
        ResponseEntity<ProductoResponseRest> response = productoService.actualizar(request, id);
        return response;
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable Integer id) {
        return productoService.mostrarImagen(id);
    }

    @GetMapping("/category/{id_category}")
    public ResponseEntity<ProductoResponseRest> consultarProductosPorIdCategory(@PathVariable Integer id_category) {
        ResponseEntity<ProductoResponseRest> response = productoService.buscarPorIdCategory(id_category);
        return response;
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseRest> eliminarProducto(@PathVariable Integer id) {
        ResponseEntity<ProductoResponseRest> response = productoService.eliminar(id);
        return response;
    }
}
