/*
#
# \file Mover.java
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

import java.util.ArrayList;

import nl.dannyarends.gameserver.movement.TileBasedMap.TileType;



public class Mover {
	private double speedMod;
	ArrayList<TileType> allowed = new ArrayList<TileType>();
	
	
   /* Mover class to specify:
	* 1: Which terrainTypes can be moved, by which movers
	* 2: Adds a constructor that allows a speed factor for an object
	* TODO: Get a Basic scheme for types
	* IDEA: Perhaps use a the real 'internal object' classes, and make this an interface ?
	*/	
	Mover(double s){
		speedMod = s;
	}
	
	Mover(ArrayList<TileType> a,double s){
		allowed.addAll(a);
		speedMod = s;
	}
	
	public boolean canMoveTo(TileType t){
		for(TileType a : allowed){
			if(a==t) return true;
		}
		return false;
	}
	
	public double costToMoveTo(TileType t){
		if(canMoveTo(t)) return (t.cost * speedMod);
		return 0;
	}
}
