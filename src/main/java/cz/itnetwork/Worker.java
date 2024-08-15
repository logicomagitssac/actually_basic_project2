package cz.itnetwork;

import java.util.ArrayList;

public class Worker {

    private final String[][] defaultEntries = {
            //{ "name", "goods", "amount", "price", "note", "status" },
            { "Snoop Dogg", "weed", "enough for every day", "on da house", "in-kind protection payment", "held up - Snoopy big mad..." }, // overrated rapper
            { "Eugene 'Ganjaman' Stoner", "cannabis sativa seeds", "2 packs", "$120", "only the best", "delivered" }, // famous US firearms designer responsible for the M16/AR15
            { "Theodore Kaczynski", "fertilizer", "800kg", "$60", "smart guy, kinda weird and twitchy", "planted" }, // the UnaBomber
            { "Jesse Pinkman", "LDPE plastic tubs", "2x 500l tubs", "$39.90", "jittery, left side black eye", "cancelled - none big enough" }, // gonna cook up some blue crystal meth
            { "Dixie Pearllilyz", "the *good* soothing cream", "500ml tube", "pro bono", "paid in \"personal favors\"", "recurring order" }, // oldest profession
            { "Bruce \"Sledgehammer\" Pummelton", "band-aids", "3 packs of 100", "$30", "urgent", "en route" }, // a generic wrestler
            { "Boris Vladimirovich Blyatov", "drones", "all of them", "on credit", "will pay in crude oil", "awaiting approval from Tehran" }, // political commentary
            { "Jedidiah Thaddeus Beauregard", "pointy white cotton hoods", "3", "$66", "Karl Kaiser & Kompany", "expedited" }, // the klan quartermaster
            { "Lil G", "assorted heat", "10-12 pieces", "$$3200$$", "complimentary spare magazines", "dead-dropped" }, // from da hood
            { "Ahmed Abdullah ibn Zayd al-Jabbar", "Semtex-1H", "286 bricks, 570g per", "$632,000", "allegedly for landscaping", "payment pending" }, // must not talk about this
            { "José Antonio Ramirez", "sturdy shovels", "5", "$30", "El Paso area", "confiscated by the FDA" }, // your typical "dreamer"
            { "Long Schlong Silver", "pieces of eight", "15,000", "trade for 180lbs of indigo", "rendezvous off Isla de la Vache", "sunk" }, // Yarr, me mateys!
            { "Edward Teach", "18pd solid shot", "2000 balls", "4,000 pieces of eight", "moored at Port Royal", "expended" }, // pyrate scum Blackbeard
            { "Dick Longenhaard", "rum", "26 barrels", "1,300 pieces of eight", "deliver to Tortuga", "drunk" }, // Dick is short for Richard (e.g. Dick Cheney)
            { "Joe Rogan", "mushrooms", "4 punnets", "$160", "for personal use", "fulfilled" }, // must maintain sanity somehow
//            { "", "", "", "", "", "" },
//            { "", "", "", "", "", "" },
//            { "", "", "", "", "", "" },
            { "John Wick", "guns", "lots of guns", "8 coins", "tactical grips", "no longer required - RIP" }, // reference
            { "Thomas Anderson", "guns", "lots of guns", "NaN", "0x00000000", "ERR-808: Deja Vu Loop" }, // glitched reference
    };

    private Reader reader = new Reader();
    private Writer writer = new Writer();

    private ArrayList<Entry> currentEntries = new ArrayList<>();
    private ArrayList<Entry> deletedEntries = new ArrayList<>();

    public void populateWithDefaults() { // call moved to Main so that restarting goWild() does not re-populate
        for (String[] defaultEntry : defaultEntries) {
            currentEntries.add(new Entry(defaultEntry));
        }
    }

    // redundant
//    private void addNewEntry(String[] attributeValues) {
//        currentEntries.add(new Entry(attributeValues));
//    }

    private void listEntries(ArrayList<Entry> entries, String listingVariant) { // add print that says how many there are
        // code
        writer.clearThatScreenYeahYouClearThatScreenHard();
        writer.writeMenuHeader();
        writer.writeMenuDisclaimers();
        writer.writeNewLine(2);
        if (entries.isEmpty()) {
            writer.writeNoSearchResults();
        }
        else {
            writer.writeListingHeader(listingVariant);
            for (Entry currentEntry : entries) {
                writer.writeOutEntry(currentEntry);
            }
            writer.writeListingFooter(listingVariant, entries.size());
        }
    }

