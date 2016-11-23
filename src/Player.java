/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Sample Solution
 * Author: Matt Giuca <mgiuca>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** The character which the user plays as.
 */
public class Player
{
	// Player images
    private Image img = null;
    private Image img_flipped = null;
    
    // Represents various stats of the player
    private int maxHP = 100;
    private int HP = 100;
    private int damage = 26;
    private int cooldown = 600;
    private int cooldownCount = 0;
    
    // Represents if an item has been used
    private boolean amulet = false;
    private boolean sword = false;
    private boolean tome = false;
    private boolean elixir = false;

    // In pixels
    private double x, y;
    private double width, height;
    private boolean face_left = false;

    // Pixels per millisecond
    private static final double SPEED = 0.25;
    // Attack range in pixels
    public static final int PLAYER_RANGE = 50;
    
    
	/** The x coordinate of the player (pixels). */
    public double getX()
    {
        return x;
    }

    /** The y coordinate of the player (pixels). */
    public double getY()
    {
        return y;
    }
	
	//Setter for HP and also accounts for health going below zero and player death
	public void setHP(int hP) {
		HP -= hP;
		//checks if player is dead
		if (HP < 0){
			HP = 0;
			//method to respawn player with max health when/if it dies
	        isDead(this);
		}
	}
	
	//Generic getters and setters
	public int getHP() {
		return HP;
	}
	public boolean isAmulet() {
		return amulet;
	}
	public void setAmulet(boolean amulet) {
		this.amulet = amulet;
	}
	public boolean isSword() {
		return sword;
	}
	public void setSword(boolean sword) {
		this.sword = sword;
	}
	public boolean isTome() {
		return tome;
	}
	public void setTome(boolean tome) {
		this.tome = tome;
	}
	public boolean isElixir() {
		return elixir;
	}
	public void setElixir(boolean elixir) {
		this.elixir = elixir;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	public void addHP(int hP) {
		this.HP = hP;
	}

	//Attacks monster if close enough to player
    public void attack(World world, boolean a_pressed, int delta) {
    	cooldownCount += delta;      //Cooldown counter
    	
    	//Checks to see if monster in range
    	for (Aggressive monster : world.getAggressive()) {
    		if (monster.getUnitNear(this, PLAYER_RANGE)){
    			
    			//Checks to see if sufficient cooldown has elapsed since last strike and that 'a' is being pressed
        		if (cooldownCount > cooldown && a_pressed){
        			//Damager monster
        			monster.setHP((int)(Math.random()*damage));
        			cooldownCount = 0;
        		}
        	}
    	}
    	for (Passive monster : world.getPassive()) {       // TODO: COMBINE THESE TWO INTO ONE
    		if (monster.getUnitNear(this, PLAYER_RANGE)){
        		if (cooldownCount > cooldown && a_pressed){
        			monster.setHP((int)(Math.random()*damage));
        			cooldownCount = 0;
        		}
        	}
    	}
	}

    /** Creates a new Player.
     * @param image_path Path of player's image file.
     * @param x The Player's starting x location in pixels.
     * @param y The Player's starting y location in pixels.
     */
    public Player(String image_path, double x, double y)
        throws SlickException
    {
        img = new Image(image_path);
        img_flipped = img.getFlippedCopy(true, false);
        this.x = x;
        this.y = y;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    /** Move the player in a given direction.
     * Prevents the player from moving outside the map space, and also updates
     * the direction the player is facing.
     * @param world The world the player is on (to check blocking).
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void move(World world, double dir_x, double dir_y, double delta)
    {
        // Update player facing based on X direction
        if (dir_x > 0)
            this.face_left = false;
        else if (dir_x < 0)
            this.face_left = true;

        // Move the player by dir_x, dir_y, as a multiple of delta * speed
        double new_x = this.x + dir_x * delta * SPEED;
        double new_y = this.y + dir_y * delta * SPEED;

        // Move in x first
        double x_sign = Math.signum(dir_x);
        if(!world.terrainBlocks(new_x + x_sign * width / 2, this.y + height / 2) 
                && !world.terrainBlocks(new_x + x_sign * width / 2, this.y - height / 2)) {
            this.x = new_x;
        }
        
        // Then move in y
        double y_sign = Math.signum(dir_y);
        if(!world.terrainBlocks(this.x + width / 2, new_y + y_sign * height / 2) 
                && !world.terrainBlocks(this.x - width / 2, new_y + y_sign * height / 2)){
            this.y = new_y;
        }
   
    }

    /** Draw the player to the screen at the correct place.
     * @param g The current Graphics context.
     * @param cam_x Camera x position in pixels.
     * @param cam_y Camera y position in pixels.
     */
    public void render()
    {
        Image which_img;
        which_img = this.face_left ? this.img_flipped : this.img;
        which_img.drawCentered((int) x, (int) y);
    }
    
    //Checks if player is dead
    private void isDead(Player player){
    	if (this.HP == 0){
    		//resets player coordinates and health
    		x = World.PLAYER_START_X;
    		y = World.PLAYER_START_Y;
    		HP = maxHP;
    	}
	}
}
