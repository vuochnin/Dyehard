import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Util.Colors;
import dyehard.Util.Timer;
import dyehard.World.GameWorldRegion;

public class DyePackGenerator extends GameWorldRegion {
	
	private static DyePackGenerator instance;
	static {
		instance = new DyePackGenerator();
	}
	private int dyePackCount = 1;
	private Random RANDOM = new Random();
	private Timer timer;
	private float dyeFrequency = 2000f;
	
	private List<DyePack> dyePackList;
	private List<DyePack> userDyePacks;
	
	public static DyePackGenerator getInstance() {
		return instance;
	}
	
	private DyePackGenerator() {
		timer = new Timer(dyeFrequency);
		dyePackList = new ArrayList<DyePack>();
		userDyePacks = new ArrayList<DyePack>();
	}

	@Override
	public void initialize(float leftEdge) {
		position = leftEdge + width * 0.5f;
		generateDyePacks();
		initializeDyePacks(dyePackList);
	}
	
	public void generateDyePacks() {
	    dyePackList = new ArrayList<DyePack>();

	    for (int i = 0; i < dyePackCount; ++i) {
	        Color randomColor = Colors.randomColor();
	        DyePack dye = new DyePack(randomColor);
	        dyePackList.add(dye);
	    }
	}

	private void initializeDyePacks(List<DyePack> dyes) {
	    assert dyes != null;

	    // Dyepacks are distributed within uniformly distributed regions
	    float regionWidth = width / dyePackList.size();
	    float regionStart = leftEdge();//leftEdge
	    float regionHeight = BaseCode.world.getHeight()
	            - BaseCode.world.getWorldPositionY();

	    float posX, posY;

	    for (int i = 0; i < dyes.size(); i++) {
	        posX = regionStart + (i * regionWidth);
	        posX += RANDOM.nextFloat() * regionWidth;

	        posY = (regionHeight - DyePack.height) * RANDOM.nextFloat()
	                + DyePack.height / 2f;

	        Vector2 position = new Vector2(posX, posY);

	        dyes.get(i).initialize(position);
	    }

	    for (int i = 0; i < userDyePacks.size(); i++) {
	        Vector2 pos = userDyePacks.get(i).center.clone();
	        pos.offset(leftEdge(), 0f);

	        DyePack d = new DyePack(userDyePacks.get(i).color);

	        d.initialize(pos);
	    }
	}
	
	public void update() {
		if(timer.isDone()) {
			initialize(100f);
			timer.reset();
		}
	}

	@Override
	public void destroy() {
		for(DyePack d : dyePackList) {
			d.destroy();
		}
	}
}

