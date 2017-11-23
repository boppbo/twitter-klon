package de.hska.twitterklon.api.exceptions;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("Login failed.");
    }
}
