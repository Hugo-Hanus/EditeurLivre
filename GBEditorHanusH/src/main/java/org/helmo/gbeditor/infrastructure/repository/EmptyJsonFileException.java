package org.helmo.gbeditor.infrastructure.repository;
/**
 * EmptyJsonFileException Exception if a JSON file is empty
 */

public class EmptyJsonFileException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the EmptyJsonFileException
     * @param ex Exception for building the EmptyJSONFile
     */
    public EmptyJsonFileException(Exception ex) {
        super(ex);
    }

}
