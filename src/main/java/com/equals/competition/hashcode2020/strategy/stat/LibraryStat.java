package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.HashSet;
import java.util.Set;

public class LibraryStat {
    private final Library library;
    public int daysToProcessAllBooks;
    private int booksNotScanned;
    private int booksCanBeScanned;
    private int currentLibraryValue;
    private int currentLibraryValueForLeftDays;
    private int minBookScore;
    private int maxBookScore;
    public float avgBookScore;
    public float scorePerDay;
    public float scorePerDayPotential;
    public int amountOfUniqueBooks;
    public int valueOfUnique;
    public Set<Library> libraryIntersection = new HashSet<>();

    LibraryStat(Library library){
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void resetBaseStatistics(){
        minBookScore = 0;
        maxBookScore = 0;
        booksCanBeScanned = 0;
        booksNotScanned = 0;
    }


    public int getMinBookScore() {
        return minBookScore;
    }

    public void setMinBookScore(int minBookScore) {
        this.minBookScore = minBookScore;
    }

    public int getMaxBookScore() {
        return maxBookScore;
    }

    public void setMaxBookScore(int maxBookScore) {
        this.maxBookScore = maxBookScore;
    }

    public int getBooksNotScanned() {
        return booksNotScanned;
    }

    public void setBooksNotScanned(int booksNotScanned) {
        this.booksNotScanned = booksNotScanned;
    }

    public int getBooksCanBeScanned() {
        return booksCanBeScanned;
    }

    public void setBooksCanBeScanned(int booksCanBeScanned) {
        this.booksCanBeScanned = booksCanBeScanned;
    }

    public int getCurrentLibraryValue() {
        return currentLibraryValue;
    }

    public void setCurrentLibraryValue(int currentLibraryValue) {
        this.currentLibraryValue = currentLibraryValue;
    }

    public int getCurrentLibraryValueForLeftDays() {
        return currentLibraryValueForLeftDays;
    }

    public void setCurrentLibraryValueForLeftDays(int currentLibraryValueForLeftDays) {
        this.currentLibraryValueForLeftDays = currentLibraryValueForLeftDays;
    }
}
