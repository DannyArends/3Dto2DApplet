/*
#
# Edge.java
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
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

package objects;

/// Edge
//<p>
//Class representing an edge, contains a face flag
//</p>
//
public class Edge {
	public int a, b;
	public int faceflag;

	public Edge(int A, int B) {
		a = A;
		b = B;
		faceflag = 0;
	}
	
	public Edge(int A, int B,int flag) {
		a = A;
		b = B;
		faceflag = flag;
	}
}
