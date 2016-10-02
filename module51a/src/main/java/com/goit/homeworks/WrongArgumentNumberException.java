package com.goit.homeworks;

/**
 * Created by SSuchock on 27.09.16.
 */
public class WrongArgumentNumberException extends Exception {
    public WrongArgumentNumberException() {
    }

    public WrongArgumentNumberException(String message) {
        super(message);
    }
}
