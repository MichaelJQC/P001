package com.crudjsp.crudjsp.service;

import com.crudjsp.crudjsp.db.AccesoDB;
import com.crudjsp.crudjsp.model.Categoria;

import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService implements CrudServiceSpec<Categoria>, RowMapper<Categoria> {

    private final String SQL_SELECT_BASE = "SELECT * FROM CATEGORIA order by id ASC";

    @Override
    public List<Categoria> getAll() {
        // Variables
        Connection cn = null;
        List<Categoria> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Categoria c;
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
    public Categoria getForId(String id) {
        return null;
    }

    @Override
    public List<Categoria> get(Categoria bean) {
        return null;
    }

    @Override
    public void insert(Categoria bean) {

    }

    @Override
    public void update(Categoria bean) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Categoria mapRow(ResultSet rs) throws SQLException {
        Categoria bean = new Categoria();
        bean.setId(rs.getLong("id"));
        bean.setNombre(rs.getString("nombre"));
        bean.setActive(rs.getString("active"));
        return bean;
    }
}
