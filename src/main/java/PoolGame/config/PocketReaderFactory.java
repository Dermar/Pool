package PoolGame.config;

/**
 * Builds a pocket reader
 */
public class PocketReaderFactory implements ReaderFactory{
    /**
     * Creates a new instance of PocketReader
     * @return an instance of PocketReader
     */
    public Reader buildReader(){return new PocketReader();}
}
