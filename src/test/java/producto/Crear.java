package producto;

import com.crudjsp.crudjsp.model.Categoria;
import com.crudjsp.crudjsp.model.Producto;
import com.crudjsp.crudjsp.service.ProductoService;
import com.google.gson.Gson;

public class Crear {
    public static void main(String[] args) {

        // Crear instancia de ProductoService
        ProductoService productoService = new ProductoService();

        // Crear un nuevo producto
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Nuevo Producto 2");
        nuevoProducto.setCodigo(1322L);
        nuevoProducto.setCantidad(50L);
        nuevoProducto.setPrecio(99.99);
        nuevoProducto.setActive("A");

        // Crear una instancia de Categoria y establecerla en el producto
        Categoria categoria = new Categoria();
        categoria.setId(1L); // Suponiendo un ID de categoría válido
        nuevoProducto.setCategoria(categoria);

        // Insertar el nuevo producto
        productoService.insert(nuevoProducto);

        // Imprimir el ID del nuevo producto creado
        System.out.println("Nuevo Producto creado con ID: " + nuevoProducto.getId());

        // Convertir el objeto a formato JSON utilizando Gson
        Gson gson = new Gson();
        String productoJson = gson.toJson(nuevoProducto);

        // Imprimir el objeto en formato JSON
        System.out.println("Producto en formato JSON:\n" + productoJson);
    }
}
