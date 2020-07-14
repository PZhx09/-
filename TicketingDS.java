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
	   //初始化每个座位
	   for(int i=0;i<routenum;i++)
	   {
		   for(int j=0;j<allSeatnum;j++)
			   allSeat[i][j]=new Seat(i+1,j);
	   }

	}
	
	@Override
	public Ticket buyTicket(String passenger, int route, int departure, int arrival) {
		//如果车票不符合规范，直接返回false
		if(departure<1||arrival>stationnum||departure>=arrival)
			return null;
		//下面找一张departure到arrival都为空闲的票
		long additionalState=TicketingDS.assist.interval[arrival-departure]<<(departure - 1);
		
		Ticket tmp;
		Seat[] thisRoute=allSeat[route-1];
		//int j=ThreadLocalRandom.current().nextInt(allSeatnum);//因为不符合可线性化，所以不使用随机数
		for(int i=0;i<allSeatnum;i++)
		{
			if((tmp=thisRoute[i].buyTicket(passenger, route, departure, arrival,i,additionalState))!=null)
			{
				return tmp;
			}
		}
		//无票可买
		return null;
	}

	@Override
	public int inquiry(int route, int departure, int arrival) {
		// 查询不符合规范，直接返回false
		if(departure<1||arrival>stationnum||departure>=arrival)
			return 0;
		long inquryState=TicketingDS.assist.interval[arrival-departure]<<(departure - 1);
		int freeCount=0;
		Seat[] thisRoute=allSeat[route-1];
		//inqury 只能顺序遍历了  符合可线性化
		for(int i=0;i<allSeatnum;i++)
		{
			freeCount+=thisRoute[i].empty(departure, arrival,inquryState);
		}
		return freeCount;
	}

	@Override
	public boolean refundTicket(Ticket ticket) {
		//票为null直接返回false
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


