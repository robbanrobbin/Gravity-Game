package me.robofon.gravity.gui;

import java.awt.Graphics2D;

public abstract class Component {
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public abstract void render(Graphics2D g);
	
	public void update(double delta) {
		
	}
	
	public abstract void onAdd();
	
}
