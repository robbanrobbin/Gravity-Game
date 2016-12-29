package me.robofon.gravity.civilisation;

public class Life {
	
	private int health = 0;
	
	public Life() {
		this.health = getMaxHealth();
	}
	
	public void update() {
		
		if(this.health <= 0) {
			die();
		}
		
	}
	
	public void reproduce() {
		
	}
	
	public void die() {
		this.health = 0;
		
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
