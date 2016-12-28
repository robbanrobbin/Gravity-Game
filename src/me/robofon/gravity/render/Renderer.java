package me.robofon.gravity.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import me.robofon.gravity.Gravity;
import me.robofon.gravity.body.Body;
import me.robofon.gravity.body.Particle;
import me.robofon.gravity.body.Trail;


public class Renderer {
	
	public HashMap<String, Image> imgChache = new HashMap<String, Image>();
	
	public boolean doFade = true;
	
	public double renderX, renderY;
	
	public double xOffset, yOffset;
	
	public double zoom = 1;
	
	public Body chained = null;
	
	public double nextFlashX = 0;
	
	public double nextFlashY = 0;
	
	public boolean doFlash = false;
	
	private int flashTimer = 0;
	
	double xHalfWidth = 0;
	double yHalfHeight = 0;
	
	public HashMap<Double, Integer> cacheX = new HashMap<Double, Integer>();
	public HashMap<Double, Integer> cacheY = new HashMap<Double, Integer>();
	public ArrayList<Double> paintedX = new ArrayList<Double>();
	public ArrayList<Double> paintedY = new ArrayList<Double>();
	double lastZoom = 0;
	double lastxOffset = 0;
	double lastyOffset = 0;
	
	public double getXInGame(int x) {
		return (((((renderX + x) - ((Gravity.getGame().getPanel().WIDTH / 2))) / zoom) - xOffset));
	}
	
	public double getYInGame(int y) {
		return (((((renderY + y) - ((Gravity.getGame().getPanel().HEIGHT / 2))) / zoom) - yOffset));
	}
	
	public double getY(double yy) {
		if(Gravity.getGame().useCache) {
			yy = Math.round(yy * zoom) / zoom;
			boolean chached = cacheY.containsKey(yy);
			if(chached) {
				return cacheY.get(yy);
			}
		}
		double y = ((-(renderY - yy) + (yHalfHeight))) + yOffset;
		y *= zoom;
		if(Gravity.getGame().useCache) {
			cacheY.put(yy, (int)y);
		}
		return y;
	}
	
	public double getX(double xx) {
		if(Gravity.getGame().useCache) {
			xx = Math.round(xx * zoom) / zoom;
			boolean chached = cacheX.containsKey(xx);
			if(chached) {
				return cacheX.get(xx);
			}
		}
		double x = ((-(renderX - xx) + (xHalfWidth))) + xOffset;
		x *= zoom;
		if(Gravity.getGame().useCache) {
			cacheX.put(xx, (int)x);
		}
		return x;
	}
	
	public Renderer() {
		
	}
	
	public void clearCache() {
		cacheX.clear();
		cacheY.clear();
	}
	
