package org.helmo.gbeditor.domains;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for class GameBook
 */
public class GameBookTest {

    public static final String MY_GAME_BOOK = "My GameBook";
    public static final String ISBN = "123456";
    public static final String RESUME = "A fun adventure";
    public static final String TITLE = "New title";
    public static final String ISBNT = "654321";
    public static final String NRESUME = "A new adventure";
    public static final Cover COVER = new Cover(MY_GAME_BOOK, ISBN, RESUME);
    public static final String TEXT_PAGE = "Some text";

    /**
     * @Test Test GameBookConstructor method
     */
    @Test
    public void testGameBookConstructor() {
        // Test with valid input
        Map<Integer, Page> pages = new HashMap<>();
        List<Choice> list = new ArrayList<>();
        pages.put(1, new Page(TEXT_PAGE, 1, list));
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        assertEquals(COVER, gameBook.getCover());
        assertTrue(gameBook.isPublish());
        assertEquals(pages, gameBook.getMapPage());
    }

    /**
     * @Test Test SetPublish method
     */
    @Test
    public void testSetPublish() {

        GameBook gameBook = new GameBook(COVER, true, false, new HashMap<>());
        gameBook.setPublish(false);
        assertFalse(gameBook.isPublish());
        gameBook.setPublish(true);
        assertTrue(gameBook.isPublish());
    }

    /**
     * @Test Test SetPublish method
     */
    @Test
    public void testSetEdited() {

        GameBook gameBook = new GameBook(COVER, false, false, new HashMap<>());
        gameBook.setEdited(false);
        assertFalse(gameBook.isEdited());
        gameBook.setEdited(true);
        assertTrue(gameBook.isEdited());
    }

    /**
     * @Test Test AddPage method
     */
    @Test
    public void testAddPage() {
        Map<Integer, Page> pages = new HashMap<>();
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        List<Choice> list = new ArrayList<>();
        Page page = new Page(TEXT_PAGE, 1, list);
        assertTrue(gameBook.addPage(page));
        assertFalse(gameBook.addPage(page));
    }

    /**
     * @Test Test FormatGameBook method
     */
    @Test
    public void testFormatGameBookWithPublish() {
        Map<Integer, Page> pages = new HashMap<>();
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        assertEquals("Publié-123456 || My GameBook", gameBook.formatGameBook(true));
    }

    /**
     * @Test Test formatGameBook method
     */
    @Test
    public void testFormatGameBookWithoutPublish() {
        Map<Integer, Page> pages = new HashMap<>();
        GameBook gameBook = new GameBook(COVER, false, false, pages);
        assertEquals("123456 || My GameBook", gameBook.formatGameBook(false));
    }


    /**
     * @Test Test isEmpty method
     */
    @Test
    public void testIsEmptyWithEmptyMap() {
        Map<Integer, Page> pages = new HashMap<>();
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        assertTrue(gameBook.isEmpty());
    }

    /**
     * @Test Test isEmpty method
     */
    @Test
    public void testIsEmptyWithNonEmptyMap() {
        Map<Integer, Page> pages = new HashMap<>();
        List<Choice> list = new ArrayList<>();
        Page page = new Page(TEXT_PAGE, 1, list);
        pages.put(1, page);
        GameBook gameBook = new GameBook(COVER, true, false, pages);

        assertFalse(gameBook.isEmpty());
    }

    /**
     * @Test Test clearPage method
     */
    @Test
    public void testClearPage() {
        Map<Integer, Page> pages = new HashMap<>();
        List<Choice> list = new ArrayList<>();
        Page page = new Page(TEXT_PAGE, 1, list);
        pages.put(1, page);
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        gameBook.clearPage();
        assertTrue(gameBook.isEmpty());
    }

    /**
     * @Test Test getNumberOfPage method
     */
    @Test
    public void testGetNumberOfPage() {
        Map<Integer, Page> pages = new HashMap<>();
        List<Choice> list = new ArrayList<>();
        Page page = new Page(TEXT_PAGE, 1, list);
        pages.put(1, page);
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        assertEquals(1, gameBook.getNumberOfPage());
    }

    /**
     * @Test Test UniquePage method
     */
    @Test
    public void testUniquePage() {
        Map<Integer, Page> pages = new HashMap<>();
        List<Choice> list = new ArrayList<>();
        Page page = new Page(TEXT_PAGE, 1, list);
        pages.put(1, page);
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        assertTrue(gameBook.uniquePage());
        pages.put(2, page);
        assertFalse(gameBook.uniquePage());
    }

    /**
     * @Test Test setCover method
     */
    @Test
    public void testSetCover() {
        Map<Integer, Page> pages = new HashMap<>();
        List<Choice> list = new ArrayList<>();
        Page page = new Page(TEXT_PAGE, 1, list);
        pages.put(1, page);
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        Cover newCover = new Cover("New title", "isbn", "resume");
        gameBook.setCover(newCover);
        assertEquals(gameBook.getCover(), newCover);
    }

    /**
     * @Test Test getAllChoiceOfGameBook method
     */
    @Test
    public void testGetAllChoiceOfGameBook() {
        Map<Integer, Page> pages = new HashMap<>();
        Choice choice = new Choice(1, 2, "vers la 2");
        Choice choiceTwo = new Choice(1, 3, "vers la 3");
        Choice choiceThree = new Choice(2, 3, "vers la 3");
        Page page = new Page(TEXT_PAGE, 1, new ArrayList<>());
        page.addChoice(choice);
        page.addChoice(choiceTwo);
        Page pageTwo = new Page(TEXT_PAGE, 2, new ArrayList<>());
        pageTwo.addChoice(choiceThree);
        Page pageThree = new Page(TEXT_PAGE, 3, new ArrayList<>());
        GameBook gameBook = new GameBook(COVER, true, false, pages);
        pages.put(1, page);
        pages.put(2, pageTwo);
        pages.put(3, pageThree);
        List<Choice> choiceLOne = pages.get(1).getChoices();
        List<Choice> choiceLTwo = pages.get(2).getChoices();
        List<Choice> choiceLThree = pages.get(3).getChoices();
        choiceLOne.addAll(choiceLTwo);
        choiceLOne.addAll(choiceLThree);
        for (Choice choiceList : gameBook.getAllChoiceofPages()) {
            assertTrue(choiceLOne.contains(choiceList));
        }
    }


}
