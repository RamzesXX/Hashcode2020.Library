package com.equals.competition.hashcode2020.strategy;


import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Go day by day and check for each day the best thing to do (best score)
 *
 * BestScore is 18145900 (when we take signed up library in ascending order of value for a day - 18145921)
 */
public class DayByDay implements Strategy {
    private int D;
    private Library[] libraries;
    private Book[] books;
    private List<Library> signedUpLibraries = new ArrayList<>();

    @Override
    public void useStrategy(LibraryScanner libraryScanner) {
        Library signingUpLibrary = null;

        libraries = libraryScanner.getLibraries();
        books = libraryScanner.getBooks();
        D = libraryScanner.getD();

        for (int currentDay = 0; currentDay < D; currentDay++) {
            if (signingUpLibrary != null && currentDay == signingUpLibrary.getSignedUpAt() + signingUpLibrary.getSignUpDuration()) {
                signedUpLibraries.add(signingUpLibrary);
                signingUpLibrary = null;
            }

            if (!signedUpLibraries.isEmpty()){
                signedUpLibraries.sort(byValueForOneDay());
                for (Library library : signedUpLibraries) {
                    library.sendBooksToScan(currentDay);
                }
            }

            if (signingUpLibrary == null) {
                signingUpLibrary = getLibraryToSignUp(currentDay);
                if (signingUpLibrary != null) {
                    signingUpLibrary.signUp(currentDay);
                }
            }
        }
    }

    private Comparator<Library> byValueForOneDay() {
        //by changing order to ascending we get small profit
        // return Comparator.comparingInt(library -> getValueForOneDay(library));
        return Comparator.comparingInt(library -> -getValueForOneDay(library));
    }

    private int getValueForOneDay(Library library){
        int value = library.getBooks().stream()
                .filter(book -> !book.isScanned())
                .mapToInt(Book::getScore)
                .limit(library.getBooksPerDay())
                .sum();

        return value;
    }

    private Library getLibraryToSignUp(final int signUpDay) {
        int value = 0;
        Library library = null;
        for (Library lib :libraries) {
            int currentValue = getValueForPeriod(lib, signUpDay);
            if (currentValue > value && !lib.isSignedUp()) {
                value = currentValue;
                library = lib;
            }
        }
        return library;
    }

    private int getValueForPeriod(Library library, int signUpDay) {
        long booksCanBeScannedForPeriod = (long) Math.max(0, D - signUpDay - library.getSignUpDuration()) * library.getBooksPerDay();

        return library.getBooks().stream()
                .filter(book -> !book.isScanned())
                .mapToInt(LibraryScanner.Book::getScore)
                .limit(booksCanBeScannedForPeriod)
                .sum();

    }
}
