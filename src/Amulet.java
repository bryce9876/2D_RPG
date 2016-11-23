import org.newdawn.slick.SlickException;

public class Amulet extends Items {
	
	final public static int AMULET_HP_BUFF = 80;

	//Constructor
	public Amulet(String image_path, double x, double y) throws SlickException {
		super(image_path, x, y);
	}
	
	//Applies buff
	@Override
	public void applyBuff(Player player) {
		//Checks that player is in range of item
		if (super.getUnitNear(player, ITEM_RANGE)){
			
			//while loop allows it to only execute once
			while (player.isAmulet() == false){
				//Increase Hp and MaxHP by amulet buff amount
				player.setMaxHP(player.getMaxHP() + AMULET_HP_BUFF);
				player.addHP(player.getHP() + AMULET_HP_BUFF);
			
				//Signifies that item has been used
				player.setAmulet(true);
			}
		}
		
	}

	

}
