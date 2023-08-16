package org.helmo.gbeditor.infrastructure.mapper;

import org.helmo.gbeditor.domains.GameBook;
import org.helmo.gbeditor.infrastructure.dto.CoverDTO;
import org.helmo.gbeditor.infrastructure.dto.GameBookDTO;
import org.helmo.gbeditor.infrastructure.dto.UserDTO;

/***
 * Class MapperOne using to Map Object to DTO and DTO to Object
 */
public class MapperOne implements UniversalMapper{

    @Override
    public GameBook DTOBookToGameBook(GameBookDTO dto) {
        return null;// new GameBook(new User(dto.userDTO.firstname,dto.userDTO.name),dto.title,dto.isbn,dto.resume,false);
    }
      /*  @Override
    public Library DTOLibraryToLibrary(LibraryDTO dto) {
            HashMap<String,GameBook> map=new HashMap<>();
        for (GameBookDTO gb:dto.library.values()) {
            if(checkNullDTO(gb)) {
                map.put(gb.isbn, DTOBookToGameBook(gb));
            }
        }
         Library library=new Library();
            library.setLibrary(map);
        return library;
    }

    private boolean checkNullDTO(GameBookDTO gb) {
        return gb.isbn != null && gb.title != null && gb.resume != null && gb.userDTO.name != null && gb.userDTO.firstname != null;
    }*/

    @Override
    public GameBookDTO gameBookToDTOBook(GameBook gameBook) {
            CoverDTO coverDTO= new CoverDTO();
            GameBookDTO dto = new GameBookDTO();
            dto.coverDTO=coverDTO;
            UserDTO uDTO=new UserDTO();
           // uDTO.firstname=gameBook.getUser().getFirstname();
            //uDTO.name=gameBook.getUser().getName();
            dto.userDTO=uDTO;
        return dto;
    }

   /* @Override
    public LibraryDTO libraryToDTOLibrary(Library lib) {
        HashMap<String,GameBookDTO> map=new HashMap<>();
        for (var element:lib.getLibrary().entrySet()) {
            map.put(element.getKey(),gameBookToDTOBook(element.getValue()));
        }
        LibraryDTO libDTO=new LibraryDTO();
        libDTO.library=map;
        return libDTO;
    }*/
}
