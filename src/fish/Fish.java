package fish;

import java.util.Random;

import database.DataBaseManager;

public class Fish{
	public static final int AGE = 1036800;	//12天長一歲
	public static final int MAX_SATIATION = 100;		//飽食度上限
	public static final int MAX_EXCRETION = 100;		//排泄上限
	public static final int MAX_LIFE = 100;				//最大生命上限
	public static final int MIN_LIFE = 50;				//最小生命上限
	public static final int SUITABLE_SATIATION = 80;	//最適飽食度
	public static final int SICK_LIFE = 40;				//生病生命力
	
	public String[] fishCategory = {"紅十字魚","孔雀魚","小丑魚","斑馬魚","麗麗魚",
							"紅劍魚","寶蓮燈魚","紅綠燈魚","三角燈魚","銀屏燈魚",
							"迷你燈魚","紅龍魚","接吻魚","非洲慈鯛","黑尾紅月光"};
	public String[] fishGender = {"雄","雌"};
	Random random = new Random();
	private String name;			//名字
	private  String category;	//魚種
	private  String gender;	//性別
	private  String character;	//性格
	private int age;			//年齡	(非歲數)
	private int realAge;		//實際年齡	(歲數)

	private int satiation; 		//飽食度
	private int excretion; 		//排泄
	private double life;		//生命力
	private double maxLife;		//最大生命力
	private boolean sick;		//生病
	private boolean dead;		//死亡
	private double length;		//長度
	private double weight;		//重量
	private  double suitableMaxTemperature = 0;	//最大適溫
	private  double suitableMinTemperature = 0;	//最小適溫
	private  double suitableMaxpH = 0;			//最大適pH
	private  double suitableMinpH = 0;			//最小適pH
	private boolean attack;  
	public  int SUITABLE_CLEANLINESS = 0;	//最低乾淨度
	public  double SUITABLE_OXYGENCONTENT = 0;	//最低含氧量
	
	public boolean suitableTemperature = true;
	public boolean suitablepH = true;
	public boolean suitableCleanliness = true;
	public boolean suitableOxygenContent = true;
	public boolean starving = false;

	private DataBaseManager db = new DataBaseManager();
    private int categoryChoice;//將魚名換算成陣列的index
	private int genderChoice;//將魚的性別換算成陣列的index
	
