package database;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import background.Background;
import fish.Fish;
import simulate.SimulateInterface;

public class DataBaseManager {
	private String selectFishSQL = "select * from fish ";
	private String deleteFishSQL = "delete from fish where name = ?";
	private String updateFishSQL = "update fish set category = ?,age = ?,gender = ?,satiation = ?,length = ?,weight = ?,excretion = ?,sick = ?,life = ? where name = ?";
	private String insertFishSQL = "insert into fish (name,category,age,gender,satiation,length,weight,excretion,sick,life) value(?,?,?,?,?,?,?,?,?,?)";
	private String selectInitialFishSQL = "select * from initial_fish where category = ?";
	private String deleteBackgroundSQL = "delete  from background ";
	private String insertBackgroundSQL = "insert into background (temperature,pHValue,oxygenContent,cleanliness,season,date,time,endTime) value(?,?,?,?,?,?,?,?)";
	private String selectBackgroundSQL = "select * from background ";
	private String deleteButtonSQL = "delete  from button ";
	private String insertButtonSQL = "insert into button (autoFeed,autoFeedTime,inflator,changeTem,changeTemDegree,filter) value(?,?,?,?,?,?)";
	private String selectButtonSQL = "select * from button ";
	private Connection con = null;
	private Statement stat = null;
	private ResultSet result = null;
	private PreparedStatement pst = null; 
	
	private Random random = new Random();
	
	public  DataBaseManager(){
		try {
			Class.forName("com.mysql.jdbc.Driver");//註冊Driver
			con = DriverManager.getConnection("jdbc:mysql://localhost/ffs?useUnicode=true&characterEncoding=Big5", "root", "");//取得connection
			
		}
		catch(ClassNotFoundException e){
			System.out.println("DriverClassNotFound"+e.toString());
		}
		catch(SQLException x){
			System.out.println("Exception"+x.toString());
		}
	}
	public void insertButtonTable(SimulateInterface simulate){
		try {
			pst = con.prepareStatement(insertButtonSQL);
			pst.setBoolean(1,simulate.isAutofeedCheck());
			pst.setInt(2,simulate.getAutofeedTime());
			pst.setBoolean(3,simulate.isInflatorCheck());
			pst.setBoolean(4,simulate.isChangeTemCheck());
			pst.setInt(5,simulate.getTem());
			pst.setBoolean(6,simulate.isFilterCheck());
			pst.executeUpdate();
		}
		catch(SQLException x){
			System.out.println("Exception insert"+x.toString());
		}
		finally {
			Close();
		}
	}
	public void selectButtonTable(SimulateInterface simulate) {
		try {
			stat = con.createStatement();
			result = stat.executeQuery(selectButtonSQL);
			 while(result.next()) 
		     { 

				 simulate.setAutofeedCheck(result.getBoolean("autoFeed"));
				 simulate.setAutofeedTime(result.getInt("autoFeedTime"));
				 simulate.setInflatorCheck(result.getBoolean("inflator"));
				 simulate.setChangeTemCheck(result.getBoolean("changeTem"));
				 simulate.setTem(result.getInt("changeTemDegree"));
				 simulate.setFilterCheck(result.getBoolean("filter"));
				 
		     }
		}
			 catch(SQLException x){
			System.out.println("Exception select"+x.toString());
		}
		finally {
			Close();
		}
	}
	public void deleteButtonTable() {
		try {
			pst = con.prepareStatement(deleteButtonSQL);
			pst.executeUpdate();
		}
		catch(SQLException x){
			System.out.println("Exception delete"+x.toString());
		}
		finally {
			Close();
		}
	}
	
