import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Elvira extends Villagers {
	
	//Speech options for Elvira
	public static final String FULLY_HEALED = "Return to me if you ever need healing";
	public static final String NEEDS_HEALING = "  You're looking much healthier now    ";
	
	public static final int SPEECH_LINGER_TIME = 2000; //How long 'NEEDS_HEALING' text lingers for
	long startTime = 0; //Starting time that is used for when 'NEEDS_HEALING' text appears

	//Constructor
	public Elvira(String image_path, String name, double x, double y, int maxHP) throws SlickException {
		super(image_path, name, x, y, maxHP);		
	}

	//Method to execute speech
	public void speech(Player player, Graphics g) {
		//Checks if player fully healed or not
		if (player.getHP() == player.getMaxHP()){
			super.talk(g, FULLY_HEALED, player);
		}
		else {
			//Checks if player is in range, and heals player if it is
			if (super.getUnitNear(player, VILLAGER_MAX_SIGHT)){
				player.addHP(player.getMaxHP());
			}
			super.talk(g, NEEDS_HEALING, player);
			//Starts timer
			startTime = System.currentTimeMillis();
		}
		// Keeps 'NEEDS_HEALING' text on the screen for 'SPEECH_LINGER_TIME' seconds
		if ((System.currentTimeMillis()-startTime)<SPEECH_LINGER_TIME){
			super.talk(g, NEEDS_HEALING, player);
		}
	}
}
