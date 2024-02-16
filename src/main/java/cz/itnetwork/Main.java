package cz.itnetwork;

public class Main {
    public static void main(String[] args) {

        // biggest downside of Java = lack of a `goto` statement...


        // shutdown hook for cleanup after AbortWatcher hard-kill - which doesn't work
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Perform cleanup tasks
            // dunno which ones tho
        }));

        // construct side thread to watch out for abort key-press - [ESC]
        // doesn't work in IDE... cos IDE pseudo-console eats it...
        // maybe a different key would be better?
        AbortWatcher watcher = new AbortWatcher();
        watcher.start();

        // construct worker, fill with sample entries, and run the main loop
        Worker worker = new Worker();
        worker.populateWithDefaults();
        worker.goWild(); // function that makes the instance of Worker class... go wild...

        // construct writer - moved to worker
        // construct reader - moved to worker

        // should save changes to file and read them at start... later


        // TO FIX
        // clearing screen doesn't work - maybe it works, just not in the IDE pseudo-terminal
        // clear-screen after each menu choice switch:case?
        // get the abort watcher to actually work - maybe also problem in IDE pseudo-terminal






    }
}