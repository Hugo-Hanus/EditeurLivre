package org.helmo.gbeditor.infrastructure.storage.dto;

import org.helmo.gbeditor.domains.Choice;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class DTO Choices
 */
public class Choices {
    private transient final Map<Integer, Choice> choicesMap;

    /**
     * Constructor of the DTO Choices
     */
    public Choices(){
        this.choicesMap =new HashMap<>();
    }

    /**
     * Add Choice to the Map with his ID
     * @param id Integer id of the Choice in the DataBase
     * @param choice  Object Choices
     */
    public void addChoice(int id,Choice choice){
        this.choicesMap.put(id,choice);
    }

    /**
     * Check if the Map contains or not the Choice
     * @param choice Object Choice
     * @return True if the Map contains the Choice else false
     */
    public boolean contains(Choice choice){
        return this.choicesMap.containsValue(choice);
    }

    /**
     *  Get the Iterator of the Map
     * @return Iterator Iterator<Map.Entry<Integer,Choice>>
     */
    public Iterator<Map.Entry<Integer,Choice>> getIterator(){
        return this.choicesMap.entrySet().iterator();
    }

    /**
     * Get the ID of a Choice in the Map using an Object Choice
     * @param choice  Object Choice
     * @return return the ID of the choice.If the choice is not in the Map return -1;
     */
    public int getId(Choice choice){
        var values=this.choicesMap.entrySet();
        for(var entry :values){
            if(entry.getValue().equals(choice)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Get the Map of All Choices
     * @return return a Map<Integer, Choice>
     */
    public Map<Integer, Choice> getChoicesMap() {
        return this.choicesMap;
    }

    /**
     * Clear the Choices Map
     */
    public void clear(){choicesMap.clear();
    }
    /**
     * Get the Choice in the Map using Integer (ID)
     * @param id Integer ID of the Choice
     * @return Object Choice
     */
    public Choice get(int id){
        return this.choicesMap.get(id);
    }
}
