package org.helmo.gbeditor.repository;

import org.helmo.gbeditor.domains.GameBook;

/***
 * Interface LibraryRepository
 */
public interface LibraryRepository {
    /***
     * Save a GameBook to the JSON File
     * @param gb Object GameBook
     * @return TRUE if it can create a GameBook else FALSE
     */
    boolean saveGameBook(GameBook gb);

    /***
     * Load a JSON File to create a Map of GameBook
     * @return True if it can load ths JSON File else FALSE
     */
    boolean loadGameBook();
}
