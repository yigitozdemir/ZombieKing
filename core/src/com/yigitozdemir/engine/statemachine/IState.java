package com.yigitozdemir.engine.statemachine;

public interface IState {
	
	public void changeState(String name);
	public String getStateName();
	public void startState();
	public void act(float delta);
	public void endState();
	public void dispose();
	public void setStateMachine(StateMachine stateMachine);
	public StateMachine getStateMachine();
}
