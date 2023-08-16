package org.helmo.gbeditor.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Class GameBook
 */
public class GameBook {

    private Cover cover;
    private final Map<Integer, Page> mapPage;
    private boolean publish;
    private boolean edited;

    /**
     * GameBook constructor
     *
     * @param cover       Object Cover of the GameBook;
     * @param publish     Boolean GameBook publish
     * @param edited      Boolean Gamebook is edited
     * @param pageHashMap HashMap of the Page of the GameBook
     */
    public GameBook(Cover cover, boolean publish,
                    boolean edited, Map<Integer, Page> pageHashMap) {
        this.cover = cover;
        this.publish = publish;
        this.edited = edited;
        this.mapPage = pageHashMap;
    }

    /***
     * Get the title of the Book
     * @return String title
     */
    public Cover getCover() {
        return this.cover;
    }

    /***
     *  Get Format of the book for the ListView
     * @return String formated GameBook
     */
    public String formatGameBook(boolean isPublish) {
        return cover.formatCover(isPublish);
    }

    /**
     * Get if a book is published or not
     *
     * @return true if he is published else false
     */
    public boolean isPublish() {
        return publish;
    }

    /**
     * Get if a book is edited or not
     *
     * @return true if he is edited else false;
     */
    public boolean isEdited() {
        return edited;
    }

    public Map<Integer, Page> getMapPage() {
        return this.mapPage;
    }

    public void setPublish(boolean bool) {
        this.publish = bool;
        this.edited = false;
    }

    public void setEdited(boolean bool) {
        this.edited = bool;
    }

    /**
     * Check is the map of page is Empty
     *
     * @return True if empty else false
     */
    public boolean isEmpty() {
        return mapPage.isEmpty();
    }

    /**
     * Check if it can add page to the map of GameBook and if the Gamebook contains this page already
     *
     * @param page page of the Gamebook
     * @return true if it can add the page to the book else false
     */
    public boolean addPage(Page page) {
            if (!mapPage.containsKey(page.getNumero())) {
                mapPage.put(page.getNumero(), page);
                return true;
            }else{
                return false;
            }
    }

    /**
     * Clear all pages in the mapPaga
     */
    public void clearPage() {
        mapPage.clear();
    }

    /**
     * Get the number of Page in the GameBook
     *
     * @return Interger return the number of the page in the GameBook
     */
    public int getNumberOfPage() {
        return mapPage.size();
    }

    /**
     * Check if the GameBook have only one page
     *
     * @return true if the gamebook have one page else false
     */
    public boolean uniquePage() {
        return (mapPage.size() == 1);
    }

    /**
     * Get all Choices of the GameBook
     * @return List of Object Choice
     */
    public List<Choice> getAllChoiceofPages(){
        List<Choice>choiceList= new ArrayList<>();
        for(Page page : mapPage.values()){
            if(!page.isEmpty()){
                choiceList.addAll(page.getChoices());
            }
        }
        return choiceList;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }


}
