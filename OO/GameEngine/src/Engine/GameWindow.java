package Engine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class GameWindow.
 */
@SuppressWarnings("serial")
public abstract class GameWindow extends Applet implements Runnable {
    //
    // Configuration
    //

    /** The target updates per second. */
    // Number of times per second to try to update and draw at
    private final long TARGET_UPDATES_PER_SECOND = 40;

    /** The max frame skip. */
    // Maximum number of frames to skip between drawing a new frame
    private final int MAX_FRAME_SKIP = 10;

    /** The frame skip rate. */
    // How much longer a frame can take before skipping drawing a frame
    private final float FRAME_SKIP_RATE = 1.5f;

    /** The loop option. */
    // Game loop (Timer = 1, loop = 2, or Scheduled thread = 3)
    private static int LOOP_OPTION = 2;

    //
    //
    //

    /** The did init. */
    private boolean didInit = false;

    /** The client width. */
    private int clientWidth = 200;
    
    /** The client height. */
    private int clientHeight = 200;

    /** The is fullscreen. */
    private boolean isFullscreen = false;

    /** The screen buffer. */
    // Double buffer
    private Image screenBuffer = null;
    
    /** The screen buffer graphics. */
    protected Graphics2D screenBufferGraphics = null;

    /** The timer. */
    // OPTION_1
    private Timer timer = null;
    
    /** The my timer. */
    private GameWindowUpdate myTimer = null;

    /** The update thread. */
    // OPTION_2
    private Thread updateThread = null;

    // If true, only draw frames if an update has
    // occurred since the last draw frame. If false, draw frames even if
    /** The sync fps. */
    // no updates have occured since the last draw frame.
    private final boolean syncFPS = true;

    // If true, frames can be skipped as needed. If false, frames will not be
    /** The skip frames. */
    // skipped and possibly cause slower performance.
    private boolean skipFrames = true;

    /** The updater. */
    // OPTION_3
    private GameWindowUpdate2 theUpdater = null;

    /** The is running. */
    private final Semaphore isRunning = new Semaphore(1);
    
    /** The frame delay. */
    private final long FRAME_DELAY = (1000 / TARGET_UPDATES_PER_SECOND);

    /** The last update time. */
    private long lastUpdateTime = 0;
    
    /** The num frames skipped. */
    private int numFramesSkipped = 0;
    
    /** The frame skip threshold. */
    private final int FRAME_SKIP_THRESHOLD = (int) (FRAME_SKIP_RATE * FRAME_DELAY);

    /** The should close. */
    private boolean shouldClose = false;
    
    /** The did clean exit. */
    private boolean didCleanExit = false;

    /** The base path. */
    // Used by ResourceHandler to locate external files
    private String basePath = "";

    /** The frame. */
    // Used when running as an application to contain the applet
    private JFrame theFrame = null;

    //
    //
    //

    /* (non-Javadoc)
     * @see java.applet.Applet#init()
     */
    // Automatically called when running as an applet
    @Override
    public void init() {
        // Since running as an applet, this shouldn't crash internally
        setBasePath(getCodeBase().toString());

        myInit();

        // getParent().validate();
    }

    /**
     * Sets the should skip frames.
     *
     * @param value the new should skip frames
     */
    public void setShouldSkipFrames(boolean value) {
        skipFrames = value;
        lastUpdateTime = (System.nanoTime() / 1000000);
    }

    /**
     * Gets the should skip frames.
     *
     * @return the should skip frames
     */
    public boolean getShouldSkipFrames() {
        return skipFrames;
    }

    /**
     * Sets the base path.
     *
     * @param value the new base path
     */
    public void setBasePath(String value) {
        basePath = value;
    }

