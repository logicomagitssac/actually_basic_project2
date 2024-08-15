package cz.itnetwork;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Writer { // all print/println strings are vertically aligned in the code directly below each other for purposes of approximate visual reference
    public void writeIntro() {
        try {
            //File introText = new File(System.getProperty("user.dir") + File.separator + "ascii_greeting.txt"); // ChatGPT droppin' much wisdoms...
            //System.out.println(System.getProperty("user.dir")); // just to check
            InputStream inputStream = getClass().getResourceAsStream("/ascii_greeting.txt");
            if (inputStream != null) {
                Scanner sc = new Scanner(inputStream);
                while (sc.hasNextLine()) {
                    System.out.println(sc.nextLine());
                    //thread sleep for dramatic effect
                    Thread.sleep(500);
                }
                Thread.sleep(1000);
            }
            else {
                System.out.println(
                "   Intro ASCII file not found..."
                );
            }
        } catch (InterruptedException ignored) {
            // ignored
        }
        System.out.print(
                "   Press [Enter] to begin... "
        );
    }

    public void clearThatScreenYeahYouClearThatScreenHard() { // does not work
        // still not working - probably will never work in IDE pseudo-console
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                // For Unix-like systems (Linux, macOS)
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            } else if (os.contains("win")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unsupported operating system
                Runtime.getRuntime().exec("clear");
                //System.out.println("Console clearing not supported on this operating system.");
            }
            // so... it does not throw exception cos it doesn't print the fail...
            // but instead of clearing screen it prints some weird "" character
        } catch (IOException | InterruptedException e) {
            System.out.println(
                "\n----------------------Clear-screen failed.----------------------\n" // so that I notice it
            );
        }
