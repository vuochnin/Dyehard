package dyehard.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.BaseCode;
import dyehard.Enums.EnemyType;
import dyehard.Enums.PowerUpType;
// TODO: Auto-generated Javadoc
/**
 * Adapted from the "Parse an XML File using the DOM Parser" example by
 * Sotirios-Efstathios Maneas located here:
 * http://examples.javacodegeeks.com/core-java/xml/java-xml-parser-tutorial/
 */
public class ConfigurationFileParser {
	
	/** The instance. */
	private static ConfigurationFileParser instance = null;

	static {
		instance = new ConfigurationFileParser();
	}
	
    /** The factory. */
    private static DocumentBuilderFactory factory;    
    
    /** The builder. */
    private static DocumentBuilder builder;
    
    // all of the parsed data is kept in these objects
    // should be redesigned to just make the real 
    // objects here instead of haveing these middlemen
    /** The powerup data. */
    // objects holding the data
    private PowerupData powerupData = new PowerupData();        
    
    /** The enemy data. */
    //private EnemyData enemyData = new EnemyData();        
    
    /** The hero data. */
    private HeroData heroData = new HeroData();        
    
    /** The weapon overheat data. */
    private WeaponOverheatData weaponOverheatData = new WeaponOverheatData();        
    
    /** The limited ammo data. */
    private LimitedAmmoData limitedAmmoData = new LimitedAmmoData();        
    
    /** The world data. */
    private WorldData worldData = new WorldData();        
    
    /** The dye pack data. */
    private DyePackData dyePackData = new DyePackData();        
    
    /** The debris data. */
    private DebrisData debrisData = new DebrisData();    
    
    /** The sound data. */
    private SoundData soundData = new SoundData();    
	
	/** The powerups. */
	private Map<PowerUpType, PowerupData> powerups = new HashMap<PowerUpType, PowerupData>();		
	
	/** The enemies. */
	private Map<EnemyType, EnemyData> enemies = new HashMap<EnemyType, EnemyData>();
	
