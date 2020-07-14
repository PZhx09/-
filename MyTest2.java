package ticketingsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MyTest2 {
	
	static AtomicInteger buyNum;
	static AtomicInteger refundNum;
	static AtomicInteger inquryNum;
	static AtomicLong buyTime;
	static AtomicLong refundTime;
	static AtomicLong inquryTime;
	
	public static void main(String[] args) throws InterruptedException {
		final  int[] threadnum={4,8,16,64}; // concurrent thread number
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
			buyNum=new AtomicInteger(0);
			refundNum=new AtomicInteger(0);
			inquryNum=new AtomicInteger(0);
			buyTime=new AtomicLong(0);
			refundTime=new AtomicLong(0);
			inquryTime=new AtomicLong(0);
			
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
	            				refundNum.getAndIncrement();
	           				if ((ticket = soldTicket.remove(select)) != null) {
	           					long refundStartTime = System.currentTimeMillis();
	            					if (tds.refundTicket(ticket)) {
	            						long refundEndTime = System.currentTimeMillis();
	            						refundTime.getAndAdd(refundEndTime-refundStartTime);
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
	            				buyNum.getAndIncrement();
	            				String passenger = "test";
	            				int route = rand.nextInt(routenum) + 1;
	            				int departure = rand.nextInt(stationnum - 1) + 1;
	            				int arrival = departure + rand.nextInt(stationnum - departure) + 1; // arrival is always greater than departure
	            				long buyStartTime = System.currentTimeMillis();
	            				if ((ticket = tds.buyTicket(passenger, route, departure, arrival)) != null) {
	            					long buyEndTime = System.currentTimeMillis();
	            					buyTime.getAndAdd(buyEndTime-buyStartTime);
	            					soldTicket.add(ticket);
	            					//System.out.println("TicketBought" + " " + ticket.tid + " " + ticket.passenger + " " + ticket.route + " " + ticket.coach + " " + ticket.departure + " " + ticket.arrival + " " + ticket.seat);
	        						//System.out.flush();
	            				} else {
	            					//System.out.println("TicketSoldOut" + " " + route+ " " + departure+ " " + arrival);
	        						//System.out.flush();
	            				}
	            			} else if (buypc <= sel && sel < inqpc) { // inquiry ticket
	            				inquryNum.getAndIncrement();
	            				int route = rand.nextInt(routenum) + 1;
	            				int departure = rand.nextInt(stationnum - 1) + 1;
	            				int arrival = departure + rand.nextInt(stationnum - departure) + 1; // arrival is always greater than departure
	            				long inquryStartTime = System.currentTimeMillis();
	            				int leftTicket = tds.inquiry(route, departure, arrival);
	            				long inquryEndTime = System.currentTimeMillis();
	            				inquryTime.getAndAdd(inquryEndTime-inquryStartTime);
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
            System.out.format("ThreadsNum: %3d TimeUsed: %3.2fs", threadnum[j], time);
            System.out.println();
            System.out.println("EachBuyTime:"+buyTime.get()*1.0/buyNum.get());
            System.out.println("EachRefundTime:"+refundTime.get()*1.0/refundNum.get());
            System.out.println("EachInquryTime:"+refundTime.get()*1.0/refundNum.get());
		}
		

	}

}
