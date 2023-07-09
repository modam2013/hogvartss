package com.example.hogvartss.exceotion;

public class AvatarNotFoundException extends RuntimeException{
    private final long id;

    public AvatarNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Аватар с id = " + id + " не найден!";
    }
}