    /**
     * Instantiates a new configuration file parser.
     */
    private ConfigurationFileParser() {
        try {
        	factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            parseEnemyData();
            parseHeroData();
            parseOverheatData();
            parseLimitedAmmoData();
            parseWorldData();
            parseDyePackData();
            parsePowerUpData();
            parseDebrisData();
            parseSoundData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the single instance of ConfigurationFileParser.
     *
     * @return single instance of ConfigurationFileParser
     */
    public static ConfigurationFileParser getInstance() {
    	return instance;
    }

    /**
     * Parses the float.
     *
     * @param elem is the element in an xml file
     * @param tag is the name of the element to be parsed
     * @return the parsed float
     */
    private static float parseFloat(Element elem, String tag) {
        if (elem.getElementsByTagName(tag).item(0) != null) {
            return Float.parseFloat(elem.getElementsByTagName(tag).item(0)
                    .getChildNodes().item(0).getNodeValue());
        }

        return 0; //bug?  isn't 0 an int?
    }

    /**
     * Parses the int.
     *
     * @param elem is the element in an xml file
     * @param tag is the name of the element to be parsed
     * @return the parsed int
     */
    private static int parseInt(Element elem, String tag) {
        if (elem.getElementsByTagName(tag).item(0) != null) {
            return Integer.parseInt(elem.getElementsByTagName(tag).item(0)
                    .getChildNodes().item(0).getNodeValue());
        }

        return 0;
    }

    /**
     * Load external file.
     *
     * @param path of the file to be loaded
     * @return the input stream
     */
    private static InputStream loadExternalFile(String path) {
        String basePath = BaseCode.resources.basePath;
        URL url;

        try {
            url = new URL(basePath + path);
            URLConnection in = url.openConnection();
            if (in.getContentLengthLong() > 0) {
                return url.openStream();
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates the node list.
     *
     * @param file is the name of the xml file to be parsed
     * @return the node list
     * @throws Exception the exception
     */
    private static NodeList createNodeList(String file) throws Exception {
        String filePath = "resources/" + file + ".xml";

        InputStream is = null;

        if (is == null) {
            is = loadExternalFile(filePath);
        }

        if (is == null) {
            is = ClassLoader.getSystemResourceAsStream(filePath);
        }

        Document document = builder.parse(is);

        return document.getDocumentElement().getChildNodes();
    }

    /**
     * Parses the enemy data.
     *
     * @throws Exception the exception
     */
    private void parseEnemyData() throws Exception {
        NodeList nodeList = createNodeList("Enemies");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                EnemyType type = EnemyType.valueOf(node.getAttributes()
                        .getNamedItem("type").getNodeValue());
                
                EnemyData enemyData = new EnemyData();
                
                enemyData.setWidth(parseFloat(elem, "width"));
                enemyData.setHeight(parseFloat(elem, "height"));
                enemyData.setSleepTimer(parseFloat(elem, "sleepTimer"));
                enemyData.setSpeed(parseFloat(elem, "speed"));
                enemyData.setUniqueAttributes(elem
                        .getElementsByTagName("uniqueAttributes"));


                enemies.put(type, enemyData);
            }
        }
    }

    /**
     * Gets the enemy data.
     *
     * @param type is the type of enemy
     * @return the enemy type
     */
    public EnemyData getEnemyData(EnemyType type) {
        return enemies.get(type);
    }

    /**
     * Parses the hero data.
     *
     * @throws Exception the exception
     */
    private void parseHeroData() throws Exception {
        NodeList nodeList = createNodeList("Hero");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                heroData.setHeroWidth(parseFloat(elem, "width"));
                heroData.setHeroHeight(parseFloat(elem, "height"));
                heroData.setHeroJetSpeed(parseFloat(elem, "jetSpeed"));
                heroData.setHeroSpeedLimit(parseFloat(elem, "speedLimit"));
                heroData.setHeroDrag(parseFloat(elem, "drag"));
            }
        }
    }

    /**
     * Parses the overheat data.
     *
     * @throws Exception the exception
     */
    private void parseOverheatData() throws Exception {
        NodeList nodeList = createNodeList("OverheatWeapon");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                weaponOverheatData.setOverheatFiringRate(parseFloat(elem, "firingRate"));
                weaponOverheatData.setOverheatCooldownRate(parseFloat(elem, "cooldownRate"));
                weaponOverheatData.setOverheatHeatLimit(parseFloat(elem, "heatLimit"));
            }
        }
    }

    /**
     * Parses the limited ammo data.
     *
     * @throws Exception the exception
     */
    private void parseLimitedAmmoData() throws Exception {
        NodeList nodeList = createNodeList("LimitedAmmoWeapon");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                limitedAmmoData.setLimitedFiringRate(parseFloat(elem, "firingRate"));
                limitedAmmoData.setLimitedMaxAmmo(parseInt(elem, "maxAmmo"));
                limitedAmmoData.setLimitedReloadAmount(parseInt(elem, "reloadAmount"));
            }
        }
    }

    /**
     * Parses the world data.
     *
     * @throws Exception the exception
     */
    private void parseWorldData() throws Exception {
        NodeList nodeList = createNodeList("World");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                worldData.setWorldEnemyFrequency(parseFloat(elem, "enemySpawnTimer"));
                worldData.setWorldPowerUpCount(parseInt(elem, "powerUpCount"));
                worldData.setWorldDyePackCount(parseInt(elem, "dyePackCount"));
                worldData.setWorldDebrisCount(parseInt(elem, "debrisCount"));
                worldData.setWorldMapLength(parseInt(elem, "mapLength"));
                worldData.setWorldGameSpeed(parseFloat(elem, "gameSpeed"));
            }
        }
    }

    /**
     * Parses the dye pack data.
     *
     * @throws Exception the exception
     */
    private void parseDyePackData() throws Exception {
        NodeList nodeList = createNodeList("DyePacks");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                dyePackData.setDyePackWidth(parseFloat(elem, "width"));
                dyePackData.setDyePackHeight(parseFloat(elem, "height"));
                dyePackData.setDyePackSpeed(parseFloat(elem, "speed"));
            }
        }
    }

    /**
     * Parses the power up data.
     *
     * @throws Exception the exception
     */
    private void parsePowerUpData() throws Exception {
        NodeList nodeList = createNodeList("PowerUps");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                if (node.getAttributes().getNamedItem("type").getNodeValue()
                        .equals("Shared")) {
                	powerupData.setPowerUpWidth(parseFloat(elem, "width"));
                	powerupData.setPowerUpHeight(parseFloat(elem, "height"));
                	powerupData.setPowerUpSpeed(parseFloat(elem, "speed"));
                } else {
                    PowerUpType type = PowerUpType.valueOf(node.getAttributes()
                            .getNamedItem("type").getNodeValue());

                    powerupData.setDuration(parseFloat(elem, "duration"));
                    powerupData.setMagnitude(parseFloat(elem, "magnitude"));

                    powerups.put(type, powerupData);
                }
            }
        }
    }

    /**
     * Parses the debris data.
     *
     * @throws Exception the exception
     */
    private void parseDebrisData() throws Exception {
        NodeList nodeList = createNodeList("Debris");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                debrisData.setDebrisWidth(parseFloat(elem, "width"));
                debrisData.setDebrisHeight(parseFloat(elem, "height"));
                debrisData.setDebrisSpeed(parseFloat(elem, "speed"));
            }
        }
    }

    /**
     * Parses the sound data.
     *
     * @throws Exception the exception
     */
    private void parseSoundData() throws Exception {
        NodeList nodeList = createNodeList("Sounds");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                soundData.setBgMusicPath(parseFloat(elem, "bgMusicPath"));
                soundData.setPickUpSound(parseFloat(elem, "pickUpSound"));
                soundData.setPowerUpSound(parseFloat(elem, "powerUpSound"));
                soundData.setPaintSpraySound(parseFloat(elem, "paintSpraySound"));
                soundData.setEnemySpaceship1(parseFloat(elem, "enemySpaceship1"));
                soundData.setLoseSound(parseFloat(elem, "loseSound"));
                soundData.setWinSound(parseFloat(elem, "winSound"));
                soundData.setLifeLostSound(parseFloat(elem, "lifeLostSound"));
                soundData.setPortalEnter(parseFloat(elem, "portalEnter"));
                soundData.setPortalExit(parseFloat(elem, "portalExit"));
                soundData.setPortalLoop(parseFloat(elem, "portalLoop"));
                soundData.setShieldSound(parseFloat(elem, "shieldSound"));
            }
        }
    }
    
    /**
     * Gets the hero data.
     *
     * @return the hero data
     */
    public HeroData getHeroData() {
    	return heroData;
    }
    
    /**
     * Gets the world data.
     *
     * @return the world data
     */
    public WorldData getWorldData() {
    	return worldData;
    }
    
    /* DEPRICATED
     * Returns the "enemy data" object, which
     * can be found in the enemies map object
     * via key. Use getEnemyData(EnemyTpe type)
     * instead.
    /**
     * Gets the enemny data.
     *
     * @return the enemny data
    public EnemyData getEnemyData() {
    	return enemyData;
    }
    */
    
    /**
     * Gets the powerup data.
     *
     * @return the powerup data
     */
    public PowerupData getPowerupData() {
    	return powerupData;
    }
    
    /**
     * Gets the weapon overheat data.
     *
     * @return the weapon overheat data
     */
    public WeaponOverheatData getWeaponOverheatData() {
    	return weaponOverheatData;
    }
    
    /**
     * Gets the limited ammo data.
     *
     * @return the limited ammo data
     */
    public LimitedAmmoData getLimitedAmmoData() {
    	return limitedAmmoData;
    }
    
    /**
     * Gets the dye pack data.
     *
     * @return the dye pack data
     */
    public DyePackData getDyePackData() {
    	return dyePackData;
    }
    
    /**
     * Gets the debris data.
     *
     * @return the debris data
     */
    public DebrisData getDebrisData() {
    	return debrisData;
    }
    
    /**
     * Gets the sound data.
     *
     * @return the sound data
     */
    public SoundData getSoundData() {
    	return soundData;
    }
    
    /**
     * Gets the powerup type.
     *
     * @param type the type
     * @return the powerup type
     */
    public PowerupData getPowerupType(PowerUpType type) {
        return powerups.get(type);
    }
}
