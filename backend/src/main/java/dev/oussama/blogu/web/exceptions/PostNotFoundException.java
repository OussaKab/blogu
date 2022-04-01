package dev.oussama.blogu.web.exceptions;

public class PostNotFoundException extends Exception {
    public PostNotFoundException(Long id) {
        super("Post with id " + id + " not found");
    }
}
