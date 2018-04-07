package com.example.taskaway;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Handles interactions with the Elasticsearch server.
 * Wraps around ElasticsearchController to make interactions and method calls more simple.
 *
 * @author Adrian Schuldhaus
 */
public class ServerWrapper {
    /**
     * Sends a new task to the server
     * @param task - the task to add
     */
    public static void addJob(Task task) {
        try{
            new ElasticsearchController.AddJobsTask().execute(task);
            User u = getUserFromId(task.getCreatorId());
            u.addTask(task);
            updateUser(u);
        }catch(Exception e){
            Log.i("ServerWrapper", e.toString());
        }finally{
            new ElasticsearchController.UpdateJobsTask().execute(task); //Ensures id is defined
        }
    }

    /**
     * Sends a new task object to replace the one with the same id
     * @param task - the task to update
     */
    public static void updateJob(Task task) {
        if (task.isDeleted()) {
            deleteJob(task);
        }else{
            try{
                if (task.getBids() != null){
                    for (Bid b : task.getBids()){
                        User u = getUserFromId(b.getUserId());
                        u.addBid(task);
                        updateUser(u);
                    }
                }
                User u = getUserFromId(task.getCreatorId());
                u.addTask(task);
                updateUser(u);
            }catch(Exception e){
                Log.i("ServerWrapper", e.toString());
            }
            try{
                new ElasticsearchController.UpdateJobsTask().execute(task);
            }catch(Exception e){
                Log.i("ServerWrapper", e.toString());
            }
        }
    }

    /**
     * Deletes the task with same id from the server
     * @param task - the task to delete
     */
    public static void deleteJob(Task task) {
        try{
            if (task.getBids() != null){
                for (Bid b : task.getBids()){
                    User u = getUserFromId(b.getUserId());
                    u.removeBid(task);
                    updateUser(u);
                }
            }
            User u = getUserFromId(task.getCreatorId());
            u.removeTask(task);
            updateUser(u);
        }catch(Exception e){
            Log.i("ServerWrapper", e.toString());
        }
        try{
            new ElasticsearchController.DeleteJobsTask().execute(task);
        }catch(Exception e){
            Log.i("ServerWrapper", e.toString());
        }
    }

    /**
     * Fetches all tasks from the server
     * @return A TaskList of all Task objects currently on the server
     */
    public static TaskList getAllJobs() {
        try{
            return new ElasticsearchController.GetJobsTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all jobs from server");
        }
        return null;
    }

    /**
     * Fetches all tasks created by a user from the server
     * @param user - the User to fetch tasks for
     * @return A TaskList of all tasks created by a user
     */
    public static TaskList getUserJobs(User user) {
        try{
            return searchJobs("creatorId", user.getId());
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user's jobs from server");
        }
        return null;
    }

