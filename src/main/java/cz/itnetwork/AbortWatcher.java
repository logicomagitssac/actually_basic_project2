package cz.itnetwork;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AbortWatcher extends Thread {

    // currently does not actually work...
    // either due to IDE's pseudo-console eating the [ESC] key-press or dunno why

    public AbortWatcher() { // to terminate watcher gracefully if app ends from Main (when worker.goWild() returns)
        setDaemon(true);
    }

    public void run()
    {
        BufferedReader watcherReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) { // yes indeed, infinite loop. intentionally. much deliberately and very on purpose. cos it needs to run continuously...
            try {
                if (System.in.available() > 0) {
                    watcherReader.mark(0);
                    while (System.in.available() > 0) {
                        // here's hoping read() actually reads enough from the stream to read a complete [ESC], not just one byte...
                        // documentation says that it reads next char... and that encoding depends on system default... so it should work?
                        if (watcherReader.read() == KeyEvent.VK_ESCAPE) {
                            System.out.println("   Exiting...");
                            Thread.sleep(1000); // why the heck do all the sleeps want to catch exceptions?????
                            System.out.println("   Thank you for flying with ITnetwork.");
                            Thread.sleep(500);
                            System.exit(0); // cause hard program shutdown
                            // maybe instead of killing the app, let's make it jump out of all the loops by calling worker.goWild() return
                            // and let it terminate gracefully by letting it run to the end of the code in main?
                            // oh wait, can't do that... COS THERE IS NO `GOTO` STATEMENT IN JAVA!!
                        }
                    }
                    watcherReader.reset();
                }
                Thread.sleep(100); // ticker for the infinite loop, so that it doesn't burn the CPU
            }
            catch (IOException | InterruptedException e) {
                // throw new RuntimeException(e);
                System.out.println("\n----------------------Watcher exception----------------------\n"); // so that I notice it
            }
        }
        // just some experimentation
//        try {
//            // Displaying the thread that is running
//            System.out.println(
//                    "Thread " + Thread.currentThread().getId() + " is running");
//        }
//        catch (Exception e) {
//            // Throwing an exception
//            System.out.println("Exception is caught");
//        }
    }
}