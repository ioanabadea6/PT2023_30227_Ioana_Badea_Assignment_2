package BusinessLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import BusinessLogic.SimulationManager.startListener;
import BusinessLogic.SimulationManager.stopListener;
import BusinessLogic.SimulationManager.submitListener;
import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

public class SimulationManager implements Runnable{
	public int timeLimit;
	public int maxProcessingTime;
	public int minProcessingTime;
	public int numberOfServers;
	public int numberOfClients;
	public int maxArrivalTime;
	public int minArrivalTime;

	private Scheduler scheduler;
	private List<Task> generatedTasks;
	private SimulationFrame frame;
	
	//public static int currentTime = 0;

	private StringBuilder file;

	public SimulationManager(SimulationFrame frame) {
		scheduler = new Scheduler(numberOfServers, numberOfClients);
		generatedTasks = new ArrayList<Task>(numberOfClients);
		this.file = new StringBuilder();
		
		this.frame=frame;

	//	frame = new SimulationFrame();

		this.frame.addStartListener(new startListener());
		this.frame.addStopListener(new stopListener());
		this.frame.addSubmitListener(new submitListener());
	}
	
	public static Comparator<Task> comparator = new Comparator<Task>() {
		public int compare(Task t1,Task t2){return t1.getArrivalTime()-t2.getArrivalTime();}};

	public void generateNRandomTasks() {
		Random random = new Random();
		for (int i = 0; i < numberOfClients; i++) {
			Task task = new Task();
			task.setID(random.nextInt(100));
			task.setArrivalTime(random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime);
			task.setServiceTime(random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime);
			generatedTasks.add(task);
		}
		for (int i = 0; i < numberOfClients - 1; i++) {
			Collections.sort(generatedTasks, SimulationManager.comparator);
		}

	}
	
	
	public int peakTime() {
		int size1 = 0;
		for (Server server:scheduler.getServers()) {
			size1 += server.getTasks().size();
		}
		return size1;
	}
	
	public static void writeFile(String s) {
		try {
			FileWriter fw = new FileWriter("result4.txt");
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() 	{
		scheduler = new Scheduler(numberOfServers, numberOfClients);
		int currentTime = 0;
		
		int peakHour = 0;
		int serviceTime = 0;
		int second=0;
		int numberOfClients2=0;
		
		
		
		while(currentTime < timeLimit && (!(generatedTasks.isEmpty()) || scheduler.emptyServers())){
			for(Server server: scheduler.getServers()) {
				for(Task task: server.getTasks()) {
					task.setServiceTime(task.getServiceTime()-1);
					if(task.getServiceTime()==0) {
						server.getTasks().remove(task);
					}
				}
			}
			
			
			currentTime++;
			
			while (!generatedTasks.isEmpty() && generatedTasks.get(0).getArrivalTime() == currentTime) {
				scheduler.dispatchTask(generatedTasks.get(0));
				if(generatedTasks.get(0).getServiceTime()+currentTime<timeLimit) {
					serviceTime+=generatedTasks.get(0).getServiceTime();
					numberOfClients2++;
				}
				generatedTasks.remove(0);
				
				
			}
			
			
						
			String s = "";
			s += "\n";
			s += "Current time: " + currentTime + "\n";
			s += "Waiting clients:\n";
			
			file.append("\n");
			file.append("Current time: " + currentTime + "\n");
			file.append("Waiting clients:\n");

			for (int i = 0; i < generatedTasks.size(); i++) {
				s += generatedTasks.get(i).toString() + " ";
				file.append(generatedTasks.get(i).toString());
			}
			
			for (int j = 0; j < scheduler.getServers().size(); j++) {
				s += "Queue" + j + ": ";
				s += scheduler.getServers().get(j);
				
				file.append("Queue" + j + ": ");
				file.append(scheduler.getServers().get(j));		
			}
			
			
			
			frame.setGeneratedClients(s);
			System.out.println(s);
			
			
			if(peakHour<peakTime()) {
				peakHour=peakTime();
				second=currentTime;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

//		String r = "";
//		r += "\n";
//		r += "Waiting clients:\n";
//		
//		file.append("\n");
//		file.append("Waiting clients:\n");
//
//		for (int i = 0; i < generatedTasks.size(); i++) {
//			r += generatedTasks.get(i).toString() + " ";
//			file.append(generatedTasks.get(i).toString());
//		}
//		
//		for (int j = 0; j < scheduler.getServers().size(); j++) {
//			r += "Queue" + j + ": ";
//			r += scheduler.getServers().get(j);
//			
//			file.append("Queue" + j + ": ");
//			file.append(scheduler.getServers().get(j));		
//		}
//		System.out.println(r);
//		frame.setGeneratedClients(r);
		
		scheduler.stopThreads();
		
		for (Thread t : scheduler.getThreads()) {
			t.interrupt();
		}
		
		float wait=Math.abs(scheduler.getWaitingPerPerson());
		file.append("\nAverage waitingTime="+ (float)wait/numberOfClients +"\n");
		System.out.println("Average waitingTime="+ (float)wait/numberOfClients +"\n");
		frame.setGeneratedClients("Average waitingTime="+ (float)wait/numberOfClients +"\n");
		
		file.append("Average serviceTime="+ (float)serviceTime/numberOfClients2 +"\n");
		System.out.println("Average serviceTime="+ (float)serviceTime/numberOfClients2 +"\n");
		frame.setGeneratedClients("Average serviceTime="+ (float)serviceTime/numberOfClients2 +"\n");
		
		file.append("PeakHour=" + second + "\n");
		System.out.println("PeakHour=" + second + "\n");
		frame.setGeneratedClients("PeakHour=" + second + "\n");
		writeFile(file.toString());
	}
	

	
	class startListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				frame.setTimeArea("0");

				String noClients = frame.getNoClients();
				String noQueues = frame.getNoQueues();
				String simulationInterval = frame.getSimulation();
				String minArrivalTime1 = frame.getMinArrival();
				String maxArrivalTime1 = frame.getMaxArrival();
				String minServiceTime = frame.getMinService();
				String maxServiceTime = frame.getMaxService();

				timeLimit = Integer.parseInt(simulationInterval);
				maxProcessingTime = Integer.parseInt(maxServiceTime);
				minProcessingTime = Integer.parseInt(minServiceTime);
				numberOfServers = Integer.parseInt(noQueues);
				numberOfClients = Integer.parseInt(noClients);
				maxArrivalTime = Integer.parseInt(maxArrivalTime1);
				minArrivalTime = Integer.parseInt(minArrivalTime1);

				if (maxProcessingTime < minProcessingTime || maxArrivalTime < minArrivalTime) {
					frame.showErrorMessage("WRONG INPUT!");
				}

				generateNRandomTasks();

				String result = "";
				for (int i = 0; i < numberOfClients; i++) {
					result += generatedTasks.get(i).toString();
				}
	
				frame.setResult(result);
				
				Thread t = new Thread();
				t.start();
				
			} catch (Exception exception) {
				frame.showErrorMessage("WRONG INPUT!");
			}

		}
	}
	
	class stopListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				frame.cleanAll();
			} catch (Exception exception) {
				frame.showErrorMessage("WRONG INPUT");
			}

		}
	}

	class submitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
			//	run();
			} catch (Exception exception) {
				frame.showErrorMessage("WRONG!");
			}
		}
	}

	public static void main(String[] args) {
		SimulationFrame frame = new SimulationFrame();
		
		SimulationManager sim = new SimulationManager(frame);
		Thread t = new Thread(sim);
		try {
			t.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.start();


	}
}