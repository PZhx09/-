package ticketingsystem;

class MyTicket extends Ticket
{
	//自带的Ticket类没有equals方法，Ticket不能修改，所以只能继承Ticket重写equals方法，不然还能更快一些
	//或者重写ConcurrentHashMap的remove试试？
	
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
		// 如果为同一对象的不同引用,则相同 
        if (this == obj) {
            return true;
        }
        // 如果传入的对象为空,则返回false
        if (obj == null) {
            return false;
        }

        // 如果两者属于不同的类型,不能相等
        //if (getClass() != obj.getClass()) {
            //return false;
        //}
         // 类型相同, 比较内容是否相同
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