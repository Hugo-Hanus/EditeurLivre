package org.helmo.gbeditor.domains;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for class User
 */
public class UserTest {

    public static final String FIRSTNAME = "John";
    public static final String NAME = "Doe";
    public static final String TITLE = "My GameBook";
    public static final String ISBN = "123456";
    public static final String RESUME = "A fun adventure";
    public static final Cover COVER = new Cover(TITLE,ISBN,RESUME);

    /**
     *@Test Test GetName method
     */
    @Test
    public void testGetName() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
        assertEquals(NAME, user.getName());
    }

    /**
     *@Test Test GetFirstname method
     */
    @Test
    public void testGetFirstname() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
         assertEquals(FIRSTNAME, user.getFirstname());
    }

    /**
     *@Test Test ToString method
     */
    @Test
    public void testToString() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
         assertEquals("John Doe", user.toString());
    }

    /**
     *@Test Test SetLibrary method
     */
    @Test
    public void testSetLibrary() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
        Map<String, GameBook> newLibrary = new HashMap<>();
        newLibrary.put(ISBN, new GameBook(COVER, false,false, new HashMap<>()));
        user.setLibrary(newLibrary);
         assertEquals(newLibrary, user.getLibrary());
    }

    /**
     *@Test Test AddGameBook method
     */
    @Test
    public void testAddGameBookWithNewBook() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
        GameBook gameBook = new GameBook(COVER, false,false, new HashMap<>());
         assertTrue(user.addGameBook(ISBN, gameBook));
         assertEquals(gameBook, user.get(ISBN));
    }

    /**
     *@Test Test createGameBook method
     */
    @Test
    public void testAddGameBookWithExistingBook() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
        GameBook gameBook = new GameBook(COVER, false,false, new HashMap<>());
        user.addGameBook(ISBN, gameBook);
         assertFalse(user.addGameBook(ISBN, gameBook));
         assertEquals(gameBook, user.get(ISBN));
    }

    /**
     *@Test Test GetLibrary method
     */
    @Test
    public void testGetLibrary() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
         assertEquals(library, user.getLibrary());
    }
    /**
     *@Test Test getNumberOfGameBook method
     */
    @Test
    public void testGetNumberOfGameBookWithEmptyLibrary() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
          assertEquals(0, user.getNumberOfGameBook());
    }

    /**
     *@Test Test getNumberOfGameBook method
     */
    @Test
    public void testGetNumberOfGameBookWithNonEmptyLibrary() {
        Map<String, GameBook> library = new HashMap<>();
        library.put(ISBN, new GameBook(COVER, false,false, new HashMap<>()));
        User user = new User(FIRSTNAME, NAME, library);
          assertEquals(1, user.getNumberOfGameBook());
    }

    /**
     *@Test Test formatUser method
     */
    @Test
    public void testFormatUser() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
          assertEquals("John.Doe", user.formatUser());
    }

    /**
     *@Test Test deletePagesOfGameBook method
     */
    @Test
    public void testDeletePagesOfGameBook() {
        Map<String, GameBook> library = new HashMap<>();
        Map<Integer, Page> pages = new HashMap<>();
        List<Choice> list=new ArrayList<>();
        pages.put(1, new Page( "Some text",1,list));
        library.put(ISBN, new GameBook(COVER, false,false, pages));
        User user = new User(FIRSTNAME, NAME, library);
        user.deletePagesOfGameBook(ISBN);
          assertTrue(user.get(ISBN).isEmpty());
    }

    /**
     *@Test Test deletePagesOfGameBook method
     */
    @Test
    public void testCheckIfTheEntryIsNotEmpty() {
       User userEmpty=new User("","",new HashMap<>());
        User userSemiEmpty=new User("","name",new HashMap<>());
       User userSpace=new User(" "," ",new HashMap<>());
       User user=new User("Hugo","Hanus",new HashMap<>());
        assertTrue(user.checkEntryConnection("Hugo","Hanus"));
        assertFalse(userEmpty.checkEntryConnection("",""));
        assertFalse(userSpace.checkEntryConnection(" "," "));
        assertFalse(userSemiEmpty.checkEntryConnection("","name"));
    }

    /**
     *@Test Test clearAllGameBook method
     */
    @Test
    public void testClearAllGameBookUser() {
        Map<String, GameBook> library = new HashMap<>();
        User user = new User(FIRSTNAME, NAME, library);
        GameBook gameBook = new GameBook(COVER, false,false, new HashMap<>());
     user.addGameBook(ISBN, gameBook);
     user.clearAllGameBook();
        assertEquals(user.getNumberOfGameBook(),0);
    }



}
