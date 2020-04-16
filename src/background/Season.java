package background;

public class Season {
	private String name;
	private double tem;
	
	public Season(int month)
	{
		month = month%12;
		month = month/3;
		switch(month)
		{
		case 0:
			name = "冬";
			tem = 15;
			break;
		case 1:
			name = "春";
			tem = 20;
			break;
		case 2:
			name = "夏";
			tem = 25;
			break;
		case 3:
			name = "秋";
			tem = 20;
			break;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSeason(int month)
	{
		month = month%12;
		month = month/3;
		switch(month)
		{
		case 0:
			setName("冬");
			//tem = 15
			setTem(15);
			break;
		case 1:
			setName("春");
			//tem = 20
			setTem(20);
			break;
		case 2:
			setName("夏");
			//tem = 25
			setTem(25);
			break;
		case 3:
			setName("秋");
			//tem = 20
			setTem(20);
			break;
		}
	}

	public double getTem() {
		return tem;
	}

	public void setTem(double tem) {
		this.tem = tem;
	}
	@Override
	public String toString(){
		return this.getName();
		
	}

}
