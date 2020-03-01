package com.equals.competition.hashcode2020.strategy.stat;

import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {
    private Map<Integer, LibraryStat> librariesStat;
    private Map<Integer, BookStat> booksStat;
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
            reCalcExtraLibraryStat(libraryStat);
        }

        booksSortedByOccurrenceDesc = booksStat.keySet().stream()
                .sorted(Comparator.comparingInt(bookId -> booksStat.get(bookId).getLibraries().size()))
                .map(booksStat::get)
                .map(BookStat::getBook)
                .collect(Collectors.toList());
    }

    private void reCalcBaseLibraryStat(LibraryStat libraryStat, boolean reCalcBooks) {
        Library library = libraryStat.getLibrary();
        int daysToSignUp = library.isSignedUp() ? 0 : library.getSignUpDuration();
        int daysLeftForScanning = Math.max(0, dayLeft - daysToSignUp);
        int booksCanBeScanned = (int) Math.min((long) daysLeftForScanning * library.getBooksPerDay(), library.getBooks().size());
        int booksNotScanned = 0;
        int minBookScore = Math.min(0, library.getBooks().get(0).getScore());
        int maxBookScore = 0;
        int currentLibraryValue = 0;
        int currentLibraryValueForLeftDays = 0;
        int daysToProcessAllBooks = 0;
        float avgBookScore = 0;
        float scorePerDay = 0;
        float scorePerDayPotential = 0;

        libraryStat.resetBaseStatistics();
        // there is no sense to calc any statistics if library can't scan books anymore
        if (booksCanBeScanned > 0) {
            for (Book book : library.getBooks()) {
                if (!book.isScanned()) {
                    if (reCalcBooks) {
                        BookStat bookStat = booksStat.computeIfAbsent(book.getId(), id -> new BookStat(book));
                        bookStat.getLibraries().add(library);//???
                    }

                    booksNotScanned++;
                    currentLibraryValue += book.getScore();
                    if (booksCanBeScanned >= booksNotScanned) {
                        currentLibraryValueForLeftDays += book.getScore();
                    }
                    minBookScore = Math.min(minBookScore, book.getScore());
                    maxBookScore = Math.max(maxBookScore, book.getScore());
                }
            }

            if (booksNotScanned > 0) {
                daysToProcessAllBooks = daysToSignUp + booksNotScanned / library.getBooksPerDay();
                avgBookScore = (float) currentLibraryValue / booksNotScanned;
                scorePerDay = (float) currentLibraryValueForLeftDays / dayLeft;
                scorePerDayPotential = daysToProcessAllBooks > 0 ? (float) currentLibraryValue / daysToProcessAllBooks : 0.0f;
            }
        }
        libraryStat.setCurrentLibraryValue(currentLibraryValue);
        libraryStat.setCurrentLibraryValueForLeftDays(currentLibraryValueForLeftDays);
        libraryStat.setMinBookScore(minBookScore);
        libraryStat.setMaxBookScore(maxBookScore);
        libraryStat.setBooksCanBeScanned(Math.min(booksCanBeScanned, booksNotScanned));
        libraryStat.setBooksNotScanned(booksNotScanned);
        libraryStat.setDaysToProcessAllBooks(daysToProcessAllBooks);
        libraryStat.setAvgBookScore(avgBookScore);
        libraryStat.setScorePerDay(scorePerDay);
        libraryStat.setScorePerDayPotential(scorePerDayPotential);
    }


    private void reCalcExtraLibraryStat(LibraryStat libraryStat) {
        Library library = libraryStat.getLibrary();
        int amountOfUniqueBooks = 0;
        int valueOfUnique = 0;

        libraryStat.resetExtraStatistics();
        if (libraryStat.getBooksCanBeScanned() > 0) {
            for (Book book : library.getBooks()) {
                BookStat bookStat = getBookStat(book.getId());
                if (!book.isScanned()) {
                    if (bookStat.getLibraries().size() == 1) {
                        amountOfUniqueBooks++;
                        valueOfUnique += book.getScore();
                        bookStat.setAssignedTo(library);
                    } else {
                        libraryStat.getLibraryIntersection().addAll(bookStat.getLibraries());
                    }
                }
            }
            libraryStat.getLibraryIntersection().remove(library);
        }
        libraryStat.setAmountOfUniqueBooks(amountOfUniqueBooks);
        libraryStat.setValueOfUnique(valueOfUnique);
    }

    public Map<Integer, LibraryStat> getLibrariesStat() {
        return librariesStat;
    }

    public Map<Integer, BookStat> getBooksStat() {
        return booksStat;
    }

    public int getDayLeft() {
        return dayLeft;
    }

    public int getLongestSignUp() {
        return longestSignUp;
    }

    public int getShortestSignUp() {
        return shortestSignUp;
    }

    public List<Book> getBooksSortedByOccurrenceDesc() {
        return booksSortedByOccurrenceDesc;
    }

    public LibraryStat getLibraryStat(int libraryId){
        return librariesStat.get(libraryId);
    }

    public BookStat getBookStat(int bookId){
        return booksStat.get(bookId);
    }
}
