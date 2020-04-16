package simulate;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;

import javax.swing.*;

import database.DataBaseManager;
import background.Background;
import fish.Fish;
import fish.FishButton;
import function.BackgroundManager;
import function.FishManager;

public class SimulateInterface extends JPanel{
	private ArrayList<FishButton> fishButtons = new ArrayList<FishButton>();//fish
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	private DataBaseManager db = new DataBaseManager();
	private Background background;
	private BackgroundTask backgroundTask;
	private BackgroundManager backgroundManager;
	private FishManager fishManager;
	public JButton set;
	public JButton fishStore;
	public JButton instruction;
	private JButton autofeed;
	private JButton changeTem;
	private JButton inflator;
	private JButton filter;
	public JComboBox autofeedComboBox;
	public JComboBox temComboBox;
	private JPanel buttonSet;
	
	private JTextField temText ;
	private JTextField phText ;
	private JTextField oxygenContentText ;
	private JTextField cleanlinessText ;
	private JTextField seasonText ;
	private JTextField dateText ;
	private JTextField timeText ;
	
	private ImageIcon inflatorIcon;
	private ImageIcon newInflatorIcon;
	private ImageIcon filterIcon;
	private ImageIcon newFilterIcon;
	private ImageIcon changeTemIcon;
	private ImageIcon newChangeTemIcon;
	private ImageIcon autofeedIcon;
	private ImageIcon newAutofeedIcon;
	
	public boolean isInflatorCheck() {
		return inflatorCheck;
	}
	public void setInflatorCheck(boolean inflatorCheck) {
		this.inflatorCheck = inflatorCheck;
	}
	public boolean isFilterCheck() {
		return filterCheck;
	}
	public void setFilterCheck(boolean filterCheck) {
		this.filterCheck = filterCheck;
	}
	public boolean isChangeTemCheck() {
		return changeTemCheck;
	}
	public void setChangeTemCheck(boolean changeTemCheck) {
		this.changeTemCheck = changeTemCheck;
	}
	public boolean isAutofeedCheck() {
		return autofeedCheck;
	}
	public void setAutofeedCheck(boolean autofeedCheck) {
		this.autofeedCheck = autofeedCheck;
	}
	private boolean inflatorCheck = false;//判斷該打氣機是否已經點擊
	private boolean filterCheck = false;//判斷過濾機是否已經點擊
	private boolean changeTemCheck = false;//判斷調溫器是否已經點擊
	private boolean autofeedCheck = false;//判斷自動餵食是否已經點擊
	
	private int tem;//要調溫成tem度
	private int autofeedTime;//要自動餵食多久
	
	private final String[] tempList = {"20°C ","21°C","22°C ","23°C ","24°C ","25°C "
			,"26°C ","27°C ","28°C ","29°C"};
	private final String[] autofeedTimeList = {"1:00","2:00","3:00","4:00","5:00","6:00","7:00","8:00","9:00","10:00"};
	
	Random random = new Random();
	
