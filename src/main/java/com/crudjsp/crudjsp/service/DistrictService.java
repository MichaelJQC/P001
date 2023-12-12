package com.crudjsp.crudjsp.service;

import com.crudjsp.crudjsp.db.AccesoDB;

import com.crudjsp.crudjsp.model.Department;
import com.crudjsp.crudjsp.model.District;

import com.crudjsp.crudjsp.model.Province;
import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictService implements CrudServiceSpec<District>, RowMapper<District> {

    private final String SQL_SELECT_BASE = "SELECT P.province_id, P.province_name, DT.district_id, DT.district_name FROM PROVINCE P JOIN DISTRICT DT ON DT.district_id = DT.district_id ORDER BY P.province_id ASC;";
    @Override
    public List<District> getAllActive() {
        // Variables
        Connection cn = null;
        List<District> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        District d;
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
    public List<District> getAllInactive() {
        return null;
    }

    @Override
    public District getForId(int id) {
        return null;
    }

    @Override
    public List<District> get(District bean) {
        return null;
    }

    @Override
    public void insert(District bean) {

    }

    @Override
    public void update(District bean) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void restore(int id) {

    }

    @Override
    public District mapRow(ResultSet rs) throws SQLException {
        District bean = new District();
        bean.setDistrictId(rs.getInt("district_id"));
        bean.setDistrictName(rs.getString("district_name"));

        // Configuración de relaciones
        Province province = new Province();
        province.setProvinceId(rs.getInt("province_id"));
        bean.setProvince(province);
        return bean;
    }
}
