package me.robofon.gravity.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import me.robofon.gravity.Gravity;

public class Button extends ClickableComponent {
	
	protected String text;
	protected int id;
	
	public Button(int id, int x, int y, String text) {
		this(id, x, y, 100, 30, text);
	}
	
	@Override
	public void update(double delta) {
		this.hovered = false;
		if(isPointInside(Gravity.getGame().getCanvas().lastMouseX, Gravity.getGame().getCanvas().lastMouseY)) {
			this.hovered = true;
		}
		super.update(delta);
	}
	
	public Button(int id, int x, int y, int width, int height, String text) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}

	@Override
	public void render(Graphics2D g) {
		
		g.setColor(hovered ? Color.GREEN : Color.WHITE);
		g.fillRoundRect(x, y, width, height, 20, 20);
		g.setColor(Color.BLACK);
		g.scale(2, 2);
		g.drawString(text, (x + (width / 2 - g.getFontMetrics().stringWidth(text))) / 2,
				(y + g.getFontMetrics().getHeight() / 2 + height / 2) / 2);
		g.scale(0.5, 0.5);
		
	}

	@Override
	public void setInteractableRegion() {
		this.interactX = x;
		this.interactY = y;
		this.interactWidth = width;
		this.interactHeight = height;
	}
	
	public String getText() {
		return text;
	}
	
	public int getId() {
		return id;
	}

}
