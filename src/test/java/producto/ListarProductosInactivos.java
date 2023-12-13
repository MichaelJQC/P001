package producto;


import com.crudjsp.crudjsp.model.Product;
import com.crudjsp.crudjsp.service.ProductService;

import java.util.List;

public class ListarProductosInactivos {
    public static void main(String[] args) {

        // Crear instancia de ProductService
        ProductService productService = new ProductService();

        // Listar productos inactivos
        System.out.println("Productos Inactivos:");
        List<Product> productosInactivos = productService.getAllInactive();
        for (Product producto : productosInactivos) {
            System.out.println(producto);
        }
    }
}