package generic;

import java.io.IOException;
import java.io.InputStream;

public class BinairyUtils {
	static int maxchars = 20;

	public static double arr2double (byte[] b) {
		int i = 0;
		int len = 8;
		int cnt = 0;
		byte[] tmp = new byte[len];
		for (i = 0; i < len; i++) {
			tmp[cnt] = b[i];
			cnt++;
		}
		long accum = 0;
		i = 0;
		for ( int shiftBy = 0; shiftBy < 64; shiftBy += 8 ) {
			accum |= ( (long)( tmp[i] & 0xff ) ) << shiftBy;
			i++;
		}
		return Double.longBitsToDouble(accum);
	}
	
	public static float arr2float (byte[] b) {
		int i = 0;
		int len = 4;
		int cnt = 0;
		byte[] tmp = new byte[len];
		for (i = 0; i < len; i++) {
			tmp[cnt] = b[i];
			cnt++;
		}
		int accum = 0;
		i = 0;
		for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
			accum |= ( (long)( tmp[i] & 0xff ) ) << shiftBy;
			i++;
		}
		return Float.intBitsToFloat(accum);
	}

	
	public static long arr2long (byte[] b) {
		int i = 0;
		int len = 4;
		int cnt = 0;
		byte[] tmp = new byte[len];
		for (i = 0; i < len; i++) {
			tmp[cnt] = b[i];
			cnt++;
		}
		long accum = 0;
		i = 0;
		for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
			accum |= ( (long)( tmp[i] & 0xff ) ) << shiftBy;
			i++;
		}
		return accum;
	}
	
	public static int arr2int (byte[] b) {
		int low = b[0] & 0xff;
		int high = b[1] & 0xff;
		return (int)( high << 8 | low );
	}
	
	public static String read3dsstring(InputStream stream){
		String s = "";
		int i=0;
		int l_char = '\0';
		do{
			if(l_char != '\0')s += ((char)l_char);
			try {
				l_char = stream.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}while(l_char != '\0' && i < maxchars);
		return s;
	}
}
