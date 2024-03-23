package com.yigitozemir.zombieking.data;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * building class
 * each building is a version of nine patch rect
 */
public class Building {
	private int x = 0;
	private int y = 0;
	
	/**
	 * building size line length, minimum 3
	 */
	private int size = 3;
	/**
	 * tile size of each building block
	 */
	private final int TILE_SIZE = 8;
	/**
	 * list of building units to be rendered
	 */
	private ArrayList<BuildingUnit> buildingUnits = new ArrayList<BuildingUnit>();
	/**
	 * texture regions of a nine patch rect
	 */
	private TextureRegion[] textureRegions = new TextureRegion[9];
	
	public Building(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		
		FileHandle fileHandle = Gdx.files.internal("Spritesheet.png");
		Texture texture = new Texture(fileHandle);
		
		//0 top left
		//1 top right
		//2 bottom left
		//3 bottom right
		textureRegions[0] = new TextureRegion(texture,  0, 16, 8, 8);
		textureRegions[1] = new TextureRegion(texture, 16, 16, 8, 8);
		textureRegions[2] = new TextureRegion(texture,  0, 32, 8, 8);
		textureRegions[3] = new TextureRegion(texture, 16, 32, 8, 8);
		//4 top
		//5 left
		//6 right
		//7 bottom
		textureRegions[7] = new TextureRegion(texture,  8, 16, 8, 8);
		textureRegions[5] = new TextureRegion(texture,  0, 24, 8, 8);
		textureRegions[6] = new TextureRegion(texture, 16, 24, 8, 8);
		textureRegions[4] = new TextureRegion(texture,  8, 32, 8, 8);
		
		initializeUnits();
		
	}
	/**
	 * intiialize building units according to size
	 */
	private void initializeUnits() {
		int i = 0, j = 0;
		
		while(i < size) {
			
			while(j < size) {
				TextureRegion tr = null;
				if(i == 0 && j == 0) {
					tr = textureRegions[2];
				}
				if(i == 0 && j == (size - 1)) {
					tr = textureRegions[0];
				}
				if(i == (size - 1) && j == 0) {
					tr = textureRegions[3];
				}
				if(i == (size - 1) && j == (size - 1)) {
					tr = textureRegions[1];
				}
				if(i == 0 && ( j != 0 && j != (size - 1))){
					tr = textureRegions[5];
				}
				if(i == (size - 1) && (j != 0 && j != (size - 1))) {
					tr = textureRegions[6];
				}
				if(j == 0 && (i != 0 && i != (size - 1))) {
					tr = textureRegions[4];
				}
				if(j == (size - 1) && (i != 0 && i != (size - 1))) {
					tr = textureRegions[7];
				}
				
				if(tr != null) {
					BuildingUnit buildingUnit = new BuildingUnit(x + i * TILE_SIZE, y + j * TILE_SIZE, tr);
					buildingUnits.add(buildingUnit);
				}
				
				j++;
			}
			i++;
			j = 0;
		}
		
		removeTheDoor();
	}
	/**
	 * Remove one of the building units as door
	 */
	private void removeTheDoor() {
		Random random = new Random();
		int indexToRemove = (int) (random.nextFloat() * (buildingUnits.size()));
		buildingUnits.remove(indexToRemove);
	}
	/**
	 * Returns the building units to draw
	 * @return
	 */
	public ArrayList<BuildingUnit> getBuildingUnits() {
		return buildingUnits;
	}
}
