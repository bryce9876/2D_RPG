import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Garth extends Villagers {
	
	//Speech options for Garth
	public static final String NOT_HOLDING_AMULET = "Find the Amulet of Vitality, across the river to the west";
	public static final String NOT_HOLDING_SWORD = "Find the Sword of Strength - cross the bridge to the east, then head south";
	public static final String NOT_HOLDING_TOME = "Find the Tome of Agility, in the Land of Shadows";
	public static final String HOLDING_ALL_ITEMS = "You have found all the treasure I know of";
	
	//Constructor
	public Garth(String image_path, String name, double x, double y, int maxHP) throws SlickException {
		super(image_path, name, x, y, maxHP);		
	}
	
	//Method to execute speech
	public void speech(Player player, Graphics g) {
		//Checks if player has a given item or not and chooses speech option accordingly
		if (!player.isAmulet()){
			super.talk(g, NOT_HOLDING_AMULET, player);
		}
		else if(!player.isSword()){
			super.talk(g, NOT_HOLDING_SWORD, player);
		}
		else if(!player.isTome()){
			super.talk(g, NOT_HOLDING_TOME, player);
		}
		else{
			super.talk(g, HOLDING_ALL_ITEMS, player);
		}
	}
}
