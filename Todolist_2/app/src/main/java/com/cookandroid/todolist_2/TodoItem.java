package com.cookandroid.todolist_2;


public class TodoItem {
    private String todo;
    private boolean checked;

    public TodoItem(String todo, boolean checked) {
        this.todo = todo;
        this.checked = checked;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
