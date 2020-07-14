package ticketingsystem;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Seat {
	
	private AtomicLong seatState;//��Ϊ��վ������64�����Բ�����AtomicInteger
	private AtomicLong tid;//int��21�ڣ�Ӧ�ù�����
	private  ConcurrentHashMap<Long, MyTicket> soldTicket;//�����������Ʊ
	
	public Seat(int routeid,int seatid)
	{
		//seatid��(ticket.coach-1)*seatnum+ticket.seat-1
		soldTicket=new ConcurrentHashMap<Long,MyTicket>(2000);
		seatState=new AtomicLong(0);	
		tid=new AtomicLong(routeid*100000000+seatid*100000);//800����λ��һ����˵�ܵ�testnum������8000��  tid�����ظ�����������˿��Խ�tid��ΪAtomicLong
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
		//�ҵ����ɹ��۳�
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
		//���з���1�����˷���0
		long oldState=seatState.get();
		
		long tmp=oldState&inquryState;
		if(tmp!=0)
			return 0;
		return 1;
	}
}
