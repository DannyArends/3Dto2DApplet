/*
#
# \file TileBasedMap.java
#
# Based on: http://www.cokeandcode.com/pathfinding
#
# copyright (c) 2009-2010, Danny Arends
# last modified Apr, 2011
# first written Apr, 2011
#
#     This program is free software; you can redistribute it and/or
#     modify it under the terms of the GNU General Public License,
#     version 3, as published by the Free Software Foundation.
# 
#     This program is distributed in the hope that it will be useful,
#     but without any warranty; without even the implied warranty of
#     merchantability or fitness for a particular purpose.  See the GNU
#     General Public License, version 3, for more details.
# 
#     A copy of the GNU General Public License, version 3, is available
#     at http://www.r-project.org/Licenses/GPL-3
#
*/

package nl.dannyarends.gameserver.movement;

public interface TileBasedMap {
	public enum TileType{
		NONE(0.0),
		GRASS(1.0),
		SAND(1.25),
		HILL(1.50),
		MOUNTAIN(2.25),
		SWATER(1.3),
		DWATER(0.5),
		ALL(1.0);
		
		double cost;
		
		TileType(double c){
			cost=c;
		}
	}
	
	int getX();
	int getY();
	float getMovementCost(Mover mover, int x, int y, int xp, int yp);
	boolean isValidLocation(Mover mover, int x,int y, int xp,int yp);
	void pathFinderVisited(int x, int y);
	boolean blocked(Mover mover, int x, int y);
}
