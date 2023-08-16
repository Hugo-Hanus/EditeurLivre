package org.helmo.gbeditor.domains;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for class Page
 */
public class PageTest {
    /**
     *@Test Test GetChoices method
     */
    @Test
    public void testGetChoices() {
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice( 1,2, "Go to page 2"));
        Page page = new Page( "Some text",1, choices);
            assertEquals(choices, page.getChoices());
    }

    /**
     *@Test Test toDetail method
     */
    @Test
    public void testToDetail() {
        Page page = new Page("Some text",1, new ArrayList<>());
            assertEquals("N°: 1 | Texte :Some text", page.toDetail());
    }

    /**
     *@Test Test editPage method
     */
    @Test
    public void testEditPage() {
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice( 1,2, "Go to page 2"));
        Page page = new Page("Some text",1, new ArrayList<>());
        page.editPage("New text", 2, choices);
            assertEquals("New text", page.getTextPage());
            assertEquals(2, page.getNumero());
            assertEquals(choices, page.getChoices());
    }

    /**
     * @Test Test addChoice method
     */
    @Test
    public void testAddChoice() {
        Choice choice=new Choice(1, 2, "Go to page 2");
        Page page = new Page("Some text",1, new ArrayList<>());
        page.addChoice(choice);
        assertEquals(choice, page.getChoices().get(0));
    }

    /**
     * @Test Test addChoice method
     */
    @Test
    public void testClearChoices() {
        List<Choice> choices = new ArrayList<>();
        Choice choice=new Choice( 1,2, "Go to page 2");
        choices.add(choice);
        Page page = new Page("Some text",1, choices);
        assertEquals(choice, page.getChoices().get(0));
        page.clear();
        assertTrue(choices.isEmpty());
    }

    /**
     * @Test Test isEmpty method
     */
    @Test
    public void testisEmpty() {
        Page page = new Page("Some text",1, new ArrayList<>());
        assertTrue(page.isEmpty());
        page.addChoice(new Choice(1,1,"testwx"));
        assertFalse(page.isEmpty());
    }

    /**
     * @Test Test getChoiceByText method
     */
    @Test
    public void testGetChoiceByText() {
        Page page = new Page("Some text",1, new ArrayList<>());
        Choice choiceO=new Choice(1,1,"test");
        Choice choiceT=new Choice(1,2,"testTexte");
        page.addChoice(new Choice(1,1,"testTexte"));
        page.addChoice(choiceO);
        page.addChoice(choiceT);
        assertEquals(choiceO,page.getChoiceByText("test"));
        assertNotEquals(choiceO,page.getChoiceByText("teST"));
        assertEquals(null,page.getChoiceByText("teST"));
    }
}
