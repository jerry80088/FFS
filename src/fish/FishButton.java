package fish;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import simulate.SimulateInterface;

public class FishButton extends JButton{
	Random random = new Random();
	
	private Fish fish;
	
	public double btnX;//fish的X軸的位置
    public double btnY;//fish的Y軸的位置
    public double vx;//X軸一次的偏移量
    public double vy;//Y軸一次的偏移量
    Timer timer = new Timer();
    RunningButton RB;
    
    private int focusx = 0;  
    private int focusy = 0;
    
    private int xLength;
    private int yLength;
    
    private ImageIcon deadFishIcon;
	
    public FishButton(Fish fish)
    {
    	this.fish = fish;
    	this.fish.setPartInitFishInfo();
		setFishButton();
    }
    
	public FishButton(String name,String category,String gender,String age,SimulateInterface SI) {
		
		fish = new Fish(name,category,gender,age);
		setFishButton();
	}
	//取得Fish物件，用來呼叫Fish裡的method
	public Fish getFish()
	{
		return fish;
	}
	
	public void setFishButton()
	{
		xLength = 150 + (int)fish.getLength()*10;
		yLength = 150 + (int)fish.getLength()*10;
		btnX = random.nextInt(1200);
        btnY = 150+random.nextInt(500);
        vx = Math.sin(1) * 5;
        vy = Math.cos(1) * 5;
        
        ImageIcon fishIcon = new ImageIcon(getClass().getResource(fish.getCategory()+".PNG"));
		fishIcon.setImage(fishIcon.getImage().getScaledInstance(xLength,yLength,Image.SCALE_DEFAULT));
		deadFishIcon = new ImageIcon(getClass().getResource("死"+fish.getCategory()+".PNG"));
		deadFishIcon.setImage(deadFishIcon.getImage().getScaledInstance(xLength,yLength,Image.SCALE_DEFAULT));
		setIcon(fishIcon);
		this.setBorder(null);//不繪製按鈕的邊
		this.setContentAreaFilled(false);//不會自行繪製按鈕背景
		this.setBounds((int)btnX,(int)btnY,xLength,yLength);
		
		
		this.addMouseListener(new MouseAdapter() {
        	@Override
            public void mousePressed(MouseEvent event) {
        		focusx = event.getX();  
                focusy = event.getY(); 
            }
        });
		this.addMouseMotionListener(new MouseMotionAdapter() {
    			@Override
    			public void mouseDragged(MouseEvent event) {
    				Container container = getParent();  
    	            int w = container.getWidth();
    	            int h = container.getHeight();  
    	  
    	            btnX = getX() + event.getX() - focusx;  
    	            btnY = getY() + event.getY() - focusy;  
    	  
    	            if (btnX +getWidth() > w) {  
    	            	btnX = w - getWidth();  
    	            }  
    	            if (btnY + getHeight() > h) {  
    	            	btnY = h - getHeight();  
    	            }  
    	            if (btnX < 0) {  
    	            	btnX = 0;  
    	            }  
    	            if (btnY < 0) {  
    	            	btnY = 0;  
    	            }  
    	  
    	            setLocation((int)btnX, (int)btnY);  

    	            container.repaint();  
    	            container = null;  
    			}
    		}
    	);
		this.addMouseListener(new MouseAdapter() {
    		@Override
    	    public void mouseEntered(MouseEvent event) {
    			RB.cancel();     
    			setToolTipText(fish.toString());
    	    }
    	});
		this.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseExited(MouseEvent event) {
    			RB = new RunningButton();
    			timer.schedule(RB, 0, 200);
                double theta = Math.random() * 2 * Math.PI;
                vx = Math.sin(theta) * 5;
                vy = Math.cos(theta) * 5;
			
                
            }
        });

    	RB = new RunningButton();
        timer.schedule(RB, 1000, 200);
	}

	//fish隨著時間移動位置
		class RunningButton extends TimerTask {
			private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			
	        public void run() {
	        	if (!fish.isDead()) {
	        		if (btnX + vx < 0) {
		                vx = -vx;
		            }
		            if (btnX + vx + xLength >= dimension.width-110) {
		                vx = -vx;
		            }
		            if (btnY + vy < 0) {
		                vy = -vy;
		            }
		            if (btnY + vy + yLength >= dimension.height) {
		                vy = -vy;
		            }
		            btnX += vx;
		            btnY += vy;
		            setBounds((int) btnX, (int) btnY, xLength, yLength);
	        	}
	        	else {
	        		setIcon(deadFishIcon);
	        	}
	        }
	    }
}
