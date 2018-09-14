package ass2;

public abstract class Enemy extends Entity{

	public Enemy(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public abstract Direction getAction();

}
