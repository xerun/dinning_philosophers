# Solving Dinning Philosophers using Monitor

There are 4 classes for this problem
1. BaseThread.java:  Customized base class for many of our own threads, it attempts to maintain an automatic unique TID (thread ID) 
among all the derivatives and allow setting your own if needed. Plus some methods for the sync.

2. Philosopher.java: Outlines main subrutines of our virtual philosopher which is actually the 
threads and this class extends the BaseThread class. The philosopher's can eat(), think() and talk(). Only one philosopher at random 
can talk at a time.

3. Monitor.java: To synchronize dining philosophers

4. DiningPhilosophers.java: The main starter 
