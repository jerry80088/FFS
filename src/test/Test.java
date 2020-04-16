package test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import javax.swing.*;

import database.DataBaseManager;
import function.Instruction;
import homePage.HomePage;
import simulate.SimulateInterface;
import function.Setting;
import fishStore.FishStore;
import function.mp3Task;

public class Test extends JFrame implements WindowListener{
	private DataBaseManager db = new DataBaseManager();
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	JFrame jFrame = this;
	JPanel homePage = new HomePage(this);
	JPanel instructionHome = new Instruction(new Color(61, 89, 171),Color.WHITE, SwingConstants.VERTICAL);
	JPanel instructionSim = new Instruction(new Color(61, 89, 171),Color.WHITE, SwingConstants.VERTICAL);
	SimulateInterface simulateInterface = new SimulateInterface(this);
	JPanel setting = new Setting(this, simulateInterface);
	JPanel fishStore = new FishStore(this,simulateInterface.getFish(),simulateInterface);
	
	CardLayout cardLayout;
	
	public Test(){
		//Container c = this.getContentPane();
		this.addWindowListener(this);
		this.setLayout(new CardLayout());
		this.add(homePage, "homePage");
		this.add(instructionHome, "instructionHome");
		this.add(instructionSim, "instructionSim");
		this.add(simulateInterface, "simulateInterface");
		this.add(setting, "setting");
		this.add(fishStore, "fishStore");
		
		cardLayout = (CardLayout) this.getContentPane().getLayout();
		
		actionSetting();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(dimension.width,dimension.height);
		this.setVisible(true);
		//this.pack();
		this.show();
	}
	public void homePageStartAction() {
		//首頁Panel中的開始按鈕，點擊後，頁面會跳至模擬Panel
		((HomePage) homePage).start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				db.deleteFishTable();
				jFrame.remove(simulateInterface);
				jFrame.remove(homePage);
				jFrame.remove(instructionHome);
				jFrame.remove(instructionSim);
				jFrame.remove(setting);
				jFrame.remove(fishStore);
				homePage = new HomePage(jFrame);
				instructionHome = new Instruction(new Color(61, 89, 171),Color.WHITE, SwingConstants.VERTICAL);
				instructionSim = new Instruction(new Color(61, 89, 171),Color.WHITE, SwingConstants.VERTICAL);
				simulateInterface = new SimulateInterface(jFrame);
				setting = new Setting(jFrame, simulateInterface);
				fishStore = new FishStore(jFrame,simulateInterface.getFish(),simulateInterface);

				jFrame.add(homePage, "homePage");
				jFrame.add(instructionHome, "instructionHome");
				jFrame.add(instructionSim, "instructionSim");
				jFrame.add(simulateInterface, "simulateInterface");
				jFrame.add(setting, "setting");
				jFrame.add(fishStore, "fishStore");
				cardLayout = (CardLayout) jFrame.getContentPane().getLayout();
				actionSetting();

				cardLayout.show(jFrame.getContentPane(), "simulateInterface");
			}
		});
	}

	public void homePageContinueAction() {

		//首頁Panel中的繼續按鈕，點擊後，頁面會跳至模擬Panel，且繼續上次模擬狀態
		((HomePage) homePage).continuee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jFrame.remove(simulateInterface);
				jFrame.remove(homePage);
				jFrame.remove(instructionHome);
				jFrame.remove(instructionSim);
				jFrame.remove(setting);
				jFrame.remove(fishStore);
				homePage = new HomePage(jFrame);
				instructionHome = new Instruction(new Color(61, 89, 171),Color.WHITE, SwingConstants.VERTICAL);
				instructionSim = new Instruction(new Color(61, 89, 171),Color.WHITE, SwingConstants.VERTICAL);
				
				
				simulateInterface = new SimulateInterface(jFrame);
				 db.selectButtonTable(simulateInterface);
				 System.out.println(simulateInterface.isInflatorCheck());
				simulateInterface.setInflatorCheck();
				simulateInterface.setAutofeedCheck();
				simulateInterface.setFilterCheck();
				simulateInterface.setChangeTemCheck();
				simulateInterface.autofeedComboBox.setSelectedIndex((simulateInterface.getAutofeedTime()/60)-1);
				simulateInterface.temComboBox.setSelectedIndex(simulateInterface.getTem() % 20);
				simulateInterface.repaint();
				simulateInterface.setBackgroundInfo();
				
				simulateInterface.setFish(db.selectFishTable());
				setting = new Setting(jFrame, simulateInterface);
				fishStore = new FishStore(jFrame,simulateInterface.getFish(),simulateInterface);
				simulateInterface.changeBackgroundInfo(db.selectBackgroundTable());
				simulateInterface.setSpeed(1000);

				jFrame.add(homePage, "homePage");
				jFrame.add(instructionHome, "instructionHome");
				jFrame.add(instructionSim, "instructionSim");
				jFrame.add(simulateInterface, "simulateInterface");
				jFrame.add(setting, "setting");
				jFrame.add(fishStore, "fishStore");
				cardLayout = (CardLayout) jFrame.getContentPane().getLayout();
				actionSetting();
				
				cardLayout.show(jFrame.getContentPane(), "simulateInterface");
			}
		});
		
	}
	public void actionSetting() {
		homePageStartAction();
		homePageContinueAction();
		//首頁Panel中的說明按鈕，點擊後，頁面會跳至說明Panel
		((HomePage) homePage).instruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(jFrame.getContentPane(), "instructionHome");
			}
		});
		
		//說明Panel中的X，點擊會回到首頁Panel
		((Instruction) instructionHome).close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.previous(jFrame.getContentPane());
			}
		});
		//說明Panel中的return按鈕，點擊會回到首頁Panel
		((Instruction) instructionHome).comeBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.previous(jFrame.getContentPane());
			}
		});
		
		//說明Panel中的X，點擊會回到模擬Panel
		((Instruction) instructionSim).close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.next(jFrame.getContentPane());
			}
		});
		//說明Panel中的return按鈕，點擊會回到模擬Panel
		((Instruction) instructionSim).comeBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.next(jFrame.getContentPane());
			}
		});
		
		//模擬Panel中的設定按鈕，點擊後頁面跳至設定Panel
		((SimulateInterface) simulateInterface).set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(jFrame.getContentPane(), "setting");
			}
		});
		
		//模擬Panel中的魚店按鈕，點擊後頁面跳至魚店Panel
		((SimulateInterface) simulateInterface).fishStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(jFrame.getContentPane(), "fishStore");
			}
		});
		
		//模擬Panel中的說明按鈕，點擊後，頁面會跳至說明Panel
		((SimulateInterface) simulateInterface).instruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(jFrame.getContentPane(), "instructionSim");
			}
		});
		
		//說明Panel中的返回按鈕，點擊後，頁面回到模擬Panel
		((Setting) setting).back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(jFrame.getContentPane(), "simulateInterface");
			}
		});
		
		
		//說明Panel中的首頁按鈕，點擊後，頁面跳至首頁Panel
		((Setting) setting).homepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				db.deleteBackgroundTable();
				db.deleteButtonTable();
				for(int i = 0;i<simulateInterface.getFish().size();i++){
					db.updateFishTable(simulateInterface.getFish().get(i).getFish());
				}
				db.insertBackgroundTable(simulateInterface.getB());
				db.insertButtonTable(simulateInterface);
				cardLayout.show(jFrame.getContentPane(), "homePage");
			}
		});

		//說明Panel中的返回按鈕，點擊後，頁面跳至模擬Panel
		((FishStore) fishStore).back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(jFrame.getContentPane(), "simulateInterface");
			}
		});
	}
	public static void main(String[] args) {
		Timer timer = new Timer();
	    timer.schedule(new mp3Task(), 1000, 10);
		new Test();
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 自動產生的方法 Stub

		db.deleteBackgroundTable();
		db.deleteButtonTable();
		for(int i = 0;i<simulateInterface.getFish().size();i++){
			db.updateFishTable(simulateInterface.getFish().get(i).getFish());
		}
		db.insertBackgroundTable(simulateInterface.getB());
		db.insertButtonTable(simulateInterface);
		System.out.println("windowClosing");
        this.dispose();
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動產生的方法 Stub
		
	}

}
