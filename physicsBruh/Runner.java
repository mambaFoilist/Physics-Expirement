package physicsBruh;

import java.awt.Color;
import java.awt.Graphics2D;

import utilities.GDV5;

public class Runner extends GDV5{

	private Planet earth;
	private Asteroid body;
	
	public Runner() {
		this.earth = new Planet(400, 400);
		this.body = new Asteroid(400, 350, this.earth);
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	body.update();	
	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		earth.render(win);
		body.render(win);
	}
	
	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.start();
		
	}

}
