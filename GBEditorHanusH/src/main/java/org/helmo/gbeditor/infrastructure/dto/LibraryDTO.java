package org.helmo.gbeditor.infrastructure.dto;


import java.util.HashMap;
import java.util.Map;

/***
 * Class LibraryDTO using by the Mapper to avoid Domain Object use in the Program
 */
public class LibraryDTO {
    public Map<String,GameBookDTO> library = new HashMap<>();

    public void setLibrary(Map<String, GameBookDTO> library) {
        this.library = library;
    }

    public Map<String, GameBookDTO> getLibrary() {
        return library;
    }
}
