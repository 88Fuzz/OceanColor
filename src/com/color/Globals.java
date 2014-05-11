package com.color;

import java.util.ArrayList;
import java.util.Random;

public class Globals
{
	public static int WIDTH=800;
	public static int HEIGHT=600;
	public static int SCALEFACTOR=10;
	public static int WIDTHMAP=WIDTH/SCALEFACTOR;
	public static int HEIGHTMAP=HEIGHT/SCALEFACTOR;
	public static int DELAY=-1;
	public static byte g_play=1;
	public static int g_drawCount=0;
	public static Square[][] map;
  public static Random randGen;
	//public static Player player;
	//public static ArrayList<Magnet> mags;
	//public static ArrayList<Collectable> collect;
	//public static BaseClass[][] map= new BaseClass[LEVELWIDTH][LEVELHEIGHT];

	public static void cout(String str)
	{
		System.out.println(str);
	}
	
	public static void cerr(String str)
	{
		System.err.println(str);
	}
}
