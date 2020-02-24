package com.equals.competition.hashcode2020.strategy;

import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Just orders libraries
 * The first library scans its all books
 * next library process all its books except the ones
 * scanned by previous library in the list etc
 */
public class OrderLibraries implements Strategy {
    private int D;
    private Library[] libraries;

    @Override
    public void useStrategy(LibraryScanner libraryScanner) {
        int currentDay = 0;
        int currentLibraryIndex = 0;

        libraries = libraryScanner.getLibraries();
        D = libraryScanner.getD();
        Arrays.sort(libraries, byLibraryDayProfitDesc());
        while (currentDay < D && currentLibraryIndex < libraries.length) {
            Library library = libraries[currentLibraryIndex];

            library.signUp(currentDay);
            library.sendBooksToScan(currentDay + library.getSignUpDuration(), D - 1);

            currentLibraryIndex++;
            currentDay += library.getSignUpDuration();
        }
    }

    /**
     * Orders libraries by sign-up time in ascending order
     *
     * BestScore is 22092944
     */
    private Comparator<Library> bySignUpDurationAsc() {
        return Comparator.comparingInt(Library::getSignUpDuration);
    }

    /**
     * Orders libraries by books-per-day in descending order
     *
     * BestScore is 10842070
     */
    private Comparator<Library> byBooksPerDayDesc() {
        return Comparator.comparingInt(Library::getBooksPerDay).reversed();
    }

    /**
     * Orders libraries by earned score for whole period in descending order
     *
     * BestScore is 10842070
     */
    private Comparator<Library> byValueForPeriodDesc() {
        return Comparator.comparingInt(library -> -valueForPeriod(library, D));
    }

    public int valueForPeriod(Library library, int periodLengthDays) {
        long booksCanBeScannedForPeriod = (long) Math.max(0, periodLengthDays - library.getSignUpDuration()) * library.getBooksPerDay();

        int value = library.getBooks().stream()
                .filter(book -> !book.isScanned())
                .mapToInt(LibraryScanner.Book::getScore)
                .limit(booksCanBeScannedForPeriod)
                .sum();

        return value;
    }

    /**
     * Orders libraries by earned score for whole period in descending order
     *
     * BestScore is 15438747
     */
    private Comparator<Library> byLibraryDayProfitDesc() {
        return Comparator.comparingDouble(library -> -maxLibraryDayProfit(library, D));
    }

    public double maxLibraryDayProfit(Library library, int periodLengthDays) {
        long booksCanBeScannedForPeriod = (long) Math.max(0, periodLengthDays - library.getSignUpDuration()) * library.getBooksPerDay();

        if (booksCanBeScannedForPeriod == 0) {
            return 0.0;
        }
        int value = library.getBooks().stream()
                .filter(book -> !book.isScanned())
                .mapToInt(LibraryScanner.Book::getScore)
                .limit(booksCanBeScannedForPeriod)
                .sum();

        return (double) value / periodLengthDays;
    }
}
