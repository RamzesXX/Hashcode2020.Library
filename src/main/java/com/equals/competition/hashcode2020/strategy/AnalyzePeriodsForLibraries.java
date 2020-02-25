package com.equals.competition.hashcode2020.strategy;


import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;
import com.equals.competition.hashcode2020.strategy.stat.Statistics;

import java.util.*;

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


        for (int currentDay = 0; currentDay < 1; currentDay++) {
            getLibraryToSignUp(currentDay);
/*            if (signingUpLibrary != null && currentDay == signingUpLibrary.getSignedUpAt() + signingUpLibrary.getSignUpDuration()) {
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
            }*/
        }

    }

    private Library getLibraryToSignUp(int signUpDay) {
        Statistics statistics = new Statistics(libraries, D - signUpDay);

        return null;
    }
}

