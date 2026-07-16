package com.dunno;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {

	private static final int NUMBER_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;
	private Thread animator;
	private long period = 16 * 1_000_000L; // Represents the time for each frame. In this case 60 FPS

	private static final int PANEL_WIDTH = 500;
	private static final int PANEL_HEIGHT = 400;

	private volatile boolean running;
	private volatile boolean gameOver;
	private volatile boolean isPaused;

	public GamePanel() {
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

		setFocusable(true);
		requestFocus();
	}

	@Override
	public void addNotify() {
		
		super.addNotify();
		startGame();
	}

	private void startGame() {
		
		if(animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void stopGame() {

		running = false;
	}

	public void pauseGame() {
		
		isPaused = true;
	}

	public void resumeGame() {

		isPaused = false;
	}

	@Override
	public void run(){
		
		long initialTime, finalTime, elapsedTime, sleepTime;
		long overSleepTime = 0L;
		int numberDelays = 0;
		long excess = 0L;

		initialTime = System.nanoTime();

		running = true;

		while(running) {
			
			// TODO: Update game state
			// TODO: Render game objects
			// TODO: Paint objects

			System.out.println("Game is running!");
			
			finalTime = System.nanoTime();
			elapsedTime = finalTime - initialTime;
			sleepTime = (period - elapsedTime) - overSleepTime;

			if(sleepTime > 0) {

				try {
					
					Thread.sleep(sleepTime/1_000_000L);
				} catch(InterruptedException e) {
					
					throw new RuntimeException(e);
				}

				overSleepTime = (System.nanoTime() - finalTime) - sleepTime;
			} else {

				excess -= sleepTime;
				overSleepTime = 0L;

				if(++numberDelays >= NUMBER_DELAYS_PER_YIELD) {

					Thread.yield();
					numberDelays = 0;
				}
			}

			initialTime = System.nanoTime();

			int skips = 0;

			while((excess > period) && (skips < MAX_FRAME_SKIPS)) {

				excess -= period;
				// TODO: update game state 
				skips++;
			}
		}

		System.exit(0);
	}
}
