package com.color;

public class Square
{
	private int x;
	private int y;
	private int r;
	private int g;
	private int b;
	
	Square(int x, int y, int r, int g, int b)
	{
		this.x=x;
		this.y=y;
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
	int getX()
	{
		return x*10;
	}
	
	int getY()
	{
		return y*10;
	}
	
	int getR()
	{
		return r;
	}
	
	int getG()
	{
		return g;
	}
	
	int getB()
	{
		return b;
	}
	
	void setX(int x)
	{
		this.x=x;
	}
	
	void setY(int y)
	{
		this.y=y;
	}
	
	void setR(int color)
	{
		r=color;
	}
	
	void setG(int color)
	{
		g=color;
	}
	
	void setB(int color)
	{
		b=color;
	}
	
	int getColor()
	{
		int tmp=r;
		tmp<<=8;
		tmp+=g;
		tmp<<=8;
		tmp+=b;
		return tmp;
	}
	
	void avgColors(int c1, int c2)
	{
		int tmp=c1+c2+getColor();
		tmp/=3;
		setRGB(tmp);
	}
	
	void avgColors(int c1, int c2, int c3)
	{
		int tmp=c1+c2+c3+getColor();
		tmp/=4;
		setRGB(tmp);
	}
	
	void avgColors(int c1, int c2, int c3, int c4)
	{
		int tmp=c1+c2+c3+c4+getColor();
		tmp/=5;
		setRGB(tmp);
	}
	
	void setRGB(int color)
	{
		b=color&0xFF;
		color>>=8;
		g=color&0xFF;
		color>>=8;
		r=color;
	}
}
