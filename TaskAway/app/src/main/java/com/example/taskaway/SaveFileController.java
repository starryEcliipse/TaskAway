package com.example.taskaway;

import android.content.Context;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Is responsible for manipulating user information saved locally in device.
 * @author Punam Woosaree
 * Created on 2018-03-16
 *
 */
public class SaveFileController {
    private ArrayList<User> allUsers; // arraylist of all users currently saved
    private User user;
    private Task task;
    private String saveFile = "test_save_file4.sav"; // local save file

    /**
     * Constructor.
     * Creates new array of all users to be locally saved.
     */
    public SaveFileController() {
        this.allUsers = new ArrayList<User>();
    }

    /**
     * Loads data from file.
     *
     * @param context instance of Context
     */
    private void loadFromFile(Context context) {
        try {
            FileInputStream ifStream = context.openFileInput(saveFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ifStream));
            Gson gson1 = new Gson();
            Type userArrayListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            this.allUsers = gson1.fromJson(bufferedReader, userArrayListType);
            ifStream.close();
        }
        // Create a new array list if a file does not already exist
        catch (FileNotFoundException e) {
            this.allUsers = new ArrayList<User>();
            saveToFile(context);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Saves current ArrayList contents in file.
     *
     * @param context instance of Context
     */
    private void saveToFile(Context context) {
        try {
            FileOutputStream ofStream = context.openFileOutput(saveFile, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(ofStream));
            Gson gson = new Gson();
            gson.toJson(this.allUsers, bufferedWriter);
            bufferedWriter.flush();
            ofStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Adds new user and saves to file.
     *
     * @param context instance of Context
     * @param user    instance of User
     * @see User
     */
    public void addNewUser(Context context, User user) {
        loadFromFile(context);
        this.allUsers.add(user);
        saveToFile(context);
    }

    /**
     * Deletes all user from file.
     *
     * @param context instance of Context
     */
    public void deleteAllUsers(Context context) {
        this.allUsers = new ArrayList<>();
        saveToFile(context);
    }

    /**
     * Gets user index in array.
     *
     * @param context  instance of Context
     * @param username string username
     * @return integer user index if username matches, -1 otherwise
     */
    // TODO: refactor like change to "getUserIndexFromUsername"? - Katherine Mae Patenio
    public int getUserIndex(Context context, String username) {
        loadFromFile(context);
        for (int i = 0; i < this.allUsers.size(); i++) {
            if (this.allUsers.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets user index in array according to the author/creator ID in task
     * Made so that a task can be identified if the task REQUESTER of that task
     * is NOT the CURRENT USER
     *
     * @author Katherine Mae Patenio
     *
     * @param context instance of Context
     * @param creatorID task requester's ID
     * @return integer of user index if Creator ID found; -1 otherwise
     */
    public int getUserIndexFromCreatorID(Context context, String creatorID) {
        loadFromFile(context);
        for (int i = 0; i < this.allUsers.size(); i++) {
            if (this.allUsers.get(i).getId().equals(creatorID)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Updates user information
     *
     * @param context
     * @param userIndex
     * @param user
     */
    public void updateUser(Context context, int userIndex, User user){
        loadFromFile(context);
        this.allUsers.set(userIndex, user);
        saveToFile(context);

    }

    /**
     * Adds a task to  user's required task list.
     *
     * @param context   instance of Context
     * @param userIndex integer user index
     * @param task      instance of Task
     * @see Task
     */
    public void addRequiredTask(Context context, int userIndex, Task task) {
        loadFromFile(context);
        this.allUsers.get(userIndex).getReqTasks().addTask(task);
        saveToFile(context);
    }

    /**
     * Adds a task to user's bidded list.
     *
     * @param context   instance of Context
     * @param userIndex integer user index
     * @param task      instance of Task
     * @see Task
     */
    public void addBiddedTask(Context context, int userIndex, Task task) {
        loadFromFile(context);
        this.allUsers.get(userIndex).getBidTasks().addTask(task);
        saveToFile(context);
    }

    /**
     * Adds a task to user's assigned task list.
     *
     * @param context   instance of Context
     * @param userIndex integer user index
     * @param task      instance of Task
     * @see Task
     */
    public void addAssignedTask(Context context, int userIndex, Task task) {
        loadFromFile(context);
        this.allUsers.get(userIndex).getAssignedTasks().addTask(task);
        saveToFile(context);
    }

    /**
     * Gets User's Required Tasks
     *
     * @param context   instance of Context
     * @param userIndex integer user index
     * @return TaskList
     */
    public TaskList getUserRequiredTasks(Context context, int userIndex) {
        loadFromFile(context);
        return this.allUsers.get(userIndex).getReqTasks();
    }

    /**
     * Gets User's Bidded Tasks
     *
     * @param context   instance of Context
     * @param userIndex integer user index
     * @return TaskList
     */
    public TaskList getUserBiddedTasks(Context context, int userIndex) {
        loadFromFile(context);
        return this.allUsers.get(userIndex).getBidTasks();
    }

    /**
     * Gets User's Assigned Tasks
     *
     * @param context   instance of Context
     * @param userIndex integer user index
     * @return TaskList
     */
    public TaskList getUserAssignedTasks(Context context, int userIndex) {
        loadFromFile(context);
        return this.allUsers.get(userIndex).getAssignedTasks();
    }


    /**
     * Gets all tasks of all users except the current user
     *
     * @param context - instance of Context
     * @param UserIndex - integer user index
     * @return TaskList containing all tasks of other users
     *
     * @see AllBids
     */
    public TaskList getEveryonesTasks(Context context, int UserIndex){
        loadFromFile(context);
        TaskList allTasks = new TaskList();
        for(int i=0; i<allUsers.size(); i++){
            if(i==UserIndex){
                Log.i("im removing you", "nice");
            }
            else{
                TaskList tasklist = this.allUsers.get(i).getReqTasks();
                for (int j = 0; j<tasklist.size(); j++) {
                    allTasks.add(tasklist.getTask(j));
                }
            }
        }
        return allTasks;

    }

    /**
     * Deletes a user's task.
     *
     * @param context - instance of Context
     * @param userIndex - integer user index
     * @param id - id of task
     */
    public void deleteTask(Context context, int userIndex, String id){
        loadFromFile(context);
        User currentuser = this.allUsers.get(userIndex);
        TaskList tasks = currentuser.getReqTasks();
        for(int i=0; i<tasks.size(); i++){
            if(tasks.getTask(i).getId().equals(id)){
                tasks.remove(i);
                break;
            }
            //i++;
        }
        saveToFile(context);
    }

    /**
     * Deletes a task from bidders' tasklist of bidded-on tasks.
     * Is only called when a task requester deletes a task that has a status "bidded".
     *
     * @author Katherine Mae Patenio
     *
     * @param context - instance of Context
     * @param userIndexCreator - user index of task requester
     * @param ID - the task id
     */
    public void deleteTaskBids(Context context, int userIndexCreator, String ID) {
        loadFromFile(context);

        // Read all other users
        for(int i=0; i<allUsers.size(); i++){
            // Ignore recent bidder bidder and task requester
            if((i==userIndexCreator)){ // ignore task requester
                // do nothing
            }
            // Update all other users' list of tasks they've bidded on
            else if (!allUsers.get(i).getBidTasks().isEmpty()){
                TaskList biddedtasks = allUsers.get(i).getBidTasks();

                // For each task
                for (int j=0; j<biddedtasks.size(); j++){
                    Task aTask = biddedtasks.getTask(j);

                    // Update task bids - remove the task
                    if (aTask.getId().equals(ID)){
                        biddedtasks.removeTask(aTask);
                        saveToFile(context);
                        break;
                    } // end of if

                } // end of for loop
            } // end of if
        } // end of for
    }


    /**
     * Retrieves a user's task.
     *
     * @param context - instance of Context
     * @param userIndex - integer user index
     * @param id - id of task
     * @return task
     */
    public Task getTask(Context context, int userIndex, String id) {
        loadFromFile(context);
        User currentuser = this.allUsers.get(userIndex);
        TaskList tasks = currentuser.getReqTasks();
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                task = t;
            }
        }
        return task;
    }

    /**
     * Updates a user's task.
     *
     * @param context - instance of Context
     * @param userIndex - integer user index
     * @param id - id of task
     * @param task - the task itself
     */
    public void updateTask(Context context, int userIndex, String id, Task task){
        loadFromFile(context);
        User currentuser = this.allUsers.get(userIndex);
        TaskList tasks = currentuser.getReqTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.getTask(i).getId().equals(id)) {
                tasks.set(i, task);
            }
        }
        saveToFile(context);
    }

    /**
     * Once a user places a bid on a certain task, bid information
     *
     * @author Katherine Mae Patenio
     *
     * @param context - instance of Context
     * @param userIndexCreator - the index of the Task Requester for a particular requested task
     * @param task - the task that has just been bidded on
     * @param ID - string id of the task itself
     * @param bid - the bid to be applied to the task for all other bidders
     */
    public void updateTaskBids(Context context, int userIndexCreator, Task task, String ID, Bid bid){

        loadFromFile(context);

        // Read all other users
        for(int i=0; i<allUsers.size(); i++){
            // Ignore recent bidder bidder and task requester
            if((i==userIndexCreator)){ // ignore task requester
                // do nothing
            }
            // Update all other users' list of tasks they've bidded on
            else if (!allUsers.get(i).getBidTasks().isEmpty()){
                TaskList biddedtasks = allUsers.get(i).getBidTasks();


                // For each task
                for (int j=0; j<biddedtasks.size(); j++){
                    Task aTask = biddedtasks.getTask(j);

                    // Update task bids
                    if (aTask.getId().equals(ID)){
                        ArrayList<Bid> bidList = aTask.getBids();
                        bidList.add(bid);
                        aTask.setBids(bidList);

                        // updates this task
                        if (biddedtasks.getTask(j).getId().equals(ID)) {
                            biddedtasks.set(j, task);
                        }

                        saveToFile(context);
                    }

                } // end of for loop
            } // end of if
        } // end of for
    } // end of function


    /**
     * Get user information via User class by using username as identifier
     *
     * @param context - instance of Context
     * @param username - username of a user
     * @return User
     */
    public User getUserFromUsername(Context context, String username){
        loadFromFile(context);
        for(int i=0; i<this.allUsers.size(); i++){
            if(this.allUsers.get(i).getUsername().equals(username)){
               user = this.allUsers.get(i);
               break;
            }
        }
        return user;
    }

    /**
     * Get user information via User class by using user ID as identifier
     *
     * @param context - instance of Context
     * @param Id - String ID of the user
     * @return User
     */
    public User getUserFromUserId(Context context, String Id){
        loadFromFile(context);
        for(int i=0; i<this.allUsers.size(); i++){
            if(this.allUsers.get(i).getId().equals(Id)){
                user = this.allUsers.get(i);
                break;
            }
        }
        return user;
    }

    /**
     * Returns Tasklist of all tasks user has not bidded on
     *
     * @param context
     * @param userIndex
     * @return TaskList
     */
    public TaskList getAllTasks(Context context, int userIndex){
        User currentUser = this.allUsers.get(userIndex);
        SaveFileController saveFileController = new SaveFileController();
        TaskList allTasks = saveFileController.getEveryonesTasks(context, userIndex);
        if(currentUser.getBidTasks().size()==0){
            return allTasks;
        }else{
            TaskList userBidTasks = currentUser.getBidTasks();
            for(int i=0; i<allTasks.size();i++){
                for(int j=0; j<userBidTasks.size(); j++){
                    if(allTasks.get(i).getId().equals(userBidTasks.get(j).getId())){
                        allTasks.remove(i);
                    }
                }

            }
        }
        return allTasks;
    }

}