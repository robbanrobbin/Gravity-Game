package me.robofon.gravity;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;

import javax.swing.event.MouseInputListener;

import me.robofon.gravity.body.Body;
import me.robofon.gravity.physics.CircleCollider;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas implements KeyListener, MouseListener, MouseWheelListener, MouseInputListener {
	
	public int WIDTH = 1080;
	public int HEIGHT = 720;
	
	public int lastMouseX = 0;
	public int lastMouseY = 0;
	
	private int translateX = 0;
	private int translateY = 0;
	
	private boolean firing = false;
	
	public BufferStrategy bufferStrategy;
	
	public void start() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
	}

	public void renderGame(Graphics2D g) {
		
		//super.paint(g);
		Gravity.getGame().render(g);
		g.setColor(Color.WHITE);
		if(firing) {
			g.drawLine(translateX, translateY, lastMouseX, lastMouseY);
		}
	}

	public void updateGame(double delta) {
		if(this.WIDTH != this.getParent().getWidth()
				|| this.HEIGHT != this.getParent().getHeight()) {
			this.WIDTH = this.getParent().getWidth();
			this.HEIGHT = this.getParent().getHeight();
		}
		Gravity.getGame().update(delta);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//Gravity.getGame().gameSettings.keyReleased(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			Gravity.getGame().div += -0.2 * Gravity.getGame().div;
			if(Gravity.getGame().div < 1) {
				Gravity.getGame().div = 1;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			Gravity.getGame().div += 0.2 * Gravity.getGame().div;
			if(Gravity.getGame().div < 1) {
				Gravity.getGame().div = 1;
			}
		}
		//Gravity.getGame().gameSettings.keyPressed(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Gravity.getGame().setMousePressed(true);
		Gravity.getGame().mousePressed(e);
		if(e.isConsumed()) {
			return;
		}
		if(e.getButton() == 1) {
			if(translateX == 9999999) {
				translateX = e.getX();
				translateY = e.getY();
			}
			else {
//				translateX = 9999999;
//				translateY = 9999999;
			}
			firing = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Gravity.getGame().setMousePressed(false);
		Gravity.getGame().mouseReleased(e);
		if(e.getButton() == 1) {
			Gravity.getGame().getWorld().addBody(new Body(Gravity.getGame().getRenderer().getXInGame(translateX), 
					Gravity.getGame().getRenderer().getYInGame(translateY), 
					((e.getX() - translateX)) / 1d, ((e.getY() - translateY)) / 1d, 10000, 0.5));
			
			firing = false;
			translateX = 9999999;
			translateY = 9999999;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Gravity.getGame().getRenderer().zoom += (e.getWheelRotation() / 5d) * Gravity.getGame().getRenderer().zoom;
		if(Gravity.getGame().getRenderer().zoom < 0.000000000000001) {
			Gravity.getGame().getRenderer().zoom = 0.000000000000001;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		lastMouseX = e.getX();
		lastMouseY = e.getY();
		if(firing) {
			
		}
		else {
			if(translateX == 9999999) {
				translateX = e.getX();
				translateY = e.getY();
			}
			else {
				Gravity.getGame().getRenderer().xOffset += ((e.getX() - translateX)) / Gravity.getGame().getRenderer().zoom * 2;
				Gravity.getGame().getRenderer().yOffset += ((e.getY() - translateY)) / Gravity.getGame().getRenderer().zoom * 2;
				translateX = 9999999;
				translateY = 9999999;
			}
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastMouseX = e.getX();
		lastMouseY = e.getY();
		translateX = 9999999;
		translateY = 9999999;
	}
	
}
