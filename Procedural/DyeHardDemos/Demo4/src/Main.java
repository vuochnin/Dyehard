import Engine.GameWindow;

/**
 * @author vuochnin
 *
 */
public class Main extends GameWindow{
	
	public Main(){
		setRunner(new Demo4());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		(new Main()).startProgram();
	}

}