    /**
     * Gets the base path.
     *
     * @return the base path
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * My init.
     */
    // Call this one from main
    private void myInit() {
        try {
            setIgnoreRepaint(true);

            initConfig(this);

            // Set the window
            setBackground(new Color(100, 100, 100));
            setSize(getWidth(), getHeight());
            setPreferredSize(new Dimension(getWidth(), getHeight()));

            screenBuffer = createImage(getWidth(), getHeight());
            screenBufferGraphics = (Graphics2D) screenBuffer.getGraphics();
            // resources.setGraphics(screenBufferGraphics);

            addRenderingHints(screenBufferGraphics);

            initializeWorld();

            didInit = true;

            lastUpdateTime = (System.nanoTime() / 1000000);

            if (LOOP_OPTION == 1) {
                // OPTION_1
                myTimer = new GameWindowUpdate(this);
                timer = new Timer();
                timer.schedule(myTimer, 1, FRAME_DELAY);
            } else if (LOOP_OPTION == 2) {
                // OPTION_2
                updateThread = new Thread(this);
                updateThread.start();
            } else if (LOOP_OPTION == 3) {
                // OPTION_3
                theUpdater = new GameWindowUpdate2(this);
                theUpdater.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Game cycle.
     */
    // OPTION_1
    private void gameCycle() {
        if (shouldClose) {
            cleanExit();

            return;
        }

        if (!isRunning.tryAcquire()) {
            return;
        }

        updateWorld();
        updateInput();

        long curTime = (System.nanoTime() / 1000000);
        long delta = curTime - lastUpdateTime;
        lastUpdateTime = curTime;

        if (delta < FRAME_SKIP_THRESHOLD || numFramesSkipped >= MAX_FRAME_SKIP) {
            numFramesSkipped = 0;

            // repaint();
            update(getGraphics());
        } else {
            numFramesSkipped += 1;
            // System.out.println("Loop time: " + delta + "; Threshold: " +
            // FRAME_SKIP_THRESHOLD);
        }

        isRunning.release();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    // OPTION_2
    @Override
    public void run() {
        long curTime = (System.nanoTime() / 1000000);
        lastUpdateTime = curTime;

        try {
            isRunning.acquire();
        } catch (InterruptedException e) {
            System.err.println("Semaphore error while starting to run!");
            e.printStackTrace();
        }

        boolean didUpdate = false;

        numFramesSkipped = -1;

        // Infinite loop instead of using a timer due to Java timer relying on
        // the system timer resolution, which may not be as precise as desired.
        while (!shouldClose) {
            curTime = (System.nanoTime() / 1000000);

            while ((curTime - lastUpdateTime) >= FRAME_DELAY
                    && numFramesSkipped < MAX_FRAME_SKIP) {
                updateWorld();
                updateInput();

                lastUpdateTime += FRAME_DELAY;

                didUpdate = true;
                numFramesSkipped += 1;

                if (!skipFrames) {
                    break;
                }
            }

            if (didUpdate || !syncFPS) {
                didUpdate = false;
                numFramesSkipped = -1;

                // repaint();
                update(getGraphics());
            }

            // Thread.yield();

            // Sleep to allow input state to be processed and changed
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }

        isRunning.release();

        cleanExit();
    }

    /*
     * // OPTION_2 public void run2() { final long timeStep = FRAME_DELAY;
     * 
     * long prevTime = (System.nanoTime() / 1000000); long curTime = prevTime;
     * 
     * boolean didUpdate = true;
     * 
     * try { isRunning.acquire(); } catch(InterruptedException e) {
     * System.err.println("Semaphore error while starting to run!");
     * e.printStackTrace(); }
     * 
     * // Infinite loop instead of using a timer due to Java timer relying on //
     * the system timer resolution, which may not be as precise as desired.
     * while(!shouldClose) { curTime = (System.nanoTime() / 1000000);
     * 
     * // Update the number of time steps since the last update while((curTime -
     * prevTime) >= timeStep) { updateWorld(); updateInput();
     * 
     * prevTime += timeStep;
     * 
     * didUpdate = true; }
     * 
     * if(didUpdate || !syncFPS) { //repaint(); Graphics gfx = getGraphics();
     * 
     * if(gfx != null) { update(gfx); }
     * 
     * didUpdate = false; }
     * 
     * //Thread.yield();
     * 
     * // Sleep to allow input state to be processed and changed try {
     * Thread.sleep(1); } catch(InterruptedException e) { } }
     * 
     * isRunning.release();
     * 
     * cleanExit(); }
     */

    /* (non-Javadoc)
     * @see java.awt.Container#paint(java.awt.Graphics)
     */
    // Paint the screen
    @Override
    public void paint(Graphics theGraphics) {
        if (didInit && theGraphics != null) {
            // Clear the buffer
            screenBufferGraphics.clearRect(0, 0, screenBuffer.getWidth(null),
                    screenBuffer.getHeight(null));

            // Draw the scene
            draw(screenBufferGraphics);

            // Draw the screen buffer to the screen
            theGraphics.drawImage(screenBuffer, 0, 0, null);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Container#update(java.awt.Graphics)
     */
    // Called when the screen should be repainted
    @Override
    public void update(Graphics theGraphics) {
        paint(theGraphics);
    }

    //
    //
    //

    /**
     * Sets the client size.
     *
     * @param width the width
     * @param height the height
     */
    public void setClientSize(int width, int height) {
        clientWidth = width;
        clientHeight = height;
    }

    /* (non-Javadoc)
     * @see java.awt.Component#setSize(int, int)
     */
    @Override
    public void setSize(int width, int height) {
        setClientSize(width, height);

        super.setSize(clientWidth, clientHeight);
    }

    /* (non-Javadoc)
     * @see java.awt.Component#getWidth()
     */
    @Override
    public int getWidth() {
        return clientWidth;
    }

    /* (non-Javadoc)
     * @see java.awt.Component#getHeight()
     */
    @Override
    public int getHeight() {
        return clientHeight;
    }

    /**
     * Clean exit.
     */
    private void cleanExit() {
        if (didCleanExit) {
            return;
        }

        didCleanExit = true;

        didInit = false;

        // Make sure things are set to close
        close();

        // Wait for the update loop to stop
        try {
            isRunning.acquire();
        } catch (InterruptedException e) {
            System.err.println("Semaphore error while trying to stop!");
            e.printStackTrace();
        }

        if (LOOP_OPTION == 1) {
            // OPTION_1
            timer.cancel();
        } else if (LOOP_OPTION == 3) {
            // OPTION_3
            theUpdater.stop();
        }

        if (LOOP_OPTION == 2) {
            // OPTION_2
            /*
             * // Wait for the update thread to end if(updateThread != null) {
             * try { updateThread.join(); } catch(InterruptedException e) {
             * System.err.println("Error while trying to join update thread!");
             * e.printStackTrace(); } }
             */
        }

        clean();

        // Close the containing frame
        if (theFrame != null) {
            // Close the frame
            theFrame.setVisible(false);
            theFrame.dispose();
        }
        // If there wasn't a frame, then try closing as an
        // applet that is using the applet viewer
        else if (getParent() != null) {
            // Close
            Window parentFrame = (Window) getParent().getParent();

            if (parentFrame != null) {
                WindowEvent wev = new WindowEvent(parentFrame,
                        WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue()
                        .postEvent(wev);
            }
        }
    }

    /* (non-Javadoc)
     * @see java.applet.Applet#destroy()
     */
    @Override
    public void destroy() {
        cleanExit();
    }

    /**
     * Close.
     */
    public void close() {
        shouldClose = true;
    }

    //
    //
    //

    /**
     * Start program.
     */
    public void startProgram() {
        // String current = System.getProperty("user.dir");
        // System.out.println("Current working directory in Java : " + current);

        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFrame.setDefaultLookAndFeelDecorated(true);

        final JFrame frame = new JFrame();
        // frame.setUndecorated(true);
        // frame.setOpacity(0.5f);
        // frame.setLocationRelativeTo(null);
        // frame.setIconImage(arg0)
        frame.setResizable(false);
        frame.setTitle("Window");
        frame.setVisible(true);
        frame.getContentPane().add(this);

        // Since running as an application, this
        // shouldn't throw a security warning
        setBasePath("file:" + System.getProperty("user.dir") + "\\");

        theFrame = frame;

        myInit();

        frame.getContentPane().setPreferredSize(
                new Dimension(getWidth(), getHeight()));
        frame.pack();
        // frame.setSize(frame.getWidth(), frame.getHeight() - 0);

        // Detect when the window wants to close
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                shouldClose = true;
                // System.exit(0);
            }
        });
    }

    //
    //
    //

    /**
     * Sets the JFrame's state to fullscreen if normal, or normal if fullscreen.
     * 
     */
    public void toggleFullscreen() {
        if (theFrame != null) {
            if (isFullscreen) {
                isFullscreen = false;
                setScreenToWindowed();
            } else {
                isFullscreen = true;
                setScreenToFullscreen();
            }
            theFrame.toFront();
        }
    }

    /**
     * Checks whether or not the state of the gameWindow is fullscreen.
     * 
     * @return Whether this is fullscreen or not.
     */
    public boolean isFullscreen() {
        return isFullscreen;
    }

    /**
     * Reinstanciates theFrame to a fullscreen size.
     * 
     * Must be instancaited as a new Frame because setUndecorated() (removes
     * topbar) will fail once theFrame becomes displayable.
     */
    public void setScreenToFullscreen() {
        theFrame.dispose();

        JFrame.setDefaultLookAndFeelDecorated(false);

        final JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().add(this);

        theFrame = frame;

        // Detect when the window wants to close
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                shouldClose = true;
            }
        });

        // Sets frame to max size.
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        // Sets the gamewindows size to the frame's size.
        this.setSize(theFrame.getWidth(), theFrame.getHeight());

        resizeDoubleBuffer();
    }

    /**
     * Reinstanciates theFrame to windowed size.
     */
    public void setScreenToWindowed() {
        theFrame.dispose();
        JFrame.setDefaultLookAndFeelDecorated(true);

        final JFrame frame = new JFrame();
        frame.setExtendedState(Frame.NORMAL);
        frame.setResizable(false);
        frame.setTitle("Window");
        frame.setVisible(true);
        frame.getContentPane().add(this);

        theFrame = frame;

        // Sets the gamewindow's size to the default values set in library code.
        this.setSize(runner.getInitWidth(), runner.getInitHeight());

        frame.getContentPane().setPreferredSize(
                new Dimension(getWidth(), getHeight()));
        frame.pack();

        // Detect when the window wants to close
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                shouldClose = true;
            }
        });

        resizeDoubleBuffer();
    }

    /**
     * Resizes the double buffer to theFrame's current size.
     */
    private void resizeDoubleBuffer() {
        // Recreate double buffer for new size
        screenBufferGraphics.dispose();
        screenBuffer = createImage(getWidth(), getHeight());
        screenBufferGraphics = (Graphics2D) screenBuffer.getGraphics();
        addRenderingHints(screenBufferGraphics);
    }

    /**
     * Adds the rendering hints to a graphics display.
     * 
     * @param g2
     *            - Graphics to be modified.
     */
    private void addRenderingHints(Graphics2D g2) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints rh2 = new RenderingHints(
                RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        // RenderingHints rh3 = new RenderingHints(
        // RenderingHints.KEY_INTERPOLATION,
        // RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2.addRenderingHints(rh);
        g2.addRenderingHints(rh2);
        // g2.addRenderingHints(rh3);
    }

    /*
     * public static void main(String[] args) {
     * System.err.println("NEED MAIN METHOD!"); }
     */

    //
    // Misc fucntions
    //
    /**
     * Removes the cursor from the window.
     */
    public void removeCursor() {
        BufferedImage blankImg = new BufferedImage(16, 16,
                BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                blankImg, new Point(0, 0), "Blank Cursor");

        theFrame.getContentPane().setCursor(blankCursor);
    }

    /**
     * Sets cursor to system default.
     */
    public void resetCursor() {
        theFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
    }

    //
    //
    //

    /** The runner. */
    private LibraryCode runner = null;

    /**
     * Sets the runner.
     *
     * @param theRunner the new runner
     */
    public void setRunner(LibraryCode theRunner) {
        runner = theRunner;
    }

    /**
     * Inits the config.
     *
     * @param theWindow the the window
     */
    private void initConfig(GameWindow theWindow) {
        runner.initConfig(this);
    }

    /**
     * Initialize world.
     */
    private void initializeWorld() {
        runner.initializeWorld();
    }

    /**
     * Update world.
     */
    private void updateWorld() {
        runner.updateWorld();
    }

    /**
     * Update input.
     */
    private void updateInput() {
        runner.updateInput();
    }

    /**
     * Clean.
     */
    private void clean() {
        runner.clean();
    }

    /**
     * Draw.
     *
     * @param gfx the gfx
     */
    private void draw(Graphics gfx) {
        runner.draw(gfx);
    }

    //
    //
    //

    /*
     * public abstract void initConfig(GameWindow theWindow);
     * 
     * public abstract void initializeWorld();
     * 
     * public abstract void updateWorld();
     * 
     * public abstract void updateInput();
     * 
     * public abstract void clean();
     * 
     * public abstract void draw(Graphics gfx);
     */

    //
    //
    //

    /**
     * The Class GameWindowUpdate2.
     */
    // OPTION_3
    private class GameWindowUpdate2 {
        
        /** The window. */
        private GameWindow window = null;
        
        /** The schedule task executor. */
        private ScheduledExecutorService scheduleTaskExecutor;

        /**
         * Instantiates a new game window update2.
         *
         * @param aWindow the a window
         */
        public GameWindowUpdate2(GameWindow aWindow) {
            window = aWindow;
        }

        /**
         * Start.
         */
        public void start() {
            scheduleTaskExecutor = Executors.newScheduledThreadPool(1);

            // This schedule a task to run
            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    window.gameCycle();
                }
            }, 0, FRAME_DELAY, TimeUnit.MILLISECONDS);
        }

        /**
         * Stop.
         */
        public void stop() {
            scheduleTaskExecutor.shutdown();
        }
    }

    /**
     * The Class GameWindowUpdate.
     */
    // OPTION_1
    private class GameWindowUpdate extends TimerTask {
        
        /** The window. */
        private GameWindow window = null;

        /**
         * Instantiates a new game window update.
         *
         * @param aWindow the a window
         */
        public GameWindowUpdate(GameWindow aWindow) {
            window = aWindow;
        }

        /* (non-Javadoc)
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run() {
            window.gameCycle();
        }
    }
}
