package it.reef.people;

import it.reef.concrete.Fruit;
import it.reef.concrete.Wall;
import it.reef.concrete.WallManager;
import it.reef.input.WormKeyAdapter;
import it.reef.interf.Drawable;
import it.reef.interf.KeyProcessableWorm;
import it.reef.interf.Updatable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worm implements Drawable, Updatable, KeyProcessableWorm {

		WormKeyAdapter kvermicello;
		WallManager wallManager;
		Fruit f;
	  // size and number of dots in a worm
	  private static int DOTSIZE = 10;
	  private static final int RADIUS = DOTSIZE/2;
	  private int POINTS = 5;
	
	  private int pWidth, pHeight;   // Dimensioni area pannello
	  
	  // Stores the increments in each of the compass dirs.
	  // An increment is added to the old head position to get the
	  // new position.
	  Point2D.Double incrs[];
	  private static final int NUM_DIRS=4;
	  private static final int N=0;
	  private static final int S=1;
	  private static final int L=2;
	  private static final int R=3;
	  private static final int SPEED=1;
	  
	  private int VITE=5;
	  
	  private int oppositeDirection[] = new int[NUM_DIRS];
	  
	  public Worm(int pWidth, int pHeight, int dotSize, WallManager w, Fruit f){
		// increments for each compass dir
		    incrs = new Point2D.Double[NUM_DIRS];
		    incrs[N] = new Point2D.Double(0.0, -SPEED);
		    incrs[S] = new Point2D.Double(0.0, SPEED);
		    incrs[L] = new Point2D.Double(-SPEED, 0.0);
		    incrs[R] = new Point2D.Double(SPEED, 0.0);
		    
		    oppositeDirection[N]=S;
		    oppositeDirection[S]=N;
		    oppositeDirection[L]=R;
		    oppositeDirection[R]=L;
		    
		    this.pWidth = pWidth;
		    this.pHeight = pHeight;
		    
		    this.DOTSIZE=dotSize;
		    
		    this.wallManager = w;
		    this.f = f;
	  }
	  

		public void reset(WallManager w, Fruit f) {
			POINTS = 5;
			positionOfWorm.clear();
			this.wallManager = w;
		    this.f = f;
		    this.lastDirection=N;
		    this.lastGoodDirection=N;
		    this.collision=false;
		}
	  
	  public void setKeyAdapter(WormKeyAdapter ka){
		  this.kvermicello = ka;
	  }
	  
	private Color headColor = Color.RED;
	private Color bodyColor = Color.GREEN;
	public void draw(Graphics g) {
		Random r = new Random();

		if (positionOfWorm.size() > 0) {
			if (collision) 
				{
					g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
				}else{
					g.setColor(bodyColor);
				}
			
			
			for (int i = 0; i < positionOfWorm.size(); i++) {
				if (i == (positionOfWorm.size() - 1)) {
					if (collision){
						g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
					}else{
						g.setColor(headColor);
					}
				}
				g.fillOval(positionOfWorm.get(i).x, positionOfWorm.get(i).y,DOTSIZE, DOTSIZE);
			}
		}
	}
	
	// Dove si trova il verme
	private java.util.List<Point> positionOfWorm = new ArrayList<Point>();
	  
	public void update() {
		this.kvermicello.processKeyEventList();
		
	    if (positionOfWorm.size() == 0) {   // empty array at start
	    	positionOfWorm.add(new Point( pWidth/2, pHeight/2 )); // center pt
	    }
	    else if (positionOfWorm.size() == POINTS) {    // la lista raggiunge il numero di punti desiderato
	    	positionOfWorm.remove(0);
	        newHead(positionOfWorm.get(positionOfWorm.size()-1));
	    }
	    else {     // still room in cells[]
	      newHead(positionOfWorm.get(positionOfWorm.size()-1));
	    }
	}

	boolean collision=false;
	


	
	private int lastGoodDirection=N;
	/**
	 * Crea una nuova testa e fa tutti i controlli di collisione
	 * @param prevPosn
	 */
	private void newHead(Point prevPosn)
	{
		if (this.lastGoodDirection == oppositeDirection[this.lastDirection]){
			this.lastDirection=this.lastGoodDirection;
		}
		Point newPt = nextPoint(this.lastGoodDirection);
		if (wallManager.detectWallCollision(newPt, DOTSIZE) || detectAutoCollision(newPt)) {
			if (!collision) VITE--;
			collision=true;
		} else{	
			pickControl(newPt);
			positionOfWorm.add(newPt);
			this.lastGoodDirection=this.lastDirection;
		}
	  }  // end of newHead()
	
	  private void pickControl(Point newPt){
		  if (f.isPicked(newPt, DOTSIZE)){
			  POINTS += 5;
		  }
	  }
	
	  public boolean detectAutoCollision(Point newPt){
			List<Point> points = this.positionOfWorm;
			Rectangle rNewPoint = new Rectangle(newPt.x,newPt.y,DOTSIZE,DOTSIZE);
			for (int i=0;i<points.size()-1;i++){
				Point p = points.get(i);
				Rectangle r = new Rectangle(p.x,p.y,DOTSIZE,DOTSIZE);
				if (r.intersects(rNewPoint)) return true;
			}
			return false;
	  }
	  
	  private Point nextPoint(int direction)
	  { 
	    // get the increments for the compass bearing
	    Point2D.Double incremento = incrs[direction];

	    int newX = positionOfWorm.get(positionOfWorm.size()-1).x + (int)(DOTSIZE * incremento.x);
	    int newY = positionOfWorm.get(positionOfWorm.size()-1).y + (int)(DOTSIZE * incremento.y);

	    // modify newX/newY if < 0, or > pWidth/pHeight; use wraparound 
	    if (newX < 0)     // is circle off the left edge of the canvas?
	      newX = newX + pWidth;
	    else  if (newX+DOTSIZE > pWidth)  // is circle off the right edge of the canvas?
	      newX = newX - pWidth;  

	    if (newY < 0)     // is circle off the top of the canvas?
	      newY = newY + pHeight;
	    else  if (newY+DOTSIZE > pHeight) // is circle off the bottom of the canvas?
	      newY = newY - pHeight;

	    return new Point(newX,newY);
	  }  // end of nextPoint()
	  
	private int lastDirection = N;
	public void handleKeyEvent(int directionKeyEvent) {
		switch (directionKeyEvent) {
			case KeyEvent.VK_UP:
				this.lastDirection=N;
				break;
			case KeyEvent.VK_DOWN:
				this.lastDirection=S;
				break;
			case KeyEvent.VK_LEFT:
				this.lastDirection=L;
				break;
			case KeyEvent.VK_RIGHT:
				this.lastDirection=R;
				break;
			default: 
				this.lastDirection=this.lastGoodDirection;
		}
	}

	public boolean isDead() {
		if (VITE==0) return true;
		return false;
	}

	public boolean isCollisione(){
		return collision;
	}
	
	public int viteRestanti(){
		return VITE;
	}
}
