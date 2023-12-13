package com.crudjsp.crudjsp.service;

import com.crudjsp.crudjsp.db.AccesoDB;
import com.crudjsp.crudjsp.model.Brand;
import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandService implements CrudServiceSpec<Brand>, RowMapper<Brand> {

    private final String SQL_SELECT_BASE = "SELECT * FROM BRAND order by brand_id ASC";

    @Override
    public List<Brand> getAllActive() {
        // Variables
        Connection cn = null;
        List<Brand> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Brand b;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE);
            rs = pstm.executeQuery();
            while (rs.next()) {
                b = mapRow(rs);
                lista.add(b);
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
    public List<Brand> getAllInactive() {
        return null;
    }

    @Override
    public Brand getForId(int id) {
        return null;
    }

    @Override
    public List<Brand> get(Brand bean) {
        return null;
    }

    @Override
    public void insert(Brand bean) {

    }

    @Override
    public void update(Brand bean) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void restore(int id) {

    }

    @Override
    public Brand mapRow(ResultSet rs) throws SQLException {
        Brand bean = new Brand();
        bean.setBrandId(rs.getInt("brand_id"));
        bean.setBrandName(rs.getString("brand_name"));
        bean.setActive(rs.getString("active"));
        return bean;
    }
}
