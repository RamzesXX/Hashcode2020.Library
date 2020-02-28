package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.ArrayList;
import java.util.List;

public class BookStat {
    private final Book book;
    private final List<Library> libraries;
    private Library assignedTo;

    public BookStat(Book book) {
        this.book = book;
        this.libraries = new ArrayList<>();
        this.assignedTo = null;
    }

    public Book getBook() {
        return book;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public Library getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Library assignedTo) {
        this.assignedTo = assignedTo;
    }
}
