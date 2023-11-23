package com.crudjsp.crudjsp.controller;

import com.crudjsp.crudjsp.model.Categoria;
import com.crudjsp.crudjsp.model.Producto;
import com.crudjsp.crudjsp.service.CategoriaService;
import com.crudjsp.crudjsp.service.ProductoService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({ "/productos", "/categorias" , "/procesar" })
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductoService productoService = new ProductoService();
    private CategoriaService categoriaService = new CategoriaService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/productos":
                listarActivos(request, response);
                break;
            case "/categorias":
                listarCategorias(request, response);
                break;
            case "/procesar":
                procesar(request, response);
                break;

        }
    }
    protected void listarActivos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Producto> productos = productoService.getAll();
        Gson gson = new Gson();
        String jsonData = gson.toJson(productos);
        ControllerUtil.responseJson(resp, jsonData);
    }

    protected void listarCategorias(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Categoria> categorias = categoriaService.getAll();
        Gson gson = new Gson();
        String jsonData = gson.toJson(categorias);
        ControllerUtil.responseJson(resp, jsonData);
    }
    private void procesar(HttpServletRequest request, HttpServletResponse response) {
        // Datos
        String accion = request.getParameter("accion");
        Producto bean = new Producto();
        bean.setId(Long.parseLong(request.getParameter("id")));
        bean.setNombre(request.getParameter("nombre"));
        bean.setCodigo(Long.parseLong(request.getParameter("codigo")));
        bean.setCantidad(Long.parseLong(request.getParameter("cantidad")));
        bean.setPrecio(Double.parseDouble(request.getParameter("precio")));
        bean.setActive(request.getParameter("active"));

        Categoria categoria = new Categoria();
        categoria.setId(Long.parseLong(request.getParameter("categoria_id"))); // Asegúrate de que el nombre del parámetro sea correcto
        bean.setCategoria(categoria);

        // Proceso
        try {
            switch (accion) {
                case ControllerUtil.CRUD_NUEVO:
                    productoService.insert(bean);
                    break;
                case ControllerUtil.CRUD_EDITAR:
                    productoService.update(bean);
                    break;
                case ControllerUtil.CRUD_ELIMINAR:
                    productoService.delete(String.valueOf(bean.getId()));
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





