package com.example.taskaway;

import java.util.ArrayList;

/**
 * Created by sameerah on 18/02/18.
 */

public class TaskList extends ArrayList<Task> {

    private ArrayList<Task> reqTask;

    public void addTask(Task task){
        reqTask.add(task);

    }

    public boolean hasTask(Task task){
        return reqTask.contains(task);
    }

    public Task getTask(int index){
        return reqTask.get(index);
    }

    public void removeTask(Task task){
        reqTask.remove(task);
    }

}
