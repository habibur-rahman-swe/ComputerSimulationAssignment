package assignment.one;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import assignment.drawing.LineGraph;

public class SingleServerQueue {
	
	public static int globalClock = 0;
	
	private static Stack<Integer> serviceEnds = new Stack<>();
	private static List<Integer> waitingTimes = new LinkedList<>();
	private static List<Integer> timeSpends = new LinkedList<>();
	private static List<Integer> timesSysemIdle = new LinkedList<>();
	private static int[] xValues, yValues;
	private static List<Service> services;
	
	public static void doService(List<Service> services) {
		for (Service service : services) {
			if (service.serviceId == 1) {
				service.setStartTime(0);
				service.setEndTime(service.getServiceTime());
				
				serviceEnds.add(service.getServiceTime());
				waitingTimes.add(0);
				timeSpends.add(service.getServiceTime());
				timesSysemIdle.add(0);
			} else {
				int startTime = Math.max(service.getArrivalTime(), serviceEnds.peek());
				int endTime = startTime + service.getServiceTime();
				int waitingTime = startTime - service.getArrivalTime();
				int timeSpend = endTime - service.getArrivalTime();
				int systemIdle =  startTime - serviceEnds.peek();
				
				service.setEndTime(endTime);
				service.setStartTime(startTime);
				
				serviceEnds.add(endTime);
				waitingTimes.add(waitingTime);
				timeSpends.add(timeSpend);
				timesSysemIdle.add(systemIdle);
				
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Random random = new Random();
		
//		totalServices
		int numOfServices = 5;
		
		services = new ArrayList<>();
		
		int arrivalTime = 0;
		int serviceTime = 0;
		
		for (int id = 1; id <= numOfServices; id++) {
			serviceTime = random.nextInt(6) + 1;
			services.add(new Service(id, arrivalTime, serviceTime));
			arrivalTime += random.nextInt(8) + 1;
		}
		
		doService(services);
		
		double avgWaitingTime = getTotalWaiting() * 1.0000 / numOfServices;
		double avgServiceTime = getTotalServiceTime(services) * 1.000 / numOfServices;
		double avgServiceInQueue = getTotalServiceInQueue() * 1.000 / serviceEnds.peek();
		double avgIdleTime = getTotalIdleTime() * 1.000 / serviceEnds.peek();

		System.out.println("Total Waiting Time:" + getTotalWaiting());
		System.out.println("Average Waiting Time:" + avgWaitingTime);
		
		System.out.println("\nTotal Service Time: " + serviceEnds.peek());
		System.out.println("Average Service Time: " + avgServiceTime);
		
		System.out.println("\nAverage Service in Queue: " + avgServiceInQueue);
		
		System.out.println("\nTotal Idle Time: " + getTotalIdleTime());
		System.out.println("Average Idle Time: " + avgIdleTime);
		
		LineGraph lg = new LineGraph(xValues, yValues);
		lg.draw();
		
	}

	private static double getTotalServiceInQueue() {
		xValues = new int[serviceEnds.peek() + 2];
		yValues = new int[serviceEnds.peek() + 2];
		
		while (globalClock <= serviceEnds.peek()) {
			for (Service service : services) {
				if (service.arrivalTime <= globalClock &&globalClock <= service.endTime) {
					yValues[globalClock]++;
				}
			}
			
			xValues[globalClock] = globalClock;
			globalClock++;
		}
		
		int total = 0;
		for (int y : yValues) {
			total += y;
		}
		
		return total;
	}

	private static double getTotalIdleTime() {
		double sum = 0;
		for (int x : timesSysemIdle) {
			sum += x;
		}
		
		return sum;
	}

	private static double getTotalServiceTime(List<Service> services) {
		int sum = 0;
		for (Service service : services) {
			sum += service.getServiceTime();
		}
		return sum;
	}

	private static int getTotalWaiting() {
		int sum = 0;
		for (int waiting : waitingTimes) {
			sum += waiting;
		}
		return sum;
	}
}

class Service {
	int serviceId;
	int arrivalTime;
	int serviceTime;
	int startTime;
	int endTime;
	
	public Service(int serviceId, int arrivalTime, int serviceTime) {
		this.serviceId = serviceId;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}
	
	public Service(int arrivalTime, int serviceTime) {
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}
	

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
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

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId + ", arrivalTime=" + arrivalTime + ", serviceTime=" + serviceTime
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
}