//        try this
//        printf("\033[2J"); // Clears the entire screen
//        printf("\033[H");  // Moves the cursor to the home position
//        stack overflow CTRL+C >> CTRL+V did not work...
    }

    public void writeMenuHeader() {
        System.out.println(
                "                                                                                    \n" +
                " ┳┓•    ┳┓╹   ┏┓       •      ┏┓      ┓•          ┓  ┏┓          ┓  ┏┓          •   \n" +
                " ┣┫┓┏┓  ┃┃ ┏  ┣ ┏┓┏┓┏┳┓┓┏┓┏┓  ┗┓┓┏┏┓┏┓┃┓┏┓┏  ┏┓┏┓┏┫  ┣┫┏┏┏┓┏┓╋┏┓┏┫  ┣┫┏┏┏┓┏┏┏┓┏┓┓┏┓┏\n" +
                " ┻┛┗┗┫  ┻┛ ┛  ┻ ┗┻┛ ┛┗┗┗┛┗┗┫  ┗┛┗┻┣┛┣┛┗┗┗ ┛  ┗┻┛┗┗┻  ┛┗┛┛┗┛┛ ┗┗ ┗┻  ┛┗┗┗┗ ┛┛┗┛┛ ┗┗ ┛\n" +
                "     ┛                     ┛      ┛ ┛                                               \n"
        );
    }

    public void writeMenuDisclaimers() {
        System.out.println(
                "   In case of The Poe-Liece, press [ESC] at any time to immediately kill the app.\n" +
                "   To select/edit an entry, search for a specific one.\n"
        );
    }

    public void writeMenuOptions(int menuContextCode) {
        switch (menuContextCode) { // I know ENUM would work here, but not doing it...
            case 0: // basic menu - no entry selected
                System.out.println(
                "\n" +
                "   Do what now?\n" +
                "       [1] Show list of current entries\n" +
                "       [2] Add new entry\n" +
                "       [3] Search entries\n" +
                "       [4] Show deleted entries\n" +
                "       [8] Clear screen\n" +
                "       [9] Restart session\n" +
                "       [0] Quit session\n"
                );
                break;
            case 1: // after search, if exactly one entry found
                System.out.println(
                "\n" +
                "   Do what now?\n" +
                "       [1] Show list of current entries\n" +
                "       [2] Add new entry\n" +
                "       [3] Search entries\n" +
                "       [4] Show deleted entries\n" +
                "       [5] Modify entry\n" +
                "       [6] Delete entry\n" +
                "       [8] Clear screen\n" +
                "       [9] Restart session\n" +
                "       [0] Quit session\n"
                );
                break;
            case 2: // modification context
                System.out.println(
                "\n" +
                "   Modify which attribute?\n" +
                "       [1] Client name\n" +
                "       [2] Ordered goods\n" +
                "       [3] Amount\n" +
                "       [4] Price\n" +
                "       [5] Note\n" +
                "       [6] Status\n" +
                "       [9] Finish modification\n" +
                "       [0] Cancel modification"
                );
                break;
//            case 3: // different context
//                System.out.println(
//                "       [5] do dis\n" +
//                "       [6] do dat");
//                break;
//            case 4: // yet another context
//                System.out.println(
//                "       [5] do another stuff\n" +
//                "       [6] do yet more stuff");
//                break;
        }
    }

    public void writeBadInputRebuke() {
        System.out.println(
                "   Nuh uh! Wrong input...\n" +
                "   Try again.\n"
        );
    }

    public void writeMenuChoicePrompt() {
        System.out.print(
                "   Your choice: "
        );
    }

    public void writeAttributeInputPrompt(String attribute) {
        System.out.print(
                "   Please enter " + attribute + ": "
        );
    }

    public void writeOutEntry(Entry entry) {
        System.out.println(entry);
    }

    public void writeSelectedEntry(Entry entry) {
        if (entry != null) {
            System.out.println(
                "   Selected entry:\n" + entry
            );
        }
    }

    public void writeSearchStringPrompt() {
        System.out.print(
                "\n   Please type string to search for, across all attributes of all current orders: "
        );
    }

    public void writeNoSearchResults() {
        System.out.println(
                "   Nothing found.\n"
        );
    }

    public void writeEmptyStringRebuke() {
        System.out.println(
                "   Thou hast naught be-scribbled!\n" +
                "   Repent!\n"
        );
    }

    public void writeEntryAddedSuccess(Entry entry) { // rewrite like modification with option to cancel
        System.out.println(
                "   Added new entry:\n" + entry
        );
    }

    /*
    // merged into writeMenuOptions
    public void writeModifyEntryOptions() {
        System.out.println(
                "   Modify which attribute?\n" +
                "       [1] Client name\n" +
                "       [2] Ordered goods\n" +
                "       [3] Amount\n" +
                "       [4] Price\n" +
                "       [5] Note\n" +
                "       [6] Status\n" +
                "       [9] Finish modification\n" +
                "       [0] Cancel modification\n"
        );
    }
    */

    public void writeEntryModificationSuccess(String[] entryAttributesBefore, Entry entryAfterModification, boolean didModify) {
        if (didModify) {
            System.out.println(
                // don't wanna clone, don't wanna instantiate new Entry, so just dirty copy attributes and fill into Entry.toString() template...
                // please don't hate me
                "   |Order ID|: " + entryAfterModification.getUniqueIndex() + " |Customer|: " + entryAttributesBefore[0] + "\n" +
                "   |Goods|: " + entryAttributesBefore[1] + " |Amount|:" + entryAttributesBefore[2] + " |Price|:" + entryAttributesBefore[3] + " |Note|:" + entryAttributesBefore[4] + " |Status|:" + entryAttributesBefore[5] + "\n" +
                "   ...has been modified to:\n" + entryAfterModification
            );
        }
        else {
            System.out.println(
                "   Entry modification canceled."
            );
        }
    }

    public void writeEntryDeletionSuccess(Entry entry) {
        System.out.println(entry +
                "   ...has been deleted."
        );
    }

    public void writeEasterEgg() {
        System.out.println(
                "   Congratulations, you have found the easter egg...\n" +
                "   **blows party horn**\n"
        );
    }

    public void writeListingHeader(String listingVariant) { // yes, String - not making ENUM for this...
        switch (listingVariant) {
            case "listCurrent":
                System.out.println(
                "   The current entries are:\n\n"
                );
                break;
            case "listDeleted":
                System.out.println(
                "   The deleted entries are:\n\n"
                );
                break;
            case "listSearchResults":
                System.out.println(
                "   The search results are:\n\n"
                );
                break;
            default: // for completion's sake
                System.out.println(
                "   The entries are:\n\n"
                );
        }
    }

    public void writeListingFooter(String listingVariant, int numberOfEntries) { // same here
        switch (listingVariant) {
            case "listCurrent":
                System.out.println(
                "\n" +
                "   The total number of current entries: " + numberOfEntries + "\n\n"
                );
                break;
            case "listDeleted":
                System.out.println(
                "\n" +
                "   The total number of deleted entries: " + numberOfEntries + "\n\n"
                );
                break;
            case "listSearchResults":
                System.out.println(
                "\n" +
                "   The total number of matching entries: " + numberOfEntries + "\n\n"
                );
                break;
            default: // also for completion's sake
                System.out.println(
                "\n" +
                "   The entries are:\n\n"
                );
        }
    }

    public void writeNewLine(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println();
        }
    }

    // moar, gimme moar, gimme moar methods...
    // if I had a heart, I could love you...
    // if I had a voice, I could sing...
}
