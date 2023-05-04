package Model;

public class Task {

	private int ID;
	private int arrivalTime;
	private int serviceTime;
	
	public Task() {
		this.ID=ID;
		this.arrivalTime=arrivalTime;
		this.serviceTime=serviceTime;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	
	public String toString() {
		return "ID: " + this.ID + "; ArrivalTime: " + this.arrivalTime + "; ServiceTime: " + this.serviceTime + "\n";
	}
}
