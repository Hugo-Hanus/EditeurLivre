package org.helmo.gbeditor.storage;

/**
 * Con
 */
public interface  ConnectionData {
    /**
     *
     * @return
     */
    String getDriverName();

    /**
     *
     * @return
     */
    String getUsername();

    /**
     *
     * @return
     */
    String getPassword();

    /**
     *
     * @return
     */
    String getDBPath();
}
