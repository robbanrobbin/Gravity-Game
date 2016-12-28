package me.robofon.gravity.physics;

public class CircleCollider extends Collider {
	
	public double radius;
	
	public CircleCollider(double x, double y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public CircleCollider(double radius) {
		this.radius = radius;
	}
	
	public CircleCollider tempAdd(double x, double y) {
		return new CircleCollider(this.x + x, this.y + y, radius);
	}
	
}
