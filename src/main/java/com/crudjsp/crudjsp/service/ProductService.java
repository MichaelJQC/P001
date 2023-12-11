package com.crudjsp.crudjsp.service;


import com.crudjsp.crudjsp.db.AccesoDB;
import com.crudjsp.crudjsp.model.Category;
import com.crudjsp.crudjsp.model.Product;
import com.crudjsp.crudjsp.service.spec.CrudServiceSpec;
import com.crudjsp.crudjsp.service.spec.RowMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements CrudServiceSpec<Product>, RowMapper<Product> {
    // Definiendo cosas
    private final String SQL_SELECT_BASE_ACTIVE = "SELECT p.*, c.category_name FROM PRODUCT as p INNER JOIN CATEGORY as c ON (p.category_id = c.category_id) WHERE p.active = 'A' ORDER BY p.product_id ASC";

    private final String SQL_SELECT_BASE_INACTIVE = "SELECT p.*, c.category_name FROM PRODUCT as p INNER JOIN CATEGORY as c ON (p.category_id = c.category_id) WHERE p.active = 'I' ORDER BY p.product_id ASC";

    private final String SQL_INSERT = "INSERT INTO PRODUCT (product_name, price, code_product , stock_quantity, category_id, active) VALUES (?, ? ,  ?, ?, ?, ?)";

    private final String SQL_UPDATE = "UPDATE PRODUCT SET product_name=?, price=?,  code_product =?, stock_quantity=?, active=?, category_id=? WHERE product_id=?";
    private final String SQL_DELETE_LOGICAL = "UPDATE PRODUCT SET active = 'I' WHERE product_id=?";

    private final String SQL_RESTORE = "UPDATE PRODUCT SET active = 'A' WHERE product_id=?";

    @Override
    public List<Product> getAllActive() {
        // Variables
        Connection cn = null;
        List<Product> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Product p;
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
    public List<Product> getAllInactive() {
        // Variables
        Connection cn = null;
        List<Product> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Product p;
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
    public Product getForId(int productId) {
        // Variables
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Product p = null;

        // Proceso
        try {
            cn = AccesoDB.getConnection();

            // Construir la consulta directamente
            String sql = "SELECT p.*, c.category_name FROM PRODUCT as p INNER JOIN CATEGORY as c ON (p.category_id = c.category_id) WHERE p.active = 'A' AND p.product_id = ?";
            pstm = cn.prepareStatement(sql);

            pstm.setLong(1, productId);
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
    public List<Product> searchProducts(String searchTerm) {
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();

        try {
            cn = AccesoDB.getConnection();
            String sql = "SELECT p.*, c.category_name FROM PRODUCT as p INNER JOIN CATEGORY as c ON (p.category_id = c.category_id) WHERE p.active = 'A' AND (LOWER(p.product_name) LIKE ? OR LOWER(p.code_product) LIKE ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, "%" + searchTerm.toLowerCase() + "%");
            pstm.setString(2, "%" + searchTerm.toLowerCase() + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {
                Product product = mapRow(rs);
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
                // Manejo de excepciones en el cierre de la conexión
            }
        }

        return productList;
    }

    @Override
    public List<Product> get(Product bean) {
        return null;
    }

    @Override
    public void insert(Product bean) {
        // Variables
        Connection cn = null;
        PreparedStatement pstm = null;
        // Proceso
        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Insertar nuevo producto
            pstm = cn.prepareStatement(SQL_INSERT);
            pstm.setString(1, bean.getProductName()); // Cambiar a getProductName()
            pstm.setDouble(2, bean.getPrice());
            pstm.setString(3, bean.getCodeProduct());
            pstm.setLong(4, bean.getStockQuantity());
            pstm.setLong(5, bean.getCategory().getCategoryId()); // Cambiar a getCategoryId()
            pstm.setString(6, String.valueOf(bean.getActive()));
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
    public void restore(int productId) {
        // Variables
        Connection cn = null;
        PreparedStatement pstmRestoreLogical = null;

        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Restauración lógica del producto
            pstmRestoreLogical = cn.prepareStatement(SQL_RESTORE);
            pstmRestoreLogical.setLong(1, productId);
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
    public void delete(int productId) {
        // Variables
        Connection cn = null;
        PreparedStatement pstmLogicalDelete = null;
        PreparedStatement pstmRestoreLogical = null;

        try {
            // Iniciar la Tx
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Eliminación lógica del producto
            pstmLogicalDelete = cn.prepareStatement(SQL_DELETE_LOGICAL);
            pstmLogicalDelete.setLong(1, productId);
            pstmLogicalDelete.executeUpdate();
            pstmLogicalDelete.close();

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
    public void update(Product bean) {
        // Variables
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
            pstm.setString(1, bean.getProductName());
            pstm.setDouble(2, bean.getPrice());
            pstm.setString(3, bean.getCodeProduct());
            pstm.setLong(4, bean.getStockQuantity());
            pstm.setString(5, String.valueOf(bean.getActive()));
            pstm.setLong(6, bean.getCategory().getCategoryId()); // Cambiar a getCategoryId()
            pstm.setLong(7, bean.getProductId()); // Cambiar a getProductId()
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
    public Product mapRow(ResultSet rs) throws SQLException {
        Product bean = new Product();
        bean.setProductId(rs.getInt("product_id"));
        bean.setProductName(rs.getString("product_name"));
        bean.setPrice(rs.getDouble("price"));
        bean.setCodeProduct(rs.getString("code_product"));
        // Crear y configurar la categoría
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setActive(String.valueOf(rs.getString("active").charAt(0)));

        // Establecer la categoría en el producto
        bean.setCategory(category);

        bean.setStockQuantity(rs.getInt("stock_quantity"));
        bean.setActive(rs.getString("active").charAt(0));
        return bean;
    }





}
