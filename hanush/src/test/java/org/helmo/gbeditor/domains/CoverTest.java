package org.helmo.gbeditor.domains;
import org.junit.jupiter.api.Test;




import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for class Choice
 */
public class CoverTest {

    public static final String ISBN = "2-190533-04-X";
    public static final String RESUME = "Resume";
    public static final String TITLE = "Titre";

    /**
     *@Test Test Getter method
     */
    @Test
    public void testGetterCover() {
        Cover newCover = new Cover(TITLE,ISBN,RESUME);
        assertEquals(TITLE,newCover.getTitle());
        assertEquals(ISBN,newCover.getIsbn());
        assertEquals(RESUME,newCover.getResume());
    }

    /**
     *@Test Test FormatCover method
     */
    @Test
    public void testFormatCover() {
        Cover newCover = new Cover(TITLE,ISBN,RESUME);
       assertEquals("2-190533-04-X || Titre",newCover.formatCover(false));
        assertEquals("Publié-2-190533-04-X || Titre",newCover.formatCover(true));
    }

    /**
     *@Test Test checkISBN method
     */
    @Test
    public void testCheckIsbn() {
        Cover newCover = new Cover(TITLE,ISBN,RESUME);
        Cover badPatternCover= new Cover(TITLE,"2-190533-3-1",RESUME);
        Cover errorCover= new Cover(TITLE,"2-19O533-3-1",RESUME);
        Cover badSecurityNumberCover= new Cover(TITLE,"2-190533-03-9",RESUME);
        Cover zeroCover= new Cover(TITLE,"2-190533-09-0",RESUME);
        assertTrue(newCover.checkISBN());
        assertFalse(badPatternCover.checkISBN());
        assertFalse(badSecurityNumberCover.checkISBN());
        assertFalse(errorCover.checkISBN());
        assertTrue(zeroCover.checkISBN());
    }
}
