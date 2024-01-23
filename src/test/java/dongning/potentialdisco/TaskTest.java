package dongning.potentialdisco;

import dongning.potentialdisco.todo.Task;
import org.junit.jupiter.api.Test;

import java.sql.Date;

public class TaskTest {
    // 测试 Task 类的构造函数和 getter 方法
    @Test
    void  testTaskConstructorAndGetters(){
        // 准备测试数据
        Date testDate = new Date(System.currentTimeMillis());
        // 创建一个 Task 对象
        Task task = new Task(1, "Test", "Description", testDate, true,false);

        // 使用断言测试 Task 对象的属性值是否正确
        assertEquals(1, task.getId());
        assertEquals("Test", task.getTitle());
        assertEquals("Description", task.getDescription());
        assertEquals(testDate, task.getDue_date());
        assertTrue(task.getReminder());
        assertFalse(task.getCompleted());
    }

    // 测试 Task 类的 setter 方法
    @Test
    void testSetters(){
        //创建一个空的 Task 对象
        Task task = new Task();
        Date testDate = new Date(System.currentTimeMillis());

        //设置 Task 对象的属性值
        task.setId(1);
        task.setTitle("Test");
        task.setDescription("Description");
        task.setDue_date(testDate);
        task.setReminder(true);
        task.setCompleted(false);

        // 使用断言测试 Task 对象的属性值是否正确
        assertEquals(1, task.getId());
        assertEquals("Test", task.getTitle());
        assertEquals("Description", task.getDescription());
        assertEquals(testDate, task.getDue_date());
        assertTrue(task.getReminder());
        assertFalse(task.getCompleted());

    }
}
