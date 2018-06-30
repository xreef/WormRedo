package it.reef.concrete;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import it.reef.interf.Drawable;
import it.reef.interf.Updatable;

public class Fruit implements Drawable,Updatable {

	private int x,y;
	
	private int wWidth=500;
	private int wHeight=400;
	
	private int dotSize=10;
	
	private final int FRUIT_PER_LEVEL = 9;
	
	private WallManager wallManager;
	
	private boolean picked=false;
	
	int counter=0;
	
	public Fruit(int wWidth, int wHeight, int dotSize, WallManager wm){
		this.wWidth=wWidth;
		this.wHeight=wHeight;
		this.dotSize=dotSize;
		this.wallManager=wm;
		this.reposition();
	}
	
	public void draw(Graphics g) {
		Random r = new Random();
		Font font = new Font("SansSerif", Font.BOLD, 10);
		g.setFont(font);
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g.drawString(String.valueOf(counter),x,y);
	}

	public void update() {
		if (picked) {
			reposition();
		}
	}

	private void reposition(){
		int posizioniW = wWidth/dotSize-1;
		int posizioniH = wHeight/dotSize-1;
		
		Random r = new Random();
		x=(r.nextInt(posizioniW)+1)*dotSize;
		y=(r.nextInt(posizioniH)+1)*dotSize;
		Point p = new Point(x,y-dotSize);
		
		while (wallManager.detectWallCollision(p, dotSize)){
			x=(r.nextInt(posizioniW)+1)*dotSize;
			y=(r.nextInt(posizioniH)+1)*dotSize;
			p = new Point(x,y-dotSize);
		}
		
		counter++;
		picked=false;

	}

	public boolean isPicked(Point p, int size){
		Rectangle rToControl = new Rectangle(p.x, p.y, size, size);
	    Rectangle rFruit = new Rectangle(this.x, this.y-dotSize, dotSize,dotSize);
	    picked = rFruit.intersects(rToControl);
	    return picked;
	}
	
	public int getFruitCounter(){
		return counter;
	}
	
	
	// public boolean lc=false;
	public boolean levelComplete(){
		// return lc;
		if (counter>FRUIT_PER_LEVEL) return true;
		return false;
	}
}
