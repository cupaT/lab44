package org.example;

import lombok.*;
import java.util.Comparator;

class BookPublicationDateComparator implements Comparator<Book> {
    @Override
    public int compare(Book book1, Book book2) {
        if (book1.getPublicationYear() == 0 && book2.getPublicationYear() == 0) {
            return 0; // Оба года равны 0, книги считаются одинаковыми
        }

        if (book1.getPublicationYear() == 0) {
            return 1; // Первая книга имеет неизвестный год публикации
        }

        if (book2.getPublicationYear() == 0) {
            return -1; // Вторая книга имеет неизвестный год публикации
        }

        return Integer.compare(book1.getPublicationYear(), book2.getPublicationYear());
    }
}