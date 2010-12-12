/*
#
# Loader3DS.java
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

package game;

import generic.BinaryUtils;
import generic.Utils;

import java.awt.Color;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

import objects.Edge;
import objects.Material3DS;
import objects.Point2D;
import objects.Point3D;
import objects.TriangleMesh;
import objects.renderables.Object3DS;

public class Loader3DS {
	InputStream stream;
	int filelength;
	URL url;

	public Loader3DS() {

	}

	public Vector<Object3DS> load3DS(String filename) {
		Utils.console("Loading file: "
				+ Engine.getParentApplet().getCodeBase().toString() + filename);
		Vector<Object3DS> objects = new Vector<Object3DS>();
		Vector<Material3DS> materials = new Vector<Material3DS>();
		Object3DS object = null;
		Material3DS material = null;
		try {
			url = new URL(Engine.getParentApplet().getCodeBase().toString()
					+ filename);
			stream = url.openStream();
			System.out.println("File: " + filename + " "
					+ (filelength = stream.available()));
			byte[] shortconversion = new byte[2];
			byte[] doubleconversion = new byte[8];
			String tempstring;
			double locx, locy, locz;
			int edgex, edgey, edgez, faceflag;
			int red,green,blue;
			double mapx, mapz;
			int length;
			byte[] chunkId = new byte[2];
			byte[] chunkLength = new byte[4];
			while (stream.available() > 0) {
				stream.read(chunkId, 0, 2);
				stream.read(chunkLength, 0, 4);
				// Utils.console("chunkid: " + unsignedShortToInt(chunkId) +
				// " length: " + unsignedIntToLong(chunkLength));
				switch (BinaryUtils.arr2int(chunkId)) {
				case 19789:
					// Utils.console("#0x4d4d");
					break;
				case 15677:
					// Utils.console("#0x3d3d");
					break;
				case 16384:
					// Utils.console("#0x4000");
					if (object != null)
						objects.add(object);
					tempstring = BinaryUtils.read3dsstring(stream);
					Utils.console("Found new object: " + tempstring);
					object = new Object3DS(tempstring);
					break;
				case 16640:
					// Utils.console("#0x4100");
					break;
				case 16656:
					// Utils.console("#0x4110");
					stream.read(shortconversion, 0, 2);
					Utils.console("# of vertices: "
							+ (length = BinaryUtils.arr2int(shortconversion)));
					Point3D[] vertices = new Point3D[length];
					for (int i = 0; i < length; i++) {
						stream.read(doubleconversion, 0, 4);
						locx = BinaryUtils.arr2float(doubleconversion);
						stream.read(doubleconversion, 0, 4);
						locy = BinaryUtils.arr2float(doubleconversion);
						stream.read(doubleconversion, 0, 4);
						locz = BinaryUtils.arr2float(doubleconversion);
						// Utils.console("Vertex: " + locx + "," + locy + "," +
						// locz);
						vertices[i] = new Point3D(locx, locy, locz);
					}
					object.setVertices(vertices);
					break;
				case 16672:
					// Utils.console("#0x4120");
					stream.read(shortconversion, 0, 2);
					Utils.console("# of Edges: "
							+ (length = BinaryUtils.arr2int(shortconversion)));
					Edge[] edges = new Edge[3 * length];
					for (int i = 0; i < 3 * length; i += 3) {
						stream.read(shortconversion, 0, 2);
						edgex = BinaryUtils.arr2int(shortconversion);
						stream.read(shortconversion, 0, 2);
						edgey = BinaryUtils.arr2int(shortconversion);
						stream.read(shortconversion, 0, 2);
						edgez = BinaryUtils.arr2int(shortconversion);
						stream.read(shortconversion, 0, 2);
						faceflag = BinaryUtils.arr2int(shortconversion);
						edges[i] = new Edge(edgex, edgez, faceflag);
						edges[i + 1] = new Edge(edgex, edgey, faceflag);
						edges[i + 2] = new Edge(edgey, edgez, faceflag);
						//Utils.console("Edge: " + edgex + "," + edgey + "," + edgez + ":" + faceflag);
					}
					object.setEdges(edges);
					break;
				case 16688:
					// Utils.console("#0x4130 - Triangle Materials");
					tempstring = BinaryUtils.read3dsstring(stream);
					stream.read(shortconversion, 0, 2);
					Utils.console((length = BinaryUtils.arr2int(shortconversion)) + " Triangles");
					int[] triangleMeshData = new int[length];
					for (int i = 0; i < length; i++) {
						stream.read(shortconversion, 0, 2);
						triangleMeshData[i] = BinaryUtils.arr2int(shortconversion);
						// Utils.console("triangle: " + triangle + " material: "
						// + tempstring);
					}
					object.addTriangleMesh(new TriangleMesh(tempstring,triangleMeshData));
					break;
				case 45055:
					// Utils.console("#0xAFFF");
					break;
				case 40960:
					// Utils.console("#0xA000 - Materials List");
					if (material != null)
						materials.add(material);
					tempstring = BinaryUtils.read3dsstring(stream);
					Utils.console("Found new material: " + tempstring);
					material = new Material3DS(tempstring);
					break;
				case 40976:
					// Utils.console("#0xA010");
					material.is_diffuse = false;
					material.is_specular = false;
					material.is_ambient = true;
					break;
				case 40992:
					// Utils.console("#0xA020");
					material.is_diffuse = true;
					material.is_specular = false;
					material.is_ambient = false;
					break;
				case 41008:
					// Utils.console("#0xA030");
					material.is_diffuse = false;
					material.is_specular = true;
					material.is_ambient = false;
					break;
				case 41472:
					// Utils.console("#0xA200");
					break;
				case 41728:
					// Utils.console("#0xA300");
					break;
				case 17:
					// Utils.console("#0x0011");
					Utils.console("R: " + (red=stream.read()) + "/255, G: "
							+ (green=stream.read()) + "/255, B: " + (blue=stream.read())
							+ "/255");
					if(material.is_diffuse){
						material.setDiffuseColor(new Color(red,green,blue));
					}else if(material.is_specular){
						material.setSpecularColor(new Color(red,green,blue));
					}else if(material.is_ambient){
						material.setAmbientColor(new Color(red,green,blue));
					}
					break;
				case 16704:
					// Utils.console("#0x4140");
					stream.read(shortconversion, 0, 2);
					Utils.console("# of mapcoords: "
							+ (length = BinaryUtils.arr2int(shortconversion)));
					Point2D[] mapcoords = new Point2D[length];
					for (int i = 0; i < length; i++) {
						stream.read(doubleconversion, 0, 4);
						mapx = BinaryUtils.arr2float(doubleconversion);
						stream.read(doubleconversion, 0, 4);
						mapz = BinaryUtils.arr2float(doubleconversion);
						mapcoords[i] = new Point2D(mapx, mapz);
					}
					object.setMapcoords(mapcoords);
					break;
				default:
					stream.skip((BinaryUtils.arr2long(chunkLength) - 6));
					break;
				}

			}
		} catch (Exception e) {
			Utils.log("Cannot open URL", e);
		}
		if (object != null)
			objects.add(object);
		return objects;
	}
}
