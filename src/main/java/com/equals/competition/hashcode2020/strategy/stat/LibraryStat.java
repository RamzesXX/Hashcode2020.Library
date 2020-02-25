package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner;

import java.util.HashSet;
import java.util.Set;

public class LibraryStat {
    public LibraryScanner.Library library;
    public int daysToProcessAllBooks;
    public int booksNotScanned;
    public int booksCanBeScanned;
    public int currentLibraryValue;
    public int currentLibraryValueForLeftDays;
    public int minBookScore;
    public int maxBookScore;
    public float avgBookScore;
    public float scorePerDay;
    public float scorePerDayPotential;
    public int amountOfUniqueBooks;
    public int valueOfUnique;
    public Set<LibraryScanner.Library> libraryIntersection = new HashSet<>();
}
