package dataStr;

import javax.sound.midi.VoiceStatus;

public class MyLinkedHeap<Type> {
	private MyHeapNode root = null;
	private int counter = 0;
	private MyHeapNode lastParent = null;
	
	//TODO uzrakstīt dokumentāciju koemntārus
	
	public boolean isFull(){
		try
		{
			MyHeapNode temp = new MyHeapNode<>((Type)new Object());
			return false;
		}
		catch (OutOfMemoryError e) {
			return true;
		}
	}
	
	public boolean isEmpty(){
		return (counter==0);
	}
	
	
	public void makeEmpty(){
		root = null;
		counter = 0;
		lastParent = null;
		System.gc();
	}
	public int size(){
		return counter;
	}
public void enqueue(Type element) throws Exception{
		if(isFull()) {
			throw new Exception("Nevar pievienot elementu, jo atmiņa pilna");
		}
		
		MyHeapNode newNode = new MyHeapNode<Type>(element);
		if(isEmpty()) {
			root = newNode;
			lastParent = newNode;
		}else {
//			gadījumā ja lastParent nav pareizs
			if(lastParent.getRightCh() != null) {
				lastParent = lastParent.getNext();
			}
//			2 saites no jaunā mazgla
			newNode.setParent(lastParent);
			MyHeapNode lastNode = getLastNode();
			newNode.setPrevious(lastNode);
//			2 saites uz jauno mezglu
			if(lastParent.getLeftCh() != null) lastParent.setRightCh(newNode);
			else lastParent.setLeftCh(newNode);
			lastNode.setNext(newNode);
			
			reheapUp(newNode);
		}
		counter++;
		
	}
	/**
	 * Atgriež root elementa vērtību un "izņem" root node no heap
	 * @return
	 * @throws Exception
	 */
	public Type dequeue() throws Exception{
		if(isEmpty()) throw new Exception("Kaudze ir tukša, tāpēc nevaar neko izdzēst");
		
		Type result = (Type) root.getElementValue();
		
		if(counter==1){
			makeEmpty();
			return result;
		}
		else
		{
			//1. paņemt pēdejo elementu un to ielikt kā root
			MyHeapNode lastNode = getLastNode();
			root.setElementValue(lastNode.getElementValue());
			//2. dzēst pēdejo mezglu
			deleteLastNode();
			//2.1. noskaidrot nākamo šo lastParent
			getLastNode();
			//3.reheapDown funkcijas izsaukums
			reheapDown(root);
			//4. samazināt counter par 1
			counter--;
			return result;
		}
		
	}
	private MyHeapNode getLastNode()
	{
		if(lastParent.getRightCh()!=null){
			return lastParent.getRightCh();
		}
		else if(lastParent.getLeftCh()!=null){
			return lastParent.getLeftCh();
		}else if(lastParent.getRightCh()==null && lastParent.getLeftCh()==null){
//	lastparent nav korekts, jo tam nav bērnu, ir jānomaina uz previous
			if(lastParent != root) {
				lastParent = lastParent.getPrevious();
				return lastParent.getLeftCh();
			}
		}
		return lastParent;
	}
	
	private void deleteLastNode(){
//		ja eksistē abi bērni
		if(lastParent.getRightCh()!=null){
//		svarīgi izdzēst visas norādes uz right child, ieskaitot left child next norāde
			MyHeapNode previousNode = lastParent.getRightCh().getPrevious();
			previousNode.setNext(null);
			lastParent.setRightCh(null); //iznīcinam saiti no vecāku puses uz labo bērnu
		}
		else{
			lastParent.setLeftCh(null);//iznīcinam saiti no vecāku puses uz kreiso bērnu
		}
	}
	
	private void identifyPreviousLastParent(){
		
		//1. vecākam ir izdzest labais bērns, bet kreisais paliek, 
		//līdz ar to lastParent pliek kā lastParent
		if(lastParent.getLeftCh()!=null && lastParent.getRightCh()==null){
			
		}
		else if(lastParent.getLeftCh()==null && lastParent.getRightCh()==null)
		{
			//noskidrot vecku šim lastParent
			MyHeapNode parentTemp = lastParent.getParent();
			//2. lastparent vairāk bērnu nav, td jānoskaidro, vai lastParent ir pats kā kreisai vai labais bērns
			if(parentTemp.getLeftCh() == lastParent) //lastParent ir kā kreisais bērns
			{
				//2.2. ja lastPrent ir kreisais bērns, td nākaiams laastParent vais nebšÅ« brālis, 
				//bet bÅ«s jāmeklē tuvākais labāis bērns caur kreio pusi
			}
			else //lastParent ir kā labais bērns
			{
				//2.1 ja lastParent ir labais bērns, tad nākamais lastParent bÅ«s šī mezgla brālis kreisajā pusē
				lastParent = parentTemp.getLeftCh();
			}
			
				
		}
	}
	private void reheapUp(MyHeapNode temp) {
		while(temp != null && temp.getParent() != null && ((Comparable)temp.getParent().getElementValue()).compareTo(temp.getElementValue()) == -1) {
			swap(temp, temp.getParent());
		}
	}
	
	private void reheapDown(MyHeapNode temp) {
//		ja mezglam ir abi bērni
		if(temp != null && temp.getRightCh()!=null){
			Type tempValue = (Type) temp.getElementValue();
			Type leftValue = (Type) temp.getLeftCh().getElementValue();
			Type rightValue = (Type) temp.getRightCh().getElementValue();
			
			if(((Comparable)rightValue).compareTo(leftValue) == 1) {
//				ja labais bērns lielāks par tā parent value
				if(((Comparable)rightValue).compareTo(tempValue) == 1) {
					swap(temp, temp.getRightCh());
					reheapDown(temp.getRightCh());
				}
			}
			if(((Comparable)leftValue).compareTo(rightValue) == 1) {
//				ja labais bērns lielāks par tā parent value
				if(((Comparable)leftValue).compareTo(tempValue) == 1) {
					swap(temp, temp.getLeftCh());
					reheapDown(temp.getLeftCh());
				}
		}
//		ja mezglam tikai kreisais bērns
	}else if(temp != null && temp.getLeftCh() != null) {
		Type tempValue = (Type) temp.getElementValue();
		Type leftValue = (Type) temp.getLeftCh().getElementValue();
		if(((Comparable)leftValue).compareTo(tempValue) == 1) {
			swap(temp, temp.getLeftCh());
			
		}
	}
		
}
	private void swap(MyHeapNode node1, MyHeapNode node2) {
		Type temp = (Type) node1.getElementValue();
		node1.setElementValue(node2);
		node2.setElementValue(temp);
	}
	
	public void print() {
		printHelper(root);
	}
	
	private void printHelper(MyHeapNode node) {
		if(node != null) {
			System.out.print("Parent: "+ node + " ");

			if(node.getLeftCh() != null) {
				System.out.print("LC: "+ node.getLeftCh() + " ");
				}
			if(node.getRightCh() != null) {
				System.out.print("RC: "+ node.getRightCh());
				}
			System.out.println();
		}
		if(node.getLeftCh() != null) {
			printHelper(node.getLeftCh());
		}
		if(node.getRightCh() != null) {
			printHelper(node.getRightCh());
		}
	}
	
	
}
