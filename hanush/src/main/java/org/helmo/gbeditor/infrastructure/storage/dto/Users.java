package org.helmo.gbeditor.infrastructure.storage.dto;

import org.helmo.gbeditor.domains.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class Users DTO
 */
public class Users {
    private transient final Map<Integer, User> usersMap;

    /**
     * Constructor of DTO Users initialized a Map
     */
    public Users(){
        this.usersMap =new HashMap<>();
    }

    /**
     * Add a User with his ID to the Map
     * @param id Integer ID of User in the DataBase
     * @param user Object User
     */
    public void addUser(int id,User user){
        usersMap.put(id,user);
    }

    /**
     * Get the Iterator of the Map
     * @return Iterator<Map.Entry<Integer,User>>
     */
    public Iterator<Map.Entry<Integer,User>> getIterator(){
        return usersMap.entrySet().iterator();
    }

    /**
     * Check if the Map contains the User
     * @param user Object User
     * @return True if the Map Contains the User else false;
     */
    public boolean contains(User user){
        return usersMap.containsValue(user);
    }

    /**
     *  Get the ID of A Object User using an Object User
     * @param user Object User
     * @return Integer ID of the User if it exists in the Map else -1
     */
    public int getId(User user){
        var values = usersMap.entrySet();
        for(var entry :values){
            if(entry.getValue().equals(user)){
                return entry.getKey();}
        }
        return -1;
    }

    /**
     * Get the Object User using an ID
     * @param id Integer id of the User in DataBase
     * @return
     */
    public User getuser(int id){
        return usersMap.get(id);
    }

    /**
     * Clear the Map
     */
    public void clear() {
        usersMap.clear();
    }
}