    private Entry addNewEntry() { // rewrite similarly to modifyEntry to give option to cancel adding - later
        String[] attributeValues = new String[Entry.attributeNames.length];
        // loop for attribute inputs
        for (int i = 0; i < Entry.attributeNames.length; i++) {
            writer.writeAttributeInputPrompt(Entry.attributeNames[i]);
            attributeValues[i] = reader.readInputString();
            while (attributeValues[i].isEmpty()) { // checks empty string, and prompts until not empty
                writer.writeEmptyStringRebuke();
                writer.writeAttributeInputPrompt(Entry.attributeNames[i]);
                attributeValues[i] = reader.readInputString();
            }
        }
        // instantiates a new entry using the saved attribute values
        Entry justCreated = new Entry(attributeValues);
        currentEntries.add(justCreated);
        writer.clearThatScreenYeahYouClearThatScreenHard();
        writer.writeMenuHeader();
        writer.writeMenuDisclaimers();
        writer.writeEntryAddedSuccess(justCreated);
        return justCreated;
    }

// merged into common listing method with modifier parameters
//
//    private void showDeletedEntries() {
//        writer.clearThatScreenYeahYouClearThatScreenHard();
//        writer.writeMenuHeader();
//        writer.writeMenuDisclaimers();
//        writer.writeNewLine(2);
//        if (deletedEntries.isEmpty()) {
//            writer.writeNoSearchResults();
//        }
//        else {
//            writer.writeListingHeader("listDeleted");
//            for (Entry deletedEntry : deletedEntries) {
//                writer.writeOutEntry(deletedEntry);
//            }
//        }
//    }

    private Entry searchEntries() {
        // calls reader, which slightly parses input string (toLowerCase().trim())
        // and checks for .contains() across all attributes of all entries
        writer.writeSearchStringPrompt();
        String searchedString = reader.readSearchString();
        writer.writeNewLine(2);
        writer.writeListingHeader("listSearchResults");
        Entry searchResultEntry = null;
        int numberOfResults = 0;
        for (Entry currentEntry : currentEntries) { // entries
            for (String readableAttribute : currentEntry.getReadableAttributes()) { // each entry's attributes
                if (readableAttribute.toLowerCase().contains(searchedString)) {
                    numberOfResults++;
                    writer.writeOutEntry(currentEntry);
                    if (numberOfResults == 1) {
                        searchResultEntry = currentEntry;
                    }
                    else {
                        searchResultEntry = null;
                    }
                    break; // makes sure each entry only prints once, even if multiple attributes of one entry would contain searchedString
                }
            }
        }
        if (numberOfResults == 0) {
            // write nothing found
            writer.clearThatScreenYeahYouClearThatScreenHard();
            writer.writeMenuHeader();
            writer.writeMenuDisclaimers();
            writer.writeNewLine(2);
            writer.writeNoSearchResults();
            return null;
        }
        writer.writeListingFooter("listSearchResults", numberOfResults);
        return searchResultEntry; // returns a single Entry if, and only if, exactly one was found, else returns null
    }

    private void modifyEntry(Entry entry) {
        // code
        // first copy original attributes to later feed to writer for comparison print
        String[] selectedEntryAttributesBefore = entry.getReadableAttributes();
        String[] newAttributes = entry.getReadableAttributes();
        boolean didModify = false;
        int menuContextCode = 2;
        while (menuContextCode == 2) {
            writer.writeMenuOptions(menuContextCode);
            writer.writeMenuChoicePrompt();
            switch (reader.readMenuChoice()) {
                case 1:
                    writer.writeAttributeInputPrompt(Entry.attributeNames[0]);
                    newAttributes[0] = reader.readInputString();
                    break;
                case 2:
                    writer.writeAttributeInputPrompt(Entry.attributeNames[1]);
                    newAttributes[1] = reader.readInputString();
                    break;
                case 3:
                    writer.writeAttributeInputPrompt(Entry.attributeNames[2]);
                    newAttributes[2] = reader.readInputString();
                    break;
                case 4:
                    writer.writeAttributeInputPrompt(Entry.attributeNames[3]);
                    newAttributes[3] = reader.readInputString();
                    break;
                case 5:
                    writer.writeAttributeInputPrompt(Entry.attributeNames[4]);
                    newAttributes[4] = reader.readInputString();
                    break;
                case 6:
                    writer.writeAttributeInputPrompt(Entry.attributeNames[5]);
                    newAttributes[5] = reader.readInputString();
                    break;
                case 9:
                    entry.modifyEntry(newAttributes);
                    didModify = true;
                    menuContextCode = 1;
                    break;
                case 0:
                    // if cancelled, entryModifiedSuccess will print cancelled due to didModify=false
                    menuContextCode = 1;
                    break;
                default:
                    writer.writeBadInputRebuke();
            }
        }
        writer.clearThatScreenYeahYouClearThatScreenHard();
        writer.writeMenuHeader();
        writer.writeMenuDisclaimers();
        writer.writeEntryModificationSuccess(selectedEntryAttributesBefore, entry, didModify);
        writer.writeMenuOptions(menuContextCode);
    }

