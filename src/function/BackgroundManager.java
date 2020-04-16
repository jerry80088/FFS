package function;
import java.util.Scanner;

import background.Background;
import function.FishManager;
import simulate.SimulateInterface;

public class BackgroundManager
{
	private static final double SEASON_APPROACHING_TEMPERATURE = 0.00167; 		// (溫度) 隨 (季節) 的趨近速度 //每分鐘0.1
	private static final double THERMOSTAT_APPROACHING_TEMPERATURE = 0.001; 	// (溫度) 隨 (調溫器) 的趨近速度 //每秒鐘0.001
	private static final double INFLATOR_APPROACHING_OXYGENCONTENT = 0.000056; 	// (含氧量) 隨 (打氣機) 的上升速度 //每小時0.2
	private static final double FISHNUM_APPROACHING_OXYGENCONTENT = -0.0000033;	// (含氧量) 隨 (魚數量) 的下降速度 //每小時0.012
	private static final double PUMP_APPROACHING_CLEANLINESS = 0.001; 			// (乾淨度) 隨 (過濾機) 的上升速度 //每秒鐘0.001
	private static final double DEADFISH_APPROACHING_CLEANLINESS = -0.001; 		// (乾淨度) 隨 (死魚) 的上升速度 //每秒鐘0.001
	private static final double PUMP_APPROACHING_PH = 0.001; 					// (pH) 隨 (過濾機) 的趨近速度 //每秒鐘0.001
	private static final double DEADFISH_APPROACHING_PH = -0.0001; 				// (pH) 隨 (死魚) 的趨近速度 //每秒鐘0.0001
	private static final double PUMP_MAX_CLEANLINESS = 90; // 過濾機的最高乾淨度
	private static final double PUMP_MAX_PH = 7.0; // 過濾機的標準pH值
	private Background background;
	private FishManager fishManager;

	private boolean inflatorBtn; // 打氣機按鈕
	private boolean thermostatBtn;// 調溫器按鈕
	private boolean filterBtn; // 幫浦按鈕
	
	private double thermostatTemperature;	//調溫器的溫度

	public BackgroundManager(Background background, FishManager fishManager)
	{
		this.background = background;
		this.fishManager = fishManager;
		
		//調溫器預設溫度為季節溫度
		thermostatTemperature = background.getSeason().getTem();
		//按鈕預設為關閉
		thermostatBtn = false;
		inflatorBtn = false;
		filterBtn = false;
	}

	/**
	 * 按下打氣機按鈕
	 */
	public void setInflatorBtn(boolean inflatorBtn) {
		this.inflatorBtn = inflatorBtn;
	}
	public void pressInflatorBtn()
	{
		if (inflatorBtn)
		{
			inflatorBtn = false;
		}
		else
		{
			inflatorBtn = true;
		}
	}

	/**
	 * 按下調溫器按鈕
	 * 開啟後需輸入溫度
	 */
	public void setThermostatBtn(boolean thermostatBtn) {
		this.thermostatBtn = thermostatBtn;
	}
	public void pressThermostatBtn(int temperature)
	{
		if (thermostatBtn)
		{
			thermostatBtn = false;
		}
		else
		{
			thermostatBtn = true;
			thermostatTemperature = temperature;
		}
	}

	/**
	 * 按下過濾機按鈕
	 */
	public void setFilterBtn(boolean filterBtn) {
		this.filterBtn = filterBtn;
	}
	public void pressFilter()
	{
		if (filterBtn)
		{
			filterBtn = false;
		}
		else
		{
			filterBtn = true;
		}
	}

	/**
	 * 溫度：趨近季節的溫度(春20 夏25 秋20 冬15)，隨時間每分鐘趨近0.1度(SEASON_APPROACHING_TEMPERATURE)。 
	 * 若調溫器開啟，則溫度隨時間每秒鐘趨近0.001(THERMOSTAT_APPROACHING_TEMPERATURE)
	 * 
	 * @param sec
	 */
	public void growingTemperature(int sec)
	{
		if (thermostatBtn)
		{
			//溫度以0.001趨近於調溫器溫度
			background.changeTemperature(approachToSpecificNum(THERMOSTAT_APPROACHING_TEMPERATURE,
																background.getTemperature(),
																thermostatTemperature));
		}
		else
		{
			//溫度以0.06趨近於季節溫度
			background.changeTemperature(approachToSpecificNum(SEASON_APPROACHING_TEMPERATURE,
																background.getTemperature(),
																background.getSeason().getTem()));
		}
	}

