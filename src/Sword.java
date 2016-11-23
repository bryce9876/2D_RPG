import org.newdawn.slick.SlickException;

public class Sword extends Items {
	
	final public static int SWORD_DAMAGE_BUFF = 30;
	
	//Constructor
	public Sword(String image_path, double x, double y) throws SlickException {
		super(image_path, x, y);
	}
	
	//Applies buff
	@Override
	public void applyBuff(Player player) {
		if (super.getUnitNear(player, ITEM_RANGE)){
			while (player.isSword() == false){
				//Increase players damage by sword buff amount
				player.setDamage(player.getDamage()+SWORD_DAMAGE_BUFF);
				//Signifies that item has been used
				player.setSword(true);
			}
		}
	}

}
