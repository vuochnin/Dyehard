
import Engine.GameWindow;

/**
 * @author vuochnin
 *
 */
public class NinMain extends GameWindow{
	
	public NinMain(){
		setRunner(new NinDemo());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		(new NinMain()).startProgram();
	}

}
