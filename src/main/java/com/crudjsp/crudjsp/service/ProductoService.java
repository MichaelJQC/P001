package com.crudjsp.crudjsp.service;


import com.crudjsp.crudjsp.db.AccesoDB;
import com.crudjsp.crudjsp.model.Categoria;
import com.crudjsp.crudjsp.model.Producto;
import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService  implements CrudServiceSpec<Producto>, RowMapper<Producto> {
    // Definiendo cosas
    private final String SQL_SELECT_BASE = "SELECT p.*, c.nombre as CATEGORIA FROM PRODUCTO as p inner join CATEGORIA as c ON (p.categoria_id = c.id) order by p.id ASC";
    private final String SQL_INSERT = "INSERT INTO PRODUCTO(nombre, codigo, cantidad, precio, active, categoria_id) VALUES(?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE PRODUCTO SET nombre=?, codigo=?, cantidad=?, precio=?, active=?, categoria_id=? WHERE id=?";
    private final String SQL_DELETE = "DELETE FROM PRODUCTO WHERE id=?";


    @Override
    public List<Producto> getAll() {
        // Variables
        Connection cn = null;
        List<Producto> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Producto p;
        // Proceso
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
    public Producto getForId(String id) {
        // Variables
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Producto p = null;
        // Proceso
        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE + " WHERE id = ?");
            pstm.setLong(1, Long.parseLong(id));
            rs = pstm.executeQuery();
            if (rs.next()) {
                p = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return p;
    }

    @Override
    public List<Producto> get(Producto bean) {
        return null;
    }

    @Override
    public void insert(Producto bean) {
        // Variables
        Connection cn = null;
        String sql = null;
        PreparedStatement pstm = null;
        // Proceso
        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Insertar nuevo producto
            pstm = cn.prepareStatement(SQL_INSERT);
            pstm.setString(1, bean.getNombre());
            pstm.setLong(2, bean.getCodigo());
            pstm.setLong(3, bean.getCantidad());
            pstm.setDouble(4, bean.getPrecio());
            pstm.setString(5, bean.getActive());
            pstm.setLong(6, bean.getCategoria().getId());  // Agregando la categoría
            pstm.executeUpdate();
            pstm.close();

            // Fin de Tx
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
    }


    @Override
    public void update(Producto bean) {
        // Variables
        Connection cn = null;
        PreparedStatement pstm = null;
        // Proceso
        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Actualizar producto
            pstm = cn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, bean.getNombre());
            pstm.setLong(2, bean.getCodigo());
            pstm.setLong(3, bean.getCantidad());
            pstm.setDouble(4, bean.getPrecio());
            pstm.setString(5, bean.getActive());
            pstm.setLong(6, bean.getCategoria().getId());  // Agregando la categoría
            pstm.setLong(7, bean.getId());  // Suponiendo que getId() devuelve el ID del producto a actualizar
            pstm.executeUpdate();
            pstm.close();

            // Fin de Tx
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
    }


    @Override
    public void delete(String id) {

    }

    @Override
    public Producto mapRow(ResultSet rs) throws SQLException {
        Producto bean = new Producto();
        bean.setId(rs.getLong("id"));
        bean.setNombre(rs.getString("nombre"));
        bean.setCodigo(rs.getLong("codigo"));
        bean.setCantidad(rs.getLong("cantidad"));
        bean.setPrecio(rs.getDouble("precio"));
        Categoria c = new Categoria();
        c.setId(rs.getLong("categoria_id")); // Corregir el nombre del campo
        c.setNombre(rs.getString("CATEGORIA"));
        bean.setCategoria(c);
        bean.setActive(rs.getString("active"));
        return bean;
    }





}
