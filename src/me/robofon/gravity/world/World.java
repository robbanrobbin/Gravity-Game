package me.robofon.gravity.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.robofon.gravity.Gravity;
import me.robofon.gravity.body.Body;
import me.robofon.gravity.body.Particle;
import me.robofon.gravity.body.Trail;
import me.robofon.gravity.civilisation.Life;
import me.robofon.gravity.math.MathUtils;
import me.robofon.gravity.physics.Collider;
import me.robofon.gravity.render.Renderer;


public class World {
	
	private ArrayList<Body> bodies = new ArrayList<Body>();
	
	private ArrayList<Body> bodiesToRemove = new ArrayList<Body>();
	private ArrayList<Body> bodiesToAdd = new ArrayList<Body>();
	
	private ArrayList<Trail> trails = new ArrayList<Trail>();
	
	private ArrayList<Trail> trailsToAdd = new ArrayList<Trail>();
	private ArrayList<Trail> trailsToRemove = new ArrayList<Trail>();
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	
	private ArrayList<Particle> particlesToAdd = new ArrayList<Particle>();
	private ArrayList<Particle> particlesToRemove = new ArrayList<Particle>();
	
	private ArrayList<Life> lives = new ArrayList<Life>();
	
	private ArrayList<Life> livesToAdd = new ArrayList<Life>();
	private ArrayList<Life> livesToRemove = new ArrayList<Life>();
	
	private Random random = new Random();
	
	double flashTimer = 0.0;
	
	public ArrayList<Body> getBodies() {
		return bodies;
	}
	
	public void update(double delta) {
		if(flashTimer > 0) {
			flashTimer -= delta;
			if(flashTimer < 0) {
				flashTimer = 0;
			}
		}
		addBodies();
		removeBodies();
		for(Body body1 : bodies) {
			if(!body1.move) {
				continue;
			}
			for(Body body2 : bodies) {
				if(body1.equals(body2)) {
					continue;
				}
				if(body2.getMass() <= 10) {
					continue;
				}
				double force = MathUtils.getAttraction(body1, body2);
				if(force > 0.000001) {
					MathUtils.addVelocityBasedOnAngle(body1, MathUtils.getAngle(body1, body2), force * body2.getMass() * delta);
				}
				
			}
		}
		HashMap<Body, Body> checked = new HashMap<Body, Body>();
		for(Body body1 : bodies) {
			for(Body body2 : bodies) {
				if(body1.equals(body2)) {
					continue;
				}
				if(checked.containsKey(body2)) {
					if(checked.get(body2).equals(body1)) {
						continue;
					}
				}
				boolean coll = false;
				if(coll = Collider.collision(body1.collider.tempAdd(body1.velX * Gravity.getGame().delta, body1.velY * Gravity.getGame().delta),
						body2.collider.tempAdd(body2.velX * Gravity.getGame().delta, body2.velY * Gravity.getGame().delta))) {
//					System.out.println("Collsion!");
//					Gravity.getGame().getWorld().removeBody(body1);
//					Gravity.getGame().getWorld().removeBody(body2);
					//body1.setVelocity(body1.velX / 1.3d, body1.velY / 1.3d);
					//MathUtils.addVelocityBasedOnAngle(body1, MathUtils.getAngle(body1, body2), -(body2.getMass() / body1.getMass()) * 2);
//					double mass = body2.getMass();
//					double velocity1 = Math.abs(MathUtils.getDistance(body1.velX, body1.velY, 0, 0) / 3000d);
//					double velocity2 = Math.abs(MathUtils.getDistance(body2.velX, body2.velY, 0, 0) / 3000d);
//					System.out.println(velocity2);
//					System.out.println(velocity2);
					if(body2.getMass() <= 10) {
						removeBody(body2);
						for(int p = 0; p < 1; p++) {
							Particle part = new Particle(body2.x, body2.y, 
									body2.velX * MathUtils.randomDouble(random, 0.05, 0.1) * 2, 
									body2.velY * MathUtils.randomDouble(random, 0.05, 0.1) * 2, 0.7f, new Color(0.3f, 0.3f, 0.3f));
							part.alphaDec = 0.1f / 1f;
							part.scale = MathUtils.randomDouble(random, 1, 10);
							part.cross = false;
							addParticle(part);
						}
						for(int p = 0; p < 1; p++) {
							Particle part = new Particle(body2.x, body2.y, 
									body2.velX * MathUtils.randomDouble(random, 0.001, 0.06) * 2, 
									body2.velY * MathUtils.randomDouble(random, 0.001, 0.06) * 2, 0.7f, new Color(0.7f, 0.7f, 0.7f));
							part.alphaDec = 0.25f / 1f;
							part.scale = MathUtils.randomDouble(random, 1, 10);
							part.cross = false;
							addParticle(part);
						}
						for(int p = 0; p < 1; p++) {
							Particle part = new Particle(body2.x, body2.y, 
									body2.velX * MathUtils.randomDouble(random, 0.001, 0.01) * 2, 
									body2.velY * MathUtils.randomDouble(random, 0.001, 0.01) * 2, 0.7f, new Color(1f, 1f, 1f));
							part.alphaDec = 0.5f / 1f;
							part.scale = MathUtils.randomDouble(random, 1, 10);
							part.cross = false;
							addParticle(part);
						}
						continue;
					}
					if(body2.getMass() <= 10) {
						continue;
					}
					double massRatio = body2.getMass() / body1.getMass();
					//System.out.println(velocity1);
					if((body1.getMass() >= body2.getMass())) {
						int pieces = 10;
						for(int i = 0; i < pieces; i++) {
							Body body = new Body(body2.x + MathUtils.randomDouble(random, -body2.collider.radius, body2.collider.radius),
									body2.y + MathUtils.randomDouble(random, -body2.collider.radius, body2.collider.radius), 
									0, 
									0, body2.getMass() / (double)pieces, body2.density);
							body.addVelocity(MathUtils.randomDouble(random, -0.1, 1) + body2.velX,
									MathUtils.randomDouble(random, -0.1, 1) + body2.velY);
//							MathUtils.addVelocityBasedOnAngle(body, MathUtils.getAngle(body2, body1) + 
//									MathUtils.randomDouble(random, -60, 60), velocity1);
							addBody(body);
						}
						removeBody(body2);
						body1.addVelocity(body2.velX / 10d * massRatio, body2.velY / 10d * massRatio);
							Renderer renderer = Gravity.getGame().getRenderer();
							renderer.doFlash = true;
							renderer.nextFlashX = renderer.getX(body2.x);
							renderer.nextFlashY = renderer.getY(body2.y);
							flashTimer = 1;
						}
				}
				if(!coll && body1.hasAtmosphere()) {
					coll = Collider.collision(body1.atmosphere.collider.tempAdd(body1.velX * Gravity.getGame().delta,
							body1.velY * Gravity.getGame().delta),
							body2.collider.tempAdd(body2.velX * Gravity.getGame().delta,
							body2.velY * Gravity.getGame().delta));
					if(coll) {
						double factor = body1.atmosphere.maxDensity;
						double[] vectors = body2.getMoveLooking(factor, 0);
						body2.addForceToMass(vectors[0], vectors[1]);
						body2.setMass(body2.getMass() - (100 * delta));
					}
				}
				if(!coll) {
					checked.put(body1, body2);
				}
			}
		}
		for(Body body : bodies) {
			body.update(delta);
		}
		if(Gravity.getGame().trails) {
			addTrails();
			removeTrails();
			for(Trail trail : trails) {
				trail.update();
				if(trail.life > 99) {
					removeTrail(trail);
				}
			}
		}
		addLives();
		removeLives();
		for(Life life : lives) {
			life.update();
		}
		addParticles();
		removeParticles();
		for(Particle part : particles) {
			part.update(delta);
		}
	}
	
