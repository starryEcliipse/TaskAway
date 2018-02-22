package com.example.taskaway;
import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by SJIsmail on 2018-02-21.
 */
public class TestTaskList extends ActivityInstrumentationTestCase2  {
    public TestTaskList() {super(TaskList.class);}

    /**
     * Test the constructor
     */
    public void testTaskList() {
        Task newTask = new Task("Adam", "Cleaner", "Requested", "Edmonton", null, null, 12.00f);
        TaskList newTaskList = new TaskList();
        newTaskList.addTask(newTask);
        assertEquals(newTaskList.getTask(0), newTask);
    }
    /**
     * Test addTask()
     */
    public void testAddTask() {
        Task newTask = new Task("Adam", "Cleaner", "Requested", "Edmonton", null, null, 12.00f);
        TaskList newTaskList = new TaskList();

        newTaskList.add(newTask);
        assertEquals(newTaskList.size(), 1);
    }

    /**
     * Test hasTask()
     */
    public void testHasTask() {
        Task newTask = new Task("Adam", "Cleaner", "Requested", "Edmonton", null, null, 12.00f);
        TaskList newTaskList = new TaskList();

        assertEquals(newTaskList.contains(newTask), true);
    }

    /**
     * Test getTask()
     */
    public void testgetTask() {
        Task newTask = new Task("Adam", "Cleaner", "Requested", "Edmonton", null, null, 12.00f);
        TaskList newTaskList = new TaskList();
        newTaskList.addTask(newTask);

        assertEquals(newTaskList.getTask(0), newTask );
    }

    /**
     * Test removeTask()
     */
    public void testRemoveTask() {
        Task newTask = new Task("Adam", "Cleaner", "Requested", "Edmonton", null, null, 12.00f);
        TaskList newTaskList = new TaskList();
        newTaskList.addTask(newTask);

        newTaskList.remove(0);
        assertEquals(newTaskList.size(), 0);


    }

}
