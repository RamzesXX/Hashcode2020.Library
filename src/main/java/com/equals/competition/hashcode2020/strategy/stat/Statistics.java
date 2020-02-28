package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {
    Map<Integer, LibraryStat> librariesStat;
    Map<Integer, BookStat> booksStat;

    private final Library[] libraries;
    private final int dayLeft;
    int longestSignUp;
    int shortestSignUp;
    List<Book> booksSortedByOccurrenceDesc;

    public Statistics(Library[] libraries, int dayLeft) {
        this.libraries = libraries;
        this.dayLeft = dayLeft;
        this.librariesStat = new HashMap<>();
        this.booksStat = new HashMap<>();
        calcStatistic();
    }

    private void calcStatistic() {
        LibraryStat libraryStat;
        longestSignUp = 0;
        shortestSignUp = dayLeft;

        for (Library library : libraries) {
            longestSignUp = Math.max(longestSignUp, library.getSignUpDuration());
            shortestSignUp = Math.min(shortestSignUp, library.getSignUpDuration());
            libraryStat = librariesStat.computeIfAbsent(library.getId(), id -> new LibraryStat(library));
            reCalcBaseLibraryStat(libraryStat, true);
        }

        for (Library library : libraries) {
            libraryStat = librariesStat.get(library.getId());
            libraryStat.amountOfUniqueBooks = 0;
            libraryStat.valueOfUnique = 0;
            for (LibraryScanner.Book book : library.getBooks()) {
                BookStat bookStat = booksStat.get(book.getId());
                if (!book.isScanned()) {
                    if (bookStat.getLibraries().size() == 1) {
                        libraryStat.amountOfUniqueBooks++;
                        libraryStat.valueOfUnique += book.getScore();
                        bookStat.setAssignedTo(library);
                    } else {
                        libraryStat.libraryIntersection.addAll(bookStat.getLibraries());
                    }
                }
            }
            libraryStat.libraryIntersection.remove(library);
        }

        booksSortedByOccurrenceDesc = booksStat.keySet().stream()
                .sorted(Comparator.comparingInt(bookId -> booksStat.get(bookId).getLibraries().size()))
                .map(booksStat::get)
                .map(BookStat::getBook)
                .collect(Collectors.toList());
    }

    private void reCalcBaseLibraryStat(LibraryStat libraryStat, boolean reCalcBooks) {
        Library library = libraryStat.getLibrary();
        int dayToSignUp = library.isSignedUp() ? 0 : library.getSignUpDuration();
        long dayLeftForScanning = Math.max(0, dayLeft - dayToSignUp);
        long booksCanBeScanned = Math.min(dayLeftForScanning * library.getBooksPerDay(), library.getBooks().size());
        int booksNotScanned = 0;
        int minBookScore = Math.min(0, library.getBooks().get(0).getScore());
        int maxBookScore = 0;
        int currentLibraryValue = 0;
        int currentLibraryValueForLeftDays = 0;

        libraryStat.resetBaseStatistics();
        for (Book book : library.getBooks()) {
            if (!book.isScanned()) {
                BookStat bookStat = booksStat.computeIfAbsent(book.getId(), id -> new BookStat(book));
                bookStat.getLibraries().add(library);//???

                booksNotScanned++;
                currentLibraryValue += book.getScore();
                if (booksCanBeScanned > 0) {
                    currentLibraryValueForLeftDays += book.getScore();
                    booksCanBeScanned--;
                }
                minBookScore = Math.min(minBookScore, book.getScore());
                maxBookScore = Math.max(maxBookScore, book.getScore());
            }
        }

        if (booksNotScanned > 0) {
            libraryStat.daysToProcessAllBooks = dayToSignUp + booksNotScanned / library.getBooksPerDay();
            libraryStat.avgBookScore = (float) currentLibraryValue / booksNotScanned;
            libraryStat.scorePerDay = (float) currentLibraryValueForLeftDays / dayLeft;
            libraryStat.scorePerDayPotential = libraryStat.daysToProcessAllBooks > 0 ? (float) currentLibraryValue / libraryStat.daysToProcessAllBooks : 0.0f;
        }

        libraryStat.setCurrentLibraryValue(currentLibraryValue);
        libraryStat.setCurrentLibraryValueForLeftDays(currentLibraryValueForLeftDays);
        libraryStat.setMinBookScore(minBookScore);
        libraryStat.setMaxBookScore(maxBookScore);
        libraryStat.setBooksCanBeScanned(( int) booksCanBeScanned);
        libraryStat.setBooksNotScanned(booksNotScanned);
        libraryStat.booksCanBeScanned = Math.min(libraryStat.booksCanBeScanned, libraryStat.booksNotScanned);
    }
}
