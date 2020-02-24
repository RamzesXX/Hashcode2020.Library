package com.equals.competition.hashcode2020.strategy;


import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BestScore is
 */
public class AnalyzePeriodsForLibraries implements Strategy {
    private int D;
    private Library[] libraries;
    private Book[] books;
    List<Library> signedUpLibraries = new ArrayList<>();

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

            if (!signedUpLibraries.isEmpty()) {
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

    private Library getLibraryToSignUp(int signUpDay) {
        Map<Integer, LibraryStat> libraryStat = Arrays.stream(libraries).collect(Collectors.toMap(Library::getId, library -> new LibraryStat(library, D - signUpDay)));
        Library library = null;

        return library;
    }
}


class LibraryStat {
    Library library;
    int dayLeft;
    int daysToProcessAllBooks;
    int booksNotScanned;
    int booksCanBeScanned;
    int currentLibraryValue;
    int currentLibraryValueForLeftDays;
    int minBookScore;
    int maxBookScore;
    float avgBookScore;
    float scorePerDay;
    float scorePerDayPotential;

    public LibraryStat(Library library, int dayLeft) {
        this.library = library;
        this.dayLeft = dayLeft;
        calcStatistic();
    }

    public void calcStatistic() {
        booksNotScanned = 0;
        currentLibraryValue = 0;
        currentLibraryValueForLeftDays = 0;
        minBookScore = library.getBooks().isEmpty() ? 0 : library.getBooks().get(0).getScore();
        booksCanBeScanned = Math.max(0, dayLeft - (library.isSignedUp() ? 0 : library.getSignUpDuration())) * library.getBooksPerDay();
        maxBookScore = 0;
        avgBookScore = 0.0f;

        for (Book book : library.getBooks()) {
            if (!book.isScanned()) {
                booksNotScanned++;
                currentLibraryValue += book.getScore();
                if (booksCanBeScanned > 0) {
                    currentLibraryValueForLeftDays += book.getScore();
                    booksCanBeScanned--;
                }
                minBookScore = Math.min(minBookScore, book.getScore());
                maxBookScore = Math.min(maxBookScore, book.getScore());
            }
        }



        if (booksNotScanned > 0) {
            int timeToSignUp = library.isSignedUp() ? 0 : library.getSignUpDuration();
            daysToProcessAllBooks = timeToSignUp + booksNotScanned / library.getBooksPerDay();
            avgBookScore = (float) currentLibraryValue / booksNotScanned;
            scorePerDay = (float) currentLibraryValueForLeftDays / dayLeft;
            scorePerDayPotential =  daysToProcessAllBooks > 0 ? (float) currentLibraryValue / daysToProcessAllBooks : 0.0f;
        }
    }
}
