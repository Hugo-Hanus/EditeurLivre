package org.helmo.gbeditor.domains;

import java.util.Map;

/***
 * Class User
 * Person using the Program and creator of book using this program
 */
public class User {

    private final String firstname;
    private final String name;
    private transient Map<String,GameBook> libraryMap;

    /***
     * Object User 
     * @param firstname FirstName of the User
     * @param name Name of the User
     */
    public User(String firstname, String name, Map<String,GameBook> libraryMap){
    this.firstname =firstname;
    this.name =name;
    this.libraryMap=libraryMap;
    }

    /**
     * Get the name of the object User
     * @return String name of the user
     */
    public String getName() {
        return name;
    }

    /***
     * Get the firstname of the object User
     * @return String firstname of the User
     */
    public String getFirstname() {
        return firstname;
    }

    /***
     * Format a User for the view
     * @return String firstname + name
     */
    @Override
    public String toString() {
        return firstname +" "+ name;
    }

    /**
     * Setter of the Map of Gamebook using isbn as a Key
     * @param map Map of Key(isbn) and Value(GameBook Ojbect)
     */
    public void setLibrary(Map<String,GameBook> map){
        this.libraryMap=map;
    }

    /**
     * Check if firstname and name are not empty String
     * @param firstname String firstname
     * @param name String name
     * @return True if Firstname and name are not empty else false
     */
    public boolean checkEntryConnection(String firstname,String name){
        return (!name.trim().isEmpty()) && (!firstname.trim().isEmpty());
    }

    /***
     * Add a GameBook to the Library
     * @param ISBN String of isbn as a KEY
     * @param book Object GameBook as a VALUE
     * @return boolean if the book has been added
     */
    public boolean addGameBook(String ISBN,GameBook book){
        if(!this.libraryMap.containsKey(ISBN)){
            this.libraryMap.put(ISBN,book);
            return true;
        }else{
            return false;
        }
    }

    /***
     *Get Map (String,GameBook) of the Library
     * @return Map(String,GameBook) of the current Library
     */
    public Map<String,GameBook> getLibrary(){
        return this.libraryMap;
    }

    /***
     * Get the GameBook using a search on the isbn
     * @param isbn ISBN use for searching a GameBook;
     * @return Object GameBook
     */
    public GameBook get(String isbn) {
        return this.libraryMap.get(isbn);
    }

    /***
     * Get the size (number of element) in the current Library
     * @return int number of element of the Library
     */
    public int getNumberOfGameBook(){return this.libraryMap.size();}

    /**
     * Format a User to a String for View
     * @return String formatted using a Object User
     */
    public String formatUser(){
        return firstname+"."+name;
    }

    /**
     * Clear all Book Of the user
     */
    public void clearAllGameBook(){
        this.libraryMap.clear();
    }

    /**
     * Delete all Page of a User GameBook using ISBN
     * @param isbn String Isbn of the GameBook
     */
    public void deletePagesOfGameBook(String isbn){
        this.libraryMap.get(isbn).clearPage();
    }

}
