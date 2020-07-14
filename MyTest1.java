package ticketingsystem;

public class MyTest1 {
//²âÊÔÓÃÀý ½öÓÃÓÃÀ´²âÊÔÂò¡¢ÍË¡¢²é¹¦ÄÜµÄÕýÈ·ÐÔ£¬·ÀÖ¹´íÎó
	public static void main(String[] args) throws InterruptedException {
		final  int[] threadnum={1}; // concurrent thread number
		final  int routenum = 2; // route is designed from 1 to 5     
		final  int coachnum = 3; // coach is arranged from 1 to 8     
		final  int seatnum = 2; // seat is allocated from 1 to 100    
		final  int stationnum = 6; // station is designed from 1 to 10
/*
___________________________     ___________________________
|      |         |        |     |      |         |        |
|------|---------|--------|     |------|---------|--------|
|      |         |        |     |      |         |        |
¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰              ¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰¨‰    
  
  
  
  */                     
		final  int testnum = 10000;
		final  int retpc = 10; // return ticket operation is 10% percent
		final  int buypc = 40; // buy ticket operation is 30% percent
		final  int inqpc = 100; //inquiry ticket operation is 60% percent
		
		
		for(int j=0;j<threadnum.length;j++)
		{
			int thisthreadnum=threadnum[j];
			Thread[] threads = new Thread[thisthreadnum];
			
			final TicketingDS tds = new TicketingDS(routenum, coachnum, seatnum, stationnum, thisthreadnum);
			Ticket[] test=new Ticket[10];
			for(int i=0;i<10;i++)
			{
				Ticket t=tds.buyTicket("2", 1, 1, 2);
				//System.out.println(t.tid+" "+t.route+" "+t.departure+" "+t.arrival);
				if(t!=null)
				{
					test[i]=t;
					System.out.println(" t.tid"+t.tid+" "+" t.route"+t.route+" "+" t.departure"+t.departure+" "+" t.arrival"+t.arrival);
					System.out.println(" t.coach"+t.coach+" "+" t.seat"+t.seat);
					System.out.println("buy success route 1,1-2");
					System.out.println("left"+tds.inquiry(1, 1, 2));
				}
				else
				{
					System.out.println("full*********************");
				}
					
				
				System.out.println("--------------------------------");
			}
			//refund error ticket!!!!
			test[7]=new Ticket();
			test[8]=new Ticket();
			test[8].tid=test[0].tid;
			if(tds.refundTicket(test[7])) System.out.println("´íÎó£¡£¡£¡£¡£¡£¡");
			if(tds.refundTicket(test[8])) System.out.println("´íÎó£¡£¡£¡£¡£¡£¡");
			for(int i=0;i<10;i++)
			{
				if(tds.refundTicket(test[i]))
				{
					System.out.println(i+" refund success");
				}
				else
				{
					System.out.println(i+" refund fail");
				}
			}
			for(int i=0;i<10;i++)
			{
				Ticket t=tds.buyTicket("2", 1, 1, 2);
				//System.out.println(t.tid+" "+t.route+" "+t.departure+" "+t.arrival);
				if(t!=null)
				{
					test[i]=t;
					System.out.println(" t.tid"+t.tid+" "+" t.route"+t.route+" "+" t.departure"+t.departure+" "+" t.arrival"+t.arrival);
					System.out.println(" t.coach"+t.coach+" "+" t.seat"+t.seat);
					System.out.println("buy success route 1,1-2");
					System.out.println("left"+tds.inquiry(1, 1, 2));
				}
				else
				{
					System.out.println("full*********************");
				}
					
				
				System.out.println("--------------------------------");
			}
			for(int i=0;i<10;i++)
			{
				Ticket t=tds.buyTicket("2", 1, 3, 4);
				//System.out.println(t.tid+" "+t.route+" "+t.departure+" "+t.arrival);
				if(t!=null)
				{
					test[i]=t;
					System.out.println(" t.tid"+t.tid+" "+" t.route"+t.route+" "+" t.departure"+t.departure+" "+" t.arrival"+t.arrival);
					System.out.println(" t.coach"+t.coach+" "+" t.seat"+t.seat);
					System.out.println("buy success route 1,3-4");
					System.out.println("left"+tds.inquiry(1, 3, 4));
				}
				else
				{
					System.out.println("full*********************");
				}
					
				
				System.out.println("--------------------------------");
			}
			
			for(int i=0;i<10;i++)
			{
				Ticket t=tds.buyTicket("2", 1, 5, 6);
				//System.out.println(t.tid+" "+t.route+" "+t.departure+" "+t.arrival);
				if(t!=null)
				{
					test[i]=t;
					System.out.println(" t.tid"+t.tid+" "+" t.route"+t.route+" "+" t.departure"+t.departure+" "+" t.arrival"+t.arrival);
					System.out.println(" t.coach"+t.coach+" "+" t.seat"+t.seat);
					System.out.println("buy success route 1,5-6");
					System.out.println("left"+tds.inquiry(1, 5, 6));
				}
				else
				{
					System.out.println("full*********************");
				}
					
				
				System.out.println("--------------------------------");
			}
			
			
			/*for(int i=0;i<6;i++)
			{
				Ticket t=tds.buyTicket("2", 2, 1, 2);
				System.out.println(t.tid+" "+t.route+" "+t.departure+" "+t.arrival);
				if(t!=null)
				    System.out.println("buy success route 2,1-2");
				else
					System.out.println("full");
			}*/
		    	
		}
		

	}


}
                          