    private void deleteEntry(Entry entry) {
        // code
        entry.makeDeleted();
        currentEntries.remove(entry);
        deletedEntries.add(entry);
        // yes, there is a redundancy in the isDeleted attribute vs deletedEntries list
        // I would lean towards having just one big collection of all entries, with listing methods filtering for isDeleted
        // but too lazy to rewrite the listing methods, and don't wanna remove the nice and pretty isDeleted attribute
        writer.clearThatScreenYeahYouClearThatScreenHard();
        writer.writeMenuHeader();
        writer.writeMenuDisclaimers();
        writer.writeEntryDeletionSuccess(entry);
    }

    // more methods here

    public void goWild() { // could rename to goToWork or goToTown
        // some starting code before the actual menu loop
        int menuContextCode = 0;
        boolean rebukeBadInput = false;
        Entry selectedEntry = null;
        writer.writeIntro();
        reader.readInputString(); // just to wait for [enter] key-press
        writer.clearThatScreenYeahYouClearThatScreenHard();
        writer.writeMenuHeader();
        writer.writeMenuDisclaimers();
        // main menu loop
        while (true) { // yes. infinite loop. perfectly valid. exit condition is clearly implemented (switch case 0)...
            if (rebukeBadInput) { // this clears screen at cost of seeing previous, for the purpose of seeming more interactive (rewrite rather than write more below)
                writer.clearThatScreenYeahYouClearThatScreenHard();
                writer.writeMenuHeader();
                writer.writeMenuDisclaimers();
                writer.writeSelectedEntry(selectedEntry);
                writer.writeMenuOptions(menuContextCode);
                writer.writeBadInputRebuke();
                rebukeBadInput = false;
            }
            else { // will not be clearing screen here, cos it would immediately overwrite search results
                writer.writeSelectedEntry(selectedEntry);
                writer.writeMenuOptions(menuContextCode);
            }
            writer.writeMenuChoicePrompt();
            switch (reader.readMenuChoice()) { // can easily be extended for double-digit inputs
                case 1:
                    // do code - show all current
                    listEntries(currentEntries, "listCurrent");
                    selectedEntry = null;
                    menuContextCode = 0;
                    break;
                case 2:
                    // do code - add new entry
                    selectedEntry = addNewEntry();
                    menuContextCode = 1;
                    break;
                case 3:
                    // do code - search
                    selectedEntry = searchEntries();
                    if (selectedEntry != null) { // searchEntries() will return a reference only if exactly one entry matches
                        menuContextCode = 1;
                    }
                    else {
                        menuContextCode = 0;
                    }
                    break;
                case 4:
                    // do code - show all deleted
                    listEntries(deletedEntries, "listDeleted");
                    selectedEntry = null; // so that it doesn't still show as selected when we had just deleted it
                    menuContextCode = 0;
                    break;
                case 5:
                    // do code - modify selected entry (when search returns exactly one result)
                    // modify behavior to something else using "if (menuContextCode == number) {code}"
                    if (menuContextCode == 1) { // so that it can't be called when not offered
                        modifyEntry(selectedEntry);
                    }
                    else {
                        rebukeBadInput = true;
                    }
                    break;
                case 6:
                    // do code - delete selected (again, when search returns exactly one result)
                    // modify behavior to something else using "if (menuContextCode == number) {code}"
                    if (menuContextCode == 1) { // so that it can't be called when not offered
                        deleteEntry(selectedEntry);
                        selectedEntry = null;
                        menuContextCode = 0;
                    }
                    else {
                        rebukeBadInput = true;
                    }
                    break;
                case 7:
                    // do code - the only unused one?
                    // maybe easter egg?
                    writer.writeEasterEgg();
                    menuContextCode = 0;
                    break;
                case 8:
                    // do code - clear screen
                    writer.clearThatScreenYeahYouClearThatScreenHard();
                    break;
                case 9:
                    // do code - restart session (does not "factory-reset" to defaults, just runs intro again, any changes to entries persist)
                    // data persistence functionality between separate runs of the app is not provided at this time
                    goWild();
                    // must not have break; otherwise it would nest as many times as user restarted... I think...
                    // without break; it will fall through to case 0 (return) and thus cascade to return to main at once
                case 0:
                    return; // this exits the infinite loop and returns to Main, where app cleanly finishes
                default:
                    rebukeBadInput = true;
                    // but do not reset menuContextCode
            }
        }
    }
    // no need for constructor
}
