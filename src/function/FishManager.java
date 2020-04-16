package function;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import background.Background;
import fish.Fish;
import fish.FishButton;
import simulate.SimulateInterface;

/*
   魚：
         魚種衝突：紅十字、非洲慈鯛會攻擊其他所有魚種，若此兩種養在一起，體重大
                            者會攻擊體重小者，被攻擊者生命力每秒下降1-3
*/
public class FishManager
{
	private static final double FTM_CLEANLINESS = -2; // 餵太多改變的乾淨度
	private static final double FTM_PHVALUE = 0.1; // 餵太多改變的pH值
	private static final int ONETIME_FEED = 5;	//餵魚一次飽食度增加值
	private static final double EXCRETION_PH = 0.02;	//排泄使pH上升的值
	private static final double EXCRETION_CLEANLINESS = -1;	//排泄使乾淨度下降的值
	private static final double EXCRETION_RISE = 1;	//排泄度上升的值
	private Random random;
	private Scanner scanner;
	private ArrayList<FishButton> fishes;
	private Background background;
	private int autoFeedTime;	//自動餵食時間(sec)
	private boolean autoFeedBtn; // 自動餵魚按鈕
	private int conflictId;//衝突魚類id
	private boolean conflict = false;//是否有衝突魚類

	public FishManager(ArrayList<FishButton> fishes, Background background)
	{
		this.random = new Random();
		this.fishes = fishes;
		this.background = background;
		autoFeedTime = 0;
		scanner = new Scanner(System.in);
	}

	/**
	 * 餵食一次：每一隻魚飽食度+5，滿是100 若餵食過多(每一隻魚飽食度皆100)，多一次餵食，ph值上升0.1， 乾淨度下降2%
	 */
	public void feed()
	{
		boolean feedTooMuch = true;
		for (int i = 0; i < fishes.size(); i++)
		{
			if (!fishes.get(i).getFish().isDead() && fishes.get(i).getFish().getSatiation() < Fish.MAX_SATIATION)
			{
				// 每條魚飽食度上升5
				fishes.get(i).getFish().changeSatiation(ONETIME_FEED);
				// 若每條魚的飽食度皆為100, 則 feedTooMuch = true
				feedTooMuch = false;
			}
		}
		if (feedTooMuch) // 乾淨度下降2%, pH值上升0.1
		{
			background.changeCleanliness(FTM_CLEANLINESS);
			background.changepHValue(FTM_PHVALUE);
		}
	}

	/**
	 * 按下自動餵食按鈕
	 */
	public void setAutoFeedBtn(boolean autoFeedBtn) {
		this.autoFeedBtn = autoFeedBtn;
	}
	public void pressAutoFeedBtn(int time)
	{
		if (autoFeedBtn)
		{
			autoFeedBtn = false;
//			System.out.println("自動餵食關閉");
		}
		else
		{
//			System.out.println("輸入自動餵食時間：");
			autoFeedTime = time;
			autoFeedBtn = true;
//			System.out.println("自動餵食開啟");
		}
	}
	/**
	 * 自動餵食：可於設定中設定每多少分鐘餵食一次(飽食度變化同上)，可主動關閉
	 * 
	 * @param autoFeedBtn
	 */
	public void autoFeed(int sec)
	{
		if (autoFeedBtn)
		{

			if (sec % autoFeedTime == 0)
			{
				
				feed();
			}
		}
	}

	/**
	 * 年齡：每12天增加一歲
	 * 
	 * @param sec
	 */
	public void growingAge(int sec) // 時間
	{
		if (sec % 1 == 0) // 每秒鐘
		{
			for (int i = 0; i < fishes.size(); i++)
			{
				if (!fishes.get(i).getFish().isDead())
					fishes.get(i).getFish().changeAge(1);
			}
		}
	}

	/**
	 * 長度：每秒增加0.000001-0.000003公分
	 * 
	 * @param sec
	 */
	public void growingLength(int sec) // 時間
	{
		if (sec % 1 == 0) // 每秒鐘
		{
			for (int i = 0; i < fishes.size(); i++)
			{
				if (!fishes.get(i).getFish().isDead())
					fishes.get(i).getFish().changeLength((random.nextDouble() * 2 + 1) / 1000000);
			}
		}
	}

	/**
	 * 重量；每秒增加0.000001-0.000005g
	 * 
	 * @param sec
	 */
	public void growingWeight(int sec) // 時間
	{
		if (sec % 1 == 0) // 每秒鐘
		{
			for (int i = 0; i < fishes.size(); i++)
			{
				if (!fishes.get(i).getFish().isDead())
					fishes.get(i).getFish().changeWeight((random.nextDouble() * 4 + 1) / 1000000);
			}
		}
	}

	/**
	 * 飽食度：隨時間飽食度每分鐘下降1
	 * 
	 * @param sec
	 */
	public void growingSatiation(int sec) // 時間
	{
		if (sec % 60 == 0) // 每分鐘
		{
			for (int i = 0; i < fishes.size(); i++)
			{
				if (!fishes.get(i).getFish().isDead())
					fishes.get(i).getFish().changeSatiation(-1);
			}
		}
	}

