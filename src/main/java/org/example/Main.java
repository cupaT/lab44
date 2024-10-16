package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Person> people = null;
        try {
            people = loadPeopleFromFile("books.json");
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }

        if (people != null) {
            task1(people);
            task2(people);
            task3(people);
            task4(people);
            task5(people);
            task6(people);
        }
    }

    // Load data from books.json
    public static List<Person> loadPeopleFromFile(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type personListType = new TypeToken<List<Person>>() {}.getType();
            return gson.fromJson(reader, personListType);
        }
    }

    // Task 1: Display the list of people and their count
    public static void task1(List<Person> people) {
        System.out.println("Task 1: People list and count");
        System.out.println("Number of people: " + people.size());
        people.forEach(person -> System.out.println(person.getName() + " " + person.getSurname()));
    }

    // Task 2: Display the list of unique books added by people
    public static void task2(List<Person> people) {
        System.out.println("\nTask 2: Unique books list");
        List<String> uniqueBooks = people.stream()
                .flatMap(person -> person.getFavoriteBooks().stream())
                .map(Book::getName)
                .distinct()
                .collect(Collectors.toList());

        uniqueBooks.forEach(System.out::println);
        System.out.println("Number of unique books: " + uniqueBooks.size());
    }

    // Task 3: Sort books by publication year and display the list
    public static void task3(List<Person> people) {
        System.out.println("\nTask 3: Sorted books by publication year");
        List<String> sortedBooks = people.stream()
                .flatMap(person -> person.getFavoriteBooks().stream())
                .sorted(Comparator.comparing(Book::getPublicationYear))
                .map(Book::getName)
                .distinct()
                .collect(Collectors.toList());

        sortedBooks.forEach(System.out::println);
    }

    // Task 4: Check if any book by Jane Austen is in favorites
    public static void task4(List<Person> people) {
        System.out.println("\nTask 4: Is there a book by Jane Austen?");
        boolean hasJaneAusten = people.stream()
                .flatMap(person -> person.getFavoriteBooks().stream())
                .anyMatch(book -> "Jane Austen".equals(book.getAuthor()));

        System.out.println(hasJaneAusten ? "Yes" : "No");
    }

    // Task 5: Find the person with the maximum number of favorite books
    public static void task5(List<Person> people) {
        System.out.println("\nTask 5: Maximum number of favorite books");
        int maxBooks = people.stream()
                .mapToInt(person -> person.getFavoriteBooks().size())
                .max()
                .orElse(0);

        System.out.println("Maximum number of favorite books: " + maxBooks);
    }

    // Task 6: Group people based on number of favorite books and send SMS
    public static void task6(List<Person> people) {
        System.out.println("\nTask 6: Sending SMS messages");

        double averageBooks = people.stream()
                .mapToInt(person -> person.getFavoriteBooks().size())
                .average()
                .orElse(0);

        Map<Sms, List<Person>> smsMessages = people.stream()
                .filter(Person::isSubscribed)
                .collect(Collectors.groupingBy(person -> {
                    int favBooks = person.getFavoriteBooks().size();
                    if (favBooks > averageBooks) {
                        return new Sms(person.getPhone(), "You are a bookworm!");
                    } else if (favBooks < averageBooks) {
                        return new Sms(person.getPhone(), "Read more!");
                    } else {
                        return new Sms(person.getPhone(), "You're doing fine!");
                    }
                }));

        smsMessages.forEach((sms, persons) -> System.out.println(sms.getMessage()));
    }
}
