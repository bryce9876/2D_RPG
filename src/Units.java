import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Units {
	
	//Unit Stats
	private Image img;
	private String name;
	private int HP, maxHP;
	protected double x;
	protected double y;
	private double width, height;
	
	//Constructor
	public Units(String image_path, String name, double x, double y, int maxHP) throws SlickException {
		img = new Image(image_path);
        this.x = x;
        this.y = y;
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.name = name;
        this.maxHP = maxHP;
        HP = maxHP;
	}
	
	//Check if player is within 50 pixels of unit
	public boolean getUnitNear(Player player, int range){
		//Use pythagoras to work out if player is within 50 pixels of unit
		return Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2)) < range;
	}
	
	//Getters and Setters
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public int getHP() {
		return HP;
	}

	//Sets hP, prevents hP going below zero to prevent health bar of unit overflowing
	public void setHP(int hP) {
		HP -= hP;
		if (HP < 0){
			HP = 0;
		}
	}
	
	//Checks if monster is dead or not
	public boolean isMonsterDead(Units unit){
		return (unit.HP == 0);
	}

	//Renders the unit
	public void render(Graphics g)
    {
		//Render unit image
        img.drawCentered((int) x, (int) y);
        //Render health and name on top of unit
        displayUnitBar(g);
    }
	
	//displays the health/name bar on top of the unit
	private void displayUnitBar(Graphics g){
		
		// Variables for layout
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle 
		
        float health_percent;       // Unit's health, as a percentage
		
        // Panel colours
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
        
		// Display the health of unit
        // Positions text on health bar
        text_x = (int) (x - width/2);
        text_y = (int) (y - height/2);
        
        //Positions the actual bar and describes colour depending on HP
        bar_x = (int) (x - width/2);
        bar_y = (int) (y - height/2);
        bar_width = 90;
        bar_height = 20;
        health_percent = (float)HP/(float)maxHP;
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(name)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(name, text_x, text_y);
	}
}
