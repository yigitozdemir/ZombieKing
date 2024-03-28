package com.yigitozdemir.pathfinding.data;

import com.badlogic.gdx.ai.pfa.Connection;

public class UnitConnection implements Connection<Unit> {
	private float cost;
	private Unit fromUnit;
	private Unit toUnit;

	public UnitConnection(Unit from, Unit to, float cost) {
		this.fromUnit = from;
		this.toUnit = to;
		this.cost = cost;
	}
	@Override
	public float getCost() {
		return cost;
	}

	@Override
	public Unit getFromNode() {
		return fromUnit;
	}

	@Override
	public Unit getToNode() {
		return toUnit;
	}

}