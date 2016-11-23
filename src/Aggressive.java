import org.newdawn.slick.SlickException;

public class Aggressive extends Monsters {
	
	//Monster damages
	public static final int ZOMBIE_DAMAGE = 10;
	public static final int BANDIT_DAMAGE = 8;
	public static final int SKELETON_DAMAGE = 16;
	public static final int DRAELIC_DAMAGE = 30;
	//Monster cooldowns
	public static final int ZOMBIE_COOLDOWN = 800;
	public static final int BANDIT_COOLDOWN = 200;
	public static final int SKELETON_COOLDOWN = 500;
	public static final int DRAELIC_COOLDOWN = 400;
	//Monster sight and attack range
	public static final int MONSTER_SIGHT_RANGE = 150;
	public static final int MONSTER_ATTACK_RANGE = 50;
	//Monster speed
	public static final double AGGRESSIVE_MONSTER_SPEED = 0.25;
	
	//Monster stats
	int cooldown;
	int cooldownCount;
	int damage;
	 
	//Constructor
	public Aggressive(String image_path, String name, double x, double y, int maxHP, int damage, int cooldown)
			throws SlickException {
		super(image_path, name, x, y, maxHP);
		this.cooldown = cooldown;
		this.damage = damage;
	}
	
	
	//Method that determines how an aggressive monster moves
	@Override
	public void move(World world, Player player, int delta) {
		
		//Checks if monster is within sight and out of attack range
		if (getUnitNear(player, MONSTER_SIGHT_RANGE) && !getUnitNear(player, MONSTER_ATTACK_RANGE)){
			
			/**The monster moves to follow the player according to algorithm
			 if within range after terrain block check is executed
			 */
			double distx = player.getX() - super.getX();
			double disty = player.getY() - super.getY();
			double distTot = Math.sqrt(Math.pow(distx, 2) + Math.pow(disty, 2));
			
			//Check if terrain can be moved to
			if (!world.terrainBlocks(super.getX() + distx/distTot * AGGRESSIVE_MONSTER_SPEED, super.getY())){
				//Move in x direction
				super.setX(super.getX() + distx/distTot * AGGRESSIVE_MONSTER_SPEED);
			}
			if (!world.terrainBlocks(super.getX(), super.getY() + disty/distTot * AGGRESSIVE_MONSTER_SPEED)){
				//Move in y direction
				super.setY(super.getY() + disty/distTot * AGGRESSIVE_MONSTER_SPEED);
			}
		}
	}
	
	//Monster attacks player
	public void attack(Player player, int delta){
		cooldownCount += delta;    //Monster attack cooldown counter
		
		//Attack player - Checks if monster is in attack range relative to player and if cooldown is over
		if (getUnitNear(player, MONSTER_ATTACK_RANGE) && cooldownCount > cooldown){
			//Actually damages player with a random damage between 0 and damage
			player.setHP((int)(Math.random()*damage));
			cooldownCount = 0;	
		}
	}
}



