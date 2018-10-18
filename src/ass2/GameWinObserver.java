package ass2;

import java.util.ArrayList;

public class GameWinObserver implements WinObserver {
	private Map map;
	public GameWinObserver(Map map) {
		this.map = map;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void update(WinCondition winCondition) {
		ArrayList<WinCondition> winConditions= map.getWinConditions();
		if(winConditions.contains(winCondition)) {
			winConditions.remove(winCondition);
		}
		else {
			winConditions.add(winCondition);
		}
		// TODO Auto-generated method stub

	}

}
