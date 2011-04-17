package nl.dannyarends.applet.objects;

import nl.dannyarends.generic.MathUtils;
import nl.dannyarends.generic.Utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.URLConnection;

import nl.dannyarends.rendering.Engine;

/// Texture
//<p>
//Class to used to load and store a texture
//</p>
//
public class Texture {
	public double[][][] texturedata =  null;
	String name;
	public int width;
	public int height;
	public double[] ambientcolor = new double[]{0,0,0};
	public double[] diffuseColor = new double[]{0,0,0};
	boolean loaded = false;
	public Image image;
	TexturePaint paint;
	
	
	public Texture(){

	}
	
	public Texture(String n){
		name=n;
	}


	public Texture(Texture h) {
		texturedata = h.texturedata;
		loaded=h.loaded;
		name=h.name;
		width=h.width;
		height=h.height;
		ambientcolor = h.ambientcolor;
		diffuseColor = h.diffuseColor;
		image = h.image;
		paint = h.paint;
	}

	public String getName() {
		return name;
	}

	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return loaded;
	}
	
	 public TexturePaint loadTextureResource() {
		  try {
			int width = image.getWidth(null);
		    int height = image.getHeight(null);
		    BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    Graphics g = buffImg.getGraphics();
		    g.drawImage(image,0,0,width,height,null);
		    g.dispose();
		    Utils.console("Loaded the paint of the texture: " + width + "," + height);
		    return new TexturePaint(buffImg,new Rectangle2D.Double(0,0,width,height));
		  }
		  catch (Exception e) {
		   Utils.log("Something wrong with paint", e);
		  }
		  return null;
		}

	/**
	 * Try to load a texture into the empty texture object created by the Loader
	 * 
	 */	
	public void TryLoadingFromName() {
		int filelength;
		byte[] b;
		try {
			URL url = new URL(Engine.getRenderWindow().getCodeBase().toString()	+ "data/textures/" + getName());
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
			ByteArrayInputStream fs = new ByteArrayInputStream(b);
			 int bflen=14;  // 14 byte BITMAPFILEHEADER
		        byte bf[]=new byte[bflen];
		        fs.read(bf,0,bflen);
		        int bilen=40; // 40-byte BITMAPINFOHEADER
		        byte bi[]=new byte[bilen];
		        fs.read(bi,0,bilen);
		        // Interperet data.
		        int nsize = (((int)bf[5]&0xff)<<24) 		        | (((int)bf[4]&0xff)<<16)		        | (((int)bf[3]&0xff)<<8)		        | (int)bf[2]&0xff;
		        System.out.println("File type is :"+(char)bf[0]+(char)bf[1]);
		        System.out.println("Size of file is :"+nsize);
		        int nbisize = (((int)bi[3]&0xff)<<24)		        | (((int)bi[2]&0xff)<<16)		        | (((int)bi[1]&0xff)<<8)		        | (int)bi[0]&0xff;
		        System.out.println("Size of bitmapinfoheader is :"+nbisize);
		        width = (((int)bi[7]&0xff)<<24)		        | (((int)bi[6]&0xff)<<16)		        | (((int)bi[5]&0xff)<<8)		        | (int)bi[4]&0xff;
		        System.out.println("Width is :"+width);
		        height = (((int)bi[11]&0xff)<<24)		        | (((int)bi[10]&0xff)<<16)		        | (((int)bi[9]&0xff)<<8)		        | (int)bi[8]&0xff;
		        System.out.println("Height is :"+height);
		        int nplanes = (((int)bi[13]&0xff)<<8) | (int)bi[12]&0xff;
		        System.out.println("Planes is :"+nplanes);
		        int nbitcount = (((int)bi[15]&0xff)<<8) | (int)bi[14]&0xff;
		        System.out.println("BitCount is :"+nbitcount);
		        // Look for non-zero values to indicate compression
		        int ncompression = (((int)bi[19])<<24)		        | (((int)bi[18])<<16)		        | (((int)bi[17])<<8)		        | (int)bi[16];
		        System.out.println("Compression is :"+ncompression);
		        int nsizeimage = (((int)bi[23]&0xff)<<24)		        | (((int)bi[22]&0xff)<<16)		        | (((int)bi[21]&0xff)<<8)		        | (int)bi[20]&0xff;
		        System.out.println("SizeImage is :"+nsizeimage);
		        int nxpm = (((int)bi[27]&0xff)<<24)		        | (((int)bi[26]&0xff)<<16)		        | (((int)bi[25]&0xff)<<8)		        | (int)bi[24]&0xff;
		        System.out.println("X-Pixels per meter is :"+nxpm);
		        int nypm = (((int)bi[31]&0xff)<<24)		        | (((int)bi[30]&0xff)<<16)		        | (((int)bi[29]&0xff)<<8)		        | (int)bi[28]&0xff;
		        System.out.println("Y-Pixels per meter is :"+nypm);
		        int nclrused = (((int)bi[35]&0xff)<<24)		        | (((int)bi[34]&0xff)<<16)		        | (((int)bi[33]&0xff)<<8)		        | (int)bi[32]&0xff;
		        System.out.println("Colors used are :"+nclrused);
		        int nclrimp = (((int)bi[39]&0xff)<<24)		        | (((int)bi[38]&0xff)<<16)		        | (((int)bi[37]&0xff)<<8)		        | (int)bi[36]&0xff;
		        System.out.println("Colors important are :"+nclrimp);
		        if (nbitcount==24){
		        	int npad = (nsizeimage / height) - width * 3;
		        	byte brgb[] = new byte [( width + npad) * 3 * height];
		        	int ndata[] = new int [height * width];
		        	fs.read (brgb, 0, (width + npad) * 3 * height);
		        	int nindex = 0;
		        	texturedata = new double[height][width][3];
		        	for (int j = 0; j < height; j++){
		        		for (int i = 0; i < width; i++){
		        			texturedata[i][j][0] = ((int)(brgb[nindex+2]) & 0xff)/255.0;
		        			texturedata[i][j][1] = ((int)(brgb[nindex+1]) & 0xff)/255.0;
		        			texturedata[i][j][2] = ((int)(brgb[nindex]) & 0xff)/255.0;
		        			ndata [width * (height - j - 1) + i] = (255&0xff)<<24 | (((int)brgb[nindex+2]&0xff)<<16) | (((int)brgb[nindex+1]&0xff)<<8) | (int)brgb[nindex]&0xff;
		        			MathUtils.addVector(ambientcolor, texturedata[i][j]);
		        			nindex += 3;
		        		}
		        		MathUtils.addVector(diffuseColor, texturedata[0][j]);
		        		nindex += npad;
		            }
		        	image = Engine.getRenderWindow().createImage(new MemoryImageSource(width, height, ndata, 0, width));
		        	MathUtils.multiplyVectorByScalar(ambientcolor, 0.5 /(height*width));
		        	MathUtils.multiplyVectorByScalar(diffuseColor, 0.5 /(width));
		        }else if (nbitcount == 8){
		        	int nNumColors = 0;
		        	if (nclrused > 0){
		        		nNumColors = nclrused;
		            }else{
		            	nNumColors = (1&0xff)<<nbitcount;
		            }
		        	System.out.println("The number of Colors is"+nNumColors);
		        	if (nsizeimage == 0){
		        		nsizeimage = ((((width*nbitcount)+31) & 31 ) >> 3);
		        		nsizeimage *= height;
		        		System.out.println("nsizeimage (backup) is"+nsizeimage);
		            }
		        	int  npalette[] = new int [nNumColors];
		        	byte bpalette[] = new byte [nNumColors*4];
		        	fs.read (bpalette, 0, nNumColors*4);
		        	int nindex8 = 0;
		        	for (int n = 0; n < nNumColors; n++){
		        		npalette[n] = (255&0xff)<<24 | (((int)bpalette[nindex8+2]&0xff)<<16) | (((int)bpalette[nindex8+1]&0xff)<<8) | (int)bpalette[nindex8]&0xff;
		        		nindex8 += 4;
		            }
		        	int npad8 = (nsizeimage / height) - width;
		        	System.out.println("nPad is:"+npad8);
		        	byte bdata[] = new byte [(width+npad8)*height];
		        	int  ndata8[] = new int [width*height];
		        	fs.read (bdata, 0, (width+npad8)*height);
		        	nindex8 = 0;
		        	for (int j8 = 0; j8 < height; j8++){
		        		for (int i8 = 0; i8 < width; i8++){
		        			texturedata[i8][j8][0] = npalette [((int)bdata[nindex8+2]&0xff)] / 255.0;
		        			texturedata[i8][j8][1] = npalette [((int)bdata[nindex8+1]&0xff)] / 255.0;
		        			texturedata[i8][j8][2] = npalette [((int)bdata[nindex8]&0xff)] / 255.0;
		        			ndata8 [width*(height-j8-1)+i8] = npalette [((int)bdata[nindex8]&0xff)];
		        			MathUtils.addVector(ambientcolor, texturedata[i8][j8]);
		        			nindex8++;
		        		}
		        		MathUtils.addVector(diffuseColor, texturedata[0][j8]);
		        		nindex8 += npad8;
		        	}
		        	image = Engine.getRenderWindow().createImage(new MemoryImageSource(width, height,ndata8, 0, width));
		        	MathUtils.multiplyVectorByScalar(ambientcolor, 0.5 /(height*width));
		        	MathUtils.multiplyVectorByScalar(diffuseColor, 0.5 /(width));
		        }else{
		        	System.out.println ("Not a 24-bit or 8-bit Windows Bitmap, aborting...");
		        }
		        fs.close();
				paint = loadTextureResource();
		        loaded=true;
			}catch(Exception e){
				Utils.log("Error downloading file from server",e);
			}
		}

	public Paint getPaint() {
		return paint;
	}
}
