package com.crudjsp.crudjsp.service;


import com.crudjsp.crudjsp.model.Department;
import com.crudjsp.crudjsp.model.Province;
import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;
import com.crudjsp.crudjsp.db.AccesoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceService implements CrudServiceSpec<Province>, RowMapper<Province> {
    private final String SQL_SELECT_BASE = "SELECT P.province_id, P.province_name, D.department_id, D.department_name FROM PROVINCE P  JOIN DEPARTMENT D ON P.department_id = D.department_id ORDER BY P.province_id ASC";



    @Override
    public List<Province> getAllActive() {
        Connection cn = null;
        List<Province> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Province p;

        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE);
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
    public List<Province> getAllInactive() {
        return null;
    }

    @Override
    public Province getForId(int id) {
        return null;
    }

    @Override
    public List<Province> get(Province bean) {
        return null;
    }

    @Override
    public void insert(Province bean) {

    }

    @Override
    public void update(Province bean) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void restore(int id) {

    }

    @Override
    public Province mapRow(ResultSet rs) throws SQLException {
        Province bean = new Province();
        bean.setProvinceId(rs.getInt("province_id"));
        bean.setProvinceName(rs.getString("province_name"));

        Department department = new Department();
        department.setDepartmentId(rs.getInt("department_id"));
        department.setDepartmentName(rs.getString("department_name"));
        bean.setDepartment(department);



        return bean;
    }
}
