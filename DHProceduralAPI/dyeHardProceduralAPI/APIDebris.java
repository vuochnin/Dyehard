package dyeHardProceduralAPI;

import java.util.Random;

import Engine.BaseCode;
import dyehard.Obstacles.Debris;

public class APIDebris extends Debris {
	int type;
	public String typeName;
	private static Random RANDOM = new Random();
	
	public APIDebris(int type, float minX, float maxX){
		super(minX, maxX);
		this.type = type;
		switch (RANDOM.nextInt(3)) {
        case 0:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_01.png");
            typeName = "Plate";
            break;
        case 1:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_02.png");
            typeName = "Light";
            break;
        case 2:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_03.png");
            typeName = "Pipe";
            size.setX(size.getY() * 1.28f);
            break;
        }
	}
	
	public APIDebris(int type, float minX, float maxX, float minY, float maxY){
		super(minX, maxX, minY, maxY);
		this.type = type;
		switch (RANDOM.nextInt(3)) {
        case 0:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_01.png");
            typeName = "Plate";
            break;
        case 1:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_02.png");
            typeName = "Light";
            break;
        case 2:
            texture = BaseCode.resources.loadImage("Textures/Debris/debris_03.png");
            typeName = "Pipe";
            size.setX(size.getY() * 1.28f);
            break;
        }
	}
	
	public String toString(){
		return typeName;
	}
}
