package PoolGame.config;

/** Builds BallReader. */
public class BallReaderFactory implements ReaderFactory {

    /**
     * Builds a BallReader.
     * 
     * @return ball reader.
     */
    public Reader buildReader() {
        return new BallReader();
    };
}
