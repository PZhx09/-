package ticketingsystem;

public class Assist {
	long[] interval;
	
	public Assist()
	{
		//����޸ĵ�--------------------------------------------------------
		//����ÿ����Ʊ����Ʊ����ѯʱ�����Ѵ���ʱ����������λ����
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
