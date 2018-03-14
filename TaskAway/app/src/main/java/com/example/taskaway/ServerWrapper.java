package com.example.taskaway;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by aschu on 2018-03-14.
 */

public class ServerWrapper {
    /**
     * Sends a new task to the server
     * @param task - the task to add
     */
    public static void addTask(Task task) {
        new ElasticsearchController.AddJobsTask().execute(task);
    }

    /**
     * Sends a new task object to replace the one with the same id
     * @param task - the task to update
     */
    public static void updateTask(Task task) {
        new ElasticsearchController.UpdateJobsTask().execute(task);
    }

    /**
     * Deletes the task with same id from the server
     * @param task - the task to delete
     */
    public static void deleteTask(Task task) {
        new ElasticsearchController.DeleteJobsTask().execute(task);
    }

    public static TaskList getAllJobs() {
        try{
            return new ElasticsearchController.GetJobsTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all jobs from server");
        }
        return null;
    }

    public static TaskList getUserJobs(User user) {
        try{
            return new ElasticsearchController.GetJobsTask().execute("creatorId", user.getId()).get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user's jobs from server");
        }
        return null;
    }

    public static Task getJobFromId(String jobId) {
        try{
            return new ElasticsearchController.GetJobsTask().execute("_id", jobId).get().getTask(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get job with _id from server");
        }
        return null;
    }




    /**
     * Sends a new user to the server
     * @param user - the user to add
     */
    public static void addUser(User user) {
        new ElasticsearchController.AddUsersTask().execute(user);
    }

    /**
     * Sends a new user object to replace the one with the same id
     * @param user - the user to update
     */
    public static void updateUser(User user) {
        new ElasticsearchController.UpdateUsersTask().execute(user);
    }

    /**
     * Deletes the user with same id from the server
     * @param user - the user to delete
     */
    public static void deleteUser(User user) {
        new ElasticsearchController.DeleteUsersTask().execute(user);
    }

    public static ArrayList<User> getAllUsers() {
        try{
            return new ElasticsearchController.GetUsersTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all users from server");
        }
        return null;
    }

    /**
     *
     * @param task - The task for which you want the creator's User object
     * @return the User object of the task creator
     */
    public static ArrayList<User> getTaskUser(Task task) {
        try{
            return new ElasticsearchController.GetUsersTask().execute("_id", task.getCreatorId()).get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get job creator's User object from server");
        }
        return null;
    }

    public static User getUserFromId(String userId) {
        try{
            return new ElasticsearchController.GetUsersTask().execute("_id", userId).get().get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user with _id from server");
        }
        return null;
    }
}
