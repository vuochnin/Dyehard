import Engine.GameWindow;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleMain.
 */
public class SampleMain extends GameWindow {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        (new SampleMain()).startProgram();
    }

    /**
     * Instantiates a new sample main.
     */
    public SampleMain() {
        setRunner(new SampleUserCode());
    }
}