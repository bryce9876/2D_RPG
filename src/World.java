/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Sample Solution
 * Author: Matt Giuca <mgiuca>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	//Player/villager starting positions
    public static final int PLAYER_START_X = 756, PLAYER_START_Y = 684;
    public static final int ALDRIC_X = 467, ALDRIC_Y = 679;
    public static final int ELVIRA_X = 738, ELVIRA_Y = 549;
    public static final int GARTH_X = 756, GARTH_Y = 870;
    
    //Item starting positions
    public static final int AMULET_X = 965, AMULET_Y = 3563;
    public static final int SWORD_X = 546, SWORD_Y = 6707;
    public static final int TOME_X = 4791, TOME_Y = 1253;
    public static final int ELIXIR_X = 1976, ELIXIR_Y = 402;
    
    // Declare objects
    private Player player = null;
    private TiledMap map = null;
    private Camera camera = null;
    private Aldric aldric = null;
    private Elvira elvira = null;
    private Garth garth = null;
	private ArrayList<Aggressive> aggressive = null;
    private ArrayList<Passive> passive = null;
    private Amulet amulet = null;
    private Sword sword = null;
    private Tome tome = null;
    private Elixir elixir = null;
    
    private int i; //counter variable for loops
    
    // Declare image panel 
    Image panel = new Image(RPG.ASSETS_PATH + "/panel.png"); 
    
    public ArrayList<Aggressive> getAggressive() {
		return aggressive;
	}

	public ArrayList<Passive> getPassive() {
		return passive;
	}
    


    /** Map width, in pixels. */
    private int getMapWidth()
    {
        return map.getWidth() * getTileWidth();
    }

    /** Map height, in pixels. */
    private int getMapHeight()
    {
        return map.getHeight() * getTileHeight();
    }

    /** Tile width, in pixels. */
    private int getTileWidth()
    {
        return map.getTileWidth();
    }

    /** Tile height, in pixels. */
    private int getTileHeight()
    {
        return map.getTileHeight();
    }

    /** Create a new World object. */
    public World()
    throws SlickException
    {
        map = new TiledMap(RPG.ASSETS_PATH + "/map.tmx", RPG.ASSETS_PATH);
        player = new Player(RPG.ASSETS_PATH + "/units/player.png", PLAYER_START_X, PLAYER_START_Y);
        camera = new Camera(player, RPG.SCREEN_WIDTH, RPG.SCREEN_HEIGHT);
        aldric = new Aldric(RPG.UNITS_PATH + "/prince.png", "Aldric", ALDRIC_X, ALDRIC_Y, Villagers.VILLAGER_MAX_HP); 
        elvira = new Elvira(RPG.UNITS_PATH + "/shaman.png", "Elvira", ELVIRA_X, ELVIRA_Y, Villagers.VILLAGER_MAX_HP); 
        garth = new Garth(RPG.UNITS_PATH + "/peasant.png", "Garth", GARTH_X, GARTH_Y, Villagers.VILLAGER_MAX_HP); 
        monstersSetup();
        amulet = new Amulet(RPG.ITEMS_PATH + "/amulet.png", AMULET_X, AMULET_Y); 
        sword = new Sword(RPG.ITEMS_PATH + "/sword.png", SWORD_X, SWORD_Y); 
        tome = new Tome(RPG.ITEMS_PATH + "/tome.png", TOME_X, TOME_Y); 
        elixir = new Elixir(RPG.ITEMS_PATH + "/elixir.png", ELIXIR_X, ELIXIR_Y); 
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(int dir_x, int dir_y, int delta, boolean a_pressed)
    throws SlickException
    {
    	//Updates player movement and attack if applicable
        player.move(this, dir_x, dir_y, delta);
        player.attack(this, a_pressed, delta);
        //Updates camera
        camera.update();
        //Updates monster movement and attack if applicable
        for (Aggressive monsters : aggressive) {
        	monsters.move(this, player, delta);
        	monsters.attack(player, delta);
        }
        for (Passive monsters : passive) {
        	monsters.move(this, player, delta);
        }
        //Applies buffer from items if player is in range
        amulet.applyBuff(player);
        sword.applyBuff(player);
        tome.applyBuff(player);
        elixir.applyBuff(player);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
        // Render the relevant section of the map
        int x = -(camera.getMinX() % getTileWidth());
        int y = -(camera.getMinY() % getTileHeight());
        int sx = camera.getMinX() / getTileWidth();
        int sy = camera.getMinY()/ getTileHeight();
        int w = (camera.getMaxX() / getTileWidth()) - (camera.getMinX() / getTileWidth()) + 1;
        int h = (camera.getMaxY() / getTileHeight()) - (camera.getMinY() / getTileHeight()) + 1;
        map.render(x, y, sx, sy, w, h);
        
        // Translate the Graphics object
        g.translate(-camera.getMinX(), -camera.getMinY());
        
        //Set text colour
        g.setColor(Color.white);
        // Render the player
        player.render();
        
        //Render the villagers on the map
        aldric.render(g);
        elvira.render(g);
        garth.render(g);
        
        //Render the villager speech options
        aldric.speech(player, g);
        elvira.speech(player, g);
        garth.speech(player, g);
        
        //Render monsters
        for (i = 0; i < aggressive.size(); i++) {
        	//Checks if monster is alive first
        	if (aggressive.get(i).isMonsterDead(aggressive.get(i))){
        		aggressive.remove(i);
        	}
        	else {
        		aggressive.get(i).render(g);
        	}
        }
        for (i = 0; i < passive.size(); i++) {
        	//Checks if monster is alive first
        	if (passive.get(i).isMonsterDead(passive.get(i))){
        		passive.remove(i);
        	}
        	else {
        		passive.get(i).render(g);
        	}
        }
        //Renders panel
        renderPanel(g);	 
        
        //Render Items after checking if it has been used or not
        if (!player.isAmulet()) amulet.render();
        if (!player.isSword()) sword.render();
        if (!player.isTome()) tome.render();
        if (!player.isElixir()) elixir.render();
    }

    /** Determines whether a particular map coordinate blocks movement.
     * @param x Map x coordinate (in pixels).
     * @param y Map y coordinate (in pixels).
     * @return true if the coordinate blocks movement.
     */
    public boolean terrainBlocks(double x, double y)
    {
        // Check we are within the bounds of the map
        if (x < 0 || y < 0 || x > getMapWidth() || y > getMapHeight()) {
            return true;
        }
        
        // Check the tile properties
        int tile_x = (int) x / getTileWidth();
        int tile_y = (int) y / getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String block = map.getTileProperty(tileid, "block", "0");
        return !block.equals("0");
    }
    
    
    
	//Sets up the monsters intially
	private void monstersSetup() throws SlickException{
		//Array lists for aggressive/passive monsters to be put in
		aggressive = new ArrayList<Aggressive>();
		passive = new ArrayList<Passive>();
		
		//Read file containing unit data
		File file = new File("data/units.dat");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		//Checks to see if file was found correctly
		} catch (FileNotFoundException e) {
			System.out.println("Error, units file not found");
			System.exit(0);
		}
		//Scans each line of input into "line" string
		while(scanner.hasNextLine()){
			   String line = scanner.nextLine();
			   addMonster(line);
			}
	}
	
	//Adds individual monsters to the game
	private void addMonster(String line) throws SlickException{
		int monsterX, monsterY;
		int comma1Index, comma2Index;
		
		//Finds where the first and second comma is so you can seperate name, x, and y
		comma1Index = line.indexOf( ',' );
		comma2Index = line.lastIndexOf( ',' );
		//Thus can now extract x and y positions and put into integer form
		monsterX = Integer.parseInt(line.substring(comma1Index+1, comma2Index));
		monsterY = Integer.parseInt(line.substring(comma2Index+1, line.length()));

		/** Creates monster object depending on the character indicated by the data file, and adds it to relevant
		aggressive/passive array
	     */
		
		if (line.substring(0, comma1Index).compareTo("GiantBat") == 0 ){
			passive.add(new Passive(RPG.UNITS_PATH + "dreadbat.png", "Giant Bat", monsterX, monsterY, Monsters.BAT_MAX_HP));
		}
		else if (line.substring(0, comma1Index).compareTo("Zombie") == 0 ){
			aggressive.add(new Aggressive(RPG.UNITS_PATH + "zombie.png", "Zombie", monsterX, monsterY, Monsters.ZOMBIE_MAX_HP,
					Aggressive.ZOMBIE_DAMAGE, Aggressive.ZOMBIE_COOLDOWN));
		}
		else if (line.substring(0, comma1Index).compareTo("Bandit") == 0 ){
			aggressive.add(new Aggressive(RPG.UNITS_PATH + "bandit.png", "Bandit", monsterX, monsterY, Monsters.BANDIT_MAX_HP,
					Aggressive.BANDIT_DAMAGE, Aggressive.BANDIT_COOLDOWN));
		}
		else if (line.substring(0, comma1Index).compareTo("Skeleton") == 0 ){
			aggressive.add(new Aggressive(RPG.UNITS_PATH + "skeleton.png", "Skeleton", monsterX, monsterY,
					Monsters.SKELETON_MAX_HP, Aggressive.SKELETON_DAMAGE, Aggressive.SKELETON_COOLDOWN));
		}
		else if (line.substring(0, comma1Index).compareTo("Draelic") == 0 ){
			aggressive.add(new Aggressive(RPG.UNITS_PATH + "necromancer.png", "Necromancer", monsterX, monsterY,
					Monsters.DRAELIC_MAX_HP, Aggressive.DRAELIC_DAMAGE, Aggressive.DRAELIC_COOLDOWN));
		}
		//else: must be villager data on the specific line and thus the line is ignored 
	}
    
	//Renders Panel
	
	/** Renders the player's status panel.
     * @param g The current Slick graphics context.
	 * @throws SlickException 
     */
    public void renderPanel(Graphics g) throws SlickException
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage
        
        Image img;                  // Inventory items image

        // Panel background image
        panel.draw(camera.getMinX(), camera.getMaxY() - RPG.PANEL_HEIGHT);

        // Display the player's health
        text_x = 15 + camera.getMinX();
        text_y = camera.getMaxY() - RPG.PANEL_HEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = Integer.toString(player.getHP()) + "/" + Integer.toString(player.getMaxHP());   

        bar_x = 90 + camera.getMinX();
        bar_y = camera.getMaxY() - RPG.PANEL_HEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = (float)player.getHP()/player.getMaxHP();                        
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200 + camera.getMinX();
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = Integer.toString(player.getDamage());                                    
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = Integer.toString(player.getCooldown());                                  
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420 +  + camera.getMinX(), text_y);
        bar_x = 490 + camera.getMinX();
        bar_y = camera.getMaxY() - RPG.PANEL_HEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490 + camera.getMinX();
        inv_y = camera.getMaxY() - RPG.PANEL_HEIGHT
            + ((RPG.PANEL_HEIGHT - getTileWidth()) / 2);
        // Display inventory items
        if (player.isAmulet()){
        	img = new Image(RPG.ITEMS_PATH + "/amulet.png");
        	img.drawCentered((int) inv_x + getTileWidth()/2, (int) inv_y + RPG.PANEL_HEIGHT/2);
        }
        if (player.isSword()){
        	img = new Image(RPG.ITEMS_PATH + "/sword.png");
        	img.drawCentered((int) inv_x + getTileWidth()/2 + getTileWidth(), (int) inv_y + RPG.PANEL_HEIGHT/2);
        }
        if (player.isTome()){
        	img = new Image(RPG.ITEMS_PATH + "/tome.png");
        	img.drawCentered((int) inv_x + getTileWidth()/2 + getTileWidth()*2, (int) inv_y + RPG.PANEL_HEIGHT/2);
        } 
        if (player.isElixir()){
        	img = new Image(RPG.ITEMS_PATH + "/elixir.png");
        	img.drawCentered((int) inv_x + getTileWidth()/2 + getTileWidth()*3, (int) inv_y + RPG.PANEL_HEIGHT/2);
        }
    }
}
