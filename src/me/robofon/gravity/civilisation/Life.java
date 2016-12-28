package me.robofon.gravity.civilisation;

public class Life {
	
	private int health = 0;
	
	public void update() {
		
		this.health = 20;
		
	}
	
	public void reproduce() {
		
	}
	
	public void die() {
		
	}
	
	public int getMaxHealth() {
		return 20;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
}
