package dataStr;

public class MyLinkedHeapWorking<Type> {
	private MyHeapNode root = null;
	private int counter = 0;
	private MyHeapNode lastParent = null;
	
	//TODO uzrakstÄ«t dokumentÄ�ciju koemntÄ�rus
	
	public boolean isFull()
	{
		try
		{
			MyHeapNode temp = new MyHeapNode<>((Type)new Object());
			return false;
		}
		catch (OutOfMemoryError e) {
			return true;
		}
	}
	public boolean isEmpty()
	{
		return (counter==0);
	}
	
	public int size()
	{
		return counter;
	}
	public void makeEmpty()
	{
		root = null;
		counter = 0;
		lastParent = null;
		System.gc();
	}
	
	public Type dequeue() throws Exception
	{
		if(isEmpty())
			throw new Exception("Kaudze ir tukÅ¡a, tÄ�pÄ“c nevaar neko izdzÄ“st");
		
		Type result = (Type) root.getElementValue();
		
		if(counter==1)
		{
			makeEmpty();
			return result;
		}
		else
		{
			//1. paÅ†emt pÄ“dejo elementu un to ielikt kÄ� root
			MyHeapNode lastNode = getLastNode();
			root.setElementValue(lastNode.getElementValue());
			//2. dzÄ“st pÄ“dejo mezglu
			deleteLastNode();
			//2.1. noskaidrot nÄ�kamo Å¡o lastParent
			//funkcija iespējasm vairs nav nepieciešama, jo getLastNode to pašu dara
			//identifyPreviousLastParent();
			
			//3.reheapDown funkcijas izsaukums
			reHeapDown(root);
			//4. samazinÄ�t counter par 1
			counter--;
			return result;
		}
		
	}
	private MyHeapNode getLastNode()
	{
		//vai lastParent eksistē abi bērni
		if(lastParent.getLeftCh()!=null 
				&&lastParent.getRightCh()!=null)
		{
			return lastParent.getRightCh();
		}
		//vai lastparent eksistē tikai kreisais bērns
		else if(lastParent.getLeftCh()!=null 
				&&lastParent.getRightCh()==null)
		{
			return lastParent.getLeftCh();
		}
		//lastParent neeksistē neviens bērns
		else if (lastParent.getLeftCh()==null 
				&&lastParent.getRightCh()==null)
		{
			if(lastParent!=root)
			{
				lastParent = lastParent.getPrevious();
				return lastParent.getLeftCh();
			}
		}
		
		return lastParent;
	}
	
	private void deleteLastNode()
	{
		//ja eksistē abi bērni
		if(lastParent.getLeftCh()!=null 
				&&lastParent.getRightCh()!=null)
		{
			MyHeapNode previousNode = lastParent.getRightCh().getPrevious();
			previousNode.setNext(null);
			
			lastParent.setRightCh(null); //iznÄ«cinam saiti no vecÄ�ku puses uz labo bÄ“Å—nu
		}
		//ja eksiste tikai kreisais bērns
		else if (lastParent.getLeftCh()!=null 
				&&lastParent.getRightCh()==null)
		{
			MyHeapNode previousNode = lastParent.getLeftCh().getPrevious();
			previousNode.setNext(null);
			lastParent.setLeftCh(null);//iznÄ«cinam saiti no vecÄ�ku puses uz kreiso bÄ“rnu
		}
	}
	
	private void identifyPreviousLastParent()
	{
		if(lastParent.getLeftCh()==null 
				&& lastParent.getRightCh()==null)
		{
			lastParent = lastParent.getPrevious();
		}
		/*
		//1. vecÄ�kam ir izdzest labais bÄ“rns, bet kreisais paliek, 
		//lÄ«dz ar to lastParent pliek kÄ� lastParent
		if(lastParent.getLeftCh()!=null && lastParent.getRightCh()==null)
		{
			
		}
		else if(lastParent.getLeftCh()==null && lastParent.getRightCh()==null)
		{
			//noskidrot vecku Å¡im lastParent
			MyHeapNode parentTemp = lastParent.getParent();
			//2. lastparent vairÄ�k bÄ“rnu nav, td jÄ�noskaidro, vai lastParent ir pats kÄ� kreisai vai labais bÄ“rns
			if(parentTemp.getLeftCh() == lastParent) //lastParent ir kÄ� kreisais bÄ“rns
			{
				//2.2. ja lastPrent ir kreisais bÄ“rns, td nÄ�kaiams laastParent vais nebÅ¡Å« brÄ�lis, 
				//bet bÅ«s jÄ�meklÄ“ tuvÄ�kais labÄ�is bÄ“rns caur kreio pusi
			}
			else //lastParent ir kÄ� labais bÄ“rns
			{
				//2.1 ja lastParent ir labais bÄ“rns, tad nÄ�kamais lastParent bÅ«s Å¡Ä« mezgla brÄ�lis kreisajÄ� pusÄ“
				lastParent = parentTemp.getLeftCh();
			}
			
				
		}*/
	}
	
