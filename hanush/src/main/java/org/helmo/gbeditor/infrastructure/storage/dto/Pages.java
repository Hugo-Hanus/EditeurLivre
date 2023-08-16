package org.helmo.gbeditor.infrastructure.storage.dto;

import org.helmo.gbeditor.domains.Page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class DTO Pages
 */
public class Pages {
    private  final Map<Integer, Page> pagesMap;

    /**
     * Contructor of the Object Pages (instancied HashMaps)
     */
    public Pages() {
        this.pagesMap = new HashMap<>();
    }


    public Map<Integer, Page> getPagesMap() {
        return pagesMap;
    }

    /**
     * Add a Page to HashMap
     * @param id ID of The Page (ID in DATABASE)
     * @param page Object Page
     */
    public void addPages(int id, Page page) {
        pagesMap.put(id, page);
    }

    /**
     * Check if the HashMap contains the Object Page
     * @param page Object Page
     * @return True if the HashMap contains the object Page else false
     */
    public boolean contains(Page page) {
        return pagesMap.containsValue(page);
    }

    /**
     * Count every Pages in the map
     * @return Int number of page in the PageMap
     */
    public int count(){
        return pagesMap.size();
    }
    /**
     * Get the iterator of the HashMap
     * @return Iterator<Map.Entry<Integer, Page>> iterator of the map
     */
    public Iterator<Map.Entry<Integer, Page>> getIterator() {
        return pagesMap.entrySet().iterator();
    }

    /**
     * Get the ID of the page contains in the HashMap
     * @param page Object Page
     * @return Integer ID of the Page if it presents in the HashMap else return -1
     */
    public int getId(Page page) {
        var values = pagesMap.entrySet();
        for (var entry : values) {
            if (entry.getValue().equals(page)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Get the id Of the Page with the number
     * @param number Int number of the Page research
     * @return Int id of the page
     */
    public int getIdOfPageNumber(int number){
        var values = pagesMap.entrySet();
        for (var entry : values) {
            if (entry.getValue().getNumero()==number) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Get the Page of the collection using his ID (integer)
     * @param id Integer ID of the Page (id in the DataBase)
     * @return Object Page
     */
    public Page get(int id) {
        return pagesMap.get(id);
    }

    /**
     * Clear all pages in the map
     */
    public void clear(){
        pagesMap.clear();
    }

    /**
     * Check if the MapPages contains the number of a page
     * @param number Int number of a page
     * @return True if he is in the map else False
     */
    public boolean containsNumber(int number){
        var values = pagesMap.entrySet();
        for (var entry : values) {
            if((entry.getValue().getNumero()==number)){
                return true;
            }
        }
      return false;
    }


}
