package engine.classes;

import org.lwjgl.Sys;

public class Timer {
	
	int startTicks, pausedTicks;
	boolean paused, started;
	
	public Timer() {
		startTicks = 0;
		pausedTicks = 0;
		paused = false;
		started = false;
	}

	public void start() {
		started = true;
		paused = false;
		startTicks = (int) Sys.getTime();
	}

	public void stop()
	{
		started = false;
		paused = false;
		startTicks = 0;
	}

	public void pause()
	{
		if ( (started == true) && (paused == false) )
		{
			paused = true;
			pausedTicks = (int) (Sys.getTime() - startTicks);
		}
	}

	public void unpause()
	{
		if ( (started == true) && (paused == true) )
		{
			paused = false;
			startTicks = (int) (Sys.getTime() - pausedTicks);
			pausedTicks = 0;
		}
	}

	public int getTicks()
	{
		if(started == true)
		{
			if(paused == true)
			{
				return pausedTicks;
			}
			else
			{
				return (int) (Sys.getTime() - startTicks);
			}
		}
		return 0;
	}	
	
}