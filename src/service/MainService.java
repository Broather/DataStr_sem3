package service;

import dataStr.*;

public class MainService {

	public static void main(String[] args) {
		MyLinkedHeap heapForInt= new MyLinkedHeap();
		try {
			heapForInt.enqueue(9);
			heapForInt.enqueue(8);
			heapForInt.enqueue(6);
			heapForInt.enqueue(2);
			heapForInt.enqueue(1);
			heapForInt.print();
			System.out.println("------------------");
			System.out.println(heapForInt.dequeue());
			System.out.println("------------------");
			heapForInt.print();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
