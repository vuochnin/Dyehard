package ExampleGame;


import Engine.GameWindow;

/**
 * @author vuochnin
 *
 */
public class Main extends GameWindow{
	
	public Main(){
		setRunner(new ExampleGame());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		(new Main()).startProgram();
	}

}
