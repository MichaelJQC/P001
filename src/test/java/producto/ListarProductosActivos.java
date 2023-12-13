package producto;


import com.crudjsp.crudjsp.model.Product;
import com.crudjsp.crudjsp.service.ProductService;

import java.util.List;

public class ListarProductosActivos {
    public static void main(String[] args) {

        // Crear instancia de ProductService
        ProductService productService = new ProductService();

        // Listar productos activos
        System.out.println("Productos Activos:");
        List<Product> productosActivos = productService.getAllActive();
        for (Product producto : productosActivos) {
            System.out.println(producto);
        }
    }
}