	public Fish(){
		
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public Fish(String name,String category,String gender,String age)
	{
		//字串比對，換算成index
		for(categoryChoice = 0;categoryChoice < 15;categoryChoice++) {
			if(fishCategory[categoryChoice].equals(category))
				break;
		}
		for(genderChoice = 0;genderChoice < 2;genderChoice++) {
			if(fishGender[genderChoice].equals(gender))
				break;
		}
		//設初始值
		this.name = name;
		this.age = Integer.parseInt(age);
		this.category = fishCategory[categoryChoice];
		this.gender = fishGender[genderChoice];
		setRealAge();
		setMaxLife();
		this.character = "溫和";
		satiation = random.nextInt(30) + 60;   //%
		excretion = 0 ; //%
		sick = false;
		dead = false;
		life = 100 ;
		setInitFishInfo();
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getSuitableMaxTemperature() {
		return suitableMaxTemperature;
	}
	public void setSuitableMaxTemperature(double suitableMaxTemperature) {
		this.suitableMaxTemperature = suitableMaxTemperature;
	}
	public double getSuitableMinTemperature() {
		return suitableMinTemperature;
	}
	public void setSuitableMinTemperature(double suitableMinTemperature) {
		this.suitableMinTemperature = suitableMinTemperature;
	}
	public double getSuitableMaxpH() {
		return suitableMaxpH;
	}
	public void setSuitableMaxpH(double suitableMaxpH) {
		this.suitableMaxpH = suitableMaxpH;
	}
	public double getSuitableMinpH() {
		return suitableMinpH;
	}
	public void setSuitableMinpH(double suitableMinpH) {
		this.suitableMinpH = suitableMinpH;
	}
	public boolean isAttack() {
		return attack;
	}
	public void setAttack(boolean attack) {
		this.attack = attack;
	}
	public int getSUITABLE_CLEANLINESS() {
		return SUITABLE_CLEANLINESS;
	}
	public void setSUITABLE_CLEANLINESS(int sUITABLE_CLEANLINESS) {
		SUITABLE_CLEANLINESS = sUITABLE_CLEANLINESS;
	}
	public double getSUITABLE_OXYGENCONTENT() {
		return SUITABLE_OXYGENCONTENT;
	}
	public void setSUITABLE_OXYGENCONTENT(double sUITABLE_OXYGENCONTENT) {
		SUITABLE_OXYGENCONTENT = sUITABLE_OXYGENCONTENT;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}


	public int getSatiation() {
		return satiation;
	}

	public void setSatiation(int satiation) {
		this.satiation = satiation;
	}



	public int getExcretion() {
		return excretion;
	}

	public void setExcretion(int excretion) {
		this.excretion = excretion;
	}

	public void setSick(boolean sick) {
		this.sick = sick;
	}
	
	public boolean getSick()
	{
		return sick;
	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	
	

	public String getCharacter() {
		return character;
	}
	
	public boolean isDead()
	{
		return dead;
	}

	public void setDead(boolean dead)
	{
		this.dead = dead;
	}

	
	public void changeAge(int var)
	{
		age += var;
		setRealAge();
		setMaxLife();
	}
	
	public void changeLength(double var)
	{
		length += var;
	}
	
	public void changeWeight(double var)
	{
		weight += var;
	}

	public void changeSatiation(int var)
	{
		satiation += var;
		if (satiation > MAX_SATIATION)	//飽食度不超過100
			satiation = MAX_SATIATION;
		else if (satiation < 0)			//飽食度不小於0
			satiation = 0;
	}
	
	public void changeLife(double var)
	{
		if (sick && var < 0)life = life + var * 2;	//生病生命力下降兩倍
		else life += var;
		
		if (life > SICK_LIFE)	//生命力 > 40 健康
		{
			sick = false;
			if (life > maxLife)	//不超過最大生命
				life = maxLife;
		}
		else					//生命力 < 40 生病
		{
			sick = true;
			if (life < 0)		//生命力< 0 死亡
			{
				life = 0;
				dead = true;
			}
		}
	}
	
	public boolean changeExcretion(double var)
	{
		excretion += var;
		if (excretion >= MAX_EXCRETION)
		{
			excretion = 0;
			return true;
		}
		return false;
	}
	
	public int getRealAge()
	{
		return realAge;
	}
	
	public void setRealAge()
	{
		realAge = age / AGE;
	}
	
	public double getMaxLife()
	{
		return maxLife;
	}
	
	public void setMaxLife()
	{
	//MAX_LIFE= {100, 90, 80, 70, 60, 50};
		if (realAge == 0)
			maxLife = MAX_LIFE;
		else
		{
			maxLife = MAX_LIFE - (realAge - 1) / 10 * 10;
			if (maxLife < MIN_LIFE)
				maxLife = MIN_LIFE;
		}
	}
	
	public boolean inSuitableTemperature(double var)
	{
		if (var > suitableMaxTemperature || var < suitableMinTemperature)
			return false;
		return true;
	}
	
	public boolean inSuitablepH(double var)
	{
		if (var > suitableMaxpH || var < suitableMinpH)
			return false;
		return true;
	}

	public String isSick() 
	{
		if(sick == true)
			return "有";
		else
			return "否";
	}
	
	public boolean isStarving()
	{
		if (satiation == 0)
			return true;
		return false;
	}
	
	public void setInitFishInfo()
	{
		db.selectInitialFishTable(this);
		db.insertFishTable(this);
	}
	
	public void setPartInitFishInfo()
	{
		db.selectPartInitialFishTable(this);
		db.insertFishTable(this);
	}
	
	@Override
	public String toString(){
		if (dead == true)
			return ("姓名:"+ getName() +" 死亡");
		String temp = String.format("<html>姓名:%s    <br>魚種:%s    <br>年齡:%d歲        <br>性別:%s\n"
						   + "<br>飽食度:%d%c    <br>長度:%.5fcm    <br>重量:%.5fg    <br>排泄:%d%c\n"
						   + "<br>有無生病:%s    <br>生命力:%.0f<br>---------------",
				getName(),getCategory(),getRealAge(),getGender(),getSatiation(),37,length,weight,getExcretion(),37,isSick(),getLife());
		if (!suitableTemperature)temp += "<br>*不在適溫下(" + suitableMinTemperature + "~" + suitableMaxTemperature + ")*";
		if (!suitablepH) temp += "<br>*不在適pH下(" + suitableMinpH + "~" + suitableMaxpH + ")*";
		if (!suitableCleanliness) temp += "<br>*乾淨度過低*";
		if (!suitableOxygenContent) temp += "<br>*含氧量過低*";
		if (starving) temp += "<br>*挨餓中*";
		temp += "</html>";
		return temp;
	}
}