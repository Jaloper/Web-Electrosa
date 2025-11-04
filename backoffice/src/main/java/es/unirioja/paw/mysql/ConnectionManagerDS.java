package es.unirioja.paw.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManagerDS {

    private static final String NOMBRE_DE_DATA_SOURCE = "java:comp/env/jdbc/electrosa";
    private static DataSource ds;
    private static final String URL = "jdbc:mysql://localhost:3306/electrosa";
    private static final String USR = "root";
    private static final String PWD = "root";

    static {
        try {
		// Variable static para contener la referencia al DataSource, que se inicializa cuando se
		// carga esta clase
            ds = getDataSource();
        } catch (NamingException ex) {
            Logger.getLogger(ConnectionManagerDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static DataSource getDataSource() throws NamingException {
        DataSource ds = null;
		//Obtener el DataSource del servidor de aplicaciones
        ds = (DataSource) new InitialContext().lookup(NOMBRE_DE_DATA_SOURCE);
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        if(ds!=null)
        return ds.getConnection();
        else {
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: No se encontró el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USR, PWD);
        }
    }

    public static void returnConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

}
