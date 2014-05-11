package com.color;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

public class Main extends GameWindow
{
//	private GameAction toggleAttr;
	private GameAction quit;
	private InputManager inMan;
	
	public static void main(String[] args)
	{
    if(args.length != 1)
    {
      Globals.cout("Usage: ./Main <random number seed>\n");
    }
    else
    {
      Globals.randGen=new Random(Long.parseLong(args[0]));
      new Main().run();
    }
	} 
	public void init()
	{
		int r=0,g=0,b=0;
    int median=Globals.randGen.nextInt();
    int blackCnt=0;
		super.init();
		Window window=device.getFullScreenWindow();
		window.setFocusTraversalKeysEnabled(false);
		
		Globals.map=new Square[Globals.WIDTHMAP+1][Globals.HEIGHTMAP];

    if(median<0)
      median*=-1;

      int maxVal=1000;
      median=Globals.randGen.nextInt(maxVal);

      Globals.cout("median: "+median);
		for(int x=0;x<=Globals.WIDTHMAP;x++)
		{
			for(int y=0;y<Globals.HEIGHTMAP;y++)
		  {

      if(Globals.randGen.nextInt(maxVal)==median)
      {
      blackCnt++;
        r=Globals.randGen.nextInt(256);
        g=Globals.randGen.nextInt(256);
        b=Globals.randGen.nextInt(256);
      }
      /*else if(Globals.randGen.nextInt(maxVal)>=90)
      {
        r=0xFF;
        g=0xFF;
        b=0xff;
      }*/
      else
      {
        r=0;
        g=0;
        b=0;
      }
/*        if(Globals.randGen.nextInt()>median)
        {
          r=Globals.randGen.nextInt(256);
          g=Globals.randGen.nextInt(256);
          b=Globals.randGen.nextInt(256);
        }
        else
        {
        blackCnt++;
          r=0;
          g=0;
          b=0;
        }*/

				Globals.map[x][y]=new Square(x,y,r,g,b);
			}
		}


    Globals.cout("black count: "+blackCnt);

		
		//Globals.map[0][0]=new Square(30,30,0xFF,0xFF,0xFF);
//		Globals.map[30][30]=new Square(30,30,0xFF,0xFF,0xFF);
//		Globals.map[29][30]=new Square(30,30,0xFF,0xFF,0xFF);
//		Globals.map[31][30]=new Square(30,30,0xFF,0xFF,0xFF);
		
		//Globals.map[Globals.WIDTHMAP][Globals.HEIGHTMAP-1]=new Square(30,30,0xFF,0xFF,0xFF);
		//resize(Globals.WIDTH,Globals.HEIGHT);
		//Globals.player=new Player(Globals.WIDTH/6, Globals.HEIGHT/6);
//		Globals.mags=new ArrayList<Magnet>();/* {new Magnet(60, Globals.WIDTH/2, Globals.HEIGHT/2),
//								  new Magnet(60, Globals.WIDTH/6*4, Globals.HEIGHT/6),
//								  new Magnet(60, Globals.WIDTH/6*4, Globals.HEIGHT/2)};*/
		
//		Globals.collect= new ArrayList<Collectable>();
		
		//keyboard input
		inMan=new InputManager(window);
	//	toggleAttr = new GameAction("attraction", GameAction.DETECT_INITIAL_ONLY);
		quit=new GameAction("quit", GameAction.DETECT_INITIAL_ONLY);
	//	inMan.mapActToKey(toggleAttr, KeyEvent.VK_SPACE);
		inMan.mapActToKey(quit, KeyEvent.VK_ESCAPE);
		
	//	setBackground(Color.WHITE);
	//	img=createImage(Globals.WIDTH, Globals.HEIGHT);
	//	gg=img.getGraphics();
		
	//	initLevel();
	}
	@Override
	public void run() {
		try
		{
			init();
			gameLoop();
		}
		finally
		{
			restoreScreen();
		}
	}

