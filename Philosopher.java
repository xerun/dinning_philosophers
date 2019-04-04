package A3;
import java.util.Random;

import A3.BaseThread;

public class Philosopher extends BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)   
	 */
	public static final long TIME_TO_WASTE = 1000; 

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating. 
	 * - Then sleep() for a random interval.
	 * - The print that they are done eating.
	 */
	public void eat()
	{
		try
		{
			// start code
			System.out.println("Philosopher " + iTID + " has started eating");
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE)); // already was given
			yield();
			System.out.println("Philosopher " + iTID + " has finished eating");
			// end code
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - Then sleep() for a random interval.
	 * - The print that they are done thinking.
	 */
	public void think()
	{
		// start code
		try
		{
			System.out.println("Philosopher " + iTID + " has started thinking");
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("Philosopher " + iTID + " has finished thinking");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.thinking():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
		// end code
	}

	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - Say something brilliant at random
	 * - The print that they are done talking.
	 */
	public void talk()
	{
		// start code
		try
		{
			System.out.println("Philosopher " + iTID + " has started talking");
			yield();
			saySomething(); // already was given
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("Philosopher " + iTID + " has finished talking");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.talking():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
		// end code
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run()
	{
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{
			//System.out.println(getTID()+" trying to pickup --------------------------------------------");
			DiningPhilosophers.soMonitor.pickUp(getTID());
			eat();
			DiningPhilosophers.soMonitor.putDown(getTID());
			//System.out.println(getTID()+" finished pickup --------------------------------------------");
			think();
			/*
			 * TODO:
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
			Random random = new Random();
			if(random.nextInt(2) == 1) // A random decision 
			{
				// star monitor operation code
				DiningPhilosophers.soMonitor.requestTalk(); // request for talking so that only one philosopher can talk
				talk(); // already was given	
				DiningPhilosophers.soMonitor.endTalk(); // end talking
				// end code
			}
			yield(); // indicates that the thread is not doing anything particularly 
			// important and if any other threads or processes need to be run, they can. 
			// Otherwise, the current thread will continue to run.
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// EOF
