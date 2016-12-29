package me.robofon.gravity.body;

import me.robofon.gravity.Gravity;
import me.robofon.gravity.body.atmosphere.Atmosphere;
import me.robofon.gravity.math.MathUtils;
import me.robofon.gravity.physics.CircleCollider;
import me.robofon.gravity.render.Texture;

public class Body {
	
	public CircleCollider collider;
	
	public boolean visible = true;
	private Texture texture;
	private double mass = 0;
	public double x;
	public double y;
	public double velX;
	public double velY;
	public boolean move = true;
	public Atmosphere atmosphere;
	public double density = 1f;
	public double lifeTime = 0;
	public boolean checked = false;
	
	public Body(double x, double y, double velX, double velY, double mass, double density) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.mass = mass;
		this.density = density;
		CircleCollider collider = new CircleCollider(mass * density * 0.002);
		this.setCollider(collider);
	}
	
	public Body(double x, double y, double velX, double velY, double mass, double density, Atmosphere atmosphere) {
		this(x, y, velX, velY, mass, density);
		this.setAtmosphere(atmosphere);
	}
	
	public CircleCollider getCollider() {
		return collider;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	protected String getTexturePath() {
		return null;
	}
	
	protected String getColliderInit() {
		return null;
	}
	
	public void setCollider(CircleCollider collider) {
		this.collider = collider;
		collider.x = x;
		collider.y = y;
	}
	
	public void setAtmosphere(Atmosphere atmos) {
		this.atmosphere = atmos;
		atmos.x = x;
		atmos.y = y;
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public void addMass(double mass) {
		this.mass += mass;
	}
	
	public void addVelocity(double x, double y) {
		this.velX += x;
		this.velY += y;
	}
	
	public void setVelocity(double x, double y) {
		this.velX = x;
		this.velY = y;
	}

	public void update(double delta) {
		checked = false;
		this.lifeTime += delta;
		this.collider.radius = mass * density * 0.002;
		this.x += this.velX * delta;
		this.y += this.velY * delta;
		this.collider.x = this.x;
		this.collider.y = this.y;
		if(this.atmosphere != null) {
			this.atmosphere.x = this.x;
			this.atmosphere.y = this.y;
			this.atmosphere.update();
		}
		if(Gravity.getGame().trails) {
			Gravity.getGame().getWorld().addTrail(new Trail(x, y));
		}
	}
	
	public double[] getMoveLooking(double factor, double angleAdd) {
		double angle = MathUtils.getAngle(velX, velY, 0, 0);
		double angleA = Math.toRadians(MathUtils.normalizeAngle(angle + angleAdd));
		double x = Math.cos(angleA) * factor;
		double y = Math.sin(angleA) * factor;
		return new double[]{x, y};
	}
	
	public double[] getMoveLookingBack(double factor) {
		return getMoveLooking(factor, 180d);
	}
	
	public void addForceToMass(double x, double y) {
		double factor = this.getMass() / 5000d;
		if(this.mass < 100) {
			factor = 1;
		}
		this.velX += x / (factor);
		this.velY += y / (factor);
	}
	
	public boolean hasAtmosphere() {
		if(atmosphere == null) {
			return false;
		}
		else {
			return atmosphere.maxDensity >= 0;
		}
	}
	
	public void markChecked() {
		checked = true;
	}
	
	public Body createBodyFromMass(double massTaken) {
		this.mass -= massTaken;
		Body body = new Body(0, 0, 0, 0, massTaken, density);
		return body;
	}
	
}
