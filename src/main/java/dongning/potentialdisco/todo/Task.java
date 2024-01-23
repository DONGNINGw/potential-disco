package dongning.potentialdisco.todo;

import java.sql.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private Date due_date;
    private boolean reminder;
    private boolean completed;

    // 无参构造函数
    public Task(){
    }
    // 全参构造函数
    public Task(int id, String title, String description, Date due_date, boolean reminder, boolean completed){
        this.id = id;
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.reminder = reminder;
        this.completed = completed;
    }



    //Getters
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Date getDue_date(){
        return due_date;
    }
    public boolean getReminder(){
        return reminder;
    }
    public boolean getCompleted(){
        return completed;
    }
    //Setters
    public void setId(int id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setDue_date(Date due_date){
        this.due_date = due_date;
    }
    public void setReminder(boolean reminder){
        this.reminder = reminder;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }

}
