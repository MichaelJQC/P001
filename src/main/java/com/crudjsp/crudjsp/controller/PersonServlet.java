package com.crudjsp.crudjsp.controller;

import com.crudjsp.crudjsp.model.Department;
import com.crudjsp.crudjsp.model.Person;
import com.crudjsp.crudjsp.model.Province;
import com.crudjsp.crudjsp.model.District;
import com.crudjsp.crudjsp.service.DepartmentService;
import com.crudjsp.crudjsp.service.DistrictService;
import com.crudjsp.crudjsp.service.PersonService;
import com.crudjsp.crudjsp.service.ProvinceService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet({ "/personas/activas", "/personas/inactivas", "/departamentos", "/distrito" , "/provincias" ,"/procesar-persona", "/eliminar-persona", "/restaurar-persona"})
public class PersonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PersonService personService = new PersonService();
    private DepartmentService departmentService = new DepartmentService();
    private DistrictService districtService = new DistrictService();
    private ProvinceService provinceService = new ProvinceService();
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
            case "/departamentos":
                listarDepartamentos(request, response);
                break;
            case "/distrito":
                listarDistrito(request, response);
                break;
            case "/provincias":
                listarProvincias(request, response);
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

    protected void listarDepartamentos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> departments = departmentService.getAllActive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(departments);
        ControllerUtil.responseJson(resp, jsonData);
    }
    protected void listarDistrito(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<District> districts = districtService.getAllActive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(districts);
        ControllerUtil.responseJson(resp, jsonData);
    }
    protected void listarProvincias(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Province> provinces  =  provinceService.getAllActive();
        Gson gson = new Gson();
        String jsonData = gson.toJson(provinces);
        ControllerUtil.responseJson(resp, jsonData);
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

        Department department = new Department();
        department.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
        bean.setDepartment(department);

        Province province = new Province();
        province.setProvinceId(Integer.parseInt(request.getParameter("provinceId")));
        bean.setProvince(province);

        District district = new District();
        district.setDistrictId(Integer.parseInt(request.getParameter("districtId")));
        bean.setDistrict(district);

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
