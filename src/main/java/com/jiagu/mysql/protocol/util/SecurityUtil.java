package com.jiagu.mysql.protocol.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * <pre><b>a security util.</b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 1.0
 */
public class SecurityUtil {

	public static final byte[] scramble411(byte[] pass, byte[] seed)
			throws NoSuchAlgorithmException {//411是mysql4.1版本之后采用的加密方式
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] pass1 = md.digest(pass);
		md.reset();
		byte[] pass2 = md.digest(pass1);
		md.reset();
		md.update(seed);
		byte[] pass3 = md.digest(pass2);
		for (int i = 0; i < pass3.length; i++) {
			pass3[i] = (byte) (pass3[i] ^ pass1[i]);
		}
		return pass3;
	}

	public static final String scramble323(String pass, String seed) {//323是MySQL4.1x版本之前采用的加密方式
		if ((pass == null) || (pass.length() == 0)) {
			return pass;
		}
		byte b;
		double d;
		long[] pw = hash(seed);
		long[] msg = hash(pass);
		long max = 0x3fffffffL;
		long seed1 = (pw[0] ^ msg[0]) % max;
		long seed2 = (pw[1] ^ msg[1]) % max;
		char[] chars = new char[seed.length()];
		for (int i = 0; i < seed.length(); i++) {
			seed1 = ((seed1 * 3) + seed2) % max;
			seed2 = (seed1 + seed2 + 33) % max;
			d = (double) seed1 / (double) max;
			b = (byte) java.lang.Math.floor((d * 31) + 64);
			chars[i] = (char) b;
		}
		seed1 = ((seed1 * 3) + seed2) % max;
		seed2 = (seed1 + seed2 + 33) % max;
		d = (double) seed1 / (double) max;
		b = (byte) java.lang.Math.floor(d * 31);
		for (int i = 0; i < seed.length(); i++) {
			chars[i] ^= (char) b;
		}
		return new String(chars);
	}

	private static long[] hash(String src) {
		long nr = 1345345333L;
		long add = 7;
		long nr2 = 0x12345671L;
		long tmp;
		for (int i = 0; i < src.length(); ++i) {
			switch (src.charAt(i)) {
			case ' ':
			case '\t':
				continue;
			default:
				tmp = (0xff & src.charAt(i));
				nr ^= ((((nr & 63) + add) * tmp) + (nr << 8));
				nr2 += ((nr2 << 8) ^ nr);
				add += tmp;
			}
		}
		long[] result = new long[2];
		result[0] = nr & 0x7fffffffL;
		result[1] = nr2 & 0x7fffffffL;
		return result;
	}

}