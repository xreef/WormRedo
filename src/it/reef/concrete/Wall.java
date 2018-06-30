package it.reef.concrete;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import it.reef.interf.Drawable;

public class Wall implements Drawable {

	private int x,y;
	private int sWidth,sHeight;
	
	public Wall(int x, int y, int sWidth, int sHeight){
		this.x=x;
		this.y=y;
		
		this.sWidth=sWidth;
		this.sHeight=sHeight;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.darkGray);
		g.fill3DRect(x, y, sWidth, sHeight, true);
	}
	
	public boolean hit(Point p, int size){
		Rectangle rToControl = new Rectangle(p.x, p.y, size, size);
	    Rectangle rWall = new Rectangle(this.x, this.y, sWidth, sHeight);
	    return rWall.intersects(rToControl);
	}
}
