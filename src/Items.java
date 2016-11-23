import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Items {
	
	final public static int ITEM_RANGE = 50;   //Maximum distance that item can be picked up by player in pixels
	
	private Image img;     //Item image
	private double x,y;    //Item location
	
	//Constructor
	public Items(String image_path, double x, double y ) throws SlickException {
		img = new Image(image_path);
		this.x = x;
		this.y = y;
	}
	
	//Render item
	public void render()
    {
        img.drawCentered((int) x, (int) y);
    }
	
	//Actually applies buffer to player
	public abstract void applyBuff(Player player);
	
	//Check if player is within 50 pixels of item
	public boolean getUnitNear(Player player, int range){
		//Use pythagoras to work out if player is within 50 pixels of player
		return Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2)) < range;
	}
}



