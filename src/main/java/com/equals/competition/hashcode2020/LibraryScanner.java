package com.equals.competition.hashcode2020;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Book {
    public static final int NOT_SCANNED = -1;

    private final int id;
    private final int score;
    int scannedAt;
    int scannedBy;

    public Book(int id, int score) {
        this.id = id;
        this.score = score;
        this.scannedAt = NOT_SCANNED;
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

class Library {
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

public class LibraryScanner {
    private static final String[] EXAMPLES = {
            "a_example.txt",
            "b_read_on.txt",
            "c_incunabula.txt",
            "d_tough_choices.txt",
            "e_so_many_books.txt",
            "f_libraries_of_the_world.txt"
    };

    private final String fileName;
    private int B;
    private int L;
    private int D;
    private int[] S;
    private Book[] books;
    private Library[] libraries;
    private int earnedScore;

    public static void main(String[] args) {

        for (String filename : EXAMPLES) {
            Instant start = Instant.now();

            System.out.println("-----------------");
            LibraryScanner libraryScanner = new LibraryScanner(filename);
            System.out.println("Solution for: " + libraryScanner.fileName);
            String solution = libraryScanner.solve();
            System.out.print(solution);
            System.out.println("Earned Score: " + libraryScanner.earnedScore);
            System.out.println("Solution took(ms): " + Duration.between(start, Instant.now()).toMillis());
        }
    }

    public LibraryScanner(String fileName) {
        this.fileName = fileName;
        loadDataFromFile(fileName);
    }

    public String solve() {
        hypothesis_1();

        return getSolution();
    }

    /**
     * First hypothesis: just order libraries by sign-up time in ascending order
     * The first library scans its all books
     * next library process all its books except ones scanned by previous library in the list etc
     */
    private void hypothesis_1() {
        List<Library> signedUpLibraries = new ArrayList<>();
        Arrays.sort(libraries, Comparator.comparingInt(Library::getSignUpDuration));
        int currentDay = 0;
        int currentLibraryIndex = 0;

        while (currentDay < D && currentLibraryIndex < libraries.length) {
            Library library = libraries[currentLibraryIndex];

            library.signUp(currentDay);
            libraries[0].sendBooksToScan(currentDay + library.getSignUpDuration(), D);

            currentLibraryIndex++;
            currentDay += library.getSignUpDuration();

        }


    }

    private void loadDataFromFile(String fileName) {
        ClassLoader classLoader = Library.class.getClassLoader();

        try (Scanner scanner = new Scanner(classLoader.getResourceAsStream(fileName))) {
            B = scanner.nextInt();
            L = scanner.nextInt();
            D = scanner.nextInt();
            S = new int[B];
            books = new Book[B];
            libraries = new Library[L];

            for (int i = 0; i < B; i++) {
                books[i] = new Book(i, scanner.nextInt());
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

    private String getSolution() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        List<Library> signedUpLibraries = Arrays.stream(libraries)
                .filter(library -> library.isSignedUp() && !library.getScannedByLibraryBooks().isEmpty())
                .filter(Library::isSignedUp)
                .sorted(Comparator.comparingInt(Library::getSignedUpAt))
                .collect(Collectors.toList());

        writer.println(signedUpLibraries.size()); // A
        signedUpLibraries.forEach(library -> {
            writer.println(library.getId() + " " + library.getScannedByLibraryBooks().size()); // Y K
            String scannedBooks = library.getScannedByLibraryBooks().stream()
                    .map(Book::getId)
                    .map(String::valueOf)
                    .collect(Collectors.joining(" "));
            writer.println(scannedBooks); // [k]
            earnedScore += library.getEarnedScore();
        });

        return stringWriter.toString();
    }


}
