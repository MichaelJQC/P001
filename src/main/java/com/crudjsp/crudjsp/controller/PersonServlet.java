package com.crudjsp.crudjsp.controller;

import com.crudjsp.crudjsp.model.Person;
import com.crudjsp.crudjsp.service.PersonService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet({ "/personas/activas", "/personas/inactivas", "/procesar-persona", "/eliminar-persona", "/restaurar-persona"})
public class PersonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PersonService personService = new PersonService();


    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/personas/activas":
                listarActivas(request, response);
                break;
            case "/personas/inactivas":
                listarInactivas(request, response);
                break;
            case "/procesar-persona":
                procesarPersona(request, response);
                break;
            case "/eliminar-persona":
                eliminarPersona(request, response);
                break;
            case "/restaurar-persona":
                restaurarPersona(request, response);
                break;
        }
    }


    private void eliminarPersona(HttpServletRequest request, HttpServletResponse response) {
        try {
            int personIdToDelete = Integer.parseInt(request.getParameter("personId"));
            personService.delete(personIdToDelete);
            ControllerUtil.responseJson(response, "Persona eliminada correctamente.");
        } catch (Exception e) {
            ControllerUtil.responseJson(response, "Hubo un error al eliminar la persona. Por favor, inténtalo nuevamente.");
        }
    }

    private void restaurarPersona(HttpServletRequest request, HttpServletResponse response) {
        try {
            int personIdToRestore = Integer.parseInt(request.getParameter("personId"));
            personService.restore(personIdToRestore);
            ControllerUtil.responseJson(response, "Persona restaurada correctamente.");
        } catch (Exception e) {
            ControllerUtil.responseJson(response, "Hubo un error al restaurar la persona. Por favor, inténtalo nuevamente.");
        }
    }

    protected void listarActivas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Person> persons = personService.getAllActive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(persons);
        ControllerUtil.responseJson(resp, jsonData);
    }

    protected void listarInactivas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Person> persons = personService.getAllInactive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(persons);
        ControllerUtil.responseJson(resp, jsonData);
    }

    private void procesarPersona(HttpServletRequest request, HttpServletResponse response) {
        // Datos
        String accion = request.getParameter("accion");
        Person bean = new Person();
        bean.setPersonId(Integer.parseInt(request.getParameter("personId")));
        bean.setFirstName(request.getParameter("firstName"));
        bean.setLastName(request.getParameter("lastName"));
        bean.setPhoneNumber(request.getParameter("phoneNumber"));
        bean.setDateOfBirth(Date.valueOf(request.getParameter("dateOfBirth")));
        bean.setTypeDocument(request.getParameter("typeDocument"));
        bean.setNumberDocument(request.getParameter("numberDocument"));
        bean.setGender(String.valueOf(request.getParameter("gender").charAt(0)));
        bean.setEmail(request.getParameter("email"));
        bean.setActive(request.getParameter("active").charAt(0));



        // Proceso
        try {
            switch (accion) {
                case ControllerUtil.CRUD_NUEVO:
                    personService.insert(bean);
                    break;
                case ControllerUtil.CRUD_EDITAR:
                    personService.update(bean);
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
