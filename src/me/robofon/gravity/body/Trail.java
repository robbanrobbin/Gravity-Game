package me.robofon.gravity.body;

public class Trail {
	
	public double x;
	public double y;
	
	public int life = 0;
	
	public Trail(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		life++;
	}
	
}
