package physicsBruh;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.util.LinkedList;

public class Asteroid extends Rectangle {

	private double vx, vy, ax, ay;
	private double mass;
	private double observeAngle;
	private Planet parent;
	
	private boolean orbit = true;
	
	private LinkedList<Point> trail = new LinkedList<>();
    private static final int MAX_TRAIL_LENGTH = 200;
	
	private int g;
	
	public Asteroid(int x,int y, Planet p) {
		super(x,y, 10, 10);
		this.vx = 2;
		this.vy = 0;
		this.ax = 0;
		this.ay = 0;
		this.parent = p;
		this.mass = 20;
		
		this.g = 100;
		
		if (this.getCenterX() != parent.getCenterX()) {
			this.observeAngle = Math.atan( (this.getCenterY() - parent.getCenterY()) / (parent.getCenterX() - this.getCenterX()) );
		} else {
			this.observeAngle = 0;
		}
		
	}
	
	public void update() {
		if (orbit) {
			this.enableOrbit();
		} else {
		double dx = parent.getCenterX() - this.getCenterX();
	    double dy = parent.getCenterY() - this.getCenterY();
	    double r2 = dx * dx + dy * dy;
	    double r = Math.sqrt(r2);

	    // gravitational acceleration magnitude
	    double a = g * parent.getMass() / r2;  

	    // normalize direction vector
	    ax = a * (dx / r);
	    ay = a * (dy / r);

	    vx += ax;
	    vy += ay;
		}
	    this.x += vx;
	    this.y += vy;

	    System.out.println("Accel=(" + ax + "," + ay + 
	        ") Vel=(" + vx + "," + vy + ")");
		
		//this.vx = drag(vx);
		//this.vy = drag(vy);
		this.handleCollisions();
		
		trail.add(new Point((int)getCenterX(), (int)getCenterY()));
		if (trail.size() > MAX_TRAIL_LENGTH) {
		    trail.removeFirst(); // remove oldest so list doesn't grow forever
		}
		
	}
	
	public void enableOrbit() {
	    double dx = this.getCenterX() - parent.getCenterX();
	    double dy = this.getCenterY() - parent.getCenterY();
	    double r = Math.sqrt(dx * dx + dy * dy);

	    // orbital speed
	    double v = Math.sqrt(g * parent.getMass() / r);

	    // perpendicular direction (normalized)
	    double perpX = -dy / r;
	    double perpY = dx / r;

	    // set velocity
	    vx = v * perpX;
	    vy = v * perpY;
	}
	
	public double drag(double v) {
		double drag = 0.01;
		if (Math.abs(v) > 10) {
			drag = 1;
		}
		int sign = (int) Math.signum(v);
		double absV = Math.abs(v) - drag;
		return sign * absV;
	}
	
	public void handleCollisions() {
		if(x < 0 || x > 1200) {
			vx=-vx;
		}
		if(y < 0 || y > 800) {
			vy=-vy;
		}
	}
	
	

	public void render(Graphics2D g) {
	    // draw asteroid
	    g.setColor(Color.WHITE);
	    g.fillRect(this.x, this.y, width, height);

	    // smooth trail
	    if (trail.size() > 1) {
	        Path2D path = new Path2D.Double();
	        Point start = trail.getFirst();
	        path.moveTo(start.x, start.y);
	        for (Point p : trail) {
	            path.lineTo(p.x, p.y);
	        }
	        g.setColor(new Color(0, 255, 255, 180)); // cyan with alpha
	        g.draw(path);
	    }

	    // draw velocity vector in green
	    int cx = (int)getCenterX();
	    int cy = (int)getCenterY();
	    g.setColor(Color.GREEN);
	    g.drawLine(cx, cy, cx + (int)(vx * 5), cy + (int)(vy * 5));

	    // draw acceleration vector in red
	    g.setColor(Color.RED);
	    g.drawLine(cx, cy, cx + (int)(ax * 500), cy + (int)(ay * 500));
	}
	
}
