package nl.dannyarends.rendering.objects.renderables;


import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import nl.dannyarends.eventHandling.ServerConnection;
import nl.dannyarends.generic.BinaryUtils;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.Engine;
import nl.dannyarends.rendering.objects.Camera;
import nl.dannyarends.rendering.objects.Edge;
import nl.dannyarends.rendering.objects.Material;
import nl.dannyarends.rendering.objects.Point2D;
import nl.dannyarends.rendering.objects.Point3D;
import nl.dannyarends.rendering.objects.Vector3D;



/// 3DS model (single file) can contain multiple Object3DS
//<p>
//TODO
//</p>
//
public class Model3DS extends Object3D{
	private ArrayList<Object3DS> objects;
	private String name;
	ByteArrayInputStream stream;
	int filelength;
	
	public Model3DS(double x, double y, double z,String name) {
		super(x, y, z);
		setName(name);
	}

	public Model3DS(Model3DS h) {
		super((Object3D) h);
		setName(h.name);
		ArrayList<Object3DS> copy = new ArrayList<Object3DS>();
		for(Object3DS o : h.getObjects()){
			copy.add(new Object3DS(o));
		}
		setObjects(copy);
		setMaterials(h.getMaterials());
		setEdgeColors(h.getEdgeColors());
		setLoaded(h.isLoaded());
	}

	public void setObjects(ArrayList<Object3DS> o) {
		objects = o;
	}

	public ArrayList<Object3DS> getObjects() {
		return objects;
	}
	
	@Override
	public void setLocation(double x, double y, double z){
		super.setLocation(x,y,z);
		if(isLoaded()){
			Utils.console("SetLocation: "+x+","+y+","+z);
			for(Object3DS o : getObjects()){
				o.setLocation(x, y, z);
			}
		}
	}
	
	@Override
	public void render(Graphics g, Camera c){
		if(isLoaded()){
			for(Object3DS o : objects){
				o.update(c);
				o.render(g, c);
			}
		}else{
			if(onServer)Utils.log("Object not loaded",System.err);
		}
	}
	
