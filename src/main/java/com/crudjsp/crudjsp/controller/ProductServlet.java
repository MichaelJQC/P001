package com.crudjsp.crudjsp.controller;

import com.crudjsp.crudjsp.model.Category;
import com.crudjsp.crudjsp.model.Product;
import com.crudjsp.crudjsp.service.CategoryService;
import com.crudjsp.crudjsp.service.ProductService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet({ "/productos/activos", "/productos/inactivos", "/categorias" , "/procesar", "/eliminar", "/restaurar" , "/buscar"})
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();

        protected void service(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String path = request.getServletPath();
            switch (path) {
                case "/productos/activos":
                    listarActivos(request, response);
                    break;
                case "/productos/inactivos":
                    listarInactivos(request, response);
                    break;
                case "/categorias":
                    listarCategorias(request, response);
                    break;
                case "/procesar":
                    procesar(request, response);
                    break;
                case "/eliminar":
                    eliminar(request, response);
                    break;
                case "/restaurar":
                    restaurar(request, response);
                    break;
                case "/buscar":
                    buscarProductos(request, response);
                    break;
            }
        }
    private void buscarProductos(HttpServletRequest request, HttpServletResponse response) {
        try {
            String searchTerm = request.getParameter("searchTerm");
            List<Product> products = productService.searchProducts(searchTerm);
            Gson gson = new Gson();
            String jsonData = gson.toJson(products);
            ControllerUtil.responseJson(response, jsonData);
        } catch (Exception e) {
            ControllerUtil.responseJson(response, "Hubo un error al realizar la búsqueda. Por favor, inténtalo nuevamente.");
        }
    }

        private void eliminar(HttpServletRequest request, HttpServletResponse response) {
            try {
                int productIdToDelete = Integer.parseInt(request.getParameter("productId"));
                productService.delete(productIdToDelete);
                ControllerUtil.responseJson(response, "Producto eliminado correctamente.");
            } catch (Exception e) {
                ControllerUtil.responseJson(response, "Hubo un error al eliminar el producto. Por favor, inténtalo nuevamente.");
            }
        }

        private void restaurar(HttpServletRequest request, HttpServletResponse response) {
            try {
                int productIdToRestore = Integer.parseInt(request.getParameter("productId"));
                productService.restore(productIdToRestore);
                ControllerUtil.responseJson(response, "Producto restaurado correctamente.");
            } catch (Exception e) {
                ControllerUtil.responseJson(response, "Hubo un error al restaurar el producto. Por favor, inténtalo nuevamente.");
            }
        }



    protected void listarActivos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAllActive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(products);
        ControllerUtil.responseJson(resp, jsonData);
    }

    protected void listarInactivos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAllInactive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(products);
        ControllerUtil.responseJson(resp, jsonData);
    }
    protected void listarCategorias(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = categoryService.getAllActive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(categories);
        ControllerUtil.responseJson(resp, jsonData);
    }

    private void procesar(HttpServletRequest request, HttpServletResponse response) {
        // Datos
        String accion = request.getParameter("accion");
        Product bean = new Product();
        bean.setProductId(Integer.parseInt(request.getParameter("productId")));
        bean.setProductName(request.getParameter("productName"));
        bean.setStockQuantity(Integer.parseInt(request.getParameter("amount")));
        bean.setPrice(Double.parseDouble(request.getParameter("price")));
        bean.setCodeProduct(request.getParameter("codeProduct"));
        bean.setActive(request.getParameter("active").charAt(0));

        Category category = new Category();
        category.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        bean.setCategory(category);

        // Proceso
        try {
            switch (accion) {
                case ControllerUtil.CRUD_NUEVO:
                    productService.insert(bean);
                    break;
                case ControllerUtil.CRUD_EDITAR:
                    productService.update(bean);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + accion);
            }

            ControllerUtil.responseJson(response, "Proceso ok.");
        } catch (Exception e) {
            ControllerUtil.responseJson(response, e.getMessage());
        }
    }


}
