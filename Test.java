package ticketingsystem;

import java.util.ArrayList;
import java.util.Random;

public class Test {
	//对购票进行性能测试，为了保证效率将所有的不必要的println注释掉，在进行吞吐量测试的时候注释掉计算方法时间用的代码

	
	
	public static void main(String[] args) throws InterruptedException {
		final  int[] threadnum={1,2,4,8,16,32,64}; // concurrent thread number
		final  int routenum = 5; // route is designed from 1 to 5
		final  int coachnum = 8; // coach is arranged from 1 to 8
		final  int seatnum = 100; // seat is allocated from 1 to 100
		final  int stationnum = 10; // station is designed from 1 to 10

		final  int testnum = 1000000;//2000000;
		final  int retpc = 10; // return ticket operation is 10% percent
		final  int buypc = 40; // buy ticket operation is 30% percent
		final  int inqpc = 100; //inquiry ticket operation is 60% percent
		
		
		for(int j=0;j<threadnum.length;j++)
		{
			int thisthreadnum=threadnum[j];
			Thread[] threads = new Thread[thisthreadnum];
			
			final TicketingDS tds = new TicketingDS(routenum, coachnum, seatnum, stationnum, thisthreadnum);
			
			long startTime = System.currentTimeMillis();
			for (int i = 0; i< thisthreadnum; i++) {
	
		    	threads[i] = new Thread(new Runnable() {
	                public void run() {
	            		Random rand = new Random();
	                	Ticket ticket = new Ticket();
	            		ArrayList<Ticket> soldTicket = new ArrayList<Ticket>();
	            		
	            		//System.out.println(ThreadId.get());
	            		for (int i = 0; i < testnum; i++) {
	            			int sel = rand.nextInt(inqpc);
	            			if (0 <= sel && sel < retpc && soldTicket.size() > 0) { // return ticket
	            				int select = rand.nextInt(soldTicket.size());
	           				if ((ticket = soldTicket.remove(select)) != null) {
	            					if (tds.refundTicket(ticket)) {
	            						//System.out.println("TicketRefund" + " " + ticket.tid + " " + ticket.passenger + " " + ticket.route + " " + ticket.coach  + " " + ticket.departure + " " + ticket.arrival + " " + ticket.seat);
	            						//System.out.flush();
	            					} else {
	            						//System.out.println("ErrOfRefund");
	            						//System.out.flush();
	            					}
	            				} else {
	            					//System.out.println("ErrOfRefund");
	        						//System.out.flush();
	            				}
	            			} else if (retpc <= sel && sel < buypc) { // buy ticket
	            				String passenger = "test";
	            				int route = rand.nextInt(routenum) + 1;
	            				int departure = rand.nextInt(stationnum - 1) + 1;
	            				int arrival = departure + rand.nextInt(stationnum - departure) + 1; // arrival is always greater than departure
	            				if ((ticket = tds.buyTicket(passenger, route, departure, arrival)) != null) {
	            					soldTicket.add(ticket);
	            					//System.out.println("TicketBought" + " " + ticket.tid + " " + ticket.passenger + " " + ticket.route + " " + ticket.coach + " " + ticket.departure + " " + ticket.arrival + " " + ticket.seat);
	        						//System.out.flush();
	            				} else {
	            					//System.out.println("TicketSoldOut" + " " + route+ " " + departure+ " " + arrival);
	        						//System.out.flush();
	            				}
	            			} else if (buypc <= sel && sel < inqpc) { // inquiry ticket
	            				
	            				int route = rand.nextInt(routenum) + 1;
	            				int departure = rand.nextInt(stationnum - 1) + 1;
	            				int arrival = departure + rand.nextInt(stationnum - departure) + 1; // arrival is always greater than departure
	            				int leftTicket = tds.inquiry(route, departure, arrival);
	            				//System.out.println("RemainTicket" + " " + leftTicket + " " + route+ " " + departure+ " " + arrival);
	    						//System.out.flush();  
	    						         			
	            			}
	            		}

	                }
	            });
	              threads[i].start();
	              
	 	    }
            for (int i = 0; i < thisthreadnum; i++) {
                threads[i].join();
            }
            long endTime = System.currentTimeMillis();
            double time =(endTime-startTime)/1000.0;
            System.out.format("ThreadsNum: %3d TimeUsed: %3.2fs Throughout: %.2f%n", threadnum[j], time, (threadnum[j] * testnum / time));
		}
		

	}
}
