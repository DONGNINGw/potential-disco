package dongning.potentialdisco.db;

import java.sql.Connection;

public class TrashsDAO {
    private Connection conn;
    public TrashsDAO(Connection conn){
        this.conn = conn;
    }
}
