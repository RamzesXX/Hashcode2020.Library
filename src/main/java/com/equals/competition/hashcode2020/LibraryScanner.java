package com.equals.competition.hashcode2020;


import java.util.Scanner;

public class LibraryScanner {
    private static final String[] EXAMPLES = {"a_example.txt", "b_read_on.txt", "c_incunabula.txt", "d_tough_choices.txt", "e_so_many_books.txt", "f_libraries_of_the_world.txt"};
    private int booksAmount;
    private int libraryAmount;
    private int days;
    private int[] score;
    private Library[] labraries;


    public LibraryScanner(String fileName) {
        loadDataFromFile(fileName);
    }

    public static void main(String[] args) {
        LibraryScanner libraryScanner = new LibraryScanner(EXAMPLES[0]);
    }

    private int solve(int index, int maxSlices) {

        return 0;
    }

    private void loadDataFromFile(String fileName) {
        ClassLoader classLoader = Library.class.getClassLoader();

        try (Scanner scanner = new Scanner(classLoader.getResourceAsStream(fileName))) {
            booksAmount = scanner.nextInt();
            libraryAmount = scanner.nextInt();
            days = scanner.nextInt();
            score = new int[booksAmount];
            labraries = new Library[libraryAmount];

            for (int i = 0; i < booksAmount; i++) {
                score[i] = scanner.nextInt();
            }

            for (int i = 0; i < libraryAmount; i++) {
                int numberOfBooks = scanner.nextInt();
                int signupTime = scanner.nextInt();
                int numberBooksPerDay = scanner.nextInt();

                int[] books = new int[numberOfBooks];
                for (int j = 0; j < numberBooksPerDay; j++) {
                    books[j] = scanner.nextInt();
                }

                labraries[i] = new Library(numberOfBooks, signupTime, numberBooksPerDay, books);
            }

        }

    }

    public static class Library {
        int numberOfBooks;
        int signupTime;
        int numberBooksPerDay;

        int[] books;

        public Library(int numberOfBooks, int signupTime, int numberBooksPerDay, int[] books) {
            this.numberOfBooks = numberOfBooks;
            this.signupTime = signupTime;
            this.numberBooksPerDay = numberBooksPerDay;
            this.books = books;
        }
    }
}
