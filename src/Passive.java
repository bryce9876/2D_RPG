
import org.newdawn.slick.SlickException;

public class Passive extends Monsters {
	
	public static final double PASSIVE_MONSTER_SPEED = 0.2;  //Passive monster movement speed
	public static final int RUN_AWAY_TIME = 3000;            //The time the creature runs away for in ms
	
	long startTime = 0; // starting time that is used for when monster starts running away
	
	private int hpPrev = super.getHP();     //Variable to determine if hp has changed (aka if unit has been damaged)

	//Constructor
	public Passive(String image_path, String name, double x, double y, int maxHP) throws SlickException {
		super(image_path, name, x, y, maxHP);
	}
	
	// How passive monster moves
	@Override
	public void move(World world, Player player, int delta) {
		
		// If health changes, start the timer
		if (super.getHP() != hpPrev){
			startTime = System.currentTimeMillis(); 
		}
		
		// Executes from when timer starts for 'RUN_AWAY_TIME' seconds
		if ((System.currentTimeMillis()-startTime)<RUN_AWAY_TIME){
			
			hpPrev = super.getHP();    // Updates hpPrev
			
			/**The monster moves to run away from player according to algorithm
			 if within range after terrain block check is executed
			 */
			double distx = player.getX() - super.getX();
			double disty = player.getY() - super.getY();
			double distTot = Math.sqrt(Math.pow(distx, 2) + Math.pow(disty, 2));
			
			// Check if terrain can be moved to and move there if it can be moved to
			if (!world.terrainBlocks(super.getX() - distx/distTot * PASSIVE_MONSTER_SPEED, super.getY())){
				// Move in x direction
				super.setX(super.getX() - distx/distTot * PASSIVE_MONSTER_SPEED);
			}
			if (!world.terrainBlocks(super.getX(), super.getY() - disty/distTot * PASSIVE_MONSTER_SPEED)){
				// Move in y direction
				super.setY(super.getY() - disty/distTot * PASSIVE_MONSTER_SPEED);
			}
		}
	}
}
