package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.HashSet;
import java.util.Set;

public class LibraryStat {
    private final Library library;
    private int daysToProcessAllBooks;
    private int booksNotScanned;
    private int booksCanBeScanned;
    private int currentLibraryValue;
    private int currentLibraryValueForLeftDays;
    private int minBookScore;
    private int maxBookScore;
    private float avgBookScore;
    private float scorePerDay;
    private float scorePerDayPotential;
    private int amountOfUniqueBooks;
    private int valueOfUnique;
    private Set<Library> libraryIntersection;

    LibraryStat(Library library){
        this.library = library;
        libraryIntersection = new HashSet<>();
    }

    public Library getLibrary() {
        return library;
    }

    public void resetBaseStatistics(){
        minBookScore = 0;
        maxBookScore = 0;
        booksCanBeScanned = 0;
        booksNotScanned = 0;
        daysToProcessAllBooks = 0;
        avgBookScore = 0;
        scorePerDay = 0;
        scorePerDayPotential = 0;
    }
    public void resetExtraStatistics(){
        amountOfUniqueBooks = 0;
        valueOfUnique = 0;
        libraryIntersection.clear();
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

    public int getDaysToProcessAllBooks() {
        return daysToProcessAllBooks;
    }

    public void setDaysToProcessAllBooks(int daysToProcessAllBooks) {
        this.daysToProcessAllBooks = daysToProcessAllBooks;
    }

    public float getAvgBookScore() {
        return avgBookScore;
    }

    public void setAvgBookScore(float avgBookScore) {
        this.avgBookScore = avgBookScore;
    }

    public float getScorePerDay() {
        return scorePerDay;
    }

    public void setScorePerDay(float scorePerDay) {
        this.scorePerDay = scorePerDay;
    }

    public float getScorePerDayPotential() {
        return scorePerDayPotential;
    }

    public void setScorePerDayPotential(float scorePerDayPotential) {
        this.scorePerDayPotential = scorePerDayPotential;
    }

    public int getAmountOfUniqueBooks() {
        return amountOfUniqueBooks;
    }

    public void setAmountOfUniqueBooks(int amountOfUniqueBooks) {
        this.amountOfUniqueBooks = amountOfUniqueBooks;
    }

    public int getValueOfUnique() {
        return valueOfUnique;
    }

    public void setValueOfUnique(int valueOfUnique) {
        this.valueOfUnique = valueOfUnique;
    }

    public Set<Library> getLibraryIntersection() {
        return libraryIntersection;
    }
}
