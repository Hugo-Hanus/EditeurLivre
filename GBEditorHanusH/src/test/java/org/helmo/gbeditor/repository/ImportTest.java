package org.helmo.gbeditor.repository;


/**
 * Class ImportTest Plus utilisé
 */
public class ImportTest {

/*
    Path pathsToMissingElement = Paths.get("src", "test", "resources", "repository.import", "missingElement").toAbsolutePath();
    Path pathsToNoGoodData = Paths.get("src", "test", "resources", "repository.import", "notGoodData").toAbsolutePath();
    Path pathsToNoGoodFormat = Paths.get("src", "test", "resources", "repository.import", "NotGoodFormat").toAbsolutePath();
    Path pathsToNoFile = Paths.get("src", "test", "resources", "repository.import", "empty").toAbsolutePath();
    Path pathsToEmptyFile = Paths.get("src", "test", "resources", "repository.import", "emptyFile").toAbsolutePath();
    Path FullToResources = Paths.get("src", "test", "resources", "repository.import").toAbsolutePath();
    UniversalMapper mapper = new MapperOne();
    LibraryRepository repository = new JSONLibraryRepository(mapper, FullToResources);
    LibraryRepository repositoryMissingElem = new JSONLibraryRepository( mapper, pathsToMissingElement);
    LibraryRepository repositoryNoFile = new JSONLibraryRepository( mapper, pathsToNoFile);
    LibraryRepository repositoryNoGoodData = new JSONLibraryRepository( mapper, pathsToNoGoodData);
    LibraryRepository repositoryNoGoodFormat = new JSONLibraryRepository( mapper, pathsToNoGoodFormat);
    LibraryRepository repositoryEmptyFile = new JSONLibraryRepository( mapper, pathsToEmptyFile);
    String jsonfile = "{\"title\":\"Titre\",\"ISBN\":\"0-000000-00-0\",\"resume\":\"Resume\",\"userDTO\":{\"prenom\":\"Prenom\",\"nom\":\"Nom\"}}";
   // GameBook gameBook = new GameBook(new User("Prenom", "Nom"), "Titre", "0-000000-00-0", "Resume");

    /**
     * Good Situation
     */
   /* @Test
    void importBookGoodSituation() {
      repository.loadGameBook();
        assertEquals(1,library.getNumberOfMovie());
    }*/
    /**
     * Import Missing a element (not resume)
     */
    /*@Test
    void importBookMissingElement() {
        repositoryMissingElem.loadGameBook();
        assertEquals(0,library.getNumberOfMovie());
    }*/
    /**
     * Not file Found
     */
    /*@Test
    void importBookNoFile() {
        repositoryNoFile.loadGameBook();
        assertEquals(0,library.getNumberOfMovie());
    }*/
    /**
     * Not Good Data (int in place of a String)
     */
    /*@Test
    void importBookNoGoodData() {
        repositoryNoGoodData.loadGameBook();
        assertEquals(0,library.getNumberOfMovie());
    }*/
    /**
     * Not Good Format (missing element to form correct JSON)
     */
    /*@Test
    void importBookNoGoodFormat() {
        repositoryNoGoodFormat.loadGameBook();
        assertEquals(0,library.getNumberOfMovie());
    }*/
    /**
     * File with nothing in
     */
    /*@Test
    void importBookRessourceWithNothingInside() {
            repositoryEmptyFile.loadGameBook();
            assertEquals(0,library.getNumberOfMovie());

    }*/

}
