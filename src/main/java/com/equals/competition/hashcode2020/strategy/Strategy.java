package com.equals.competition.hashcode2020.strategy;

import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;

public abstract class Strategy {
    protected int D;
    protected Library[] libraries;
    protected Book[] books;

    public abstract void useStrategy(LibraryScanner libraryScanner);
}
