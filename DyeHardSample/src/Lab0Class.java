import dyehard.Player.Hero;

public class Lab0Class extends Hero {
	
	public float width = 20f;
	
	public float height = 20f;
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public void setSize(float x, float y){
		size.setX(x);
		size.setY(y);
	}

}
