package ticketingsystem;

public class Assist {
	long[] interval;
	
	public Assist()
	{
		//这次修改的--------------------------------------------------------
		//避免每次卖票、退票、查询时，花费大量时间来进行移位操作
		interval=new long[65];
		interval[0]=0;
		interval[1]=1;
		for(int i=1;i<64;i++)
		{
			long tmp=1<<i;
		   tmp=tmp|interval[i];
		   interval[i+1]=tmp;
		}
	}

}
