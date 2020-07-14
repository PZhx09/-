package ticketingsystem;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Seat {
	
	private AtomicLong seatState;//因为车站不超过64，所以不能用AtomicInteger
	private AtomicLong tid;//int有21亿，应该够用了
	private  ConcurrentHashMap<Long, MyTicket> soldTicket;//存放已卖出的票
	
	public Seat(int routeid,int seatid)
	{
		//seatid是(ticket.coach-1)*seatnum+ticket.seat-1
		soldTicket=new ConcurrentHashMap<Long,MyTicket>(2000);
		seatState=new AtomicLong(0);	
		tid=new AtomicLong(routeid*100000000+seatid*100000);//800个座位，一般来说总的testnum不超过8000万  tid不会重复，如果超过了可以将tid改为AtomicLong
	}
	
	public Ticket buyTicket(String passenger, int route, int departure, int arrival,int allseatid,long additionalState)
	{
		long oldState;
		long newState;
		
		do{
			oldState=seatState.get();
			long tmp=oldState&additionalState;
			if(tmp!=0)
				return null;
			newState=oldState|additionalState;
		}while(!seatState.compareAndSet(oldState, newState));
		//找到并成功售出
		long thisTid=tid.getAndIncrement();
		
		MyTicket addTicket=new MyTicket(thisTid,passenger,route,allseatid/TicketingDS.seatnum+1,allseatid%TicketingDS.seatnum+1,departure,arrival);
		soldTicket.put(thisTid,addTicket);
		return addTicket;
			
	}
	public boolean refundTicket(Ticket ticket,long refundState)
	{
		if(!soldTicket.remove(ticket.tid,new MyTicket(ticket)))
			return false;
		refundState=~refundState;
		long oldState;
		long tmp;
		do
		{
			oldState=seatState.get();
			tmp=oldState&refundState;
		}
		while(!seatState.compareAndSet(oldState, tmp));
		return true;
	}
	public int empty(int departure,int arrival,long inquryState)
	{
		//空闲返回1，有人返回0
		long oldState=seatState.get();
		
		long tmp=oldState&inquryState;
		if(tmp!=0)
			return 0;
		return 1;
	}
}
