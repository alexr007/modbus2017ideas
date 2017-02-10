package common.sw.primitives;

/*
 * if (origin<0 || origin>0xFFFF) {
 * throw new InvalidRangeException();
 *
 */
public class InvalidRangeException extends Exception {

    private static final String MESSAGE = "Invalid Range for Word type, must be 0-0xFFFF";

    public InvalidRangeException() {
        super(MESSAGE);
    }

}
