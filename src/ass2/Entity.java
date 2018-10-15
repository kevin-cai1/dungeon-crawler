package ass2;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 7629282020419643851L;
	private int id;
	public Entity(int id){
		this.id = id;
	}

	/**
	 * returns ID of the entity
	 * @return id of entity
	 */
	public int getId() {
		return id;
	}

	public abstract String toString();
}
