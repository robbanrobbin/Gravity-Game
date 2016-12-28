package me.robofon.gravity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import me.robofon.gravity.body.Body;
import me.robofon.gravity.body.atmosphere.Atmosphere;
import me.robofon.gravity.gui.GuiInGame;
import me.robofon.gravity.gui.GuiScreen;
import me.robofon.gravity.physics.CircleCollider;
import me.robofon.gravity.render.Renderer;
import me.robofon.gravity.world.World;

public class Gravity {

	private static Gravity game;
	private Renderer renderer;
	public double delta = 1;
	private boolean mousePressed;
	private GameCanvas canvas;
	private World world;
	private JFrame frame;
	private boolean gameRunning = true;
	public int mouseX, mouseY;
	public double mouseXinGame, mouseYinGame;
	public double div = 100d;
	public boolean trails = false;
	public GuiScreen guiScreen;
	public boolean useCache = false;
	public boolean pause = false;

	public static void main(String[] args) {
		game = new Gravity();
		game.init();
	}

	private void init() {
		this.renderer = new Renderer();
		this.canvas = new GameCanvas();
		
		frame = new JFrame("Gravity");
		frame.add(canvas);
		
		canvas.addKeyListener(canvas);
		canvas.addMouseListener(canvas);
		canvas.addMouseWheelListener(canvas);
		canvas.addMouseMotionListener(canvas);
		canvas.setFocusable(true);
		canvas.requestFocusInWindow();
		frame.setSize(canvas.WIDTH, canvas.HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setUndecorated(true);
		frame.setVisible(true);
		this.openGui(new GuiInGame());
		canvas.start();
		world = new World();
		Body root = new Body(0, 0, 0, 0, 50000, 0.5, new Atmosphere(new CircleCollider(70), 0.7, 0.7));
		root.move = false;
		//world.addBody(new Body(new CircleCollider(0, 0, 1), 0, -1500, 55, 0, 1000, new Atmosphere(new CircleCollider(1.2), 1, 1)));
		world.addBody(root);
		//renderer.chained = root;
		gameLoop();

		
	}

	public static Gravity getGame() {
		return game;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, canvas.WIDTH, canvas.HEIGHT);
//		RenderingHints rh = new RenderingHints(
//	             RenderingHints.KEY_ANTIALIASING,
//	             RenderingHints.VALUE_ANTIALIAS_ON);
//	    g.setRenderingHints(rh);
		renderer.render(g);
		if(guiScreen != null) {
			guiScreen.render(g);
		}
	}

	public void update(double delta) {
		this.mouseXinGame = renderer.getXInGame(mouseX);
		this.mouseYinGame = renderer.getYInGame(mouseY);
		if(!pause) {
			world.update(delta);
		}
		if(guiScreen != null) {
			guiScreen.update(delta);
		}
	}

	public GameCanvas getCanvas() {
		return canvas;
	}

	public World getWorld() {
		return world;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void gameLoop() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 120;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		long lastFpsTime = 0;
		long fps = 0;
		while (gameRunning) {
			// work out how long its been since the last update, this
		      // will be used to calculate how far the entities should
		      // move this loop
		      long now = System.nanoTime();
		      long updateLength = now - lastLoopTime;
		      lastLoopTime = now;
		      delta = updateLength / ((double)OPTIMAL_TIME) / div / 2d;

		      // update the frame counter
		      lastFpsTime += updateLength;
		      fps++;
		      
		      // update our FPS counter if a second has passed since
		      // we last recorded
		      if (lastFpsTime >= 1000000000)
		      {
		         System.out.println("(FPS: "+fps+")");
		         lastFpsTime = 0;
		         fps = 0;
		      }
		      
		      // update the game logic
		      canvas.updateGame(delta);
		      
		      // draw everyting
		      gameRender();
		      
		      // we want each frame to take 10 milliseconds, to do this
		      // we've recorded when we started the frame. We add 10 milliseconds
		      // to this and then factor in the current time to give 
		      // us our final value to wait for
		      // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
		      try {
		    	  long wait = (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000;
		    	  if(wait > 0) {
		    		  Thread.sleep(wait);
		    	  }
		      }
		      catch(Exception e) {
		    	  e.printStackTrace();
		      }
		}
	}
	
	public void gameRender() {
		Graphics2D g = (Graphics2D)canvas.bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, canvas.WIDTH, canvas.HEIGHT);
		canvas.renderGame(g);
		g.dispose();
		canvas.bufferStrategy.show();
	}
	
	public void openGui(GuiScreen guiScreen) {
		this.guiScreen = guiScreen;
		this.guiScreen.init();
	}

	public void mousePressed(MouseEvent e) {
		if(guiScreen != null) {
			guiScreen.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(guiScreen != null) {
			guiScreen.mouseReleased(e);
		}
	}

}
