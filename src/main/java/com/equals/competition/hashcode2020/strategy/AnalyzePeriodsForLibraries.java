package com.equals.competition.hashcode2020.strategy;


import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;
import com.equals.competition.hashcode2020.strategy.stat.LibraryStat;
import com.equals.competition.hashcode2020.strategy.stat.Statistics;

import java.util.*;

/**
 * BestScore is
 */
public class AnalyzePeriodsForLibraries extends Strategy {
    private List<Library> signedUpLibraries = new ArrayList<>();
    private Statistics statistics;

    @Override
    public void useStrategy(LibraryScanner libraryScanner) {
        Library signingUpLibrary = null;

        libraries = libraryScanner.getLibraries();
        books = libraryScanner.getBooks();
        D = libraryScanner.getD();


        for (int currentDay = 0; currentDay < D; currentDay++) {
            statistics = new Statistics(libraries, D - currentDay);
           /* getLibraryToSignUp(currentDay);
            if (signingUpLibrary != null && currentDay == signingUpLibrary.getSignedUpAt() + signingUpLibrary.getSignUpDuration()) {
                signedUpLibraries.add(signingUpLibrary);
                signingUpLibrary = null;
            }

            if (!signedUpLibraries.isEmpty()) {
                signedUpLibraries.sort(Comparator.comparingInt(library -> {
                    LibraryStat libraryStat = statistics.getLibrariesStat().get(library.getId());
                    return libraryStat.getCurrentLibraryValueForLeftDays();
                }));
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

