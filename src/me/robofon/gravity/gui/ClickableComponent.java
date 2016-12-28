package me.robofon.gravity.gui;

import java.awt.event.MouseEvent;

import me.robofon.gravity.Gravity;

public abstract class ClickableComponent extends Component {
	
	public int interactX;
	public int interactY;
	public int interactWidth;
	public int interactHeight;
	
	protected int clickedCount = 0;
	protected boolean mouseDown;
	protected boolean hovered = false;
	
	public void onClicked(MouseEvent event) {
		clickedCount++;
		mouseDown = true;
	}
	
	public void onStopClicked(MouseEvent event) {
		mouseDown = false;
	}
	
	public int getClickedCount() {
		return clickedCount;
	}
	
	public boolean isMouseDown() {
		return mouseDown;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public abstract void setInteractableRegion();
	
	@Override
	public void onAdd() {
		this.setInteractableRegion();
	}
	
	public boolean isPointInside(int x, int y) {
		return x > this.interactX
		&& x < this.interactWidth + this.interactX
		&& y > this.interactY
		&& y < this.interactHeight + this.interactY;
	}
	
}
