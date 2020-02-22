package com.equals.competition.hashcode2020;

import com.equals.competition.hashcode2020.hypothesis.Hypothesis;
import com.equals.competition.hashcode2020.hypothesis.OrderLibraries;

import java.time.Duration;
import java.time.Instant;

public class LibraryScannerApp {
    private static final String[] EXAMPLES = {
            "a_example.txt",
            "b_read_on.txt",
            "c_incunabula.txt",
            "d_tough_choices.txt",
            "e_so_many_books.txt",
            "f_libraries_of_the_world.txt"
    };

    public static void main(String[] args) {
        int fullEarnedScore = 0;
        int fullTime = 0;
        boolean showSolution = false;
        Hypothesis hypothesis = new OrderLibraries();
//        String filename = EXAMPLES[0];

        for (String filename : EXAMPLES) {
            Instant start = Instant.now();

            System.out.println("-----------------");
            LibraryScanner libraryScanner = new LibraryScanner(filename, hypothesis);
            System.out.println("Solution for: " + libraryScanner.getFileName());
            String solution = libraryScanner.solve();
            if (showSolution) {
                System.out.print(solution);
            }
            System.out.println("Earned Score: " + libraryScanner.getEarnedScore());
            System.out.println("Solution took(ms): " + Duration.between(start, Instant.now()).toMillis());

            fullTime += Duration.between(start, Instant.now()).toMillis();
            fullEarnedScore += libraryScanner.getEarnedScore();
        }
        System.out.println("=======================");
        System.out.println("Earned Score for all libraries: " + fullEarnedScore);
        System.out.println("Full time: " + fullTime);
    }
}
