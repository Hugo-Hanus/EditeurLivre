package org.helmo.gbeditor.infrastructure.repository;

import org.helmo.gbeditor.domains.GameBook;
import org.helmo.gbeditor.infrastructure.mapper.UniversalMapper;
import org.helmo.gbeditor.repository.LibraryRepository;

import java.nio.file.Path;

/***
 * Repository to Use EXTENSION JSON
 */
public class JSONLibraryRepository implements LibraryRepository {
    private final UniversalMapper mapper;
    private final Path path;

    /***
     * JSON Repository Constructor
     * @param mapper Object Mapper to Transform Object
     * @param path Path to the file JSON (%USER%/ue36/%MATRICULE%.json)
     */
    public JSONLibraryRepository( UniversalMapper mapper, Path path) {
        this.mapper = mapper;
        this.path = path;
    }

    /***
     * Save a Gamebook to a JSON File
     * @param gb Object Gamebook
     * @return TRUE if SAVE , FALSE if NOT
     */
    @Override
    public boolean saveGameBook(GameBook gb) {
        mapper.gameBookToDTOBook(gb);
        var a=path;
        a.toString();
       /* String pathF = checkingDirectory(path);
        if (!pathF.equals(error)) {
            Path file = Path.of(pathF + "/190533.json");
            Library lib =new Library();
            if(Files.exists(file)){
                try {
                    lib = loadFromFile(file);
                }catch (EmptyJsonFileException e){
                    return false;
                }
            }
                lib.addGameBook(gb.getIsbn(), gb);
                return exportToFile(file, lib);
        } else {
            return false;
        }
    */return false;
    }

    /***
     * Export A Library to A JSON file
     * @param file Path file to JSON File
     * @return TRUE if it can export to a JSON File else FALSE
     */
   /* private boolean exportToFile(Path file) {
      /*  try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, CREATE)) {
            Gson gson = new Gson();
            gson.toJson(mapper.libraryToDTOLibrary(lib), writer);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }
*/
    /***
     * Checking the Directory
     * //@param PATH  to the JSON file
     * @return String if it can't create directory(return "error") if it can create it (return Path)
     */
/*    private String checkingDirectory(Path path) {
        String ue = "/ue36";
        Path fPath = Path.of(path + ue);
        if (Files.exists(Path.of(path + ue))) {
            return fPath.toString();
        } else {
            try {
                Files.createDirectories(fPath);
                return fPath.toString();
            } catch (IOException ex) {
                return error;
            }
        }
    }

    /***
     * Load Gamebooks from JSON File in the Library
     * @return TRUE if it can load a Map of GameBook | FALSE if it can't charge it
     */
    @Override
    public boolean loadGameBook() {
      /*  Path file = Path.of(path + "/ue36" + "/190533.json");
        if (Files.exists(file)) {
            Library lib;
            try{
                 lib = loadFromFile(file);
            } catch (EmptyJsonFileException|JsonSyntaxException e){
                return false;
            }
                for (var element : lib.getLibrary().entrySet()) {
                    library.addGameBook(element.getKey(), element.getValue());
                }
                return true;

        } else {
            return false;
        }*/
        return false;
    }
/*
    /***
     * Load a Library from JSON file
     * @param  Path file to a JSON File
     * @return A Library by a DTOLibrary
     */
    /*private Library loadFromFile(Path file) throws EmptyJsonFileException{
            try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                Gson gson = new Gson();
                var result = gson.fromJson(reader, LibraryDTO.class);
                 if(result==null){
                     throw new EmptyJsonFileException(new NullPointerException());
                 }else{
                     return mapper.DTOLibraryToLibrary(result);
                 }
            }catch (JsonSyntaxException e){
                throw new EmptyJsonFileException(e);
            } catch (IOException ex) {
                throw new EmptyJsonFileException(ex);
            }
        }*/

    public Path getPath() {
        return path;
    }

    public UniversalMapper getMapper() {
        return mapper;
    }

    }



