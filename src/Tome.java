import org.newdawn.slick.SlickException;

public class Tome extends Items {
	
	final public static int TOME_COOLDOWN_BUFF = -300;
	
	//Constructor
	public Tome(String image_path, double x, double y) throws SlickException {
		super(image_path, x, y);
	}
	
	//Applies buff
	@Override
	public void applyBuff(Player player) {
		//Checks if player is in range of item
		if (super.getUnitNear(player, ITEM_RANGE)){
			// Executes buff once
			while (player.isTome() == false){
				//Changes player attack cooldown by tome buff amount
				player.setCooldown(player.getCooldown() + TOME_COOLDOWN_BUFF);
				//Signifies that item has been used
				player.setTome(true);
			}
		}
	}

}