	public void render(Graphics2D g) {
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		
		Gravity game = Gravity.getGame();
		xHalfWidth = ((game.getPanel().WIDTH / 2) / zoom);
		yHalfHeight = ((game.getPanel().HEIGHT / 2) / zoom);
		if(Gravity.getGame().useCache) {
			if(lastZoom != zoom) {
				clearCache();
			}
			if(lastyOffset != yOffset) {
				clearCache();
			}
			if(lastxOffset != xOffset) {
				clearCache();
			}
			lastZoom = zoom;
			lastyOffset = yOffset;
			lastxOffset = xOffset;
		}
		if(chained != null) {
			this.renderX = (int) chained.x;
			this.renderY = (int) chained.y;
		}
		if(game.getWorld() != null) {
			g.setColor(Color.WHITE);
			ArrayList<Body> render = Gravity.getGame().getWorld().getBodies();
			for(Body body : render) {
				if(!body.hasAtmosphere()) {
					continue;
				}
				double x = getX(body.x);
				double y = getY(body.y);
				g.setColor(new Color(0.35f, 0.35f, 0.8f));
				drawCenteredCircle(g, x, y, body.atmosphere.collider.radius * zoom * 2.0);
			}
			for(Body body : render) {
				g.setColor(new Color(0.7f, 0.7f, 0.7f));
				if(body.hasAtmosphere()) {
					g.setColor(new Color(0.2f, 0.7f, 0.2f));
				}
				double x = getX(body.x);
				double y = getY(body.y);
				if(x - body.getCollider().radius * 2.0 * zoom > game.getPanel().WIDTH) {
					continue;
				}
				if(x + body.getCollider().radius * 2.0 * zoom < 0) {
					continue;
				}
				if(y - body.getCollider().radius * 2.0 * zoom > game.getPanel().HEIGHT) {
					continue;
				}
				if(y + body.getCollider().radius * 2.0 * zoom < 0) {
					continue;
				}
				if(Gravity.getGame().useCache) {
					if(paintedX.contains(x) && paintedY.contains(y)) {
						continue;
					}
					paintedX.add(x);
					paintedY.add(y);
				}
				double radius = body.getCollider().radius * zoom * 2.0;
				if(radius > 2) {
					drawCenteredCircle(g, x, y, radius);
				}
				else {
					drawPixel(g, x, y);
				}
			}
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
			
			for(Particle part : Gravity.getGame().getWorld().getParticles()) {
				g.scale(part.scale, part.scale);
				double x = ((-(renderX - part.x) + (xHalfWidth))) + xOffset;
				double y = ((-(renderY - part.y) + (yHalfHeight))) + yOffset;
				x *= zoom;
				y *= zoom;
				x /= part.scale;
				y /= part.scale;
				g.setColor(new Color(part.color.getRed() / 255f, part.color.getGreen() / 255f, part.color.getBlue() / 255f, part.alpha / 2f));
				g.fillRect((int)x, (int)y, 1, 1);
				if(part.cross) {
					g.setColor(new Color(part.color.getRed() / 255f, part.color.getGreen() / 255f, part.color.getBlue() / 255f, part.alpha / 2f));
					g.fillRect((int)x + 1, (int)y, 1, 1);
					g.setColor(new Color(part.color.getRed() / 255f, part.color.getGreen() / 255f, part.color.getBlue() / 255f, part.alpha / 2f));
					g.fillRect((int)x - 1, (int)y, 1, 1);
					g.setColor(new Color(part.color.getRed() / 255f, part.color.getGreen() / 255f, part.color.getBlue() / 255f, part.alpha / 2f));
					g.fillRect((int)x, (int)y - 1, 1, 1);
					g.setColor(new Color(part.color.getRed() / 255f, part.color.getGreen() / 255f, part.color.getBlue() / 255f, part.alpha / 2f));
					g.fillRect((int)x, (int)y + 1, 1, 1);
				}
				g.scale(1d / part.scale, 1d / part.scale);
			}
			if(Gravity.getGame().trails) {
				ArrayList<Trail> trails = Gravity.getGame().getWorld().getTrails();
				for(Trail trail : trails) {
					int x = (int) getX(trail.x);
					int y = (int) getY(trail.y);
					g.setColor(new Color(1f, 1f, 1f,1f));
					g.fillRect(x, y, 1, 1);
				}
			}
			
			if(doFlash) {
				//System.out.println("flash " + flashTimer);
				float time = 10f;
				double scale = 1;
				if(flashTimer == -1) {
					flashTimer = (int) time;
				}
				g.scale(scale, scale);
				int x = (int)nextFlashX;
				int y = (int)nextFlashY;
				x /= scale;
				y /= scale;
				for(int i = 1; i < 100; i++) {
					g.setColor(new Color(1f,1f,1f, (1f - i / 100f) * ((float)flashTimer / time) ));
					g.fillRect((int)x + i, (int)y, 1, 1);
				}
				for(int i = 1; i < 100; i++) {
					g.setColor(new Color(1f,1f,1f, (1f - i / 100f) * ((float)flashTimer / time) ));
					g.fillRect((int)x, (int)y - i, 1, 1);
				}
				for(int i = 1; i < 100; i++) {
					g.setColor(new Color(1f,1f,1f, (1f - i / 100f) * ((float)flashTimer / time) ));
					g.fillRect((int)x - i, (int)y, 1, 1);
				}
				for(int i = 1; i < 100; i++) {
					g.setColor(new Color(1f,1f,1f, (1f - i / 100f) * ((float)flashTimer / time) ));
					g.fillRect((int)x, (int)y + i, 1, 1);
				}
				g.setColor(new Color(1f, 1f, 1f, Math.min(((float)flashTimer / 50f) + 0.1f, 1f)));
				g.fillRect((int)x, (int)y, 1, 1);
				g.scale(1d / scale, 1d / scale);
			}
			flashTimer--;
			if(flashTimer < 0) {
				flashTimer = -1;
				doFlash = false;
			}
			int mouseX = game.getPanel().lastMouseX;
			Gravity.getGame().mouseX = (int) (((((renderX + mouseX) - ((game.getPanel().WIDTH / 2)))) - xOffset) / zoom);
			
			int mouseY = game.getPanel().lastMouseY;
			Gravity.getGame().mouseY = (int) (((((renderY + mouseY) - ((game.getPanel().HEIGHT / 2)))) - yOffset) / zoom);
			
			if(Gravity.getGame().useCache) {
				paintedX.clear();
				paintedY.clear();
			}
		}
	}
	
	private void drawPixel(Graphics2D g, double x, double y) {
		Line2D line = new Line2D.Double(x, y, x + 1, y);
		g.draw(line);
	}

	public void drawCenteredCircle(Graphics2D g, double x, double y, double r) {
		x = x - (r / 2d);
		y = y - (r / 2d);
		Ellipse2D ell = new Ellipse2D.Double(x, y, r, r);
		g.fill(ell);
		//g.fillOval((int)x, (int)y, (int)r, (int)r);
	}
	
	public Image getImage(String path) {
		Image img = null;
		if(imgChache.containsKey(path)) {
			img = imgChache.get(path);
		}
		else {
			System.out.println("Loading image: " + path);
			
			try {
				img = ImageIO.read(Gravity.class.getClassLoader().getResource("textures/" + path));
				imgChache.put(path, img);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
}
