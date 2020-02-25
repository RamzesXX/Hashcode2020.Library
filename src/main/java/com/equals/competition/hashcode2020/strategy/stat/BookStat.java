package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner;

import java.util.ArrayList;
import java.util.List;

public class BookStat {
    public LibraryScanner.Book book;
    public List<LibraryScanner.Library> libraries = new ArrayList<>();

    public LibraryScanner.Book getBook() {
        return book;
    }
}
