package ticketingsystem;

public class TicketingDS implements TicketingSystem {
	//ToDo
	static int seatnum;
	private int stationnum;
	private int threadnum;
	final static Assist assist=new Assist();
	private Seat[][] allSeat;
	private int allSeatnum;
	public TicketingDS (int routenum ,int coachnum ,int seatnum ,int stationnum ,int threadnum )
	{
	   this.seatnum=seatnum;
	   this.stationnum=stationnum;
	   this.threadnum=threadnum;
	   
	   allSeatnum=coachnum*seatnum;
	   allSeat=new Seat[routenum][allSeatnum];
	   //��ʼ��ÿ����λ
	   for(int i=0;i<routenum;i++)
	   {
		   for(int j=0;j<allSeatnum;j++)
			   allSeat[i][j]=new Seat(i+1,j);
	   }

	}
	
	@Override
	public Ticket buyTicket(String passenger, int route, int departure, int arrival) {
		//�����Ʊ�����Ϲ淶��ֱ�ӷ���false
		if(departure<1||arrival>stationnum||departure>=arrival)
			return null;
		//������һ��departure��arrival��Ϊ���е�Ʊ
		long additionalState=TicketingDS.assist.interval[arrival-departure]<<(departure - 1);
		
		Ticket tmp;
		Seat[] thisRoute=allSeat[route-1];
		//int j=ThreadLocalRandom.current().nextInt(allSeatnum);//��Ϊ�����Ͽ����Ի������Բ�ʹ�������
		for(int i=0;i<allSeatnum;i++)
		{
			if((tmp=thisRoute[i].buyTicket(passenger, route, departure, arrival,i,additionalState))!=null)
			{
				return tmp;
			}
		}
		//��Ʊ����
		return null;
	}

	@Override
	public int inquiry(int route, int departure, int arrival) {
		// ��ѯ�����Ϲ淶��ֱ�ӷ���false
		if(departure<1||arrival>stationnum||departure>=arrival)
			return 0;
		long inquryState=TicketingDS.assist.interval[arrival-departure]<<(departure - 1);
		int freeCount=0;
		Seat[] thisRoute=allSeat[route-1];
		//inqury ֻ��˳�������  ���Ͽ����Ի�
		for(int i=0;i<allSeatnum;i++)
		{
			freeCount+=thisRoute[i].empty(departure, arrival,inquryState);
		}
		return freeCount;
	}

	@Override
	public boolean refundTicket(Ticket ticket) {
		//ƱΪnullֱ�ӷ���false
		if(ticket==null)
			return false;
		long refundState=TicketingDS.assist.interval[ticket.arrival-ticket.departure]<<(ticket.departure - 1);
		int seat=(ticket.coach-1)*seatnum+ticket.seat-1;
		try{
			return allSeat[ticket.route-1][seat].refundTicket(ticket,refundState);
		}catch (ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
			
	}

}


