package org.helmo.gbeditor.infrastructure.mapper;

import org.helmo.gbeditor.domains.GameBook;
import org.helmo.gbeditor.infrastructure.dto.GameBookDTO;

/**
 * UniversalMapper Interface using to Map Object to DTO and DTO to Object
 */
public interface UniversalMapper {
    /**
     * Transform GameBookDTO to GameBook
     * @param dto Object GameBookDTO
     * @return Objet GameBook
     */
    GameBook DTOBookToGameBook(GameBookDTO dto);

    /**
     * Transform GameBook to GameBookDTO
     * @param gameBook Object GameBook
     * @return Object GameBookDTO
     */
    GameBookDTO gameBookToDTOBook(GameBook gameBook);

    /**
     * Transform LibraryDTO to Library
     * @param dto Object LibraryDTO
     * @return Object Library
     */
   // Library  DTOLibraryToLibrary(LibraryDTO dto);

    /**
     *  Transform Library to DTOLibrary
     * @param library Object Library
     * @return Object LibraryDTO
     */
    //LibraryDTO libraryToDTOLibrary(Library library);
}
