package prog2.parcial.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class DBConnection {

    private DBConnection() {
        //
    }

    public static Connection getConnection() throws SQLException {

        try (FileInputStream in = new FileInputStream("config.properties")) {

            Properties props = new Properties();
            props.load(in);

            return DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );

        } catch (IOException e) {
            throw new SQLException(
                    "Error al leer la configuración de la base de datos", e
            );
        }
    }
}
