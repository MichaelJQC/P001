package producto;

import com.crudjsp.crudjsp.model.Product;
import com.crudjsp.crudjsp.service.ProductService;

public class Eliminar {
    public static void main(String[] args) {
        testDeleteProduct();
    }

    public static void testDeleteProduct() {
        ProductService productService = new ProductService();

        // Obtener un producto existente para la prueba (ajustar según sea necesario)
        Product productToDelete = getProductForTest();

        // Imprimir información del producto antes de la eliminación
        System.out.println("Producto antes de la eliminación: " + productToDelete);

        // Realizar la eliminación lógica
        productService.delete(productToDelete.getProductId());

        // Obtener el producto después de la eliminación
        Product deletedProduct = productService.getForId(productToDelete.getProductId());

        // Imprimir información del producto después de la eliminación
        System.out.println("Producto después de la eliminación: " + deletedProduct);
    }

    // Método de utilidad para obtener un producto para la prueba (ajustar según sea necesario)
    private static Product getProductForTest() {
        // Puedes obtener un producto existente de la base de datos o crear uno nuevo para la prueba
        // Asegúrate de ajustar esto según la estructura y lógica de tu aplicación
        Product product = new Product();
        product.setProductId(1); // ID del producto existente
        return product;
    }
}
