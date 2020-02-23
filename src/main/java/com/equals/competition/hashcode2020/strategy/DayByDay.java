package com.equals.competition.hashcode2020.strategy;


import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.ArrayList;
import java.util.List;

/**
 * Go day by day and check for each day the best thing to do (best score)
 *
 * BestScore is 10842070
 */
public class DayByDay implements Strategy {
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

            //for each signed up library determine which  books and order of libraries

            if (signingUpLibrary == null) {
                //find library
                signingUpLibrary = libraries[0];
                signingUpLibrary.signUp(currentDay);
            }
        }
/*

        while (currentDay < D && currentLibraryIndex < libraries.length) {
            Library library = libraries[currentLibraryIndex];

            library.signUp(currentDay);
            library.sendBooksToScan(currentDay + library.getSignUpDuration(), D - 1);

            currentLibraryIndex++;
            currentDay += library.getSignUpDuration();
        }*/
    }
}
