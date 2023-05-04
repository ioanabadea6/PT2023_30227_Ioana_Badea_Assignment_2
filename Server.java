package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
	private BlockingQueue<Task> tasks;
	public AtomicInteger waitingPeriod;
	public AtomicInteger waitingPerPerson;
	private boolean open;

	public Server() {
		this.tasks = new ArrayBlockingQueue<Task>(1000);
		this.waitingPeriod = new AtomicInteger(0);
		this.setWaitingPerPerson(new AtomicInteger(0));
		this.setOpen(true);
	}

	public void setWaitingPeriod(AtomicInteger waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	public AtomicInteger getWaitingPeriod() {
		return this.waitingPeriod;
	}

	public void addTask(Task newTask) {
		
		waitingPerPerson.getAndAdd(waitingPeriod.get());
		waitingPeriod.addAndGet(1);
		tasks.add(newTask);
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public synchronized void run() {
		while (isOpen()) {
			//Task task =tasks.peek() ;
			while(tasks.peek() != null) {
				try {
					Thread.sleep(500);
				waitingPeriod.addAndGet(-tasks.peek().getServiceTime());
				tasks.peek().setServiceTime(tasks.peek().getServiceTime()-1);
				//waitingPeriod.getAndDecrement();
				if(tasks.peek().getServiceTime() == 0) {
					tasks.remove(tasks.peek());
				}
				
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Thread.currentThread().interrupt();

				}
			}
			setOpen(false);
		}

	}

	public BlockingQueue<Task> getTasks() {
		return tasks;
	}

	public String toString() {
		String result = "";
		if (tasks.isEmpty() || waitingPeriod.get()==0) {
			return " closed\n";
		} else {
			result += tasks.toString() + "\n";
		}
		return result;
	}

	public AtomicInteger getWaitingPerPerson() {
		return waitingPerPerson;
	}

	public void setWaitingPerPerson(AtomicInteger waitingPerPerson) {
		this.waitingPerPerson = waitingPerPerson;
	}

}
