package nl.dannyarends.generic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class UniversalLoader {
	public byte[] loadResource(String location){
		BufferedInputStream instream;
		int filesize;
		byte[] buffer = null;
		System.out.print("(1) from URL");
		try{
			URL url = new URL(location);
			URLConnection conn = url.openConnection();
			filesize = conn.getContentLength();
			instream = new BufferedInputStream(conn.getInputStream());
			buffer = new byte[filesize];
			System.out.println("File: " + location + " " + conn.getContentLength() + " M: " + filesize);
			for(int i=0;i < filesize; i++){
				buffer[i] = (byte) instream.read();
				if(i%1024==0)System.out.print(".");
			}
			System.out.print("\n");
			instream.close();
			return buffer;
		}catch(Exception e){
			System.out.println("failed");
		}
		
		System.out.print("(2) from FILE");
		try{
			ArrayList<String> totry = new ArrayList<String>();
			totry.add(location);
			totry.add(System.getProperty("user.home") + location);
			for(String s : totry){
				File f = new File(s);
				if(f.exists() && f.isFile()){
					return(bytearrayfromfile(f));
				}else{
					System.out.println("Failed: no such file: " + f.getAbsolutePath());
				}
			}
		}catch(Exception e){
			System.out.println("File failed");
		}
		
		System.out.print("(3) from BUILDpath");
		try{
			ArrayList<URL> totry = new ArrayList<URL>();
			totry.add(this.getClass().getResource(location));
			totry.add(this.getClass().getClassLoader().getResource(location));
			totry.add(this.getClass().getSuperclass().getResource(location));
			for(URL u : totry){
				File f = new File(u.getPath());
				if(f.exists() && f.isFile()){
					return(bytearrayfromfile(f));
				}else{
					System.out.println("Failed: no such file: " + f.getAbsolutePath());
				}
			}
		}catch(Exception e){
			System.out.println("getClass().getResource failed");
		}
		return buffer;
	}

		
	
	byte[] bytearrayfromfile(File f) throws IOException{
		int filesize = (int) f.length();
		byte[] buffer = new byte[filesize];
		BufferedInputStream instream = new BufferedInputStream(new FileInputStream(f));
		for(int i=0;i < filesize; i++){
			buffer[i] = (byte) instream.read();
			if(i%1024==0)System.out.print(".");
		}
		return buffer;
	}
}
