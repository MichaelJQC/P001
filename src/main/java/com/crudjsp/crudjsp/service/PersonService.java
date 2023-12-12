package com.crudjsp.crudjsp.service;

import com.crudjsp.crudjsp.db.AccesoDB;
import com.crudjsp.crudjsp.model.*;
import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonService implements CrudServiceSpec<Person>, RowMapper<Person> {
    private final String SQL_SELECT_BASE_ACTIVE = "SELECT " +
            "p.person_id, p.first_name, p.last_name, p.phone_number, p.date_of_birth, p.type_document, " +
            "p.number_document, p.gender, p.email, p.active ,d.department_id, d.department_name, " +
            "pr.province_id, pr.province_name, di.district_id, di.district_name " +
            "FROM PERSON AS p " +
            "INNER JOIN DEPARTMENT AS d ON (p.department_id = d.department_id) " +
            "INNER JOIN PROVINCE AS pr ON (p.province_id = pr.province_id) " +
            "INNER JOIN DISTRICT AS di ON (p.district_id = di.district_id) " +
            "WHERE p.active = 'A'";

    private final String SQL_SELECT_BASE_INACTIVE = "SELECT " +
            "p.person_id, p.first_name, p.last_name, p.phone_number, p.date_of_birth, p.type_document, " +
            "p.number_document, p.gender, p.email, p.active, d.department_id, d.department_name, " +
            "pr.province_id, pr.province_name, di.district_id, di.district_name " +
            "FROM PERSON AS p " +
            "INNER JOIN DEPARTMENT AS d ON (p.department_id = d.department_id) " +
            "INNER JOIN PROVINCE AS pr ON (p.province_id = pr.province_id) " +
            "INNER JOIN DISTRICT AS di ON (p.district_id = di.district_id) " +
            "WHERE p.active = 'I'";

    private final String SQL_INSERT_PERSON = "INSERT INTO PERSON " +
            "(first_name, last_name, phone_number, date_of_birth, type_document, number_document, " +
            "gender, email, department_id, province_id, district_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String SQL_UPDATE_PERSON = "UPDATE PERSON SET " +
            "first_name=?, last_name=?, phone_number=?, date_of_birth=?, type_document=?, " +
            "number_document=?, gender=?, email=?, department_id=?, province_id=?, district_id=? " +
            "WHERE person_id=?";

    private final String SQL_DELETE_PERSON_LOGICAL = "UPDATE PERSON SET active = 'I' WHERE person_id=?";

    private final String SQL_RESTORE_PERSON = "UPDATE PERSON SET active = 'A' WHERE person_id=?";

    @Override
    public List<Person> getAllActive() {
        // Variables
        Connection cn = null;
        List<Person> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Person p;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE_ACTIVE);
            rs = pstm.executeQuery();
            while (rs.next()) {
                p = mapRow(rs);
                lista.add(p);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
        return lista;
    }

    @Override
    public List<Person> getAllInactive() {
        // Variables
        Connection cn = null;
        List<Person> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Person p;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE_INACTIVE);
            rs = pstm.executeQuery();
            while (rs.next()) {
                p = mapRow(rs);
                lista.add(p);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
        return lista;
    }

    @Override
    public Person getForId(int personId) {
        return null;
    }

    @Override
    public List<Person> get(Person bean) {
        return null;
    }

    @Override
    public void insert(Person bean) {
        // Variables
        Connection cn = null;
        PreparedStatement pstm = null;

        // Proceso
        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Insertar nueva persona
            pstm = cn.prepareStatement(SQL_INSERT_PERSON);
            pstm.setString(1, bean.getFirstName());
            pstm.setString(2, bean.getLastName());
            pstm.setString(3, bean.getPhoneNumber());
            pstm.setDate(4, bean.getDateOfBirth());
            pstm.setString(5, bean.getTypeDocument());
            pstm.setString(6, bean.getNumberDocument());
            pstm.setString(7, bean.getGender());
            pstm.setString(8, bean.getEmail());
            pstm.setInt(9, bean.getDepartment().getDepartmentId());
            pstm.setInt(10, bean.getProvince().getProvinceId());
            pstm.setInt(11, bean.getDistrict().getDistrictId());
            pstm.executeUpdate();
            pstm.close();

            // Fin de Tx
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
                // Manejo de excepciones en el cierre de la conexión
            }
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
                // Manejo de excepciones en el cierre de la conexión
            }
        }
    }

    @Override
    public void update(Person bean) {
        // Variables
        Connection cn = null;
        PreparedStatement pstm = null;

        // Proceso
        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Actualizar persona
            pstm = cn.prepareStatement(SQL_UPDATE_PERSON);
            pstm.setString(1, bean.getFirstName());
            pstm.setString(2, bean.getLastName());
            pstm.setString(3, bean.getPhoneNumber());
            pstm.setDate(4, bean.getDateOfBirth());
            pstm.setString(5, bean.getTypeDocument());
            pstm.setString(6, bean.getNumberDocument());
            pstm.setString(7, bean.getGender());
            pstm.setString(8, bean.getEmail());
            pstm.setInt(9, bean.getDepartment().getDepartmentId());
            pstm.setInt(10, bean.getProvince().getProvinceId());
            pstm.setInt(11, bean.getDistrict().getDistrictId());
            pstm.setInt(12, bean.getPersonId());
            pstm.executeUpdate();
            pstm.close();

            // Fin de Tx
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
                // Manejo de excepciones en el cierre de la conexión
            }
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
                // Manejo de excepciones en el cierre de la conexión
            }
        }
    }


    @Override
    public void delete(int personId) {

    }

    @Override
    public void restore(int personId) {
        // Variables
        Connection cn = null;
        PreparedStatement pstmRestoreLogical = null;

        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Restauración lógica de la persona
            pstmRestoreLogical = cn.prepareStatement(SQL_RESTORE_PERSON);
            pstmRestoreLogical.setInt(1, personId);
            pstmRestoreLogical.executeUpdate();
            pstmRestoreLogical.close();

            // Fin de Tx
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
                // Manejo de excepciones en el cierre de la conexión
            }
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
                // Manejo de excepciones en el cierre de la conexión
            }
        }
    }
    @Override
    public Person mapRow(ResultSet rs) throws SQLException {
        Person bean = new Person();
        bean.setPersonId(rs.getInt("person_id"));
        bean.setFirstName(rs.getString("first_name"));
        bean.setLastName(rs.getString("last_name"));
        bean.setPhoneNumber(rs.getString("phone_number"));
        bean.setDateOfBirth(rs.getDate("date_of_birth"));
        bean.setTypeDocument(rs.getString("type_document"));
        bean.setNumberDocument(rs.getString("number_document"));
        bean.setGender(rs.getString("gender"));
        bean.setEmail(rs.getString("email"));

        // Configuración de relaciones
        Department department = new Department();
        department.setDepartmentId(rs.getInt("department_id"));
        department.setDepartmentName(rs.getString("department_name"));
        bean.setDepartment(department);

        Province province = new Province();
        province.setProvinceId(rs.getInt("province_id"));
        province.setProvinceName(rs.getString("province_name"));
        bean.setProvince(province);

        District district = new District();
        district.setDistrictId(rs.getInt("district_id"));
        district.setDistrictName(rs.getString("district_name"));
        bean.setDistrict(district);
        bean.setActive(rs.getString("active").charAt(0));
        return bean;
    }

}
