package dongning.potentialdisco.todo;

import dongning.todowin.db.TasksDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.Date;

public class TaskUI extends VBox {

    private TableView<Task> taskTable;// 用于展示任务列表的表格
    private TextField titleTextField;// 输入标题的文本字段
    private TextArea descriptionTextArea;// 输入描述的文本区域
    private DatePicker dueDatePicker;// 选择截止日期的日期选择器
    private CheckBox reminderCheckBox;// 勾选提醒的复选框
    private CheckBox completedCheckBox;// 勾选完成状态的复选框
    private TasksDAO tasksDAO;// 与数据库交互的 DAO
    private ObservableList<Task> tasks;// 观察列表，用于更新表格数据

    public TaskUI(TasksDAO tasksDAO){
        this.tasksDAO = tasksDAO;
        this.tasks = FXCollections.observableArrayList();

        //初始化任务列表视图
        initTaskTableView();

        //初始化任务表单
        GridPane taskForm = initTaskForm();

        this.getChildren().addAll(taskTable,taskForm);
        this.setSpacing(10);
        this.setPadding(new Insets(10));


        loadTasks();//加载任务数据
    }

    // 初始化任务列表视图
    private void initTaskTableView(){
        taskTable = new TableView<>();
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //定义表格列
        TableColumn<Task, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Task, Date> dueDateColumn = new TableColumn<>("Due Date");
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("due_Date"));

        TableColumn<Task, Boolean> reminderColumn = new TableColumn<>("Reminder");
        reminderColumn.setCellValueFactory(new PropertyValueFactory<>("reminder"));

        TableColumn<Task, Boolean> completedColumn = new TableColumn<>("Completed");
        reminderColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));

        //添加列到表格
        taskTable.getColumns().addAll(titleColumn, descriptionColumn, dueDateColumn, reminderColumn, completedColumn);

        //设置选中项的监听
        taskTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            if (newSelection != null){
                fillFormWithTask(newSelection);//当用户选择一行时，用该行数据填充表单
            }
        });

        taskTable.setItems(tasks);//将观察列表设置为表格的数据源
    }



    // 填充表单的方法
    private void fillFormWithTask(Task task){
        titleTextField.setText(task.getTitle());
        descriptionTextArea.setText(task.getDescription());
        dueDatePicker.setValue(task.getDue_date().toLocalDate());
        reminderCheckBox.setSelected(task.getReminder());
        completedCheckBox.setSelected(task.getCompleted());
    }
    // 初始化任务表单的方法
    private GridPane initTaskForm(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        titleTextField = new TextField();
        descriptionTextArea = new TextArea();
        dueDatePicker = new DatePicker();
        reminderCheckBox = new CheckBox("Reminder");
        completedCheckBox = new CheckBox("Completed");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> saveTask());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleteTask());

        grid.addRow(0, new Label("Title:"), titleTextField);
        grid.addRow(1, new Label("Description:"), descriptionTextArea);
        grid.addRow(2, new Label("Due Date:"), dueDatePicker);
        grid.addRow(3, reminderCheckBox);
        grid.addRow(4, completedCheckBox);
        grid.addRow(5, saveButton, deleteButton);

        return grid;
    }
    // 保存任务的方法
    private void saveTask(){
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if(selectedTask == null){
            selectedTask = new Task();
        }
        // 从表单收集数据并更新 selectedTask 对象
        selectedTask.setTitle(titleTextField.getText());
        selectedTask.setDescription(descriptionTextArea.getText());
        selectedTask.setDue_date(java.sql.Date.valueOf(dueDatePicker.getValue()));
        selectedTask.setReminder(reminderCheckBox.isSelected());
        selectedTask.setCompleted(completedCheckBox.isSelected());

        try {
            if (selectedTask.getId() > 0) {
                tasksDAO.updateTask(selectedTask.getId(), selectedTask.getTitle(), selectedTask.getDescription(), selectedTask.getDue_date(), selectedTask.getReminder(), selectedTask.getCompleted());
            } else {
                tasksDAO.addTask(selectedTask.getTitle(),selectedTask.getDescription(),selectedTask.getDue_date(),selectedTask.getReminder(),selectedTask.getReminder());// 添加新任务
            }
            loadTasks();// 重新加载任务列表
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: 异常处理，比如显示错误消息
        }
    }
    // 删除任务的方法
    private void deleteTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                tasksDAO.deleteTask(selectedTask.getId());
                loadTasks();// 重新加载任务列表
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: 异常处理，比如显示错误消息
            }
        }
    }
    // 从数据库加载任务的方法
    private void loadTasks(){
        try {
            tasks.setAll(tasksDAO.getAllTasks());// 使用 DAO 获取所有任务并更新观察列表
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: 异常处理，比如显示错误消息
        }
    }
}
