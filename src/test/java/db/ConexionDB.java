package db;

import com.crudjsp.crudjsp.db.AccesoDB;
import java.sql.Connection;

public class ConexionDB {
    public static void main(String[] args) {
        try {
            Connection cn = AccesoDB.getConnection();
            System.out.println("Conexión ok.");
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}