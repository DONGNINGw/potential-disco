package dongning.potentialdisco.db;



import dongning.potentialdisco.todo.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TasksDAO {
    private Connection conn;
    public TasksDAO(Connection conn){
        this.conn = conn;
    }

    //Get all tasks from the database
    public List<Task> getAllTasks() throws SQLException{
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM Tasks";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                // 创建 Task 对象并添加到列表
                Task task = new Task(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("due_date"),
                        rs.getBoolean("reminder"),
                        rs.getBoolean("is_done")
                );
                taskList.add(task);
            }
        }
        return taskList;
    }

    //Add a task to the database
    public void addTask(String title, String description, Date dueDate, boolean reminder, boolean isDone) throws SQLException {
        String sql = "INSERT INTO Tasks (title, description, due_date, reminder, is_done) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setDate(3, dueDate);
            pstmt.setBoolean(4, reminder);
            pstmt.setBoolean(5, isDone);
            pstmt.executeUpdate();
        }
    }
    //Update a task in the database
    public void updateTask(int id, String title, String description, Date dueDate, boolean reminder, boolean isDone) throws SQLException {
        String sql = "UPDATE Tasks SET title = ?, description = ?, due_date = ?, reminder = ?, is_done = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setDate(3, dueDate);
            pstmt.setBoolean(4, reminder);
            pstmt.setBoolean(5, isDone);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        }
    }
    //Delete a task from the database
    public void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM Tasks WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
