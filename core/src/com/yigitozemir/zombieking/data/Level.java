package com.yigitozemir.zombieking.data;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.yigitozdemir.pathfinding.data.Unit;
import com.yigitozdemir.pathfinding.data.UnitGraph;
import com.yigitozemir.zombieking.npc.Human;
import com.yigitozemir.zombieking.npc.Zombie;

public class Level {
	private int MAP_WIDTH = 30;
	private int MAP_HEIGHT = 30;
	private final int TILE_SIZE = 16;
	/**
	 * background texture region
	 */
	private TextureRegion backgroundTextureRegion;
	/**
	 * main camera of the game
	 */
	private OrthographicCamera camera;
	/**
	 * List of zombies
	 */
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	/**
	 * List of humans
	 */
	private ArrayList<Human> humans = new ArrayList<Human>();
	/**
	 * List of building
	 */
	private ArrayList<Building> buildings = new ArrayList<>();
	
	/**
	 * Debug purpose shape renderer
	 */
	private ShapeRenderer shapeRenderer;
	
	/**
	 * boolean map for path finding
	 */
	private int[][] walkableMap;
	
	/**
	 * Unit graph for path finding
	 */
	UnitGraph unitGraph = new UnitGraph();
	GraphPath<Unit> path;
	
	public Level() {
		FileHandle fileHandle = Gdx.files.internal("Spritesheet.png");
		Texture texture = new Texture(fileHandle);
		backgroundTextureRegion = new TextureRegion(texture, 0, 0, TILE_SIZE, TILE_SIZE);
		
		//camera = new OrthographicCamera(30, 30 * (h / w));
		camera = new OrthographicCamera(30, 30);
		
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		
		zombies.add(new Zombie(20, 25));
		
		// TODO: debug purposes, remove it
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		
		Building b1 = new Building(30, 30, 3);
		Building b2 = new Building(60, 60, 4);
		buildings.add(b1);
		buildings.add(b2);
		
		generateWalkableMap();
		System.out.println("walkable map generated");
		unitGraph.createConnections(walkableMap);
		System.out.println("connection created");
		path = unitGraph.getPath(unitGraph.getUnitByCoordinate(20, 20), unitGraph.getUnitByCoordinate(75, 75));
		System.out.println("Path created");
	}
	
	public void renderLevel(float delta, SpriteBatch spriteBatch) {
		handleCemeraInput();
		camera.update();
		
		spriteBatch.setProjectionMatrix(camera.combined);
		
		spriteBatch.begin();
		renderBg(spriteBatch);
		renderZombies(spriteBatch);
		renderHumans(spriteBatch);
		renderBuildings(spriteBatch);
		spriteBatch.end();
		
		debugBuildingUnits();
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Filled);
		
		Iterator<Unit> iterator = path.iterator();
		Vector2 start = null;
		
		while(iterator.hasNext()) {
			Unit u = iterator.next();
			if(start == null) {
				start = new Vector2(u.getX(), u.getY());
			}
			else {
				shapeRenderer.rectLine(start, new Vector2(u.getX(), u.getY()), 2);
				start = new Vector2(u.getX(), u.getY());
			}
			
		}
		
