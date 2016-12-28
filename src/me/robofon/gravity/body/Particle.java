package me.robofon.gravity.body;

import java.awt.Color;

import me.robofon.gravity.Gravity;

public class Particle {
	
	public double x;
	public double y;
	public double velX;
	public double velY;
	public float alpha = 0.5f;
	public float alphaDec = 0.25f;
	public Color color = Color.GREEN;
	public boolean cross = false;
	public double scale = 4;
	
	public Particle(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Particle(double x, double y, float alpha) {
		this.x = x;
		this.y = y;
		this.alpha = alpha;
	}
	
	public Particle(double x, double y, double velX, double velY, float alpha, Color color) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.alpha = alpha;
		this.color = color;
	}
	
	public void update(double delta) {
		this.x += this.velX * delta;
		this.y += this.velY * delta;
		alpha -= alphaDec * delta;
		if(alpha < 0) {
			alpha = 0;
			Gravity.getGame().getWorld().removeParticle(this);
		}
	}
	
}
