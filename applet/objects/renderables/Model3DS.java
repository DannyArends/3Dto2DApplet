package objects.renderables;

import events.ServerConnection;
import generic.BinaryUtils;
import generic.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

import objects.Camera;
import objects.Edge;
import objects.Material3DS;
import objects.Point2D;
import objects.Point3D;
import rendering.Engine;


public class Model3DS extends Object3D{
	private Vector<Object3DS> objects;
	private Vector<Material3DS> materials;
	private boolean loaded = false;
	private String name;
	InputStream stream;
	int filelength;
	URL url;
	
	public Model3DS(double x, double y, double z,String name) {
		super(x, y, z);
		setName(name);
	}

	public Model3DS(Model3DS h) {
		super((Object3D) h);
		setName(h.name);
		Vector<Object3DS> copy = new Vector<Object3DS>();
		for(Object3DS o : h.getObjects()){
			copy.add(new Object3DS(o));
		}
		setObjects(copy);
		setMaterials(h.getMaterials());
		setEdgeColors(h.getEdgeColors());
		setLoaded(h.isLoaded());
	}

	public void setObjects(Vector<Object3DS> objects) {
		this.objects = objects;
	}

	public Vector<Object3DS> getObjects() {
		return objects;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public void setMaterials(Vector<Material3DS> materials) {
		this.materials = materials;
	}

	public Vector<Material3DS> getMaterials() {
		return materials;
	}
	
	@Override
	public void setLocation(double x, double y, double z){
		super.setLocation(x,y,z);
		if(isLoaded()){
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
			Utils.log("Object not loaded",System.err);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isLoaded() {
		return loaded;
	}
	
	public void TryLoadingFromName() {
		if(Engine.verbose) Utils.console("Loading file: " + Engine.getParentApplet().getCodeBase().toString()	+ "data/models/" + getName());
		Vector<Object3DS> objects = new Vector<Object3DS>();
		Vector<Material3DS> materials = new Vector<Material3DS>();
		Object3DS object = null;
		Material3DS material = null;
		try {
			url = new URL(Engine.getParentApplet().getCodeBase().toString()	+ "data/models/" + getName());
			stream = url.openStream();
			if(Engine.verbose) Utils.console("File: " + getName() + " " + (filelength = stream.available()));
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
						if(Engine.verbose) Utils.console("Vertex: " + locx + "," + locy + "," + locz);
						vertices[i] = new Point3D(locx, locy, locz);
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
					//Utils.console("#0x4130 - Triangle Materials");
					tempstring = BinaryUtils.read3dsstring(stream);
					stream.read(shortconversion, 0, 2);
					length = BinaryUtils.arr2int(shortconversion);
					if(Engine.verbose) Utils.console(length + " Triangles");
					int[] triangleMeshData = new int[length];
					for (int i = 0; i < length; i++) {
						stream.read(shortconversion, 0, 2);
						triangleMeshData[i] = BinaryUtils.arr2int(shortconversion);
					}
					for(Material3DS m : materials){
						if(m.getName().equals(tempstring)){
							object.addTriangleColor(triangleMeshData,m);
						}
					}
					
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
		} catch (Exception e) {
			Utils.log("Cannot open URL", e);
			setLoaded(loaded);
		}
		if (object != null)	objects.add(object);
		
		setObjects(objects);
		setMaterials(materials);
		setLoaded(true);
		Utils.console("Loaded object:" + getName());
	}
	
}
