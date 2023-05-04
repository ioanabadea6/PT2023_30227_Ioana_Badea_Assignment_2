package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame{
	private JFrame simulareCozi;
	
	private JTextField minArrival;
	private JTextField maxArrival;
	private JTextField minService;
	private JTextField maxService;
	private JTextField noClients;
	private JTextField noQueues;
	private JTextField simulation;
	
	private JButton start;
	private JButton stop;
	private JButton submit;
	
	private JLabel minArrivalTime;
	private JLabel maxArrivalTime;
	private JLabel minServiceTime;
	private JLabel maxServiceTime;
	private JLabel queues;
	private JLabel numberOfClients;
	private JLabel noOfQueues;
	private JLabel simulationInterval;
	private JLabel time;
	
	private JTextArea result;
	private JTextArea generatedClients;
	private JTextArea timeArea;
	
	public SimulationFrame() {
		simulareCozi = new JFrame();
		simulareCozi.setBounds(100, 100, 892, 644);
		simulareCozi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		simulareCozi.getContentPane().setLayout(null);
		
		start = new JButton("START");
		start.setFont(new Font("Tahoma", Font.PLAIN, 19));
		start.setBounds(113, 328, 164, 41);
		simulareCozi.getContentPane().add(start);
		
		stop = new JButton("STOP");
		stop.setFont(new Font("Tahoma", Font.PLAIN, 19));
		stop.setBounds(359, 328, 153, 41);
		simulareCozi.getContentPane().add(stop);
		
		submit = new JButton("SUBMIT");
		submit.setFont(new Font("Tahoma", Font.PLAIN, 19));
		submit.setBounds(588, 328, 164, 41);
		simulareCozi.getContentPane().add(submit);
		
		minArrivalTime = new JLabel("MINIMUM ARRIVAL TIME:");
		minArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		minArrivalTime.setBounds(21, 91, 230, 41);
		simulareCozi.getContentPane().add(minArrivalTime);
		
		maxArrivalTime = new JLabel("MAXIMUM ARRIVAL TIME:");
		maxArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		maxArrivalTime.setBounds(21, 143, 230, 46);
		simulareCozi.getContentPane().add(maxArrivalTime);
		
		minServiceTime = new JLabel("MINIMUM SERVICE TIME:");
		minServiceTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		minServiceTime.setBounds(21, 200, 230, 41);
		simulareCozi.getContentPane().add(minServiceTime);
		
		maxServiceTime = new JLabel("MAXIMUM SERVICE TIME:");
		maxServiceTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		maxServiceTime.setBounds(21, 252, 230, 41);
		simulareCozi.getContentPane().add(maxServiceTime);
		
		minArrival = new JTextField();
		minArrival.setBounds(261, 104, 94, 20);
		simulareCozi.getContentPane().add(minArrival);
		minArrival.setColumns(10);
		
		maxArrival = new JTextField();
		maxArrival.setColumns(10);
		maxArrival.setBounds(261, 159, 94, 20);
		simulareCozi.getContentPane().add(maxArrival);
		
		minService = new JTextField();
		minService.setColumns(10);
		minService.setBounds(261, 213, 94, 20);
		simulareCozi.getContentPane().add(minService);
		
		maxService = new JTextField();
		maxService.setColumns(10);
		maxService.setBounds(261, 265, 94, 20);
		simulareCozi.getContentPane().add(maxService);
		
		queues = new JLabel("QUEUES MANAGEMENT");
		queues.setHorizontalAlignment(SwingConstants.CENTER);
		queues.setFont(new Font("Tahoma", Font.PLAIN, 25));
		queues.setBounds(188, 11, 500, 56);
		simulareCozi.getContentPane().add(queues);
		
		result = new JTextArea();
		
		JScrollPane scrollPane = new JScrollPane(result);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(46, 386, 371, 195);
		simulareCozi.getContentPane().add(scrollPane);
		result.setLineWrap(true);
		
		generatedClients = new JTextArea();
		generatedClients.setLineWrap(true);
		JScrollPane scrollPane1 = new JScrollPane(generatedClients);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane1.setBounds(457, 386, 371, 195);
		simulareCozi.getContentPane().add(scrollPane1);
		
		numberOfClients = new JLabel("NUMBER OF CLIENTS:");
		numberOfClients.setFont(new Font("Tahoma", Font.PLAIN, 18));
		numberOfClients.setBounds(420, 91, 230, 41);
		simulareCozi.getContentPane().add(numberOfClients);
		
		noClients = new JTextField();
		noClients.setColumns(10);
		noClients.setBounds(660, 104, 94, 20);
		simulareCozi.getContentPane().add(noClients);
		
		noOfQueues = new JLabel("NUMBER OF QUEUES:");
		noOfQueues.setFont(new Font("Tahoma", Font.PLAIN, 18));
		noOfQueues.setBounds(420, 146, 230, 41);
		simulareCozi.getContentPane().add(noOfQueues);
		
		noQueues = new JTextField();
		noQueues.setColumns(10);
		noQueues.setBounds(660, 159, 94, 20);
		simulareCozi.getContentPane().add(noQueues);
		
		simulationInterval = new JLabel("SIMULATION INTERVAL:");
		simulationInterval.setFont(new Font("Tahoma", Font.PLAIN, 18));
		simulationInterval.setBounds(420, 200, 230, 41);
		simulareCozi.getContentPane().add(simulationInterval);
			
		simulation = new JTextField();
		simulation.setColumns(10);
		simulation.setBounds(658, 213, 94, 20);
		simulareCozi.getContentPane().add(simulation);
		
		JLabel time = new JLabel("TIME:");
		time.setFont(new Font("Tahoma", Font.PLAIN, 18));
		time.setBounds(420, 252, 230, 41);
		simulareCozi.getContentPane().add(time);
		
		timeArea = new JTextArea();
		timeArea.setBounds(660, 265, 94, 20);
		simulareCozi.getContentPane().add(timeArea);
		
		simulareCozi.setTitle("QUEUES MANAGEMENT");

		simulareCozi.setVisible(true);
	}
	
	public String getMinArrival() {
		return this.minArrival.getText();
	}
	
	public String getMaxArrival() {
		return this.maxArrival.getText();
	}
	
	public String getMinService(){
		return this.minService.getText();
	}
	
	public String getMaxService() {
		return this.maxService.getText();
	}
	
	public String getNoClients() {
		return this.noClients.getText();
	}
	
	public String getNoQueues() {
		return this.noQueues.getText();
	}
	
	public String getSimulation() {
		return this.simulation.getText();
	}

	public String getResult() {
		return result.getText();
	}
	
	public void setResult(String result) {
		this.result.setText(result);
	}
	
	public String getGeneratedClients() {
		return generatedClients.getText();
	}
	
	public void setTimeArea(String timeArea) {
		this.timeArea.setText(timeArea);
	}
	
	public String getTimeArea() {
		return timeArea.getText();
	}
	
	public void setGeneratedClients(String generatedClients) {
		this.generatedClients.append(generatedClients);
	}
	
	public void addStartListener(ActionListener ac) {
		start.addActionListener(ac);
	}
	
	public void addStopListener(ActionListener ac) {
		stop.addActionListener(ac);
	}
	
	public void addSubmitListener(ActionListener ac) {
		submit.addActionListener(ac);
	}
	
	public void cleanAll() {
		minArrival.setText(null);
		maxArrival.setText(null);
		minService.setText(null);
		maxService.setText(null);
		noClients.setText(null);
		noQueues.setText(null);
		simulation.setText(null);
		result.setText(null);
		generatedClients.setText(null);
	}

	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "TRY AGAIN!", JOptionPane.ERROR_MESSAGE);
		//cleanAll();
	}
}
