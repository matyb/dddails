package com.sandwich.shared;


public class DoTimes implements Runnable {

	private final int numberOfTimes;
	private final Runnable runnable;
	
	public DoTimes(int numberOfTimes, Runnable runnable){
		if(numberOfTimes < 0){
			throw new IllegalArgumentException("\nnumber must be zero or greater.");
		}
		this.numberOfTimes = numberOfTimes;
		this.runnable = runnable;
	}
	
	public void run() {
		for(int i = 0; i < numberOfTimes; i++){
			runnable.run();
		}
	}
	
}
