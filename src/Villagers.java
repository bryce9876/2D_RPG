import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Villagers extends Units {
	
	public static final int VILLAGER_MAX_HP = 1;
	public static final int VILLAGER_MAX_SIGHT = 50;
	
	public Villagers(String image_path, String name, double x, double y, int maxHP)
	        throws SlickException
	    {
	        super(image_path, name, x, y, maxHP);
	    }
	
	//Talk to player
	public void talk(Graphics g, String speech, Player player){
		//Checks if player is within range of villager
		if (super.getUnitNear(player, VILLAGER_MAX_SIGHT)){
			
			// Variables for layout
	        int text_x, text_y;         // Coordinates to draw text
	        int bar_x, bar_y;           // Coordinates to draw rectangles
	        int bar_width, bar_height;  // Size of rectangle to draw
			
			//Actually draws text
			// Panel colours
	        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
	        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
	        
	        //Positions the actual bar and describes colour depending on HP
	        bar_height = 20;
	        //Dynamically sizes width depending on speech length
	        bar_width = g.getFont().getWidth(speech);
	        //Positions it so speech is always just above health bar and centered
	        bar_x = (int) (x - super.getWidth()/2 - bar_width/2 + 90/2);   // where 90 is health bar
	        bar_y = (int) (y - super.getHeight()/2-bar_height);
	        
	        // Positions text on health bar
	        text_x = (int) (x - super.getWidth()/2);
	        text_y = (int) (y - super.getHeight()/2 - bar_height);
	        
	        //Sizes bar depending on length of speech being projected.
	        text_x = bar_x + (bar_width - g.getFont().getWidth(speech)) / 2;
	        g.setColor(BAR_BG);
	        g.fillRect(bar_x, bar_y, bar_width, bar_height);
	        g.setColor(VALUE);
	        g.drawString(speech, text_x, text_y);
			
		}
	}
}