	@Override
	public double intersect(Vector3D ray) {
		double mdistance = Double.POSITIVE_INFINITY;
		if(objects != null){
			for(Object3DS o : objects){
				double d = o.intersect(ray);
				if(d < mdistance) mdistance=d;
			}
		}
		return mdistance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Try to load an object of type 3DS into the empty object created by the Loader
	 * 
	 */	
	public void TryLoadingFromName() {
		if(!onServer) return;
		if(Engine.verbose) Utils.console("Loading file: " + Engine.getRenderWindow().getCodeBase().toString()	+ "data/models/" + getName());
		ArrayList<Object3DS> objects = new ArrayList<Object3DS>();
		Object3DS object = null;
		Material material = null;
		byte[] b = null;
		try {
			URL url = new URL(Engine.getRenderWindow().getCodeBase().toString()	+ "data/models/" + getName());
			URLConnection conn = url.openConnection();
			filelength = conn.getContentLength();
			BufferedInputStream s = new BufferedInputStream(conn.getInputStream());
			b = new byte[conn.getContentLength()];
			Utils.console("File: " + getName() + " " + conn.getContentLength() + " M: " + b.length);
			for(int i=0;i < filelength; i++){
				b[i] = (byte) s.read();
				if(i%1024==0)System.out.print(".");
			}
			System.out.print("\n");
			s.close();
		}catch(Exception e){
			Utils.log("Error downloading file "+getName()+" from server",System.err);
			onServer=false;
		}
		if(b==null) return;
		try{
			stream = new ByteArrayInputStream(b);
			ServerConnection.down+=filelength;
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
				// Utils.console("chunkid: " + unsignedShortToInt(chunkId) + " length: " + unsignedIntToLong(chunkLength));
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
					if(Engine.verbose) Utils.console("Found new object: " + tempstring);
					object = new Object3DS(tempstring);
					break;
				case 16640:
					// Utils.console("#0x4100");
					break;
				case 16656:
					// Utils.console("#0x4110");
					stream.read(shortconversion, 0, 2);
					length = BinaryUtils.arr2int(shortconversion);
					if(Engine.verbose) Utils.console("# of vertices: "	+ length);
					Point3D[] vertices = new Point3D[length];
					for (int i = 0; i < length; i++) {
						stream.read(doubleconversion, 0, 4);
						locx = BinaryUtils.arr2float(doubleconversion);
						stream.read(doubleconversion, 0, 4);
						locy = BinaryUtils.arr2float(doubleconversion);
						stream.read(doubleconversion, 0, 4);
						locz = BinaryUtils.arr2float(doubleconversion);
						//Utils.console("Vertex: " + locx + "," + locy + "," + locz);
						vertices[i] = new Point3D(locx/2.0, locy/2.0, locz/2.0);
					}
					object.setVertices(vertices);
					break;
				case 16672:
					// Utils.console("#0x4120");
					stream.read(shortconversion, 0, 2);
					length = BinaryUtils.arr2int(shortconversion);
					if(Engine.verbose) Utils.console("# of Edges: " + length);
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
					if(Engine.verbose) Utils.console("#0x4130 - Triangle Materials");
					if (material != null) getMaterials().add(material);
					tempstring = BinaryUtils.read3dsstring(stream);
					stream.read(shortconversion, 0, 2);
					length = BinaryUtils.arr2int(shortconversion);
					if(Engine.verbose) Utils.console(length + " Triangles");
					int[] triangleMeshData = new int[length];
					for (int i = 0; i < length; i++) {
						stream.read(shortconversion, 0, 2);
						triangleMeshData[i] = BinaryUtils.arr2int(shortconversion);
					}
					if(Engine.verbose) Utils.console("MSIZE: " + getMaterials().size());
					for(Material m : getMaterials()){
						if(Engine.verbose) Utils.console("!!!!!!->"+m.getName());	
						if(m.getName().equals(tempstring)){
							object.addTriangleColor(triangleMeshData,m);
						}
					}
					
					break;
				case 45055:
					// Utils.console("#0xAFFF");
					break;
				case 40960:
					if(Engine.verbose) Utils.console("#0xA000 - Materials List");
					if (material != null)
						getMaterials().add(material);
					tempstring = BinaryUtils.read3dsstring(stream);
					if(Engine.verbose) Utils.console("Found new material: " + tempstring);
					material = new Material(tempstring);
					break;
				case 40976:
					//Utils.console("#0xA010");
					material.is_diffuse = false;
					material.is_specular = false;
					material.is_ambient = true;
					break;
				case 40992:
					//Utils.console("#0xA020");
					material.is_diffuse = true;
					material.is_specular = false;
					material.is_ambient = false;
					break;
				case 41008:
					//Utils.console("#0xA030");
					material.is_diffuse = false;
					material.is_specular = true;
					material.is_ambient = false;
					break;
				case 41472:
					//Utils.console("#0xA200");
					break;
				case 41728:
					//Utils.console("#0xA300");
					tempstring = BinaryUtils.read3dsstring(stream);
					break;
				case 17:
					//Utils.console("#0x0011");
					red=stream.read();
					green=stream.read();
					blue=stream.read();
					if(Engine.verbose) Utils.console("R: " + red + "/255, G: "	+ green + "/255, B: " + blue + "/255");
					if(material.is_diffuse){
						material.setDiffuseColor(new Color(red,green,blue));
					}else if(material.is_specular){
						material.setSpecularColor(new Color(red,green,blue));
					}else if(material.is_ambient){
						material.setAmbientColor(new Color(red,green,blue));
					}
					break;
				case 16704:
					//Utils.console("#0x4140");
					stream.read(shortconversion, 0, 2);
					length = BinaryUtils.arr2int(shortconversion);
					if(Engine.verbose) Utils.console("# of mapcoords: " + length);
					Point2D[] mapcoords = new Point2D[length];
					for (int i = 0; i < length; i++) {
						stream.read(doubleconversion, 0, 4);
						mapx = BinaryUtils.arr2float(doubleconversion);
						stream.read(doubleconversion, 0, 4);
						mapz = BinaryUtils.arr2float(doubleconversion);
						mapcoords[i] = new Point2D((int)mapx,(int) mapz);
					}
					object.setMapcoords(mapcoords);
					break;
				default:
					//Utils.console("Skipping:" + BinaryUtils.arr2int(chunkId));
					stream.skip((BinaryUtils.arr2long(chunkLength) - 6));
					break;
				}

			}
			stream.close();
		} catch (Exception e) {
			Utils.log("Cannot open URL", e);
			setLoaded(false);
		}
		if (object != null)	objects.add(object);
		setObjects(objects);
		if(objects.isEmpty()){
			setLoaded(false);
		}else{
			setLoaded(true);
			for(Object3DS o : objects){
				if(o.edges == null)setLoaded(false);
				if(o.vertices == null)setLoaded(false);
			}
		}
		
		Utils.console("Loaded object:" + getName());
	}

	@Override
	public double[] getTextureCoords(double[] point) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