	@Override
	public void gameLoop()
	{
		Graphics2D g;
		long currTime=System.currentTimeMillis();
		long elapsedTime;
		while(Globals.g_play>0)
		{
			elapsedTime=System.currentTimeMillis()-currTime;
			currTime+=elapsedTime;
			
			updateGraphicsPos(elapsedTime);
			
			g=getGraphics();
			draw(g);
			g.dispose();
			update();
			
			try
			{
				Thread.sleep(20);//TODO make this 60 fps
			}
			catch(InterruptedException e){}
		}
	}
	
	public void updateGraphicsPos(long diff)
	{
		if(Globals.g_drawCount++>Globals.DELAY)
		{
			Globals.g_drawCount=0;
			for(int x=0;x<=Globals.WIDTHMAP;x++)
			{
				for(int y=0;y<Globals.HEIGHTMAP;y++)
				{
					if(x==0)
					{
						if(y==0)
						{
							Globals.map[x][y].avgColors(Globals.map[x+1][y].getColor(),
									Globals.map[x][y+1].getColor());
						}
						else if(y==Globals.HEIGHTMAP-1)
						{
							Globals.map[x][y].avgColors(Globals.map[x+1][y].getColor(),
									Globals.map[x][y-1].getColor());
						}
						else
						{
							Globals.map[x][y].avgColors(Globals.map[x+1][y].getColor(),
									Globals.map[x][y+1].getColor(),
									Globals.map[x][y-1].getColor());
						}
					}
					else if(y==0)
					{
						if(x==Globals.WIDTHMAP)
						{
							Globals.map[x][y].avgColors(Globals.map[x-1][y].getColor(),
									Globals.map[x][y+1].getColor());
						}
						else
						{
							Globals.map[x][y].avgColors(Globals.map[x+1][y].getColor(),
									Globals.map[x][y+1].getColor(),
									Globals.map[x-1][y].getColor());
						}
					}
					else if(x==Globals.WIDTHMAP)
					{
						if(y==Globals.HEIGHTMAP-1)
						{
							Globals.map[x][y].avgColors(Globals.map[x-1][y].getColor(),
									Globals.map[x][y-1].getColor());
						}
						else
						{
							Globals.map[x][y].avgColors(Globals.map[x-1][y].getColor(),
									Globals.map[x][y+1].getColor(),
									Globals.map[x][y-1].getColor());
						}
					}
					else if(y==Globals.HEIGHTMAP-1)
					{
						Globals.map[x][y].avgColors(Globals.map[x-1][y].getColor(),
								Globals.map[x+1][y].getColor(),
								Globals.map[x][y-1].getColor());
					}
					else
					{
						Globals.map[x][y].avgColors(Globals.map[x+1][y].getColor(),
								Globals.map[x-1][y].getColor(),
								Globals.map[x][y-1].getColor(),
								Globals.map[x][y+1].getColor());
					}
				}
			}
		}
		if(quit.isPressed())
		{
			Globals.g_play=0;
		}
		
	/*	if(toggleAttr.isPressed())
		{
			Globals.player.toggleAttact();
		}
		
		if(Globals.player.getAttract())//magnets will attract
		{
			Globals.player.calcMovement(diff);
		}
		else//momentum is conserved
		{
			Globals.player.updatePos(diff);
		}*/
	}

	@Override
	public void draw(Graphics2D g) {
//		gg=img.getGraphics();
		Window window=device.getFullScreenWindow();
		g.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		for(int x=0;x<=Globals.WIDTHMAP;x++)
		{
			for(int y=0;y<Globals.HEIGHTMAP;y++)
			{
				g.setColor(new Color(Globals.map[x][y].getR(),
						Globals.map[x][y].getG(),Globals.map[x][y].getB()));
				g.fillRect(x*Globals.SCALEFACTOR, y*Globals.SCALEFACTOR, Globals.SCALEFACTOR, Globals.SCALEFACTOR);
			}
		}
//		g.drawImage(img,0,0,this);
	}
}
