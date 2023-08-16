package org.helmo.gbeditor.domains;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for class Choice
 */
public class ChoiceTest {

    public static final String GO = "Go to page 2";

    /**
     *@Test Test GetToPage method
     */
    @Test
    public void testGetToPage() {
        Choice choice = new Choice( 1,2, GO);
           assertEquals(2, choice.getToPage());
    }


    /**
     *@Test Test getText method
     */
    @Test
    public void testGetText() {
        Choice choice = new Choice( 1,2, GO);
           assertEquals(GO, choice.getText());
    }

    /**
     *@Test Test getFromPage method
     */
    @Test
    public void testGetFromPage() {
        Choice choice = new Choice( 1,2, GO);
        assertEquals(1, choice.getFromPage());
    }

    /**
     *@Test Test toChoiceOption method
     */
    @Test
    public void testToChoiceOption() {
        Choice choice = new Choice( 1,2, GO);
           assertEquals("Go to page 2\n--> Vers la page N°:2", choice.toChoiceOption());
    }
}
