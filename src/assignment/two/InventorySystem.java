package assignment.two;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import assignment.drawing.LineGraph;

public class InventorySystem {
	
	static Random random = new Random();
	
	static List<Integer> demands = new ArrayList<>();
	static List<Integer> beginnings = new ArrayList<>();
	static List<Integer> endings = new ArrayList<Integer>();
	static List<Integer> shortages = new ArrayList<Integer>();
	static List<Integer> orders = new ArrayList<Integer>();
	static List<Integer> untillOderArrive = new ArrayList<>();
	
	static int maxSize = 31;
	static int minSize = 10;
	static int orderDays = getRandom(2);
	
	static int[] xValues = new int[31];
	static int[] yValues = new int[31];
	
	public static void main(String[] args) {
		
		int n = 25;
		int orderIdx = -1;
		
		for (int i = 0; i < n; i++) {
			
			int days = 0, begin = 0, demand = 0, ending = 0, shortage = 0, order = 0;
			
			if (i == 0) {
				begin = getRandom(minSize);
			} else {
				begin = endings.get(i-1);
			}
			
			// if had shortage
			shortage = i == 0 ? 0 : shortages.get(i - 1);
			
			// if had pending order
			if (i > 0 && orderIdx != -1 && untillOderArrive.get(i-1) == 0) {
				begin += orders.get(orderIdx);
				orderIdx = -1;
			}
			
			if (begin >= shortage && begin >= minSize) {
				begin -= shortage;
				shortage = 0;
			}
			
			// demand
			demand = getRandom(3);
			
			// endings
			if (begin - demand >= 0 && begin >= minSize) {
				ending = begin - demand;
			} else {
				ending = begin;
				shortage += demand;
			}
			
			
			// do order
			if (ending < minSize && orderIdx == -1) {
				orders.add(maxSize - ending);
				orderIdx = i;
				untillOderArrive.add(getRandom(2));
			} else {
				untillOderArrive.add(untillOderArrive.get(i-1) - 1);
			}
			
			// adding all in a lists
			beginnings.add(begin);
			endings.add(ending);
			demands.add(demand);
			shortages.add(shortage);
			orders.add(order);
			
			yValues[i] = beginnings.get(i) - shortages.get(i);
			xValues[i] = i;
		}
		
		// drawing graph
		LineGraph lg = new LineGraph(xValues, yValues);
		lg.draw();
		
		System.out.println("Max: " + maxSize + " MinSize: " + minSize);
		// print
		System.out.printf("|%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |\n", "Day", "Begin", "Demand", "Ending", "Shortage", "Order", "UOArrive");
		for (int i = 0; i < beginnings.size(); i++) {
			print( i + 1, beginnings.get(i), demands.get(i), endings.get(i), shortages.get(i), orders.get(i), untillOderArrive.get(i));
		}
	}
	
	public static void print(int day, int begin, int demand, int ending, int shortage, int order, int until) {
		System.out.printf("|%-10d |%-10d |%-10d |%-10d |%-10d |%-10d | %-10d|\n", day, begin, demand, ending, shortage, order, until);
	}
	
	public static int getRandom(int x) {
		return random.nextInt(1, x) + 1;
	}
}
