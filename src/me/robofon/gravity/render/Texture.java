package me.robofon.gravity.render;

import java.awt.Image;

import me.robofon.gravity.Gravity;

public class Texture {
	
	public String path;
	
	public double scale = 1d;
	
	public int xOffset = 0;
	
	public int yOffset = 0;
	
	public Texture(String path, double scale, int xOffset, int yOffset) {
		this.path = path;
		this.scale = scale;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public Texture(String path) {
		this.path = path;
	}
	
	public Texture(String path, double scale) {
		this.path = path;
		this.scale = scale;
	}
	
	public Image getImage() {
		return Gravity.getGame().getRenderer().getImage(this.path);
	}
	
}
