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
    final int id;
    final int score;
    boolean isScanned;

    public Book(int id, int score) {
        this.id = id;
        this.score = score;
        this.isScanned = false;
    }
}

class Library {
    public static final int DAY_TO_START_FOR_NOT_SIGNED_UP = -1;
    int id;
    int size;
    int signup;
    int booksPerDay;
    int dayToStart;
    List<Book> scannedBooks;
    List<Book> books;

    public Library(int id, int size, int signup, int booksPerDay, List<Book> books) {
        this.id = id;
        this.size = size;
        this.signup = signup;
        this.booksPerDay = booksPerDay;
        this.books = books;
        this.dayToStart = DAY_TO_START_FOR_NOT_SIGNED_UP;
    }

    public boolean isSignedUp() {
        return dayToStart != DAY_TO_START_FOR_NOT_SIGNED_UP;
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

    private String fileName;
    private int B;
    private int L;
    private int D;
    private int[] S;
    private Book[] books;
    private Library[] libraries;

    public LibraryScanner(String fileName) {
        loadDataFromFile(fileName);
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
                int signupTime = scanner.nextInt();
                int numberBooksPerDay = scanner.nextInt();
                List<Book> libraryBooks = new ArrayList<>();

                for (int j = 0; j < numberOfBooks; j++) {
                    libraryBooks.add(books[scanner.nextInt()]);
                }
                libraryBooks.sort(Comparator.comparingInt(book -> -book.score));
                libraries[i] = new Library(i, numberOfBooks, signupTime, numberBooksPerDay, libraryBooks);
            }
        }
    }

    private void printResult() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        List<Library> signedUpLibraries = Arrays.stream(libraries)
                .filter(Library::isSignedUp)
                .sorted(Comparator.comparingInt(library -> library.dayToStart))
                .collect(Collectors.toList());

        int A = signedUpLibraries.size();
        writer.println(A);
        signedUpLibraries.forEach(library -> {
            int Y = library.id;
            writer.print(Y);
            writer.print(" ");
            int K = library.scannedBooks.size();
            writer.println(K);
            for (Book book : library.scannedBooks) {
                writer.print(book.id);
                writer.print(" ");
            }
            writer.println();
        });
    }

    public static void main(String[] args) {
        for (String filename : EXAMPLES) {
            System.out.println("-------" + filename + "----------");
            Instant start = Instant.now();
            LibraryScanner libraryScanner = new LibraryScanner(filename);
            Instant end = Instant.now();
            System.out.println(Duration.between(start, end));
        }


    }

}
