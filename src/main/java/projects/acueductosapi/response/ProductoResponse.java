package projects.acueductosapi.response;

import projects.acueductosapi.entities.Product;



import java.util.List;

public class ProductoResponse {
    private List<Product> productos;

    public List<Product> getProducto() {
        return productos;
    }

    public void setProductos(List<Product> productos) {
        this.productos = productos;
    }
}
