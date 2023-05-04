package BusinessLogic;

import java.util.ArrayList;
import java.util.List;


import Model.Server;
import Model.Task;

public class Scheduler {
	
	private List<Server> servers;
	private int maxNoServers;
	private int maxTasksPerServer;
	
	private ArrayList<Thread> threads;
	
	
	public Scheduler(int maxNoServers, int maxTasksPerServer) {
		this.servers = new ArrayList<Server>();
		this.threads = new ArrayList<Thread>();
		this.setMaxNoServers(maxNoServers);
		this.setMaxTasksPerServer(maxTasksPerServer);
		for(int i=0; i<maxNoServers; i++) {
			Server server = new Server();
			servers.add(server);
			server.setOpen(false);
			Thread thread = new Thread(server, "Q" + (i+1));
			threads.add(thread);
			threads.get(i).start();		
		}
	}
	
	public int getMaxNoServers() {
		return maxNoServers;
	}

	public void setMaxNoServers(int maxNoServers) {
		this.maxNoServers = maxNoServers;
	}

	public int getMaxTasksPerServer() {
		return maxTasksPerServer;
	}

	public void setMaxTasksPerServer(int maxTasksPerServer) {
		this.maxTasksPerServer = maxTasksPerServer;
	}
	
	public void dispatchTask(Task t) {
		int minTime = 9999;
		int queue = 0;
		for(int i = 0; i< servers.size(); i++) {
			if(servers.get(i).getWaitingPeriod().intValue() < minTime) {
				minTime = servers.get(i).getWaitingPeriod().intValue();
				queue = i;
				
			}
		}
		servers.get(queue).getWaitingPeriod().getAndAdd(t.getServiceTime());
		servers.get(queue).setOpen(true);
		servers.get(queue).addTask(t);
		Thread thread = new Thread(servers.get(queue), "Q" + (queue+1));
		threads.add(thread);
		thread.start();	
	}
	
	public List<Server> getServers(){
		return servers;
	}

	public ArrayList<Thread> getThreads() {
		return threads;
	}

	public void setThreads(ArrayList<Thread> threads) {
		this.threads = threads;
	}
	
	public boolean emptyServers() {
		for(Server s: servers) {
			if(!s.getTasks().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public void stopThreads() {
		for(Server s:servers) {
			s.setOpen(false);
		}
	}
	
	public int getWaitingPerPerson() {
		int waiting=0;
		for(Server s: servers) {
			waiting+=s.waitingPerPerson.get();
		}
		return waiting;
	}
	

	
}