		shapeRenderer.end();
	}
	
	/**
	 * debug shape renderer for building units
	 */
	private void debugBuildingUnits() {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.begin();
		
		for(Building building : buildings) {
			for(BuildingUnit buildingUnit : building.getBuildingUnits()) {
				shapeRenderer.polygon(buildingUnit.getBuildingUnitPolygon().getVertices());
			}
		}
		shapeRenderer.end();
	}
	
	/**
	 * Render buildings
	 * @param spriteBatch
	 */
	private void renderBuildings(SpriteBatch spriteBatch) {
		for(Building building : buildings) {
			for(BuildingUnit buildingUnit : building.getBuildingUnits()) {
				spriteBatch.draw(buildingUnit.getTextureRegion(), buildingUnit.getX(), buildingUnit.getY());
			}
		}
	}
	
	private void handleCemeraInput() {
		// zoom out
		if(Gdx.input.isKeyPressed(Keys.Q)) {
			if(camera.zoom < 10f) {
				camera.zoom += 0.05;
			}
		}
		// zoom in
		if(Gdx.input.isKeyPressed(Keys.E)) {
			if (camera.zoom > 0.1) {
				camera.zoom -= 0.05;
			}
		}
		// move camera left
		if(Gdx.input.isKeyPressed(Keys.A)) {
			camera.position.x -= 2;
		}
		// move camera right
		if(Gdx.input.isKeyPressed(Keys.D)) {
			camera.position.x += 2;
		}
		// move camera up
		if(Gdx.input.isKeyPressed(Keys.W)) {
			camera.position.y += 2;
		}
		// move camera down
		if(Gdx.input.isKeyPressed(Keys.S)) {
			camera.position.y -= 2;
		}
	}
	
	/**
	 * this method renders the green grasses in the background
	 * @param spriteBatch
	 */
	private void renderBg(SpriteBatch spriteBatch) {
		int i = 0, j = 0;
		
		for(;i < MAP_WIDTH; i++) {
			for(; j < MAP_HEIGHT; j++) {
				spriteBatch.draw(backgroundTextureRegion, i * TILE_SIZE, j * TILE_SIZE);
			}
			j = 0;
		}
	}
	
	/**
	 * This method renders the zombies
	 * @param spriteBatch
	 */
	private void renderZombies(SpriteBatch spriteBatch) {
		for (Zombie zombie : zombies) {
			spriteBatch.draw(zombie.getTextureRegion(), zombie.getX(), zombie.getY());
		}
	}
	/**
	 * this method renders the humans
	 * @param spriteBatch
	 */
	private void renderHumans(SpriteBatch spriteBatch) {
		for(Human human : humans) {
			spriteBatch.draw(human.getTextureRegion(), human.getX(), human.getY());
		}
	}
	
	
	
	/**
	 * generate walkable map
	 */
	private void generateWalkableMap() {
		int regular = 1;
		int blocking = 1000;
		walkableMap = new int[MAP_WIDTH * TILE_SIZE][MAP_HEIGHT * TILE_SIZE];
		for(int i = 0; i < MAP_WIDTH * TILE_SIZE; i++) {
			for(int j = 0; j < MAP_HEIGHT *  TILE_SIZE; j++) {
				walkableMap[i][j] = regular;
			}
		}
		
		for(Building building: buildings) {
			for(BuildingUnit unit: building.getBuildingUnits()) {
				switch (unit.getBuildingUnitShape()) {
				case BOTTOM:
					walkableMap[unit.getX()][unit.getY()] = blocking;
					walkableMap[unit.getX() + 1][unit.getY()] = blocking;
					walkableMap[unit.getX() + 2][unit.getY()] = blocking;
					walkableMap[unit.getX() + 3][unit.getY()] = blocking;
					walkableMap[unit.getX() + 4][unit.getY()] = blocking;
					walkableMap[unit.getX() + 5][unit.getY()] = blocking;
					walkableMap[unit.getX() + 6][unit.getY()] = blocking;
					walkableMap[unit.getX() + 7][unit.getY()] = blocking;
					walkableMap[unit.getX()][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 8][unit.getY()] = blocking;
					walkableMap[unit.getX() + 8][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 9][unit.getY()] = blocking;
					walkableMap[unit.getX() + 9][unit.getY() + 1] = blocking;
					break;
				case TOP:
					walkableMap[unit.getX()][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 6] = blocking;
					walkableMap[unit.getX()][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 8][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 8][unit.getY() + 6] = blocking;
					break;
				case LEFT:
					walkableMap[unit.getX()][unit.getY()] = blocking;
					walkableMap[unit.getX()][unit.getY() + 1] = blocking;
					walkableMap[unit.getX()][unit.getY() + 2] = blocking;
					walkableMap[unit.getX()][unit.getY() + 3] = blocking;
					walkableMap[unit.getX()][unit.getY() + 4] = blocking;
					walkableMap[unit.getX()][unit.getY() + 5] = blocking;
					walkableMap[unit.getX()][unit.getY() + 6] = blocking;
					walkableMap[unit.getX()][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 1][unit.getY()] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 7] = blocking;
					walkableMap[unit.getX()][unit.getY() + 8] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 8] = blocking;
					break;
				case RIGHT:
					walkableMap[unit.getX() + 6][unit.getY()] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 7][unit.getY()] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 7] = blocking;
					break;
				case TOPLEFT:
					walkableMap[unit.getX()][unit.getY()] = blocking;
					walkableMap[unit.getX()][unit.getY() + 1] = blocking;
					walkableMap[unit.getX()][unit.getY() + 2] = blocking;
					walkableMap[unit.getX()][unit.getY() + 3] = blocking;
					walkableMap[unit.getX()][unit.getY() + 4] = blocking;
					walkableMap[unit.getX()][unit.getY() + 5] = blocking;
					walkableMap[unit.getX()][unit.getY() + 6] = blocking;
					walkableMap[unit.getX()][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 1][unit.getY()] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 7] = blocking;
					walkableMap[unit.getX()][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 6] = blocking;
					walkableMap[unit.getX()][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 7] = blocking;
					break;
				case TOPRIGHT:
					walkableMap[unit.getX()][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 6] = blocking;
					walkableMap[unit.getX()][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 6][unit.getY()] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 7][unit.getY()] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 7] = blocking;
					break;
				case BOTTOMLEFT:
					walkableMap[unit.getX()][unit.getY()] = blocking;
					walkableMap[unit.getX() + 1][unit.getY()] = blocking;
					walkableMap[unit.getX() + 2][unit.getY()] = blocking;
					walkableMap[unit.getX() + 3][unit.getY()] = blocking;
					walkableMap[unit.getX() + 4][unit.getY()] = blocking;
					walkableMap[unit.getX() + 5][unit.getY()] = blocking;
					walkableMap[unit.getX() + 6][unit.getY()] = blocking;
					walkableMap[unit.getX() + 7][unit.getY()] = blocking;
					walkableMap[unit.getX()][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 1] = blocking;
					walkableMap[unit.getX()][unit.getY()] = blocking;
					walkableMap[unit.getX()][unit.getY() + 1] = blocking;
					walkableMap[unit.getX()][unit.getY() + 2] = blocking;
					walkableMap[unit.getX()][unit.getY() + 3] = blocking;
					walkableMap[unit.getX()][unit.getY() + 4] = blocking;
					walkableMap[unit.getX()][unit.getY() + 5] = blocking;
					walkableMap[unit.getX()][unit.getY() + 6] = blocking;
					walkableMap[unit.getX()][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 1][unit.getY()] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 7] = blocking;
					break;
				case BOTTOMRIGHT:
					walkableMap[unit.getX() + 6][unit.getY()] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 7] = blocking;
					walkableMap[unit.getX() + 7][unit.getY()] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 2] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 3] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 4] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 5] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 6] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 7] = blocking;
					walkableMap[unit.getX()][unit.getY()] = blocking;
					walkableMap[unit.getX() + 1][unit.getY()] = blocking;
					walkableMap[unit.getX() + 2][unit.getY()] = blocking;
					walkableMap[unit.getX() + 3][unit.getY()] = blocking;
					walkableMap[unit.getX() + 4][unit.getY()] = blocking;
					walkableMap[unit.getX() + 5][unit.getY()] = blocking;
					walkableMap[unit.getX() + 6][unit.getY()] = blocking;
					walkableMap[unit.getX() + 7][unit.getY()] = blocking;
					walkableMap[unit.getX()][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 1][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 2][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 3][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 4][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 5][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 6][unit.getY() + 1] = blocking;
					walkableMap[unit.getX() + 7][unit.getY() + 1] = blocking;
					break;
				}
			}
		}
	}
	
}
