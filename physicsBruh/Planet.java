package physicsBruh;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Planet extends Rectangle {

	private double mass;
	
	public Planet(int x, int y) {
		super(x,y, 20, 20);
		this.mass = 20;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillOval(this.x,this.y,this.width,this.height);
	}
	
	public double getMass() {return mass;}
	
}