	/**
	 * 生命力：依年齡決定生命力最高值如下，若溫度不在適溫內、不在適合ph值中、乾淨度低於40%、含氧量低於4ppm、飽食度為0，
	 * 其一成立則生命力下降速率，每秒下降0.1，若兩者成立速率為0.2，依此類推，若低於40則會生病。 生病：生命力下降速率為平常的兩倍，
	 * 飽食度高於80且環境為適溫、適ph、適乾淨度、適含氧量則每秒生命力增加0.05，最高增至為生命力上限。 生命力上限0-10歲->100 ,
	 * 11-20歲->90 , 21-30歲->80 , 31-40歲->70 , 41-50歲->60 , 50以上->50
	 * 
	 * @param sec
	 */
	public void growingLife(int sec)
	{
		double var;
		if (sec % 1 == 0)	//每秒鐘
		{
			for (int i = 0; i < fishes.size(); i++)
			{
				if (!fishes.get(i).getFish().isDead())
				{
					var = 0;
					// 是否在適溫內
					if (!fishes.get(i).getFish().inSuitableTemperature(background.getTemperature()))
					{
						var -= 0.1;
						fishes.get(i).getFish().suitableTemperature = false;
					}
					else 
						fishes.get(i).getFish().suitableTemperature = true;
					// 是否在適pH內
					if (!fishes.get(i).getFish().inSuitablepH(background.getpHValue()))
					{
						var -= 0.1;
						fishes.get(i).getFish().suitablepH = false;
					}
					else 
						fishes.get(i).getFish().suitablepH = true;
					// 是否在適乾淨度內
					if (background.getCleanliness() < fishes.get(i).getFish().getSUITABLE_CLEANLINESS())
					{
						var -= 0.1;
						fishes.get(i).getFish().suitableCleanliness = false;
					}
					else 
						fishes.get(i).getFish().suitableCleanliness = true;
					// 是否在適含氧量內
					if (background.getOxygenContent() < fishes.get(i).getFish().getSUITABLE_OXYGENCONTENT())
					{
						var -= 0.1;
						fishes.get(i).getFish().suitableOxygenContent = false;
					}
					else 	
						fishes.get(i).getFish().suitableOxygenContent = true;
					// 是否挨餓中
					if (fishes.get(i).getFish().isStarving())
					{
						var -= 0.1;
						fishes.get(i).getFish().starving = true;
					}
					else
						fishes.get(i).getFish().starving = false;
					// 若以上皆符合，且飽食度大於80則生命力每秒上升0.05
					if (var == 0 && fishes.get(i).getFish().getSatiation() > Fish.SUITABLE_SATIATION)
						var = 0.05;
					fishes.get(i).getFish().changeLife(var);
				}
			}
		}
	}
	//----------------------------------------------------------------------
	/**			//10秒1%
	 * 排泄：飽食度非 0% 時隨時間每秒增加 0.5% ， 100% 時排泄，造成 pH值上升0.02，乾淨度下降1%，排泄完歸零
	 * @param sec
	 */
	public void growingExcretion(int sec)
	{
		if (sec % 10 == 0)
//		if (sec % 2 == 0)
		{
			for (int i = 0; i < fishes.size(); i++)
			{
				if (!fishes.get(i).getFish().isDead())
				{
					if (fishes.get(i).getFish().changeExcretion(EXCRETION_RISE))
					{
						background.changepHValue(EXCRETION_PH);
						background.changeCleanliness(EXCRETION_CLEANLINESS);
					}
				}
			}
		}
	}
	
	/*
	   魚：
	         魚種衝突：紅十字、非洲慈鯛會攻擊其他所有魚種，若此兩種養在一起，體重大
	                            者會攻擊體重小者，被攻擊者生命力每秒下降1-3
	*/
	public void conflict(int sec)
	{
		if(conflict == true)
		{
			for (int i = 0; i < fishes.size(); i++)
			{
				if(i != conflictId)
				{
					fishes.get(i).getFish().setLife
					(fishes.get(i).getFish().getLife()-(random.nextDouble()));
				}
			}
		}
	}
	
	//找出是否有衝突的魚類    並知道哪一隻最重
	
	public void check(int sec)
	{
		double maxWeight=0;
		int id=0;
		for (int i = 0; i < fishes.size(); i++)
		{
			if(fishes.get(i).getFish().isAttack() == true)
			{
				conflict = true;
				if(fishes.get(i).getFish().getWeight()>maxWeight)
				{
					id = i;
					maxWeight = fishes.get(i).getFish().getWeight();
				}
			}
			else
			{
				conflict = false;
			}
		}
		conflictId = id;
	}
	
	//刪除魚類
	public void delete(int sec)
	{
		for (int i = 0; i < fishes.size(); i++)
		{
			if(fishes.get(i).btnX >=1400 && fishes.get(i).btnX <=1520 
					&& fishes.get(i).btnY >=165 && fishes.get(i).btnY <=210 )
			{
				fishes.remove(i);
			}
		}
	}

	/**
	 * 印出魚缸所有魚
	 */
	public void printFishes()
	{
		if (!haveFish())
			System.out.println("水族箱並沒有魚種\n");
		else
			for (int i = 0; i < fishes.size(); i++)
			{
				System.out.printf("魚%d\n", i);
				System.out.println(fishes.get(i));
			}
	}

	/**
	 * 判斷魚缸內是否有魚
	 */
	public boolean haveFish()
	{
		if (fishes.size() > 0)
			return true;
		return false;
	}
	
	public int getFishNum()
	{
		return fishes.size();
	}
	
	public int getDeadFishNum()
	{
		int num = 0;
		for (int i = 0; i < fishes.size(); i++)
		{
			if (fishes.get(i).getFish().isDead())
				num++;
		}
		return num;
	}
	
	public void setButton(SimulateInterface SI)
	{
		if (SI.getAutofeedCheck())
			pressAutoFeedBtn(SI.getAutofeedTime());
		else
			autoFeedBtn = false;
	}
}