	/**
	 * 若過濾機開啟ph值趨近於7.0(PUMP_APPROACHING_PH)，每秒0.01(PUMP_APPROACHING_PH)
	 * @param sec
	 */
	public void growingpH(int sec)	//因為pHValue不會因時間而改變數值，因此將按鈕條件寫在外面直接判斷
	{
		
		double var = fishManager.getDeadFishNum() * DEADFISH_APPROACHING_PH;
		if (filterBtn)
		{
			var += PUMP_APPROACHING_PH;
			//每秒趨近0.01
			
			background.changepHValue(approachToSpecificNum(var,
															background.getpHValue(),
															PUMP_MAX_PH));
			
		}
		background.changepHValue(var);
	}
	//---------------------------------------------------------------------------------
	/**
	 * 含氧量：隨時間每小時下降0.012，若魚的數量為n，則速率為0.012*n。
	 * 打氣：依溫度決定含氧量上升到最大值如下，速度為每小時+0.2ppm。
	 * 最大含氧量：0-10度：11ppm  ,  11-20：9ppm  ,  21-30：7ppm  ,  31-40：6ppm
	 * @param sec
	 */
	public void growingOxygenContent(int sec)
	{
		double OxygenContentVar = 0;
		if (inflatorBtn)
		{
			OxygenContentVar += INFLATOR_APPROACHING_OXYGENCONTENT;
		}
		if (fishManager.haveFish())
		{
			OxygenContentVar += fishManager.getFishNum() * FISHNUM_APPROACHING_OXYGENCONTENT;
		}
		if (OxygenContentVar != 0)
		{
			if (sec % 1 == 0)
				background.changeOxygenContent(approachToSpecificNum(OxygenContentVar,
																	background.getOxygenContent(),
																	background.getMaxOxygenContent()));
		}
	}

	/**過濾機開啟時乾淨度緩慢上升趨近90%，速度為每秒+0.01%，
	 * @param sec
	 * @param pumpBtn
	 */
	public void growingCleanliness(int sec)
	{
		double var = fishManager.getDeadFishNum() * DEADFISH_APPROACHING_CLEANLINESS;
		if (filterBtn)
		{
			var += PUMP_APPROACHING_CLEANLINESS;
			background.changeCleanliness(approachToSpecificNum(PUMP_APPROACHING_CLEANLINESS,
																	background.getCleanliness(),
																	PUMP_MAX_CLEANLINESS));
		}
		else 
			background.changeCleanliness(var);
	}

	/**
	 * 換水：直接將乾淨度設為100%，含氧量依溫度給予預設值如下，ph值設為7.0。 含氧量： 0-10度：11ppm , 11-20：9ppm ,
	 * 21-30：7ppm , 31-40：6ppm
	 */
	public void changeWater()
	{
		background.setDefaultBackground();
	}
	
	/**
	 * 來源值(src)以特定速率(var)趨近於特定溫度(target)
	 * 
	 * @param var : 特定速率
	 * @param src : 來源值
	 * @param target : 趨近值
	 * @return 改變值
	 */
	public double approachToSpecificNum(double var, double src, double target)
	{
		if (src > target)
		// 來源值大於趨近值->回傳負值
		{
			return -1 * var;
		}
		// 來源值小於趨近值->回傳正數
		else if (src < target)
		{
			return var;
		}
		// 來源值等於趨近值->回傳0
		else return 0;
	}
	
	public void setButton(SimulateInterface SI)
	{
		if (SI.getFilterCheck())
			pressFilter();
		else
			inflatorBtn = false;
		
		if (SI.getInflatorCheck())
			pressInflatorBtn();
		else
			inflatorBtn = false;
		if (SI.getChangeTemCheck())
			pressThermostatBtn(SI.getTem());
		else
			thermostatBtn = false;
	}

	public void display()
	{
		System.out.println(background);
	}
}
