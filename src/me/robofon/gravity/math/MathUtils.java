package me.robofon.gravity.math;

import java.util.Random;

import me.robofon.gravity.body.Body;

public class MathUtils {

	public static double clamp(double value, double min, double max) {
		if (value > max) {
			value = max;
		}
		if (value < min) {
			value = min;
		}
		return value;
	}

	public static double getDistanceBetweenBodies(Body b1, Body b2) {
		double xDif = b1.x - b2.x;
		double yDif = b1.y - b2.y;
		double distanceSquared = xDif * xDif + yDif * yDif;
		return distanceSquared;
	}
	
	public static double getDistance(double x, double y, double x2, double y2) {
		double xDif = x - x2;
		double yDif = y - y2;
		double distanceSquared = xDif * xDif + yDif * yDif;
		return distanceSquared;
	}

	public static double getAttraction(Body b1, Body b2) {
		return (Constants.G * ((b2.getMass()) * 1)) / getDistanceBetweenBodies(b1, b2);
	}

	public static double normalizeAngle(double angle) {
		return (angle + 360) % 360;
	}

	public static float normalizeAngle(float angle) {
		return (angle + 360) % 360;
	}

	public static void addVelocityBasedOnAngle(Body body, double angle, double multiplier) {
		double angleA = Math.toRadians(normalizeAngle(angle));
		double x = Math.cos(angleA) * multiplier;
		double y = Math.sin(angleA) * multiplier;
		body.addVelocity(x, y);
	}
	
	public static double[] getVectorsBasedOnAngle(double angle, double multiplier) {
		double angleA = Math.toRadians(normalizeAngle(angle));
		double x = Math.cos(angleA) * multiplier;
		double y = Math.sin(angleA) * multiplier;
		return new double[]{x, y};
	}
	
	public static double getRelativeVelocity(Body b1, Body b2) {
		double vel1 = getDistance(b1.velX - b2.velX, b1.velY - b2.velY, 0, 0);
		
		return vel1;
	}
	
	public static double getAngle(Body b1, Body b2) {
		return getAngle(b1.x, b1.y, b2.x, b2.y);
	}
	
	public static double getAngle(double x1, double y1, double x2, double y2) {
		double angle = Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
	}
	
	public static double randomDouble(Random rand, double min, double max) {
		double randomValue = min + (max - min) * rand.nextDouble();
		return randomValue;
	}
	
	

}
