package com.jiagu.mysql.main;/*
 * Author: park.yq@alibaba-inc.com
 * Date: 2019/2/19 上午11:03
 */

import java.nio.ByteBuffer;

public class TestByte {
	public static void main(String[] args) {
		int num=1505;
		byte[] result=new byte[4];


		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (num >> ((4 - i - 1) * 8) & 0xff);
		}

		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}


		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.putInt(1505);

		byteBuffer.flip();


		System.out.println("-------------------------------------------------");
//		for (int i = 0; i < byteBuffer.remaining(); i++) {
//			System.out.println(byteBuffer.get());
//		}
//
		int remain = byteBuffer.remaining();

		for (int i = 0; i < remain; i++) {
			System.out.println(byteBuffer.get());
		}

		//这个应该差不多的
	}
}
