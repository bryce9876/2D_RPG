import org.newdawn.slick.SlickException;

public class Elixir extends Items {
	
	//Constructor
	public Elixir(String image_path, double x, double y) throws SlickException {
		super(image_path, x, y);
	}
	
	//Applies buff
	@Override
	public void applyBuff(Player player) {
		//Checks if player is in range of item
		if (super.getUnitNear(player, ITEM_RANGE)){
			//Signifies that item has been used
			player.setElixir(true);
		}
	}

}
