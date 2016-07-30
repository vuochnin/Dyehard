package lab1;


import Engine.GameWindow;

/**
 * @author vuochnin
 *
 */
public class Main extends GameWindow{
	
	public Main(){
		setRunner(new Lab1());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		(new Main()).startProgram();
	}

}
