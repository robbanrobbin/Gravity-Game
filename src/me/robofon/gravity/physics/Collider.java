package me.robofon.gravity.physics;


import java.awt.geom.Rectangle2D;

import me.robofon.gravity.math.MathUtils;

public abstract class Collider {
	
	public double x;
	public double y;
	
	public static boolean collision(RectCollider r, CircleCollider c)
	{
		double closestX = MathUtils.clamp(c.x, r.x, r.x2);
		double closestY = MathUtils.clamp(c.y, r.y, r.y2);
		
		double distanceX = c.x - closestX;
		double distanceY = c.y - closestY;
		
		double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
		return distanceSquared < (c.radius * c.radius);
	}
	
	public static boolean collision(RectCollider r, RectCollider r2)
	{
		Rectangle2D.Double rect1 = new Rectangle2D.Double(r.x, r.y, r.x2 - r.x, r.y2 - r.y);
		Rectangle2D.Double rect2 = new Rectangle2D.Double(r2.x, r2.y, r2.x2 - r2.x, r2.y2 - r2.y);
		return rect1.intersects(rect2);
	}
	
	public static boolean collision(CircleCollider c1, CircleCollider c2) {
		double xDif = c1.x - c2.x;
		double yDif = c1.y - c2.y;
		double distanceSquared = xDif * xDif + yDif * yDif;
		boolean collision = distanceSquared < (c1.radius + c2.radius) * (c1.radius + c2.radius);
		return collision;
	}
	
	public static boolean collision(double x, double y, CircleCollider c2) {
		double xDif = x - c2.x;
		double yDif = y - c2.y;
		double distanceSquared = xDif * xDif + yDif * yDif;
		boolean collision = distanceSquared < (c2.radius) * (c2.radius);
		return collision;
	}
	
}
