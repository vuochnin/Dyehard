/*
 * 
 */
package dyehard.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class Timer.
 */
public class Timer {
    
    /** The start time. */
    private float startTime;
    
    /** The end time. */
    private float endTime;
    
    /** The interval. */
    private float interval;
    
    /** The active. */
    private boolean active;

    /**
     * Instantiates a new timer.
     *
     * @param milliSeconds is the interval
     */
    public Timer(float milliSeconds) {
        startTime = System.nanoTime();
        interval = milliSeconds * 1000000; // milli to nano
        endTime = startTime + interval;
        active = true;
    }

    /**
     * Instantiates a new timer.
     */
    public Timer() {
        startTime = System.nanoTime();
        interval = 100000000;
        endTime = startTime + interval;
        active = true;
    }

    /**
     * Instantiates a new timer.
     *
     * @param bool sets timer to be active
     */
    public Timer(boolean bool) {
        active = bool;
        startTime = System.nanoTime();
        interval = 100000000;
        endTime = startTime + interval;
    }

    /**
     * Checks if is done.
     *
     * @return true, if is done
     */
    public boolean isDone() {
        if (!active) {
            return false;
        } else {
            return System.nanoTime() >= endTime;
        }
    }

    /**
     * Sets the active.
     *
     * @param bool the new active
     */
    public void setActive(boolean bool) {
        active = bool;
    }

    /**
     * Reset.
     */
    public void reset() {
        endTime = System.nanoTime() + interval;
    }

    /**
     * Sets the interval.
     *
     * @param milliSeconds the new interval to be converted to nano
     */
    public void setInterval(float milliSeconds) {
        interval = milliSeconds * 1000000; // milli to nano
    }

    /**
     * Time remaining.
     *
     * @return the amount of time left in milli secs
     */
    public float timeRemaining() {
        // Returns the amount of time left in milliseconds
        return (endTime - System.nanoTime()) / 1000000;
    }

    /**
     * Delta time.
     *
     * @return the float
     */
    public float deltaTime() {
        float delta = (System.nanoTime() - startTime) / 1000000000;
        startTime = System.nanoTime();
        return delta;
    }

}
