package com.equals.competition.hashcode2020;

import com.equals.competition.hashcode2020.strategy.Strategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryScanner {
    private final Strategy strategy;
    private final String fileName;

    private int B;
    private int L;
    private int D;
    private Book[] books;
    private Library[] libraries;
    private int earnedScore;

    public static class Book {
        public static final int NOT_SCANNED = -1;

        private final int id;
        private final int score;
        int scannedAt;
        int scannedBy;

        public Book(int id, int score) {
            this.id = id;
            this.score = score;
            this.scannedAt = NOT_SCANNED;
            this.scannedBy = NOT_SCANNED;
        }

        public int getId() {
            return id;
        }

        public int getScore() {
            return score;
        }

        public boolean isScanned() {
            return scannedAt != NOT_SCANNED;
        }

        public boolean isScanned(int day) {
            return scannedAt != NOT_SCANNED && day < scannedAt;
        }

        public void scan(int day, int library) {
            this.scannedAt = day;
            this.scannedBy = library;
        }

        public void undoScan() {
            this.scannedAt = NOT_SCANNED;
            this.scannedBy = NOT_SCANNED;
        }
    }

    public static class Library {
        public static final int NOT_SIGNED_UP = -1;

        private final int id;
        private final int size;
        private final int signUpDuration;
        private final int booksPerDay;
        private final List<Book> scannedByLibraryBooks;
        private final List<Book> books;

        private int signedUpAt;
        private int earnedScore;

        public Library(int id, int size, int signUpDuration, int booksPerDay, List<Book> books) {
            this.id = id;
            this.size = size;
            this.signUpDuration = signUpDuration;
            this.booksPerDay = booksPerDay;
            this.books = books;
            this.books.sort(Comparator.comparingInt(book -> -book.getScore()));
            this.scannedByLibraryBooks = new ArrayList<>();
            this.signedUpAt = NOT_SIGNED_UP;
            this.earnedScore = 0;
        }

        public int getId() {
            return id;
        }

        public int getSize() {
            return size;
        }

        public int getSignUpDuration() {
            return signUpDuration;
        }

        public int getBooksPerDay() {
            return booksPerDay;
        }

        public int getSignedUpAt() {
            return signedUpAt;
        }

        public int getEarnedScore() {
            return earnedScore;
        }

        public List<Book> getScannedByLibraryBooks() {
            return scannedByLibraryBooks;
        }

        public List<Book> getBooks() {
            return books;
        }

        public boolean isSignedUp() {
            return signedUpAt != NOT_SIGNED_UP;
        }

        public void signUp(int day) {
            this.signedUpAt = day;
        }

        public void sendBooksToScan(int dayWhenScanStart) {
            sendBooksToScan(dayWhenScanStart, dayWhenScanStart);
        }

        public void sendBooksToScan(int dayWhenScanStart, int dayWhenScanStop) {
            if (signedUpAt != NOT_SIGNED_UP) {
                for (int currentDay = Math.max(dayWhenScanStart, signUpDuration + signedUpAt); currentDay <= dayWhenScanStop; currentDay++) {
                    int booksToScanLeft = booksPerDay;
                    while (!books.isEmpty() && booksToScanLeft > 0) {
                        Book book = books.remove(0);
                        if (!book.isScanned()) {
                            book.scan(currentDay, id);
                            scannedByLibraryBooks.add(book);
                            earnedScore += book.getScore();
                            booksToScanLeft--;
                        }
                    }
                    if (books.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }

    public LibraryScanner(String fileName, Strategy strategy) {
        this.fileName = fileName;
        this.strategy = strategy;
        loadDataFromFile(fileName);
    }


    public String getFileName() {
        return fileName;
    }

    public int getB() {
        return B;
    }

    public int getL() {
        return L;
    }

    public int getD() {
        return D;
    }

    public Book[] getBooks() {
        return books;
    }

    public Library[] getLibraries() {
        return libraries;
    }

    public int getEarnedScore() {
        return earnedScore;
    }

    /**
     * Solve the current task using hypothesis which describe algorithm
     * @return
     */
    public String solve() {
        strategy.useStrategy(this);

        return getSolution();
    }

    /**
     * Load data from file, the file should be present within classpath
     * @param fileName
     */
    private void loadDataFromFile(String fileName) {
        ClassLoader classLoader = Library.class.getClassLoader();

        try (Scanner scanner = new Scanner(classLoader.getResourceAsStream(fileName))) {
            B = scanner.nextInt();
            L = scanner.nextInt();
            D = scanner.nextInt();
            books = new Book[B];
            libraries = new Library[L];

            for (int i = 0; i < B; i++) {
                int score = scanner.nextInt();
                books[i] = new Book(i, score);
            }

            for (int i = 0; i < L; i++) {
                int numberOfBooks = scanner.nextInt();
                int signUpDuration = scanner.nextInt();
                int numberBooksPerDay = scanner.nextInt();
                List<Book> libraryBooks = new ArrayList<>();

                for (int j = 0; j < numberOfBooks; j++) {
                    libraryBooks.add(books[scanner.nextInt()]);
                }

                libraries[i] = new Library(i, numberOfBooks, signUpDuration, numberBooksPerDay, libraryBooks);
            }
        }
    }

    /**
     *
     * @return string representing solution in form
     *  first line is an integer number of libraries to sign up (A)
     *  following by A blocks from 2 lines for each library
     *  ID of library (Y) and number f books to be scanned from library Y (K)
     *  IDs of books scanned from that library Y
     */
    private String getSolution() {
        final StringBuilder solutionStringBuilder = new StringBuilder();

        List<Library> signedUpLibraries = Arrays.stream(libraries)
                .filter(library -> library.isSignedUp() && !library.getScannedByLibraryBooks().isEmpty())
                .filter(Library::isSignedUp)
                .sorted(Comparator.comparingInt(Library::getSignedUpAt))
                .collect(Collectors.toList());
        earnedScore = 0;
        solutionStringBuilder.append(signedUpLibraries.size()); // A
        signedUpLibraries.forEach(library -> {
            solutionStringBuilder.append(library.getId()).append(" ").append(library.getScannedByLibraryBooks().size()); // Y K
            String scannedBooks = library.getScannedByLibraryBooks().stream()
                    .map(Book::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(" "));
            solutionStringBuilder.append(scannedBooks); // [k]
            earnedScore += library.getEarnedScore();
        });

        return solutionStringBuilder.toString();
    }
}