	private void reHeapDown(MyHeapNode temp)
	{
		//mezglam ir abi bērni
		if(temp!=null && temp.getLeftCh()!=null 
				&& temp.getRightCh()!=null)
		{
			Type leftValue = (Type) temp.getLeftCh().getElementValue();
			Type rightValue = (Type) temp.getRightCh().getElementValue();
			Type tempValue = (Type) temp.getElementValue();
			//kreisais bērns ir lielaks par labo bērnu
			if(     ((Comparable) leftValue).compareTo(rightValue) == 1     )
			{
				//kreisais bērns jāsalīdzina ar pašu elementu  -bērns ir lielāks
				if (   ((Comparable) leftValue).compareTo(tempValue)  == 1   )
				{
					swap(temp, temp.getLeftCh());
					reHeapDown(temp.getLeftCh());
				}
			}
			//labais bērns ir lielāks par kreiso bērnu
			else
			{
				//labis bērns jāsalīdzina ar pašu elementu  -bērns ir lielāks
				if (   ((Comparable) rightValue).compareTo(tempValue)  == 1   )
				{
					swap(temp, temp.getRightCh());
					reHeapDown(temp.getRightCh());
				}
			}
		}
		//mezglam ir tikai kreisais bērns
		if(temp!=null && temp.getLeftCh()!=null 
				&& temp.getRightCh()==null)
		{
			Type leftValue = (Type) temp.getLeftCh().getElementValue();
			Type tempValue = (Type) temp.getElementValue();
			
			//2. salīdznāt kreisā bērna vērību ar temp vēŗtību
			if (   ((Comparable) leftValue).compareTo(tempValue)  == 1   )
			{
				//3. pec nepieciešmaības mainam vietām
				swap(temp, temp.getLeftCh());
			
			}
		}
		
		
		
		//4.uztaisīt swap funkciju
		
		
	}
	private void swap(MyHeapNode node1, MyHeapNode node2) {
		Type temp  = (Type) node1.getElementValue();
		node1.setElementValue(node2.getElementValue());
		node2.setElementValue(temp);
		
	}

	
	//1. enqueue funkcijas galvene
	public void enqueue(Type element) throws Exception
	{
	//2. pāŗbudām, vai nav jau pilna kaudze
		if(isFull())
			throw new  Exception("Kaudze ir jau pilna, nevar ielikt elementu");
		
		//3.0 - uztiasīt jauno mezglu
		MyHeapNode newNode = new MyHeapNode<Type>(element);

	//3. pāŗbaudam, vai gdījumā jaunās elements nav pats pirmais, 
	//ko ievietojam kaudzē
		//3.1. ja ir -  jaunais elements ir root un arī actualParent
		if(isEmpty())
		{
			root = newNode;
			lastParent = newNode;
			counter++;
		}
		//3.2.1 jāuzstāda divas izejošās saites no jaunā mezgla
		//3.2.2. jāuzstāda divas ienākošās saites uz mezglu
		//(neaaizmirstrm pr to, ka vecākam jānoskaidro, kurš bērns tas ir)
		else
		{
			newNode.setParent(lastParent);
			MyHeapNode lastNode = getLastNode();
			newNode.setPrevious(lastNode);
			lastNode.setNext(newNode);
			//pēdejam vecākam eksistē tikai kreisais bērns
			if(lastParent.getLeftCh()!=null 
					&& lastParent.getRightCh()==null)
			{
				lastParent.setRightCh(newNode);
			}
			//pēdejam vecākam nav vispār bērnu
			else if (lastParent.getLeftCh()==null 
					&& lastParent.getRightCh()==null)
			{
				lastParent.setLeftCh(newNode);
			}
			//vecākam ju ir abi bērni
			else if(lastParent.getLeftCh()!=null 
					&& lastParent.getRightCh()!=null)
			{
				lastParent = lastParent.getNext();
				lastParent.setLeftCh(newNode);
			}
			
			//4. reHeapUp() izsaukums
			reHeapUp(getLastNode());
			
			//5. counter palielinam par 1
			counter++;
			
		}
	
	
	
	}
		
	
	//6.reHeapUp() funkciju
	//1.funkcija galvene
	private void reHeapUp(MyHeapNode temp)
	{
		if(temp!=null && temp.getParent()!=null)
		{
			Type tempValue = (Type) temp.getElementValue();
			Type parentValue = (Type) temp.getParent().getElementValue();
			
			if(  ((Comparable) tempValue).compareTo(parentValue) ==1  )
			{
				swap(temp, temp.getParent());
				reHeapUp(temp.getParent());
				
			}
		}
	//2. pārbaudam, vai eksistē elements un tā vecāks
	//3.veicam pŗbaud starp mezglu vērtībām
	//pēc nepeciešamības mainām vērtības vietam
	}
	//7.print()
	public void print()
	{
		printHelper(root);
	}
	
	private void printHelper(MyHeapNode node)
	{
		if(node!=null)
		{
			System.out.print("P: "+ node.getElementValue());
			if(node.getLeftCh()!=null)
				System.out.print(" , Left: " + node.getLeftCh().getElementValue());
			if(node.getRightCh()!=null)
				System.out.print(" , Right: " + node.getRightCh().getElementValue());
		
			System.out.println();
			
			if(node.getLeftCh()!=null)
				printHelper(node.getLeftCh());
			if(node.getRightCh()!=null)
				printHelper(node.getRightCh());
		}
	}
	//8. uztaisīt mainServisu ar min funkciju un 
	//izveidot kaudzi ar int elementime
	
	
}
