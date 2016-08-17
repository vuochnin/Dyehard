
import Engine.GameWindow;

/**
 * @author vuochnin
 *
 */
public class APIMain extends GameWindow{
	
	public APIMain(){
		setRunner(new APIDemo());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		(new APIMain()).startProgram();
	}

}
