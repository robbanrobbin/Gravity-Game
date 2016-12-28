package me.robofon.gravity.body.atmosphere;

import me.robofon.gravity.physics.CircleCollider;

public class Atmosphere {
	
	public CircleCollider collider;
	public double maxDensity;
	public double minDensity;
	public double x;
	public double y;
	
	public Atmosphere(CircleCollider collider, double minDensity, double maxDensity) {
		this.collider = collider;
		this.minDensity = minDensity;
		this.maxDensity = maxDensity;
	}
	
	public void update() {
		this.collider.x = x;
		this.collider.y = y;
	}
	
}
