package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Product;

import projects.acueductosapi.repository.ProductoRepository;
import projects.acueductosapi.response.ProductoResponseRest;
import projects.acueductosapi.services.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductoResponseRest> buscarProductos(int offset, int pageSize) {

        ProductoResponseRest response = new ProductoResponseRest();

        try {
            List<Product> productos = productoRepository.findAll(PageRequest.of(offset, pageSize)).getContent();
            response.getProductoResponse().setProductos(productos);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar Productos");
            log.error("error al consultar productos: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductoResponseRest> buscarPorId(Integer id){
        ProductoResponseRest response = new ProductoResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            Optional<Product> products = productoRepository.findById(id);

            if (products.isPresent()) {
                list.add(products.get());
                response.getProductoResponse().setProductos(list);
            } else {
                log.error("Error en consultar pais");
                response.setMetadata("Respuesta nok", "-1", "pais no encontrada");
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("Error en consultar producto");
            response.setMetadata("Respuesta nok", "-1", "Error al consultar producto");
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //devuelve 200

    }
    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> crear(Product country) {
        log.info("Inicio metodo crear Country");

        ProductoResponseRest response = new ProductoResponseRest();
        List<Product> list = new ArrayList<>();

        try {

            Product productGuardado = productoRepository.save(country);

            if( productGuardado != null) {
                list.add(productGuardado);
                response.getProductoResponse().setProductos(list);
            } else {
                log.error("Error en grabar Producto");
                response.setMetadata("Respuesta nok", "-1", "Producto no guardado");
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch( Exception e) {
            log.error("Error en grabar Producto ");
            response.setMetadata("Respuesta nok", "-1", "Error al grabar Producto");
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetadata("Respuesta ok", "00", "Producto creado");
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> actualizar(Product product, Integer id) {

        log.info("Inicio metodo actualizar");

        ProductoResponseRest response = new ProductoResponseRest();
        List<Product> list = new ArrayList<>();

        try {

            Optional<Product> ProductoBuscado = productoRepository.findById(id);

            if (ProductoBuscado.isPresent()) {
                ProductoBuscado.get().setName(product.getName());
                ProductoBuscado.get().setDescription(product.getDescription());
                ProductoBuscado.get().setPrice(product.getPrice());

                Product ProductoActualizar = productoRepository.save(ProductoBuscado.get()); //actualizando

                if( ProductoActualizar != null ) {
                    response.setMetadata("Respuesta ok", "00", "Producto actualizado");
                    list.add(ProductoActualizar);
                    response.getProductoResponse().setProductos(list);
                } else {
                    log.error("error en actualizar Country");
                    response.setMetadata("Respuesta nok", "-1", "Producto no actualizado");
                    return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.BAD_REQUEST);
                }


            } else {
                log.error("error en actualizar Producto");
                response.setMetadata("Respuesta nok", "-1", "Producto no actualizado");
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch ( Exception e) {
            log.error("error en actualizar Producto", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Producto no actualizado");
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> eliminar(Integer id) {

        log.info("Inicio metodo eliminar producto");

        ProductoResponseRest response = new ProductoResponseRest();

        try {

            //eliminamos el registro
            productoRepository.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "producto eliminado");

        } catch (Exception e) {
            log.error("error en eliminar producto", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "producto no eliminado");
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);

    }


}
