package dongning.potentialdisco;

import dongning.potentialdisco.db.TasksDAO;
import dongning.potentialdisco.todo.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TasksDaoTest {

    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private TasksDAO dao;

    @BeforeEach
    void setUp() throws SQLException{
        // 使用 Mockito 创建模拟的数据库连接和附属对象
        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockRs = mock(ResultSet.class);

        //配置模拟对象的行为
        // 配置模拟 Connection 返回模拟 PreparedStatement
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        // 配置模拟 Connection 返回模拟 Statement
        when(mockConn.createStatement()).thenReturn(mockStmt);
        // 配置模拟 Statement 返回模拟 ResultSet
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        // 配置模拟 Statement 返回模拟更新操作
        when(mockStmt.executeUpdate()).thenReturn(1);//模拟更新操作
        // 配置模拟 ResultSet 行为
        when(mockRs.next()).thenReturn(true).thenReturn(false);

        //配置模拟 ResultSet 的返回值
        when(mockRs.next()).thenReturn(true).thenReturn(false);
        when(mockRs.getInt("id")).thenReturn(1);
        when(mockRs.getString("title")).thenReturn("Test");
        when(mockRs.getString("description")).thenReturn("Description");
        when(mockRs.getDate("due_date")).thenReturn(null);
        when(mockRs.getBoolean("reminder")).thenReturn(true);
        when(mockRs.getBoolean("completed")).thenReturn(false);

        // 初始化被测试的 DAO 对象
        dao = new TasksDAO(mockConn);
    }

    // 测试 TasksDao 的 getAllTasks() 方法
    @Test
    void testGetAllTasks() throws SQLException{
        //调用 getAllTasks 方法并获取结果
        List<Task> tasks = dao.getAllTasks();
        // 断言结果列表不为空且包含一个任务对象
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        //验证返回的任务对象是否符合预期
        Task task = tasks.get(0);
        assertAll(
                () -> assertEquals(1, task.getId()),
                () -> assertEquals("Test", task.getTitle()),
                () -> assertEquals("Description", task.getDescription()),
                () -> assertNull(task.getDue_date()),
                () -> assertTrue(task.getReminder()),
                () -> assertFalse(task.getCompleted())
        );
        //验证是否调用了正确的数据库操作方法
        verify(mockConn).createStatement();
        verify(mockStmt).executeQuery(anyString());
    }

    //测试 addTask 方法
    @Test
    void testAddTask() throws SQLException{
        Date testDate = new Date(System.currentTimeMillis());
        dao.addTask("New Task", "New Description", testDate, true, false);

        //验证是否正确设置了预编译语句的参数
        verify(mockStmt).setString(1, "New Task");
        verify(mockStmt).setString(2, "New Description");
        verify(mockStmt).setDate(3, testDate);
        verify(mockStmt).setBoolean(4, true);
        verify(mockStmt).setBoolean(5, false);

        //验证是否执行了更新
        verify(mockStmt).executeUpdate();
    }

    //测试 updateTask 方法
    @Test
    void testUpdateTask() throws SQLException{
        Date testDate = new Date(System.currentTimeMillis());
        dao.updateTask(1, "Update Task", "Update Description", testDate, true, false);

        //验证是否正确设置了预编译语句的参数
        verify(mockStmt).setString(1, "Update Task");
        verify(mockStmt).setString(2, "Update Description");
        verify(mockStmt).setDate(3, testDate);
        verify(mockStmt).setBoolean(4, true);
        verify(mockStmt).setBoolean(5, false);
        verify(mockStmt).setInt(6, 1);

        //验证是否执行了更新
        verify(mockStmt).executeUpdate();
    }

    //测试 deleteTask 方法
    @Test
    void testDeleteTask() throws SQLException{
        dao.deleteTask(1);

        //验证是否正确设置了预编译语句的参数
        verify(mockStmt).setInt(1, 1);

        //验证是否执行了更新
        verify(mockStmt).executeUpdate();
    }

}
