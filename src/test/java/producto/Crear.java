package producto;

import com.crudjsp.crudjsp.model.Brand;
import com.crudjsp.crudjsp.model.Category;
import com.crudjsp.crudjsp.model.Product;
import com.crudjsp.crudjsp.service.ProductService;
import com.google.gson.Gson;

public class Crear {
    public static void main(String[] args) {

        // Crear instancia de ProductoService
        ProductService productService = new ProductService();

        // Crear un nuevo producto
        Product nuevoProduct = getProduct();

        // Insertar el nuevo producto
        productService.insert(nuevoProduct);

        // Imprimir el ID del nuevo producto creado
        System.out.println("Nuevo Producto creado con ID: " + nuevoProduct.getProductId());

        // Convertir el objeto a formato JSON utilizando Gson
        Gson gson = new Gson();
        String productoJson = gson.toJson(nuevoProduct);

        // Imprimir el objeto en formato JSON
        System.out.println("Producto en formato JSON:\n" + productoJson);
    }

    private static Product getProduct() {
        Product nuevoProduct = new Product();
        nuevoProduct.setProductName("Nuevo Producto 2");
        nuevoProduct.setPrice(1322L);
        nuevoProduct.setCodeProduct("12345TREWQ");
        nuevoProduct.setStockQuantity(50);
        nuevoProduct.setPrice(99.99);
        nuevoProduct.setActive('A');

        // Crear una instancia de Categoria y establecerla en el producto
        Category category = new Category();
        category.setCategoryId(1); // Suponiendo un ID de categoría válido
        nuevoProduct.setCategory(category);

        Brand brand = new Brand();
        brand.setBrandId(1); // Suponiendo un ID de categoría válido
        nuevoProduct.setBrand(brand);
        return nuevoProduct;
    }
}
