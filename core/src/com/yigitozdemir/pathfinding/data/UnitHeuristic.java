package com.yigitozdemir.pathfinding.data;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

/**
 * taken from: https://happycoding.io/tutorials/libgdx/pathfinding#rooms
 */
public class UnitHeuristic implements Heuristic<Unit> {

	@Override
	public float estimate(Unit node, Unit endNode) {
		return Vector2.dst(node.getX(), node.getY(), endNode.getX(), endNode.getY());
	}

}