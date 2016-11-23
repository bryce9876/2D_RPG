import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Aldric extends Villagers {
	
	//Speech options for Aldric
	public static final String HOLDING_ELIXIR = "The elixir! My father is cured! Thank you!";
	public static final String NOT_HOLDING_ELIXIR = "Please seek out the Elixir of Life to cure the king";
	
	//Signifies if player has won
	private boolean hasWon = false;
	
	//Constructor
	public Aldric(String image_path, String name, double x, double y, int maxHP) throws SlickException {
		super(image_path, name, x, y, maxHP);
	}
	
	//Method to execute speech
	public void speech(Player player, Graphics g) {
		//Checks if player has elixir or not and chooses speech option accordingly
		if (player.isElixir()){
			super.talk(g, HOLDING_ELIXIR, player);
			//Remove elixir from player's inventory and set the 
			if (super.getUnitNear(player, VILLAGER_MAX_SIGHT)){
				player.setElixir(false);
				hasWon = true;
			}
		}
		//Only executes if player doesn't have elixir and hasn't won
		else if (!player.isElixir() && !hasWon){
			super.talk(g, NOT_HOLDING_ELIXIR, player);
		}
	}
}