	private Timer timer;
	public SimulateInterface(){
		
	}
	public SimulateInterface(JFrame frame)
	{
		tem = 20;//初始調溫器時的溫度，因為如果還沒有選擇comboBox裡的任一item，則會讀不到值
		autofeedTime = 60;//初始自動餵食的時間，因為如果還沒有選擇comboBox裡的任一item，則會讀不到值
		
		
		setLayout(null);
		//setBackground(frame);
		this.setOpaque(false);
		
		//放模擬介面中的Button
		buttonSet = new JPanel();
		buttonSet.setLayout(new GridLayout(12,1));
		//放模擬介面中的背景參數
		JPanel textSet = new JPanel();
        textSet.setLayout(new GridLayout(2,7));

		temText = new JTextField("溫度:25.5°C");
		phText = new JTextField("pH值:7.0");
		oxygenContentText = new JTextField("含氧量:7.0ppm");
		cleanlinessText = new JTextField("乾淨度:95%");
		seasonText = new JTextField("季節:冬");
		dateText = new JTextField("日期:2017.11.11");
		timeText = new JTextField("時間:10:10");
		
        background = new Background();
		fishManager = new FishManager(fishButtons, background);
		backgroundManager = new BackgroundManager(background, fishManager);
        backgroundTask = new BackgroundTask(background, fishManager, backgroundManager, this);
        
        timer = new Timer();
		timer.schedule(backgroundTask, new Date(), 1000);
		
		
		temText.setFont(new Font("",Font.BOLD,45));
		temText.setBorder(null);//不繪製按鈕的邊
		temText.setOpaque(false);
		temText.setEditable(false);
		textSet.add(temText);

		phText.setFont(new Font("",Font.BOLD,45));
		phText.setBorder(null);//不繪製按鈕的邊
		phText.setOpaque(false);
		phText.setEditable(false);
		textSet.add(phText);

		oxygenContentText.setFont(new Font("",Font.BOLD,45));
		oxygenContentText.setBorder(null);//不繪製按鈕的邊
		oxygenContentText.setOpaque(false);
		oxygenContentText.setEditable(false);
		textSet.add(oxygenContentText);

		cleanlinessText.setFont(new Font("",Font.BOLD,45));
		cleanlinessText.setBorder(null);//不繪製按鈕的邊
		cleanlinessText.setOpaque(false);
		cleanlinessText.setEditable(false);
		textSet.add(cleanlinessText);

		seasonText.setFont(new Font("",Font.BOLD,45));
		seasonText.setBorder(null);//不繪製按鈕的邊
		seasonText.setOpaque(false);
		seasonText.setEditable(false);
		textSet.add(seasonText);

		
		dateText.setFont(new Font("",Font.BOLD,45));
		dateText.setBorder(null);//不繪製按鈕的邊
		dateText.setOpaque(false);
		dateText.setEditable(false);
		textSet.add(dateText);

		timeText.setFont(new Font("",Font.BOLD,45));
		timeText.setBorder(null);//不繪製按鈕的邊
		timeText.setOpaque(false);
		timeText.setEditable(false);
		textSet.add(timeText);

		ImageIcon setIcon = new ImageIcon(getClass().getResource("設定.PNG"));
		setIcon.setImage(setIcon.getImage().getScaledInstance(90,65,Image.SCALE_DEFAULT));
		
		ImageIcon fishStoreIcon = new ImageIcon(getClass().getResource("魚店.PNG"));
		fishStoreIcon.setImage(fishStoreIcon.getImage().getScaledInstance(80,55,Image.SCALE_DEFAULT));
		
		ImageIcon trashcanIcon = new ImageIcon(getClass().getResource("垃圾桶.PNG"));
		trashcanIcon.setImage(trashcanIcon.getImage().getScaledInstance(80,55,Image.SCALE_DEFAULT));
		
		ImageIcon feedIcon = new ImageIcon(getClass().getResource("餵食.PNG"));
		feedIcon.setImage(feedIcon.getImage().getScaledInstance(130,80,Image.SCALE_DEFAULT));
		
		autofeedIcon = new ImageIcon(getClass().getResource("自動餵食.PNG"));
		autofeedIcon.setImage(autofeedIcon.getImage().getScaledInstance(110,60,Image.SCALE_DEFAULT));
		newAutofeedIcon = new ImageIcon(getClass().getResource("自動餵食-2.PNG"));
		newAutofeedIcon.setImage(newAutofeedIcon.getImage().getScaledInstance(105,55,Image.SCALE_DEFAULT));
		
		ImageIcon changeWaterIcon = new ImageIcon(getClass().getResource("換水.PNG"));
		changeWaterIcon.setImage(changeWaterIcon.getImage().getScaledInstance(110,60,Image.SCALE_DEFAULT));
		
		inflatorIcon = new ImageIcon(getClass().getResource("打氣機.PNG"));
		inflatorIcon.setImage(inflatorIcon.getImage().getScaledInstance(115,65,Image.SCALE_DEFAULT));
		newInflatorIcon = new ImageIcon(getClass().getResource("打氣機-2.PNG"));
		newInflatorIcon.setImage(newInflatorIcon.getImage().getScaledInstance(100,55,Image.SCALE_DEFAULT));
		
		changeTemIcon = new ImageIcon(getClass().getResource("調溫器.PNG"));
		changeTemIcon.setImage(changeTemIcon.getImage().getScaledInstance(110,60,Image.SCALE_DEFAULT));
		newChangeTemIcon = new ImageIcon(getClass().getResource("調溫器-2.PNG"));
		newChangeTemIcon.setImage(newChangeTemIcon.getImage().getScaledInstance(100,55,Image.SCALE_DEFAULT));
		
		filterIcon = new ImageIcon(getClass().getResource("過濾機.PNG"));
		filterIcon.setImage(filterIcon.getImage().getScaledInstance(110,60,Image.SCALE_DEFAULT));
		newFilterIcon = new ImageIcon(getClass().getResource("過濾機-2.PNG"));
		newFilterIcon.setImage(newFilterIcon.getImage().getScaledInstance(105,55,Image.SCALE_DEFAULT));
		
		ImageIcon instructionIcon = new ImageIcon(getClass().getResource("說明.PNG"));
		instructionIcon.setImage(instructionIcon.getImage().getScaledInstance(110,60,Image.SCALE_DEFAULT));
		
		//設定
		set = new JButton(setIcon);
		set.setBorder(null);//不繪製按鈕的邊
		set.setContentAreaFilled(false);//不會自行繪製按鈕背景
		set.setFocusable(false);
		setIcon = new ImageIcon(getClass().getResource("設定-2.PNG"));
		setIcon.setImage(setIcon.getImage().getScaledInstance(85,60,Image.SCALE_DEFAULT));
		set.setRolloverIcon(setIcon);
		buttonSet.add(set);
		
		//魚店
		fishStore = new JButton(fishStoreIcon);
		fishStore.setBorder(null);//不繪製按鈕的邊
		fishStore.setContentAreaFilled(false);//不會自行繪製按鈕背景
		fishStore.setFocusable(false);
		fishStoreIcon = new ImageIcon(getClass().getResource("魚店-2.PNG"));
		fishStoreIcon.setImage(fishStoreIcon.getImage().getScaledInstance(75,50,Image.SCALE_DEFAULT));
		fishStore.setRolloverIcon(fishStoreIcon);
		buttonSet.add(fishStore);
		
		//垃圾桶
		JButton trashcan = new JButton(trashcanIcon);
		trashcan.setBorder(null);//不繪製按鈕的邊
		trashcan.setContentAreaFilled(false);//不會自行繪製按鈕背景
		trashcan.setFocusable(false);
		buttonSet.add(trashcan);
		
		//手動餵食
		JButton feed = new JButton(feedIcon);
		feed.setBorder(null);//不繪製按鈕的邊
		feed.setContentAreaFilled(false);//不會自行繪製按鈕背景
		feed.setFocusable(false);
		feedIcon = new ImageIcon(getClass().getResource("餵食-2.PNG"));
		feedIcon.setImage(feedIcon.getImage().getScaledInstance(125,75,Image.SCALE_DEFAULT));
		feed.setRolloverIcon(feedIcon);
		feed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fishManager.feed();
			}
		});
		buttonSet.add(feed);
		
		//自動餵食
		autofeed = new JButton(autofeedIcon);
		autofeed.setBorder(null);//不繪製按鈕的邊
		autofeed.setContentAreaFilled(false);//不會自行繪製按鈕背景
		autofeed.setFocusable(false);
		autofeed.setRolloverIcon(newAutofeedIcon);
		autofeedComboBox = new JComboBox(autofeedTimeList) ;
		autofeedComboBox.setFont(new Font("標楷體", Font.BOLD, 30));
		autofeedComboBox.setBackground(Color.lightGray);
		autofeedComboBox.setMaximumRowCount(3);
		autofeedComboBox.addItemListener(
			new ItemListener() {
				@Override
				public void  itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						autofeedTime = 60*Integer.parseInt(autofeedTimeList
								[autofeedComboBox.getSelectedIndex()].split(":")[0]);
					}
				}
				
			}
		);
		autofeed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fishManager.pressAutoFeedBtn(autofeedTime);
				if(autofeedCheck) {
					autofeed.setIcon(autofeedIcon);
					autofeedCheck = false;
				}
				else {
					autofeed.setIcon(newAutofeedIcon);
					autofeedCheck = true;
				}
			}
		});
		buttonSet.add(autofeedComboBox);
		buttonSet.add(autofeed);
		
		//換水
		JButton changeWater = new JButton(changeWaterIcon);
		changeWater.setBorder(null);//不繪製按鈕的邊
		changeWater.setContentAreaFilled(false);//不會自行繪製按鈕背景
		changeWaterIcon = new ImageIcon(getClass().getResource("換水-2.PNG"));
		changeWaterIcon.setImage(changeWaterIcon.getImage().getScaledInstance(100,55,Image.SCALE_DEFAULT));
		changeWater.setFocusable(false);
		changeWater.setRolloverIcon(changeWaterIcon);
		changeWater.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroundManager.changeWater();
			}
		});
		buttonSet.add(changeWater);
		
		//打氣機
		inflator = new JButton(inflatorIcon);
		inflator.setBorder(null);//不繪製按鈕的邊
		inflator.setContentAreaFilled(false);//不會自行繪製按鈕背景
		inflator.setFocusable(false);
		inflator.setRolloverIcon(newInflatorIcon);
		inflator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroundManager.pressInflatorBtn();
				if(inflatorCheck) {
					inflator.setIcon(inflatorIcon);
					inflatorCheck = false;
				}
				else {
					inflator.setIcon(newInflatorIcon);
					inflatorCheck = true;
				}
			}
		});
		buttonSet.add(inflator);
		
		//調溫器
		changeTem = new JButton(changeTemIcon);
		changeTem.setBorder(null);//不繪製按鈕的邊
		changeTem.setContentAreaFilled(false);//不會自行繪製按鈕背景
		changeTem.setFocusable(false);
		changeTem.setRolloverIcon(newChangeTemIcon);
		temComboBox = new JComboBox(tempList) ;
		temComboBox.setFont(new Font("標楷體", Font.BOLD, 30));
		temComboBox.setBackground(Color.lightGray);
		temComboBox.setMaximumRowCount(3);
		temComboBox.addItemListener(
			new ItemListener() {
				@Override
				public void  itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						tem = Integer.parseInt(tempList[temComboBox.getSelectedIndex()].split("°")[0]);
					}
				}
			}
		);
		
		changeTem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroundManager.pressThermostatBtn(tem);
				if(changeTemCheck) {
					changeTem.setIcon(changeTemIcon);
					changeTemCheck = false;
				}
				else {
					changeTem.setIcon(newChangeTemIcon);
					changeTemCheck = true;
				}
			}
		});
		buttonSet.add(changeTem);
		buttonSet.add(temComboBox);
		
		//過濾機
		filter = new JButton(filterIcon);
		filter.setBorder(null);//不繪製按鈕的邊
		filter.setContentAreaFilled(false);//不會自行繪製按鈕背景
		filter.setFocusable(false);
		filter.setRolloverIcon(newFilterIcon);
		filter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroundManager.pressFilter();
				if(filterCheck) {
					filter.setIcon(filterIcon);
					filterCheck = false;
				}
				else {
					filter.setIcon(newFilterIcon);
					filterCheck = true;
				}
			}
		});
		buttonSet.add(filter);
		
		//說明
		instruction = new JButton(instructionIcon);
		instruction.setBorder(null);//不繪製按鈕的邊
		instruction.setContentAreaFilled(false);//不會自行繪製按鈕背景
		instructionIcon = new ImageIcon(getClass().getResource("說明-2.PNG"));
		instructionIcon.setImage(instructionIcon.getImage().getScaledInstance(100,55,Image.SCALE_DEFAULT));
		instruction.setFocusable(false);
		instruction.setRolloverIcon(instructionIcon);
		buttonSet.add(instruction);
		
		textSet.setOpaque(false);
		textSet.setBounds(0,0,1500,150);
		this.add(textSet);
		buttonSet.setOpaque(false);
		buttonSet.setBounds(1400,50,120,750);
		this.add(buttonSet);
		
	}
	//取得魚的ArrayList
	public ArrayList<FishButton> getFish() {
		return fishButtons;
	}
	
	public void setFish(ArrayList<Fish> fishes)
	{
		db.deleteFishTable();
		for(int i = 0; i < fishes.size(); i++)
		{
			FishButton fishButton = new FishButton(fishes.get(i));
			add(fishButton);
			fishButtons.add(fishButton);
		}
		
	}
	
	//將魚的Button加入模擬介面
	public void addFishToInterFace() {
		for(FishButton fishButton:fishButtons) {
			add(fishButton);
		}
	}
	
	public void removeFish()
	{
		for (int i = 0; i < fishButtons.size(); i++)
		{
			if(fishButtons.get(i).btnX >=1280 && fishButtons.get(i).btnX <=1500 
					&& fishButtons.get(i).btnY >=80 && fishButtons.get(i).btnY <=170 
					)
			{
				db.deleteFishTable(fishButtons.get(i).getFish().getName());
				this.remove(fishButtons.get(i));
				fishButtons.remove(i);
				repaint();
			}
		}
	}
	//設定背景參數
	public void setBackgroundInfo()
	{
		temText.setText(String.format("溫度:%.1f°C", background.getTemperature()));
		phText.setText(String.format("pH值:%.1f", background.getpHValue()));
		oxygenContentText.setText(String.format("含氧量:%.1fPPM", background.getOxygenContent()));
		cleanlinessText.setText(String.format("乾淨度:%.1f%c", background.getCleanliness(), 37));
		seasonText.setText(String.format("季節:%s", background.getSeason().getName()));
		dateText.setText(String.format("日期:%tF%n", background.getDate()));
		timeText.setText(String.format("時間:%tT%n", background.getTime()));
	}
	
	public void changeBackgroundInfo(Background background)
	{
		this.background = background;
		fishManager = new FishManager(fishButtons, background);
		fishManager.setButton(this);
		backgroundManager = new BackgroundManager(background, fishManager);
		backgroundManager.setButton(this);
        backgroundTask = new BackgroundTask(background, fishManager, backgroundManager, this);
		long changeTime = (this.background.getNowTime() - this.background.getEndTime()) / 1000;
		
		for (long i = 0; i < changeTime; i++)
			backgroundTask.growingInfo((int)i);
	}
	public int getTem() {
		return tem;
	}
	public void setTem(int tem) {
		this.tem = tem;
	}
	public int getAutofeedTime() {
		return autofeedTime;
	}
	public void setAutofeedTime(int autofeedTime) {
		this.autofeedTime = autofeedTime;
	}
	//設定速度
	public void setSpeed(int speed)
	{
		Date date = background.getDate();
		backgroundTask.cancel();
		backgroundTask = new BackgroundTask(background, fishManager, backgroundManager, this, date);
		timer = new Timer();
		timer.schedule(backgroundTask,new Date(),speed);
	}
	
	public void cancelSpeed()
	{
		backgroundTask.cancel();
	}
	public Background getB(){
		return background;
	}

	public void setInflatorCheck(){
		if(inflatorCheck) {
			inflator.setIcon(newInflatorIcon);
			backgroundManager.setInflatorBtn(true);
			inflatorCheck = true;
		}
		else {
			inflator.setIcon(inflatorIcon);
			backgroundManager.setInflatorBtn(false);
			inflatorCheck = false;
		}
	}
	public void setFilterCheck(){
		if(filterCheck) {
			filter.setIcon(newFilterIcon);
			backgroundManager.setFilterBtn(true);
			filterCheck = true;
		}
		else {
			filter.setIcon(filterIcon);
			backgroundManager.setFilterBtn(false);
			filterCheck = false;
		}
	}
	public void setChangeTemCheck(){
		if(changeTemCheck) {
			changeTem.setIcon(newChangeTemIcon);
			backgroundManager.setThermostatBtn(true);
			changeTemCheck = true;
		}
		else {
			changeTem.setIcon(changeTemIcon);
			backgroundManager.setThermostatBtn(false);
			changeTemCheck = false;
		}
	}
	public void setAutofeedCheck(){
		if(autofeedCheck) {
			autofeed.setIcon(newAutofeedIcon);
			fishManager.setAutoFeedBtn(true);
			autofeedCheck = true;
		}
		else {
			autofeed.setIcon(autofeedIcon);
			fishManager.setAutoFeedBtn(false);
			autofeedCheck = false;
		}
	}
	
	public boolean getInflatorCheck()
	{
		return inflatorCheck;
	}

	public boolean getFilterCheck()
	{
		return filterCheck;
	}
	
	public boolean getChangeTemCheck()
	{
		return changeTemCheck;
	}
	
	public boolean getAutofeedCheck()
	{
		return autofeedCheck;
	}
}