package nl.dannyarends.gameserver.movement;
/*
#
# \file Path.java
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

import java.util.ArrayList;

public class Path {
	public class Step{
		int x;
		int y;
		
		public Step(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	
	ArrayList<Step> steps = new ArrayList<Step>();
	
	public int getLength(){
		return steps.size();	
	}
	
	public Step getStep(int index){
		return steps.get(index);
	}
	
	public int getX(int index){
		return steps.get(index).x;
	}
	
	public int getY(int index){
		return steps.get(index).y;
	}
	public void appendStep(int x, int y){
		steps.add(new Step(x,y));
	}
	
	public void prependStep(int x, int y){
		ArrayList<Step> t = new ArrayList<Step>();
		t.add(new Step(x,y));
		t.addAll(steps);
		steps = t;
	}
	
	public boolean contains(int x, int y){
		for(Step s : steps){
			if(s.x==x && s.y==y)return true;
		}
		return false;
	}
}
