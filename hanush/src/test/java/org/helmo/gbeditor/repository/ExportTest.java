package org.helmo.gbeditor.repository;


/**
 * Class ExportTest Plus Utilisé
 */
public class ExportTest {

/*
     Path pathsToResources= Paths.get("src","test","resources","repository","export").toAbsolutePath();
     Path FullToResources= Paths.get("src","test","resources","repository","export","ue36","190533.json").toAbsolutePath();
    Path FullToResourcesTwo= Paths.get("src","test","resources","repository","export","OneBook","ue36","190533.json").toAbsolutePath();
    UniversalMapper mapper=new MapperOne();
    LibraryRepository repository= new JSONLibraryRepository(mapper,pathsToResources);

    String jsonfile="{\"library\":{\"0-000000-00-0\":{\"title\":\"Titre\",\"isbn\":\"0-000000-00-0\",\"resume\":\"Resume\",\"userDTO\":{\"firstname\":\"Prenom\",\"name\":\"Nom\"}}}}";
    String jsonFileTwo="{\"library\":{\"0-000000-00-0\":{\"title\":\"Titre\",\"isbn\":\"0-000000-00-0\",\"resume\":\"Resume\",\"userDTO\":{\"firstname\":\"Prenom\",\"name\":\"Nom\"}},\"2-070039-01-3\":{\"title\":\"TitreTitre\",\"isbn\":\"2-070039-01-3\",\"resume\":\"ResumeResume\",\"userDTO\":{\"firstname\":\"PrenomPrenom\",\"name\":\"NomNom\"}}}}";
   //GameBook gameBook = new GameBook(new User("Prenom","Nom"),"Titre","0-000000-00-0","Resume");
    //GameBook gameBookTwo = new GameBook(new User("PrenomPrenom","NomNom"),"TitreTitre","2-070039-01-3","ResumeResume");

    /**
     * @Test Directory not exist and file not exist
     */
  /*  @Test
    void ExportBookDirectoryNotExistAndFileNotExist() {
        try {
        if(Files.exists(Path.of(pathsToResources+"/ue36"))){
            if(Files.exists(Path.of(pathsToResources+"/ue36"+"/190533.json"))) {
                Files.delete(Path.of(pathsToResources + "/ue36" + "/190533.json"));
            }
            Files.delete(Path.of(pathsToResources+"/ue36"));
        }
        repository.saveGameBook(gameBook);
        System.out.println(Files.readString(FullToResources));
        assertTrue(jsonfile.equals(Files.readString(FullToResources)));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/
    /**
     * @Test Directory exist and file not exist
     */
    /*@Test
    void ExportBookDirectoryExistAndFileNotExist() {
        try {
            if(Files.exists(Path.of(pathsToResources+"/ue36"))){
                if(Files.exists(Path.of(pathsToResources+"/ue36"+"/190533.json"))) {
                    Files.delete(Path.of(pathsToResources + "/ue36" + "/190533.json"));
                }
            }
                repository.saveGameBook(gameBook);
                assertTrue(jsonfile.equals(Files.readString(FullToResources)));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/

    /**
     * @Test Directory exist and file exist with Same GameBook in
     */
    /*@Test
    void ExportBookDirectoryExistAndFileExistWithSameGameBookIn() {
        try {
            if(Files.exists(Path.of(pathsToResources+"/ue36"+"/190533.json"))){

                if(repository.saveGameBook(gameBook)){
                    assertTrue(jsonfile.equals(Files.readString(FullToResources)));
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
*/
    /**
     * @Test Directory exist and file exist with GameBook in
     */
  /*  @Test
    void ExportBookDirectoryExistAndFileExistWithABookIn() {
        LibraryRepository repositoryTwo= new JSONLibraryRepository(library,mapper,Path.of(pathsToResources+"/OneBook"));
        try {
            if(Files.exists(Path.of(pathsToResources+"/OneBook/ue36"+"/190533.json"))){
                    Files.delete(Path.of(pathsToResources+"/OneBook/ue36"+"/190533.json"));
            }
            if(repositoryTwo.saveGameBook(gameBook)){
                if(repositoryTwo.saveGameBook(gameBookTwo)){
                    assertTrue(jsonFileTwo.equals(Files.readString(FullToResourcesTwo)));
                }
        }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

*/
}
