package nl.dannyarends.gameserver.movement.heuristics;

import nl.dannyarends.gameserver.movement.Mover;
import nl.dannyarends.gameserver.movement.SearchHeuristic;

public class ManhattanEstimator implements SearchHeuristic{

	@Override
	public double getCost(Mover mover, int x, int y, int tx, int ty) {
		int dx = Math.abs(tx - x);
	    int dy = Math.abs(ty - y);
	    return dx+dy;
	}
}
