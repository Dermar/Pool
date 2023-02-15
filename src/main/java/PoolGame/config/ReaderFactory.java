package PoolGame.config;

/** Interface for building readers. */
public interface ReaderFactory {
    /**
     * Builds a reader.
     * 
     * @return reader.
     */
    public Reader buildReader();
}