	public void addBody(Body body) {
		this.bodiesToAdd.add(body);
	}
	
	public void removeBody(Body body) {
		this.bodiesToRemove.add(body);
	}
	
	public void addTrail(Trail body) {
		this.trailsToAdd.add(body);
	}
	
	public void removeTrail(Trail body) {
		this.trailsToRemove.add(body);
	}
	
	public void addBodies() {
		bodies.addAll(bodiesToAdd);
		bodiesToAdd.clear();
	}
	
	public void removeBodies() {
		bodies.removeAll(bodiesToRemove);
		bodiesToRemove.clear();
	}
	
	public void addTrails() {
		trails.addAll(trailsToAdd);
		trailsToAdd.clear();
	}
	
	public void removeTrails() {
		trails.removeAll(trailsToRemove);
		trailsToRemove.clear();
	}
	
	public void addParticles() {
		particles.addAll(particlesToAdd);
		particlesToAdd.clear();
	}
	
	public void removeParticles() {
		particles.removeAll(particlesToRemove);
		particlesToRemove.clear();
	}
	
	public ArrayList<Trail> getTrails() {
		return trails;
	}
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}
	
	public void addParticle(Particle part) {
		particlesToAdd.add(part);
	}
	
	public void removeParticle(Particle part) {
		particlesToRemove.add(part);
	}
	
	public ArrayList<Life> getLives() {
		return lives;
	}
	
	public void addLife(Life life) {
		livesToAdd.add(life);
	}
	
	public void removeParticle(Life life) {
		livesToRemove.add(life);
	}
	
	public void addLives() {
		lives.addAll(livesToAdd);
		livesToAdd.clear();
	}
	
	public void removeLives() {
		lives.removeAll(livesToRemove);
		livesToRemove.clear();
	}
	
}
