package background;

import java.util.Date;

public class Background {

	public static final double DEFAULT_TEMPERATURE	= 26;
	public static final double DEFAULT_PH	= 7;
	public static final double DEFAULT_CLEANLINESS = 90;
	public static final double DEFAULT_OXYGENCONTENT = 8;
	private static final double BACKGROUND_MAX_PH = 14.0; // 環境的最高pH值
	private static final double BACKGROUND_MAX_CLEANLINESS = 100.0; // 環境的最高pH值
	private static final double BACKGROUND_MAX_OXYGENCONTENT = 100.0; // 環境的最高含氧量
	
	private double temperature;
	private double pHValue;
	private double oxygenContent;  //含氧量
	private double maxOxygenContent;
	private double cleanliness;
	private Date date;
	private Date time;
	private String month_string;
	private int month_int;
	private int second=0;
//	private int speed = 1000;
	private Season season;
	private long endTime;
	
	public long getNowTime() {
		Date nowTime = new Date();
		return nowTime.getTime();
	}
	
	public long getEndTime()
	{
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Background()
	{
		setDefaultBackground();
		date = new Date();
		time = new Date();
		month_string = String.format("%tD%n",date);
		month_int = 10*Integer.parseInt(String.valueOf(month_string.charAt(0)))+
				Integer.parseInt(String.valueOf(month_string.charAt(1)));
		season = new Season(month_int);
		setDate(date);
	}
	
	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
		setMaxOxygenContent();
	}

	public double getpHValue() {
		return pHValue;
	}

	public void setpHValue(double pHValue) {
		this.pHValue = pHValue;
	}

	public double getOxygenContent() {
		return oxygenContent;
	}

	public void setOxygenContent(double oxygenContent) {
		this.oxygenContent = oxygenContent;
	}

	public double getCleanliness() {
		return cleanliness;
	}

	public void setCleanliness(double cleanliness) {
		this.cleanliness = cleanliness;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
		month_string = String.format("%tD%n",date);
		month_int = 10*Integer.parseInt(String.valueOf(month_string.charAt(0)))+
				Integer.parseInt(String.valueOf(month_string.charAt(1)));
		season.setSeason(month_int);
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public void setMaxOxygenContent(double maxOxygenContent)
	{
		this.maxOxygenContent = maxOxygenContent;
	}
	
	public double getMaxOxygenContent()
	{
		return maxOxygenContent;
	}
	
	public void setSeason(Season season) {
		this.season = season;
	}
	
	public void setDefaultBackground()
	{
		setTemperature(DEFAULT_TEMPERATURE);
		setpHValue(DEFAULT_PH);
		setCleanliness(DEFAULT_CLEANLINESS);
		setOxygenContent(DEFAULT_OXYGENCONTENT);
		setMaxOxygenContent();
	}
	
	public void changeTemperature(double var)
	{
		temperature += var;
		setMaxOxygenContent();
	}
	
	public void changepHValue(double var)
	{
		pHValue += var;
		if (pHValue > BACKGROUND_MAX_PH)
			pHValue = BACKGROUND_MAX_PH;
		else if (pHValue < 0)
			pHValue = 0;
	}
	
	public void changeOxygenContent(double var)
	{
		oxygenContent += var;
		if (oxygenContent > BACKGROUND_MAX_OXYGENCONTENT)
			oxygenContent = BACKGROUND_MAX_OXYGENCONTENT;
		else if (oxygenContent < 0)
			oxygenContent = 0;
	}
	
	public void changeCleanliness(double var)
	{
		cleanliness += var;
		if (cleanliness > BACKGROUND_MAX_CLEANLINESS)
			cleanliness = BACKGROUND_MAX_CLEANLINESS;
		else if (cleanliness < 0)
			cleanliness = 0;
	}
	
	public void setMaxOxygenContent()
	{
		//0-10度：11ppm  ,  11-20：9ppm  ,  21-30：7ppm  ,  31-40：6ppm
		if (temperature < 10)
			maxOxygenContent = 11;
		else if (temperature < 20)
			maxOxygenContent = 9;
		else if (temperature < 30)
			maxOxygenContent = 7;
		else 
			maxOxygenContent = 6;
	}
	
	@Override
	public String toString(){
		return String.format("溫度:%.1f°C pH值:%.1f 含氧量:%.1fPPM 乾淨度:%.1f%c  季節:%s 日期:%tF%n 時間:%tT%n ",
				getTemperature(),getpHValue(),getOxygenContent(),getCleanliness(),37,season.getName(),getDate(),getTime());
	}

	public void setDate(long long1) {
		date.setTime(long1);
	}

	public void setTime(long long1) {
		date.setTime(long1);
		
	}
}
