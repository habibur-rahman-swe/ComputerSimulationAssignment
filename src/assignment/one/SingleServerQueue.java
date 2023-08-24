package assignment.one;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import assignment.drawing.LineGraph;

public class SingleServerQueue {
	private static Random random = new Random();
	
	private static int numberOfThreads = 10;
	
	private static int clock;
	private static List<Integer> inter_arrival_time = new LinkedList<>();
	private static List<Integer> arrival_time = new LinkedList<>();
	private static List<Integer> service_time = new LinkedList<>();
	private static List<Integer> queue_size = new LinkedList<>();
	private static List<Integer> service_begin = new LinkedList<>();
	private static List<Integer> service_end = new LinkedList<>();
	private static Queue<Service> queue = new LinkedList<>();
	
	public static void main(String[] args) throws InterruptedException {
        List<Thread> allThreads = new ArrayList<>();
        queue_size.add(0);
        
        for (int i = 0; i < numberOfThreads; i++) {
			var t = new Thread(()-> {
				SingleServerQueue.completeTask(new Service(random.nextInt(5) + 1, random.nextInt(10) + 1));
			});
			allThreads.add(t);
			t.start();
		}
        
        for (Thread thread : allThreads) {
			thread.join();
		}
        
        System.out.println(clock);
        System.out.println(queue_size.size());
        
        LineGraph lg = new LineGraph(queue_size);
        lg.draw();
    }
	
	public static void completeTask(Service s) {
		System.out.println(Thread.currentThread().getId() + " interArrivalTime: " + s.interArrivalTime + " serviceTime: " + s.serviceTime);
		queue.add(s);
		
		if (arrival_time.size() == 0) {
			arrival_time.add(0);
			service_begin.add(0);
			service_end.add(service_begin.get(service_begin.size() - 1) + s.serviceTime);
		} else {
			arrival_time.add(arrival_time.get(arrival_time.size() - 1) + s.interArrivalTime);
			int serviceTimeBegin = Math.max(arrival_time.get(arrival_time.size() - 1), service_end.get(service_end.size() - 1));
			service_begin.add(serviceTimeBegin);
			int serviceTimeEnd = serviceTimeBegin + s.serviceTime;
			service_end.add(serviceTimeEnd);
		}
		
		increment(service_end.get(service_end.size() - 1));
	}
	
	public synchronized static void increment(int serviceEnd) {
		while (clock < serviceEnd) {
			++clock;
			queue_size.add(queue.size());
		}
		queue.poll();
	}
}

class Service {
	int interArrivalTime;
	int serviceTime;
	
	Service(int interArrivalTime, int serviceTime) {
		this.interArrivalTime = interArrivalTime;
		this.serviceTime = serviceTime;
	}
}
