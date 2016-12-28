package me.robofon.gravity.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import me.robofon.gravity.Gravity;

public class GuiInGame extends GuiScreen {

	@Override
	public void init() {
		addButton(new Button(1, 10, 100, "Trails"));
		addButton(new Button(2, 10, 170, "Pause"));
	}
	
	@Override
	public void actionPress(int id) {
		super.actionPress(id);
		if(id == 1) {
			Gravity.getGame().trails = !Gravity.getGame().trails;
		}
		if(id == 2) {
			Gravity.getGame().pause = !Gravity.getGame().pause;
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawString("Delta: " + Gravity.getGame().delta, 5, g.getFontMetrics().getHeight());
		g.drawString("div: " + Gravity.getGame().div, 5, g.getFontMetrics().getHeight() * 2);
		g.drawString("Zoom: " + Gravity.getGame().getRenderer().zoom, 5, g.getFontMetrics().getHeight() * 3);
		super.render(g);
	}

}
