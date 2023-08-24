package assignment.one;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class SingleServerQueue {
	private static Random random = new Random();
	
	private static int numberOfThreads = 10;
	
	private static int clock;
	private static List<Integer> inter_arrival_time = new LinkedList<>();
	private static List<Integer> arrival_time = new LinkedList<>();
	private static List<Integer> service_time = new LinkedList<>();
	private static List<Integer> quee_size = new LinkedList<>();
	private static List<Integer> service_begin = new LinkedList<>();
	private static List<Integer> service_end = new LinkedList<>();
	
	public static void main(String[] args) throws InterruptedException {
        List<Thread> allThreads = new ArrayList<>();
        
        
        for (int i = 0; i < numberOfThreads; i++) {
			var t = new Thread(()-> {
				SingleServerQueue.completeTask(random.nextInt(9) + 1, random.nextInt(9) + 1);
			});
			allThreads.add(t);
			t.start();
		}
        
        for (Thread thread : allThreads) {
			thread.join();
		}
        
        System.out.println(clock);
    }
	
	public static void completeTask(int interArrivalTime, int serviceTime) {
		System.out.println(Thread.currentThread().getId() + " interArrivalTime: " + interArrivalTime + " serviceTime: " + serviceTime);
		increment();
	}
	
	public synchronized static void increment() {
		++clock;
	}
}
