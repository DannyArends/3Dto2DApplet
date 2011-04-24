package nl.dannyarends.gameserver.movement.heuristics;

import nl.dannyarends.gameserver.movement.Mover;
import nl.dannyarends.gameserver.movement.SearchHeuristic;

public class EuclideanEstimator implements SearchHeuristic {

	@Override
	public double getCost(Mover mover, int x, int y, int tx, int ty) {
		int dx = tx - x;
		int dy = ty - y;
		return Math.sqrt((dx*dx)+(dy*dy));
	}

}
