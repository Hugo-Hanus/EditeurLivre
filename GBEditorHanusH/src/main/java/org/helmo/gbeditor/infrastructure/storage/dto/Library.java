package org.helmo.gbeditor.infrastructure.storage.dto;

import org.helmo.gbeditor.domains.GameBook;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class Library Get a Collection of GameBook related on  his ID
 */
public class Library {
    private  Map<Integer, GameBook> libraryMap;

    /**
     * Constructor of the Library
     */
    public Library(){
        this.libraryMap =new HashMap<>();
    }

    /**
     * Add the GameBook and his ID the Map
     * @param id Integer ID of the GameBook in the DataBase
     * @param gamebook Object GameBook
     */
    public void addGamebook(int id,GameBook gamebook){
        libraryMap.put(id,gamebook);
    }

    /**
     * Check if the Map contains the GameBook
     * @param gamebook Object GameBook
     * @return True if the Map contains the GameBook else false
     */
    public boolean contains(GameBook gamebook){
    return libraryMap.containsValue(gamebook);
    }

    /**
     * Get the Iterator of the Map
     * @return Iterator Iterator<Map.Entry<Integer,GameBook>>
     */
    public Iterator<Map.Entry<Integer,GameBook>> getIterator(){
        return libraryMap.entrySet().iterator();
    }

    /**
     * Get the ID of the GameBook if it is in the Map
     * @param gamebook Object GameBook
     * @return Integer the ID of the GameBook if the Gamebook is not in the Map return -1
     */
    public int getId(GameBook gamebook){
        var values= libraryMap.entrySet();
        for(var entry :values){
            if(entry.getValue().equals(gamebook)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Get the ID of a gamebook how contains this isbn
     * @param isbn String isbn of the GameBook
     * @return Int the id of the book if the library doesn't contain it return -1
     */
    public int getGameBookIdByIsbn(String isbn){
        var values= libraryMap.entrySet();
        for(var entry :values){
            if(entry.getValue().getCover().getIsbn().equals(isbn)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Get the gamebook how contains this isbn
     * @param isbn String isbn of the GameBook
     * @return Object GameBook the book if the library doesn't contain it return null
     */
    public GameBook getGameBookByIsbn(String isbn){
        var values= libraryMap.entrySet();
        for(var entry :values){
            if(entry.getValue().getCover().getIsbn().equals(isbn)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Get the GameBook in the Map using an Integer ID
     * @param id Integer id of the GameBook
     * @return Object GameBook
     */
    public GameBook get(int id){
        return libraryMap.get(id);
    }


    /**
     * Delete all the object int the Map
     */
    public void clear(){
        libraryMap.clear();
    }


    public Map<Integer, GameBook> getLibraryMap() {
        return libraryMap;
    }

    public void setLibraryMap(Map<Integer, GameBook> libraryMap) {
        this.libraryMap = libraryMap;
    }
}
