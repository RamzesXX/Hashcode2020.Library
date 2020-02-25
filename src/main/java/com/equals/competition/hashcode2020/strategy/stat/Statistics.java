 package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {
    Map<Integer, LibraryStat> librariesStat;
    Map<Integer, BookStat> booksStat;

    private final LibraryScanner.Library[] libraries;
    private final int dayLeft;
    int longestSignUp;
    int shortestSignUp;
    List<LibraryScanner.Book> booksSortedByOccurrenceDesc;

    public Statistics(LibraryScanner.Library[] libraries, int dayLeft) {
        this.libraries = libraries;
        this.dayLeft = dayLeft;
        this.librariesStat = new HashMap<>();
        this.booksStat = new HashMap<>();
        calcStatistic();
    }

    private void calcStatistic() {
        longestSignUp = 0;
        shortestSignUp = dayLeft;
        for (LibraryScanner.Library library : libraries) {
            LibraryStat libraryStat = librariesStat.computeIfAbsent(library.getId(), id -> new LibraryStat());
            libraryStat.library = library;
            libraryStat.minBookScore = library.getBooks().isEmpty() ? 0 : library.getBooks().get(0).getScore();
            long booksCanBeScanned = (long)Math.max(0, dayLeft - (library.isSignedUp() ? 0 : library.getSignUpDuration())) * library.getBooksPerDay();
            booksCanBeScanned = Math.min(booksCanBeScanned, library.getBooks().size());
            libraryStat.booksCanBeScanned = (int)booksCanBeScanned;
            longestSignUp = Math.max(longestSignUp, library.getSignUpDuration());
            shortestSignUp = Math.min(shortestSignUp, library.getSignUpDuration());
            for (LibraryScanner.Book book : library.getBooks()) {
                if (!book.isScanned()) {
                    BookStat bookStat = booksStat.computeIfAbsent(book.getId(), id -> new BookStat());
                    bookStat.book = book;
                    bookStat.libraries.add(library);

                    libraryStat.booksNotScanned++;
                    libraryStat.currentLibraryValue += book.getScore();
                    if (booksCanBeScanned > 0) {
                        libraryStat.currentLibraryValueForLeftDays += book.getScore();
                        booksCanBeScanned--;
                    }
                    libraryStat.minBookScore = Math.min(libraryStat.minBookScore, book.getScore());
                    libraryStat.maxBookScore = Math.max(libraryStat.maxBookScore, book.getScore());
                }
            }

            if (libraryStat.booksNotScanned > 0) {
                int timeToSignUp = library.isSignedUp() ? 0 : library.getSignUpDuration();
                libraryStat.daysToProcessAllBooks = timeToSignUp + libraryStat.booksNotScanned / library.getBooksPerDay();
                libraryStat.avgBookScore = (float) libraryStat.currentLibraryValue / libraryStat.booksNotScanned;
                libraryStat.scorePerDay = (float) libraryStat.currentLibraryValueForLeftDays / dayLeft;
                libraryStat.scorePerDayPotential = libraryStat.daysToProcessAllBooks > 0 ? (float) libraryStat.currentLibraryValue / libraryStat.daysToProcessAllBooks : 0.0f;
            }

            libraryStat.booksCanBeScanned = Math.min(libraryStat.booksCanBeScanned,  libraryStat.booksNotScanned);
        }

        booksSortedByOccurrenceDesc = booksStat.keySet().stream()
                .sorted(Comparator.comparingInt(bookId -> booksStat.get(bookId).libraries.size()))
                .map(booksStat::get)
                .map(BookStat::getBook)
                .collect(Collectors.toList());

        for (LibraryScanner.Library library : libraries) {
            LibraryStat libraryStat = librariesStat.get(library.getId());
            libraryStat.amountOfUniqueBooks = 0;
            libraryStat.valueOfUnique = 0;
            for (LibraryScanner.Book book : library.getBooks()) {
                BookStat bookStat = booksStat.get(book.getId());
                if (!book.isScanned()) {
                    if (bookStat.libraries.size() == 1) {
                        libraryStat.amountOfUniqueBooks++;
                        libraryStat.valueOfUnique += book.getScore();
                    } else {
                        libraryStat.libraryIntersection.addAll(bookStat.libraries);
                    }
                }
            }
            libraryStat.libraryIntersection.remove(library);
        }
    }
}
