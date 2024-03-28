package com.yigitozdemir.pathfinding.data;

public class Unit {
	private float x;
	private float y;
	private int index;
	
	public Unit(float x, float y, int index) {
		this.x = x;
		this.y = y;
		this.index = index;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		
		return "X: "+ getX() + ", Y: " +getY();
	}
	
}