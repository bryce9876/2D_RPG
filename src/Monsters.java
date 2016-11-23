import org.newdawn.slick.SlickException;

public abstract class Monsters extends Units {
	
	//Monster HP's
	public static final int BAT_MAX_HP = 40;
	public static final int ZOMBIE_MAX_HP = 60;
	public static final int BANDIT_MAX_HP = 40;
	public static final int SKELETON_MAX_HP = 100;
	public static final int DRAELIC_MAX_HP = 140;
	
	//Constructor
	public Monsters(String image_path, String name, double x, double y, int maxHP)
	        throws SlickException
	    {
	        super(image_path, name, x, y, maxHP);
	    }
	
	//How the monster moves
	public abstract void move(World world, Player player, int delta);
	
}



