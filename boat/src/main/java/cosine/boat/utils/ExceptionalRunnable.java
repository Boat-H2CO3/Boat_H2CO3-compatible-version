package cosine.boat.utils;

import java.util.concurrent.Callable;

public interface ExceptionalRunnable<E extends Exception> {

    void run() throws E;

    default Callable<Void> toCallable() {
        return () -> {
            run();
            return null;
        };
    }

}
