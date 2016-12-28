package me.robofon.gravity.gui;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class GuiScreen {
	
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public ArrayList<Button> getButtons() {
		return buttons;
	}
	
	public void actionPress(int id) {
		
	}
	
	public void actionRelease(int id) {
		
	}
	
	public void addButton(Button btn) {
		this.buttons.add(btn);
		btn.onAdd();
	}
	
	public void init() {
		this.buttons.clear();
	}
	
	public void update(double delta) {
		for(Button button : buttons) {
			button.update(delta);
		}
	}
	
	public void render(Graphics2D g) {
		for(Button button : buttons) {
			button.render(g);
		}
	}

	public void mousePressed(MouseEvent e) {
		for(Button button : buttons) {
			if(button.isPointInside(e.getX(), e.getY())) {
				button.onClicked(e);
				actionPress(button.getId());
				e.consume();
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		for(Button button : buttons) {
			if(button.isPointInside(e.getX(), e.getY())) {
				button.onStopClicked(e);
				actionRelease(button.getId());
				e.consume();
			}
		}
	}
	
}
