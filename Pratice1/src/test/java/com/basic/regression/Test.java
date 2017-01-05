package com.basic.regression;

import java.io.File;

public class Test {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir").replace("\\", "\\");
		String s = path+"\\Maven Dependencies";
		File f=new File(s);
		System.out.println(f.getAbsolutePath());
		String[] allFiles = f.list();
		System.out.println(allFiles.length);
		System.out.println(allFiles[0]);
		for(String file:allFiles)
		{
			System.out.println(file);
			if(file.contains("selenium-java"))
			{
				System.out.println("mvkdvm");
				String ver = file.split("-")[2];
				int len = ver.length();
				System.out.println(ver);
				String version = ver.substring(len, len-3);
				System.out.println(version);
			}
		}
	}
}
