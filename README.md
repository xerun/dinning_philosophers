# Solving Dinning Philosophers using Monitor

Dinning Philosopher problem is a classical problem where all the philosophers sit on a round table with a meal and forks, the number of forks are same as the number of philosophers. Inorder for the philosopher to eat, he/she will need two forks that mean all the philosphers can not eat at the same time.

There are 4 classes for this problem
1. BaseThread.java:  Customized base class for many of our own threads, it attempts to maintain an automatic unique TID (thread ID) 
among all the derivatives and allow setting your own if needed. Plus some methods for the sync.

2. Philosopher.java: Outlines main subrutines of our virtual philosopher which is actually the 
threads and this class extends the BaseThread class. The philosopher's can eat(), think() and talk(). Only one philosopher at random 
can talk at a time.

3. Monitor.java: To synchronize dining philosophers

4. DiningPhilosophers.java: The main starter 

### The solution is both deadlock and starvation free

As we know the philosphers might starve if other philosophers dont let go of the forks, but if they start eating with their hands instead of waiting for other forks to be free they won't starve. So problem solved :D

Jokes apart, I have to heck if my neighboring philosophers picked up the right or the left fork. If any one of the forks is picked up then check if any one of the left or right fork is available. If any one of the left or right fork is available then pick it up if it was not the last philosopher to use that fork before and wait until the other fork is available. 

When it is able to pick up both the forks then it exist from the wait and start eating.

In this way the hungry philosopher will get turn to eat by allowing philosophers who were not the last users of available forks to pick them up while waiting for the other fork to become available.