    /**
     * Fetches the task with the provided id from the server
     * @param taskId - the Task's id String
     * @return A Task of all tasks created by a user
     */
    public static Task getJobFromId(String taskId) {
        try{
            return searchJobs("id", taskId).getTask(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get job with id from server");
        }
        return null;
    }

    /**
     * Fetches all tasks matching provided search criteria
     * @param field - the field to search in each Task object
     * @param value - the required value in the field
     * @return A TaskList of all task that have 'value' in the provided 'field'
     */
    public static TaskList searchJobs(String field, String value) {
        try{
            return new ElasticsearchController.GetJobsTask().execute(field, value).get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to search jobs on server");
        }
        return null;
    }

    /**
     * Fetches all tasks for provided keyword(s) in name and description
     * @param term - the search term(s)
     * @return A TaskList of all task that have 'value' in the provided 'field'
     */
    public static TaskList searchJobsTerms(String term) {
        try{
            TaskList nameHits = new ElasticsearchController.GetJobsTask().execute("match_phrase", "name", term).get();
            TaskList descriptionHits = new ElasticsearchController.GetJobsTask().execute("term", "description", term).get();
            TaskList totalHits = descriptionHits;
            for (Task a : nameHits){ //combines search results without duplicating
                boolean duplicate = false;
                for (Task b : totalHits){
                    if (b.getId().equals(a.getId())){
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) totalHits.add(a);
            }
            return totalHits;
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to search jobs for keyword(s) on server");
        }
        return null;
    }




    /**
     * Sends a new user to the server
     * @param user - the user to add
     */
    public static void addUser(User user) {
        try{
            new ElasticsearchController.AddUsersTask().execute(user);
        }catch(Exception e){
        }finally{
            new ElasticsearchController.UpdateUsersTask().execute(user); //Ensures id is defined
        }
    }

    /**
     * Sends a new user object to replace the one with the same id
     * @param user - the user to update
     */
    public static void updateUser(User user) {
        if (user.isDeleted()){
            new ElasticsearchController.DeleteUsersTask().execute(user);
        }else{
            new ElasticsearchController.UpdateUsersTask().execute(user);
        }
    }

    /**
     * Deletes the user with same id from the server
     * @param user - the user to delete
     */
    public static void deleteUser(User user) {
        try{
            if (user.getReqTasks() != null){
                for (Task t : user.getReqTasks()){
                    deleteJob(t);
                }
            }
            if (user.getBidTasks() != null){
                for (Task t : user.getBidTasks()){
                    t.deleteBid(user.getId());
                    updateJob(t);
                }
            }
        }catch(Exception e){
            Log.i("ServerWrapper", e.toString());
        }
        try{
            new ElasticsearchController.DeleteUsersTask().execute(user);
        }catch(Exception e){
            Log.i("ServerWrapper", e.toString());
        }
    }

    /**
     * Fetches all users from the server
     * @return An ArrayList of all User objects on the server
     */
    public static ArrayList<User> getAllUsers() {
        try{
            return new ElasticsearchController.GetUsersTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all users from server");
            Log.i("Exception", e.toString());
        }
        return null;
    }

    /**
     * Fetches the User who created the provided task
     * @param task - The task for which you want the creator's User object
     * @return the User object of the task creator
     */
    public static User getTaskCreator(Task task) {
        try{
            return searchUsers("id", task.getCreatorId()).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get job creator's User object from server");
            Log.i("Exception", e.toString());
        }
        return null;
    }

    /**
     * Fetches the user with the provided id from the server
     * @param userId - the id of the user
     * @return the User object with the provided id
     */
    public static User getUserFromId(String userId) {
        try{
            return searchUsers("id", userId).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user with id from server");
            Log.i("Exception", e.toString());
        }
        return null;
    }

    /**
     * Fetches the user with the provided username from the server
     * @param username - the username of the user
     * @return the User object with the provided username
     */
    public static User getUserFromUsername(String username) {
        try{
            return searchUsers("username", username).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user with username from server");
            Log.i("Exception", e.toString());
        }
        return null;
    }

    /**
     * Fetches all tasks matching provided search criteria
     * @param field - the field to search in each User object
     * @param value - the required value in the field
     * @return A ArrayList of all users that have 'value' in the provided 'field'
     */
    public static ArrayList<User> searchUsers(String field, String value){
        try{
            return new ElasticsearchController.GetUsersTask().execute(field, value).get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to search users on server");
            Log.i("Exception", e.toString());
        }
        return null;
    }

    /**
     * Syncs the local job data with the server
     * @param context - the application context
     * @param userName - userName of the current user
     */
    public static void syncJobs(Context context, String userName) {
        if (!MainActivity.isOnline()) return;

        try{
            SaveFileController saveFileController = new SaveFileController();
            int userIndex = saveFileController.getUserIndex(context, userName);
            TaskList loadedTasks = saveFileController.getUserRequiredTasks(context, userIndex);
            for (Task t : loadedTasks){
                if (t.getSyncInstruction() == null) t.clearSyncInstruction();
                if (t.getSyncInstruction().equals("OFFLINE_ADD")){
                    t.clearSyncInstruction();
                    addJob(t);
                }else if (t.getSyncInstruction().equals("OFFLINE_UPDATE")){
                    t.clearSyncInstruction();
                    updateJob(t);
                }else if (t.getSyncInstruction().equals("OFFLINE_DELETE")){
                    t.clearSyncInstruction();
                    deleteJob(t);
                }else{
                    t.clearSyncInstruction();
                }
                saveFileController.deleteTask(context, userIndex, t.getId());
            }
            //Wait 2 seconds before pulling changes from server
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch(Exception e){
                Log.i("SWrapper SyncJ", "Something happened when trying to stop thread. Aborting.");
                return;
            }
            TaskList userTasks = getUserFromUsername(userName).getReqTasks();
            for (Task t : userTasks){
                saveFileController.addRequiredTask(context, userIndex, t);
            }
        }catch(Exception e){
            Toast.makeText(context, "Something went wrong when trying to sync local jobs with the server", Toast.LENGTH_SHORT).show();
            Log.i("SWrapper SyncJ", e.toString());
        }
    }

    /**
     * Syncs the local user data with the server
     * @param context - the application context
     * @param userName - userName of the current user
     */
    public static void syncUser(Context context, String userName) {
        if (!MainActivity.isOnline()) return;

        try{
            SaveFileController saveFileController = new SaveFileController();
            int userIndex = saveFileController.getUserIndex(context, userName);
            User u = getUserFromUsername(userName);
            saveFileController.updateUser(context, userIndex, u);
        }catch(Exception e){
            Toast.makeText(context, "Something went wrong when trying to sync local user with the server", Toast.LENGTH_SHORT).show();
            Log.i("SWrapper SyncU", e.toString());
        }
    }

    /**
     * Syncs the local data with the server
     * by calling syncJobs(...) followed by syncUser(...)
     * @param context - the application context
     * @param userName - userName of the current user
     */
    public static void syncWithServer(Context context, String userName) {
        if (!MainActivity.isOnline()) return;

        try{
            syncJobs(context, userName);
            syncUser(context, userName);
        }catch(Exception e){
            Toast.makeText(context, "Something went wrong when trying to sync local data with the server", Toast.LENGTH_SHORT).show();
            Log.i("SWrapper Sync", e.toString());
        }
    }

    //For testing purposes only
    public static void deleteAllJobs() {
        TaskList taskList = ServerWrapper.getAllJobs();
        for (Task t : taskList){
            ServerWrapper.deleteJob(t);
        }
        taskList = ServerWrapper.getAllJobs();
        if (taskList.size() > 0) deleteAllJobs();//guarantees the server is wiped
    }

    //For testing purposes only
    public static void deleteAllUsers() {
        ArrayList<User> userList = ServerWrapper.getAllUsers();
        for (User u : userList){
            ServerWrapper.deleteUser(u);
        }
        userList = ServerWrapper.getAllUsers();
        if (userList.size() > 0) deleteAllUsers();//guarantees the server is wiped
    }
}
