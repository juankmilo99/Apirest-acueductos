package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import projects.acueductosapi.entities.Product;
import projects.acueductosapi.response.ProductoResponseRest;

public interface ProductoService {

   public ResponseEntity<ProductoResponseRest> buscarProductos(int offset, int pageSize);


    public ResponseEntity<ProductoResponseRest> buscarPorId(Integer id);



    public ResponseEntity<byte[]> mostrarImagen(Integer id);

    public ResponseEntity<ProductoResponseRest> crear(Product product, MultipartFile imageFile);

    public ResponseEntity<ProductoResponseRest> actualizar(Product product, Integer id);



 public ResponseEntity<ProductoResponseRest> buscarPorIdCategory(Integer id_category);

 public ResponseEntity<ProductoResponseRest> eliminar(Integer id);
}
