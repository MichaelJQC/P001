package com.crudjsp.crudjsp.service;

import com.crudjsp.crudjsp.db.AccesoDB;
import com.crudjsp.crudjsp.model.Department;

import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService implements CrudServiceSpec<Department>, RowMapper<Department> {

    private final String SQL_SELECT_BASE = "SELECT * FROM DEPARTMENT order by department_id ASC";
    @Override
    public List<Department> getAllActive() {
        // Variables
        Connection cn = null;
        List<Department> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Department d;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE);
            rs = pstm.executeQuery();
            while (rs.next()) {
                d = mapRow(rs);
                lista.add(d);
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
    public List<Department> getAllInactive() {
        return null;
    }

    @Override
    public Department getForId(int id) {
        return null;
    }

    @Override
    public List<Department> get(Department bean) {
        return null;
    }

    @Override
    public void insert(Department bean) {

    }

    @Override
    public void update(Department bean) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void restore(int id) {

    }

    @Override
    public Department mapRow(ResultSet rs) throws SQLException {
        Department bean = new Department();
        bean.setDepartmentId(rs.getInt("department_id"));
        bean.setDepartmentName(rs.getString("department_name"));
        return bean;
    }
}
