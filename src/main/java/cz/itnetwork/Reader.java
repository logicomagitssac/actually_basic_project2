package cz.itnetwork;

import java.util.Scanner;

public class Reader {

    Scanner scanner = new Scanner(System.in);

    public int readMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1; // stand-in for any "wrong number" which will get handled in worker.goWild()
        }
    }

    public String readSearchString() { // entry attributes also lower-cased for search purposes
        return scanner.nextLine().toLowerCase().trim();
    }

    public String readInputString() { // for names and such (with capital letters and such)
        return scanner.nextLine().trim();
    }

    // method to read timestamps?
}
