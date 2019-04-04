package A3;
public class Monitor   
{
	/*
	 * ------------    
	 * Data members 
	 * ------------
	 */
	//forks around the table
	Fork[] forks;
	//number of philosophers
	int noP;
	boolean someonetalking;

	private class Fork
	{
		public boolean pickedUp;
		public int lastPickedUpBy;
		
		public Fork()
		{
			pickedUp = false;
			lastPickedUpBy = 0;
		}
		
		/**
		 * Determines whether a fork was last picked up by a philosopher
		 * @param piTID Thread ID of the philosopher
		 * @return Whether the philosopher last picked by the fork
		 */
		public boolean lastPickedUpByMe(final int piTID)
		{
			return lastPickedUpBy == piTID;
		}
		
		/**
		 * Determines whether a fork is currently picked up by another philosopher 
		 * @param piTID Thread ID of the philosopher
		 * @return True if the fork is picked up and the philosopher ID does not match
		 */
		public boolean pickedUpByAnother(final int piTID)
		{
			return lastPickedUpBy != piTID && pickedUp;
		}
		
		/**
		 * Pick up a fork and set the new philosopher
		 * @param piTID  Thread ID of the philosopher
		 */
		public void pickUp(final int piTID)
		{
			pickedUp = true;
			lastPickedUpBy = piTID;
		}
		
		/**
		 * Put a fork down
		 */
		public void putDown()
		{
			pickedUp = false;
		}
	}
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of forks based on the # of philosophers
		noP = piNumberOfPhilosophers;
		forks = new Fork[piNumberOfPhilosophers];
		
		//initialize all the forks
		for(int i = 0; i < forks.length; i++)
		{
			forks[i] = new Fork();
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		//fork id of a corresponding philosopher Thread ID
		int pid = piTID-1;
		while(true)
		{
			// Check if my neighboring philosophers picked up the right or the left fork. 
			// If any one of the forks is picked up then check if any one of the left or 
			// right fork is available. If any one of the left or right fork is available 
			// then pick it up if it was not the last philosopher to use that fork before
			// and wait until the other fork is available. 
			// When it is able to pick up both the forks then I exist from the wait and start eating.
			// In this way the hungry philosopher will get turn to eat by allowing philosophers 
			// who were not the last users of available forks to pick them up while waiting for 
			// the other fork to become available.
			
			if(forks[pid].pickedUpByAnother(piTID) || forks[(pid + 1) % noP].pickedUpByAnother(piTID)) 
			// check if any of the neighboring philosophers has picked up the forks
			{
				
				if(!forks[pid].pickedUp && !forks[pid].lastPickedUpByMe(piTID)) 
				// if fork on the left not picked up and it was not picked up
				// by current philosopher
				{
					//fork on the left
					forks[pid].pickUp(piTID);
				} 
				else if(!forks[(pid + 1) % noP].pickedUp && !forks[(pid + 1) % noP].lastPickedUpByMe(piTID))
				// if fork on the right not picked up and it was not picked up
				// by current philosopher
				{
					//fork on the right
					forks[(pid + 1) % noP].pickUp(piTID);
				}
			} 
			else
			{
				//both forks are available to you, pick them up (even though you may already have them) and leave the loop
				forks[pid].pickUp(piTID);
				forks[(pid + 1) % noP].pickUp(piTID);
				break; // exit the wait and go into eating
			}
			
			try {
				wait(); // goes to wait if none of the forks or only one fork is available
			} catch (InterruptedException e) {
				System.err.println("Monitor.pickUp():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		//put down forks
		forks[piTID - 1].putDown();
		forks[(piTID) % noP].putDown();
		notifyAll();
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while he/she is not eating).
	 */
	public synchronized void requestTalk()
	{
		//wait while someone is talking
		while(someonetalking) 
		{
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Monitor.requestTalk():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}
		
		//requester is now talking
		someonetalking = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		//requester is no longer talking
		someonetalking = false;
		notifyAll();
	}
}

// EOF
