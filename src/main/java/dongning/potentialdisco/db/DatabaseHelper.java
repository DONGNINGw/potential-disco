package dongning.potentialdisco.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:todo_db.db";

    public static Connection connect(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DB_URL);// Initialise the database (run schema.sql)
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
