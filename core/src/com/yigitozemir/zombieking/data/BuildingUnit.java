package com.yigitozemir.zombieking.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

/**
 * A unit of building
 * buildings consist of different units
 */
public class BuildingUnit {
	private int x;
	private int y; 
	private TextureRegion textureRegion;
	
	/**
	 * Shape / position of building unit. Bottom/top/left/right etc.
	 */
	private BuildingUnitShape buildingUnitShape = BuildingUnitShape.NONE;
	/**
	 * Polygon object holds position of the building unit in the space
	 */
	private Polygon buildingUnitPolygon;
	
	public BuildingUnit(int x, int y, TextureRegion textureRegion, BuildingUnitShape buildingUnitShape) {
		this.x = x;
		this.y = y;
		this.textureRegion = textureRegion;
		this.buildingUnitShape = buildingUnitShape;
		
		switch (buildingUnitShape) {
		case BOTTOM:
			buildingUnitPolygon = new Polygon(new float[] {x, y, x, y + 2, x + 8, y + 2, x + 8, y});
			break;
		case TOP:
			buildingUnitPolygon = new Polygon(new float[] {x, y + 6, x, y + 8, x + 8, y + 8, x + 8, y + 6});
			break;
		case LEFT:
			buildingUnitPolygon = new Polygon(new float[] {x, y, x + 2, y, x + 2, y + 8, x, y + 8});
			break;
		case RIGHT:
			buildingUnitPolygon = new Polygon(new float[] {x + 6, y, x + 8, y, x + 8, y + 8, x + 6, y + 8});
			break;
		case BOTTOMLEFT:
			buildingUnitPolygon = new Polygon(new float[] {x, y, x + 8, y, x + 8, y + 2, x + 2 , y + 2, x + 2 , y + 8, x, y + 8});
			break;
		case BOTTOMRIGHT:
			buildingUnitPolygon = new Polygon(new float[] {x, y, x + 8, y, x + 8, y + 8, x + 6, y + 8, x + 6, y + 2, x, y + 2});
			break;
		case TOPRIGHT:
			buildingUnitPolygon = new Polygon(new float[] {x + 6, y, x + 8, y, x + 8, y + 8, x, y + 8, x , y + 6, x + 6, y + 6});
			break;
		case TOPLEFT:
			buildingUnitPolygon = new Polygon(new float[] {x, y, x + 2, y, x + 2, y+6, x + 8, y + 6, x +8, y + 8, x, y + 8});
			break;
		}
	}

	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	public BuildingUnitShape getBuildingUnitShape() {
		return buildingUnitShape;
	}
	public Polygon getBuildingUnitPolygon() {
		return buildingUnitPolygon;
	}
	public void setBuildingUnitShape(BuildingUnitShape buildingUnitShape) {
		this.buildingUnitShape = buildingUnitShape;
	}
	public void setBuildingUnitPolygon(Polygon buildingUnitPolygon) {
		this.buildingUnitPolygon = buildingUnitPolygon;
	}
}
