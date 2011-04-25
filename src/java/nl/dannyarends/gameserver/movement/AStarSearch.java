/**
# \file AStarSearch.java
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

import nl.dannyarends.gameserver.movement.heuristics.EuclideanEstimator;


/**
 * \brief Implementation of AStar 2D search.<br>
 *
 * Based on: http://www.cokeandcode.com/pathfinding
 * bugs: none found<br>
 */
public class AStarSearch implements Runnable{
	private ArrayList<Node> closed = new ArrayList<Node>();
	private ArrayList<Node> open = new ArrayList<Node>();
	private Node[][] nodes;
	private SearchHeuristic heuristic;
	private int maxSearchDistance;
	private TileBasedMap map;
	private Mover mover;
	int sx,sy;
	int tx,ty;
	
	//AstarSearch for finding paths
	//TODO: Use either maxDepth or a set time
	//TODO: Create a TestMap, and some minor tests
	//TODO: Add it to a java Servlet ? So we can serve movements to the java Applet ?
	AStarSearch(int sx,int sy,int tx,int ty,TileBasedMap map){
		this(sx,sy,tx,ty,map,new EuclideanEstimator(),10);
	}
	
	class Node{
		float cost   = 0;
		int   depth  = 0;
		int   x;
		int   y;
		Node  parent = null;
		public Object heuristic;
		
		public int setParent(Node p) {
			parent=p;
			return (depth+1);
		}
	}
	
	AStarSearch(int sx, int sy, int tx, int ty, TileBasedMap map, SearchHeuristic heuristic, int maxDepth){
		this.heuristic=heuristic;
		this.map=map;
		nodes = new Node[map.getX()][map.getX()];
		nodes[sx][sy].cost = 0;
		nodes[sx][sy].depth = 0;
		closed.clear();
		open.clear();
		this.sx=sx;
		this.sy=sy;
		this.tx=tx;
		this.ty=ty;
		maxSearchDistance=maxDepth;
		open.add(nodes[sx][sy]);
	}
	
	
	public Path getPath(){
		Path path;
		if (nodes[tx][ty].parent == null) return null;
		
		path = new Path();
		Node target = nodes[tx][ty];
		while (target != nodes[sx][sy]) {
			path.prependStep(target.x, target.y);
			target = target.parent;
		}
		path.prependStep(sx,sy);
		return path;
	}
	
	@Override
	public void run() {
		int maxDepth = 0;
		while ((open.size() != 0) && (maxDepth < maxSearchDistance)) {
			Node current = getFirstInOpen();
			if (current == nodes[tx][ty]) {
			     break;
			}
			removeFromOpen(current);
			addToClosed(current);
						
			for (int x=-1;x<2;x++) {
				for (int y=-1;y<2;y++) {
					if ((x == 0) && (y == 0)) {
						continue;
					}
								
					int xp = x + current.x;
					int yp = y + current.y;
								
					if (map.isValidLocation(mover,sx,sy,xp,yp)) {	
						int nextStepCost = (int) (current.cost + map.getMovementCost(mover, current.x, current.y, xp, yp));
						Node neighbour = nodes[xp][yp];
						map.pathFinderVisited(xp, yp);
						if (nextStepCost < neighbour.cost) {
							if (inOpenList(neighbour)) {
								removeFromOpen(neighbour);
							}
							if (inClosedList(neighbour)) {
								removeFromClosed(neighbour);
							}
						}
								
						if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
							neighbour.cost = nextStepCost;
							maxDepth = Math.max(maxDepth, neighbour.setParent(current));
							neighbour.heuristic = heuristic.getCost(mover, xp, yp, tx, ty);
							open.add(neighbour);
						}
					}
				}
			}
			open = mostLikelyOnTop(open);
		}
	}
	
	private ArrayList<Node> mostLikelyOnTop(ArrayList<Node> in) {
		//TODO sort the in list based on cost and heuristic 
		return in;
	}

	private boolean inClosedList(Node n) {
		for(Node x : closed){
			if(n.x == x.x && n.y == x.y) return true;
		}
		return false;
	}

	private boolean inOpenList(Node n) {
		for(Node x : open){
			if(n.x == x.x && n.y == x.y) return true;
		}
		return false;
	}

	public Node getFirstInOpen(){
		return open.get(0);
	}
	
	void removeFromOpen(Node n){
		open.remove(n);
	}
	
	void removeFromClosed(Node n){
		closed.remove(n);
	}
	
	void addToClosed(Node n){
		closed.add(n);
	}
}