	public void insertFishTable(Fish fish){
		try {
			pst = con.prepareStatement(insertFishSQL);
			pst.setString(1,fish.getName());
			pst.setString(2,fish.getCategory());
			pst.setInt(3,fish.getAge());
			pst.setString(4,fish.getGender());
			pst.setInt(5,fish.getSatiation());
			pst.setDouble(6,fish.getLength());
			pst.setDouble(7,fish.getWeight());
			pst.setInt(8,fish.getExcretion());
			pst.setBoolean(9,fish.getSick());
			pst.setDouble(10,fish.getLife());
			pst.executeUpdate();
		}
		catch(SQLException x){
			System.out.println("Exception insert"+x.toString());
		}
		finally {
			Close();
		}
	}
	public  ArrayList<Fish> selectFishTable() {
		ArrayList<Fish> fish = new ArrayList<Fish>();
		try {
			stat = con.createStatement();
			result = stat.executeQuery(selectFishSQL);
			 while(result.next()) 
		     { 
				 Fish tempFish = new Fish();
				 tempFish.setName(result.getString("name"));
				 tempFish.setAge(result.getInt("age"));
				 tempFish.setSatiation(result.getInt("satiation"));
				 tempFish.setLength(result.getDouble("length"));
				 tempFish.setWeight(result.getDouble("weight"));
				 tempFish.setExcretion(result.getInt("excretion"));
				 tempFish.setSick(result.getBoolean("sick"));
				 tempFish.setLife(result.getDouble("life"));
				 tempFish.setCategory(result.getString("category"));
				 tempFish.setGender(result.getString("gender"));
				 fish.add(tempFish);
		     }
			
		}
			 catch(SQLException x){
			System.out.println("Exception select"+x.toString());
		}
		finally {
			Close();
		}
		 return fish;
	}
	public void deleteFishTable(String name) {
		try {
			pst = con.prepareStatement(deleteFishSQL);
			pst.setString(1,name);
			pst.executeUpdate();
		}
		catch(SQLException x){
			System.out.println("Exception delete"+x.toString());
		}
		finally {
			Close();
		}
	}
	public void deleteBackgroundTable() {
		try {
			pst = con.prepareStatement(deleteBackgroundSQL);
			pst.executeUpdate();
		}
		catch(SQLException x){
			System.out.println("Exception delete"+x.toString());
		}
		finally {
			Close();
		}
	}
	public void updateFishTable(Fish fish){
		try {
			pst = con.prepareStatement(updateFishSQL);
			pst.setString(10,fish.getName());
			pst.setString(1,fish.getCategory());
			pst.setInt(2,fish.getAge());
			pst.setString(3,fish.getGender());
			pst.setInt(4,fish.getSatiation());
			pst.setDouble(5,fish.getLength());
			pst.setDouble(6,fish.getWeight());
			pst.setInt(7,fish.getExcretion());
			pst.setBoolean(8,fish.getSick());
			pst.setDouble(9,fish.getLife());
			pst.executeUpdate();
		}
		catch(SQLException x){
			System.out.println("Exception update"+x.toString());
		}
		finally {
			Close();
		}
	}
	public void selectInitialFishTable(Fish fish) {
		try {
			pst = con.prepareStatement(selectInitialFishSQL);
			pst.setString(1,fish.getCategory());
			result = pst.executeQuery();
			 while(result.next()) 
		     { 
				 fish.setLength(result.getInt("length") + random.nextDouble() * 3);
				 fish.setWeight(result.getInt("weight") + random.nextDouble() * 30);
				 fish.setSuitableMaxTemperature(result.getDouble("temperature_max"));
				 fish.setSuitableMinTemperature(result.getDouble("temperature_min"));
				 fish.setSuitableMaxpH(result.getDouble("pHValue_max"));
				 fish.setSuitableMinpH(result.getDouble("pHValue_min"));
				 fish.setSUITABLE_OXYGENCONTENT(result.getDouble("oxygenContent"));
				 fish.setSUITABLE_CLEANLINESS(result.getInt("cleanliness"));
				 fish.setAttack(result.getBoolean("attack"));
				 
		     }
		}
			 catch(SQLException x){
			System.out.println("Exception select"+x.toString());
		}
		finally {
			Close();
		}
	}
	public void selectPartInitialFishTable(Fish fish) {
		try {
			pst = con.prepareStatement(selectInitialFishSQL);
			pst.setString(1,fish.getCategory());
			result = pst.executeQuery();
			 while(result.next()) 
		     { 
				 fish.setSuitableMaxTemperature(result.getDouble("temperature_max"));
				 fish.setSuitableMinTemperature(result.getDouble("temperature_min"));
				 fish.setSuitableMaxpH(result.getDouble("pHValue_max"));
				 fish.setSuitableMinpH(result.getDouble("pHValue_min"));
				 fish.setSUITABLE_OXYGENCONTENT(result.getDouble("oxygenContent"));
				 fish.setSUITABLE_CLEANLINESS(result.getInt("cleanliness"));
				 fish.setAttack(result.getBoolean("attack"));
				 
		     }
		}
			 catch(SQLException x){
			System.out.println("Exception select"+x.toString());
		}
		finally {
			Close();
		}
	}
	public void insertBackgroundTable(Background BG){
		try {
			pst = con.prepareStatement(insertBackgroundSQL);
			pst.setDouble(1,BG.getTemperature());
			pst.setDouble(2,BG.getpHValue());
			pst.setDouble(3,BG.getOxygenContent());
			pst.setDouble(4,BG.getCleanliness());
			pst.setString(5,BG.getSeason().toString());
			pst.setLong(6,BG.getDate().getTime());
			pst.setLong(7,BG.getDate().getTime());
			pst.setLong(8,BG.getNowTime());
			pst.executeUpdate();
		}
		catch(SQLException x){
			System.out.println("Exception insert"+x.toString());
		}
		finally {
			Close();
		}
	}
	public Background selectBackgroundTable() {
		Background BG = new Background();
		try {
			stat = con.createStatement();
			result = stat.executeQuery(selectBackgroundSQL);
			 while(result.next()) 
		     { 
				 BG.setTemperature(result.getDouble("temperature"));
				 BG.setpHValue(result.getDouble("pHValue"));
				 BG.setOxygenContent(result.getDouble("oxygenContent"));
				 BG.setCleanliness(result.getDouble("cleanliness"));
				 BG.setDate(result.getLong("date"));
				 BG.setTime(result.getLong("time"));
				 BG.setEndTime(result.getLong("endTime"));
				 
		     }
		}
			 catch(SQLException x){
			System.out.println("Exception select"+x.toString());
		}
		finally {
			Close();
		}
		return BG;
	}
	private void Close() {
		try {
			if(result!=null) {
				result.close();
				result = null;
			}
			if(stat!=null) {
				stat.close();
				stat = null;
			}
			if(pst!=null) {
				pst.close();
				pst = null;
			}
		}
		catch(SQLException e) {
			System.out.println("Close Exception :" + e.toString()); 
		}
		
	} 
	private String deleteAllFishSQL = "delete from fish";
	 public void deleteFishTable() {
	  try {
	   pst = con.prepareStatement(deleteAllFishSQL);
	   pst.executeUpdate();
	  }
	  catch(SQLException x){
	   System.out.println("Exception delete"+x.toString());
	  }
	  finally {
	   Close();
	  }
	 }
}
