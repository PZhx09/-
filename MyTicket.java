package ticketingsystem;

class MyTicket extends Ticket
{
	//�Դ���Ticket��û��equals������Ticket�����޸ģ�����ֻ�ܼ̳�Ticket��дequals��������Ȼ���ܸ���һЩ
	//������дConcurrentHashMap��remove���ԣ�
	
	public MyTicket(long tid,
	String passenger,
	int route,
	int coach,
	int seat,
	int departure,
	int arrival)
	{
		this.tid=tid;
		this.passenger=passenger;
		this.route=route;
		this.coach=coach;
		this.seat=seat;
		this.departure=departure;
		this.arrival=arrival;
	}
	public MyTicket(Ticket t)
	{
		this.tid=t.tid;
		this.passenger=t.passenger;
		this.route=t.route;
		this.coach=t.coach;
		this.seat=t.seat;
		this.departure=t.departure;
		this.arrival=t.arrival;
	}
	
	@Override 
	public boolean equals(Object obj)
	{
		// ���Ϊͬһ����Ĳ�ͬ����,����ͬ 
        if (this == obj) {
            return true;
        }
        // �������Ķ���Ϊ��,�򷵻�false
        if (obj == null) {
            return false;
        }

        // ����������ڲ�ͬ������,�������
        //if (getClass() != obj.getClass()) {
            //return false;
        //}
         // ������ͬ, �Ƚ������Ƿ���ͬ
		Ticket tmp=(Ticket) obj;
		
		if(this.tid!=tmp.tid) return false;
		if(!this.passenger.equals(tmp.passenger)) return false;
		if(this.route!=tmp.route) return false;
		if(this.coach!=tmp.coach) return false;
		if(this.seat!=tmp.seat) return false;
		if(this.departure!=tmp.departure) return false;
		if(this.arrival!=tmp.arrival) return false;
		
		
		return true;
	}
}