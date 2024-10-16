package org.example;

import lombok.Data;
import java.util.List;

@Data
public class Person {
    private String name;
    private String surname;
    private String phone;
    private List<Book> favoriteBooks;
    private boolean subscribed;
}

