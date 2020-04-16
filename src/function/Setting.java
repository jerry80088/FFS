package function;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import background.Background;
import simulate.BackgroundTask;
import simulate.SimulateInterface;

public class Setting extends JPanel{
	Scanner scanner = new Scanner(System.in);
	
	public SimulateInterface SI;
	public JButton back;
	public JButton homepage;
	public Setting(JFrame frame, SimulateInterface SI) {		
		
		this.SI = SI;
		setLayout(null); 
		this.setOpaque(false); //把JPanel設置為透明 這樣就不會遮住後面的背景 這樣你就能在JPanel隨意加元件了
		
		ImageIcon setIcon = new ImageIcon(getClass().getResource("set.PNG"));
		setIcon.setImage(setIcon.getImage().getScaledInstance(600,150,Image.SCALE_DEFAULT));
		
		ImageIcon speedUpIcon = new ImageIcon(getClass().getResource("speedUp.PNG"));
		speedUpIcon.setImage(speedUpIcon.getImage().getScaledInstance(1200, 400,Image.SCALE_DEFAULT));
		
				
		ImageIcon backIcon = new ImageIcon(getClass().getResource("返回.PNG"));
		backIcon.setImage(backIcon.getImage().getScaledInstance(400,150,Image.SCALE_DEFAULT));
		
		ImageIcon returnHomepageIcon = new ImageIcon(getClass().getResource("homepage.PNG"));
		returnHomepageIcon.setImage(returnHomepageIcon.getImage().getScaledInstance(1200, 200,Image.SCALE_DEFAULT));
		
		ImageIcon _1xIcon = new ImageIcon(getClass().getResource("1X.PNG"));
		_1xIcon.setImage(_1xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _2xIcon = new ImageIcon(getClass().getResource("2X.PNG"));
		_2xIcon.setImage(_2xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _4xIcon = new ImageIcon(getClass().getResource("4X.PNG"));
		_4xIcon.setImage(_4xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _8xIcon = new ImageIcon(getClass().getResource("8X.PNG"));
		_8xIcon.setImage(_8xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _16xIcon = new ImageIcon(getClass().getResource("16X.PNG"));
		_16xIcon.setImage(_16xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _32xIcon = new ImageIcon(getClass().getResource("32X.PNG"));
		_32xIcon.setImage(_32xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _64xIcon = new ImageIcon(getClass().getResource("64X.PNG"));
		_64xIcon.setImage(_64xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _128xIcon = new ImageIcon(getClass().getResource("128X.PNG"));
		_128xIcon.setImage(_128xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _256xIcon = new ImageIcon(getClass().getResource("256X.PNG"));
		_256xIcon.setImage(_256xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		ImageIcon _512xIcon = new ImageIcon(getClass().getResource("512X.PNG"));
		_512xIcon.setImage(_512xIcon.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
		
		JLabel set = new JLabel(setIcon);
		set.setBounds(500,30,600,150); //設定位置、大小           
		add(set);
		
		JLabel speedUp = new JLabel(speedUpIcon);
		speedUp.setBounds(150, 180,1200, 400); //設定位置、大小        
		speedUp.setOpaque(false);
		
		
		back = new JButton(backIcon);
		back.setBorder(null);//不會繪製按鈕的邊
		back.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		back.setBounds(-100, 20,400,150); //設定位置、大小           
		add(back);
		
		homepage = new JButton(returnHomepageIcon);
		homepage.setBorder(null);//不會繪製按鈕的邊
		homepage.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		homepage.setBounds(15, 550, 1500, 300); //設定位置、大小             
		add(homepage);
		
		ButtonHandler handler = new ButtonHandler();
		
		JButton _1X = new JButton(_1xIcon);
		_1X.setBorder(null);//不會繪製按鈕的邊
		_1X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_1X.setBounds(500, 230, 120, 120);  //設定位置、大小           
		_1X.setActionCommand("1");
		_1X.addActionListener(handler);
		add(_1X);                      
		                                                 
		JButton _2X = new JButton(_2xIcon);  
		_2X.setBorder(null);//不會繪製按鈕的邊
		_2X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_2X.setBounds(650, 230, 120, 120);  //設定位置、大小         
		_2X.setActionCommand("2");
		_2X.addActionListener(handler);
		add(_2X);                    
		                                                  
		JButton _4X = new JButton(_4xIcon); 
		_4X.setBorder(null);//不會繪製按鈕的邊
		_4X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_4X.setBounds(800, 230, 120, 120); //設定位置、大小          
		_4X.setActionCommand("4");
		_4X.addActionListener(handler);
		add(_4X);                    
		                                                  
		JButton _8X = new JButton(_8xIcon); 
		_8X.setBorder(null);//不會繪製按鈕的邊
		_8X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_8X.setBounds(950, 230, 120, 120); //設定位置、大小          
		_8X.setActionCommand("8");
		_8X.addActionListener(handler);
		add(_8X);                    
		                                                   
		JButton _16X = new JButton(_16xIcon);
		_16X.setBorder(null);//不會繪製按鈕的邊
		_16X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_16X.setBounds(1100, 230, 120, 120); //設定位置、大小         
		_16X.setActionCommand("16");
		_16X.addActionListener(handler);
		add(_16X);     
		
		JButton _32X = new JButton(_32xIcon);
		_32X.setBorder(null);//不會繪製按鈕的邊
		_32X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_32X.setBounds(500, 400, 120, 120); //設定位置、大小             
		_32X.setActionCommand("32");
		_32X.addActionListener(handler);
		add(_32X);                      
                                                
		JButton _64X = new JButton(_64xIcon); 
		_64X.setBorder(null);//不會繪製按鈕的邊
		_64X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_64X.setBounds(650, 400, 120, 120); //設定位置、大小           
		_64X.setActionCommand("64");
		_64X.addActionListener(handler);
		add(_64X);                    
		                                                  
		JButton _128X = new JButton(_128xIcon);
		_128X.setBorder(null);//不會繪製按鈕的邊
		_128X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_128X.setBounds(800, 400, 120, 120); //設定位置、大小          
		_128X.setActionCommand("128");
		_128X.addActionListener(handler);
		add(_128X);                    
		                                                  
		JButton _256X = new JButton(_256xIcon); 
		_256X.setBorder(null);//不會繪製按鈕的邊
		_256X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_256X.setBounds(950, 400, 120, 120); //設定位置、大小          
		_256X.setActionCommand("256");
		_256X.addActionListener(handler);
		add(_256X);                    
		                                                   
		JButton _512X = new JButton(_512xIcon);
		_512X.setBorder(null);//不會繪製按鈕的邊
		_512X.setContentAreaFilled(false);//不會自行繪製按鈕的背景
		_512X.setBounds(1100, 400, 120, 120); //設定位置、大小         
		_512X.setActionCommand("512");
		_512X.addActionListener(handler);
		add(_512X);     
		add(speedUp);

	}
	
	private class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			SI.setSpeed(1000 / Integer.parseInt(e.getActionCommand()));
		}
	}
}