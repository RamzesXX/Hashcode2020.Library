package com.equals.competition.hashcode2020.strategy;


import com.equals.competition.hashcode2020.LibraryScanner;
import com.equals.competition.hashcode2020.LibraryScanner.Book;
import com.equals.competition.hashcode2020.LibraryScanner.Library;
import com.equals.competition.hashcode2020.strategy.stat.BookStat;
import com.equals.competition.hashcode2020.strategy.stat.Statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
// Сканнирования перекрываются - значит что это не сумма всех сканов  и сайн апов
/**
 * BestScore is
 */
public class StatisticsBasedOnBookValues extends Strategy {
    private Statistics statistics;
    private Set<Library> signedUpLibraries;
    private Set<Book> booksToScan;

    @Override
    public void useStrategy(LibraryScanner libraryScanner) {
        libraries = libraryScanner.getLibraries();
        books = libraryScanner.getBooks();
        D = libraryScanner.getD();
        statistics = new Statistics(libraries, D);

        int score = 0;
        float timeToScanAllBooks = 0;
        booksToScan = new HashSet<>();
        signedUpLibraries = new HashSet<>();
        List<Book> orderedByScoreBooks = getBooksOrdered();

        for (Book book : orderedByScoreBooks) {
            BookStat bookStat = statistics.getBookStat(book.getId());
            if (bookStat == null) {
                continue;
            }
            Library bestLibrary = getLibraryWithMinTimeToGetBook(bookStat.getLibraries());
            float timeToScanBook = 1f / bestLibrary.getBooksPerDay();
            if (!signedUpLibraries.contains(bestLibrary)) {
                timeToScanBook += bestLibrary.getSignUpDuration();
            }

            if (timeToScanAllBooks + timeToScanBook <= D + 1) {
                timeToScanAllBooks += timeToScanBook;
                score += book.getScore();
                signedUpLibraries.add(bestLibrary);
                booksToScan.add(book);
            }
        }

        System.out.printf("\t\tScore %d\n \t\tTime to scan %.2f (%d)\n", score, timeToScanAllBooks, D);
    }

    /**
     * 1. by score (Book::getScore)
     * 2. by score * occurrences
     *
     * @return
     */
    private List<Book> getBooksOrdered() {
        return Arrays.stream(books)
//                .sorted(Comparator.comparingLong(Book::getScore).reversed())
                .sorted(Comparator.comparingLong(book -> {
                    BookStat bookStat = statistics.getBookStat(book.getId());
                    if (bookStat != null) {
                        return (-(long) book.getScore() * bookStat.getLibraries().size());
                    }
                    return 0;

                }))
                .collect(Collectors.toList());
    }

    /**
     * mintime =  signuptime + scantime
     *
     * @param libraries
     * @return
     */
    private Library getLibraryWithMinTimeToGetBook(List<Library> libraries) {
        Library bestLibrary = libraries.get(0);
        float bestTimeToScanBook = 1f / bestLibrary.getBooksPerDay();
        if (!signedUpLibraries.contains(bestLibrary)) {
            bestTimeToScanBook += bestLibrary.getSignUpDuration();
        }

        for (Library library : libraries) {
            float timeToScanBook = 1f / library.getBooksPerDay();
            if (!signedUpLibraries.contains(library)) {
                timeToScanBook += library.getSignUpDuration();
            }
            if (bestTimeToScanBook > timeToScanBook) {
                bestLibrary = library;
                bestTimeToScanBook = timeToScanBook;
            }
        }

        return bestLibrary;
    }
}

