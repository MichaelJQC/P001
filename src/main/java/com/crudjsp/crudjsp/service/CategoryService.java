package com.crudjsp.crudjsp.service;

import com.crudjsp.crudjsp.db.AccesoDB;
import com.crudjsp.crudjsp.model.Category;

import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements CrudServiceSpec<Category>, RowMapper<Category> {

    private final String SQL_SELECT_BASE = "SELECT * FROM CATEGORY order by category_id ASC";

    @Override
    public List<Category> getAllActive() {
        // Variables
        Connection cn = null;
        List<Category> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Category c;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE);
            rs = pstm.executeQuery();
            while (rs.next()) {
                c = mapRow(rs);
                lista.add(c);
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
    public List<Category> getAllInactive() {
        return null;
    }

    @Override
    public Category getForId(int id) {
        return null;
    }

    @Override
    public List<Category> get(Category bean) {
        return null;
    }

    @Override
    public void insert(Category bean) {

    }

    @Override
    public void update(Category bean) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void restore(int id) {

    }

    @Override
    public Category mapRow(ResultSet rs) throws SQLException {
        Category bean = new Category();
        bean.setCategoryId(rs.getInt("category_id"));
        bean.setCategoryName(rs.getString("category_name"));
        bean.setActive(rs.getString("active"));
        return bean;
    }
}
