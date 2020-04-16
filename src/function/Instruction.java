package function;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;


//彈出的說明文件
public class Instruction extends JPanel{
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Color from_color;
	private Color to_color;
	private int mode;
	
	//Popup pop = null;
	
	//說明文字
	private String instructionString = 
			"1.設定按鈕提供時間加速功能：\r\n" + 
			"	有1X、2X、4X、8X、16X、32X、64X、128X、256、512X。\r\n" + 
			"2.餵食按鈕提供手動餵食功能：\r\n" + 
			"	點一次每一隻魚飽食度+5，最大值為100，當飽食度為100時\r\n" + 
			"	，多一次餵食，ph值上升0.1，乾淨度下降2%。\r\n" + 
			"3.自動餵食按鈕提供定時餵食，亦會有倒數器顯示：\r\n" + 
			"	可於設定中設定每多少分鐘餵食一次(飽食度變化同上)，可主動關閉。\r\n" + 
			"4.換水按鈕可以重置回正常數值的水：\r\n" + 
			"	直接將乾淨度設為100%，含氧量依溫度給予預設值如下，ph值設為7.0。\r\n" + 
			"	0-10度：11ppm 、 11-20：9ppm 、 21-30：7ppm 、 31-40：6ppm\r\n" + 
			"5.打氣機按鈕可以提高水中含氧量：\r\n" + 
			"	依溫度決定含氧量上升到最大值如下，速度為每小時+0.2ppm。\r\n" + 
			"	0-10度：11ppm 、 11-20：9ppm 、 21-30：7ppm 、 31-40：6ppm\r\n" + 
			"6.過濾機按鈕將使水的乾淨度和含氧量下降速率變慢：\r\n" + 
			"	乾淨度緩慢上升趨近90%，速度為每秒+0.01%，ph值趨近於7.0，每秒0.01。\r\n" + 
			"7.調溫器按鈕提供上調、下調溫度功能：\r\n" + 
			"	隨時間趨向設定之溫度，趨近速度為每秒0.001度。\r\n" + 
			"8.魚店按鈕提供增加新的飼養魚：\r\n" + 
			"	可以命名飼養魚、選魚種、性別、年齡。\r\n" + 
			"9.垃圾桶可將死魚拖曳置此而自魚缸刪除。\r\n";
	
	public JButton close;//close按鈕
	public JButton comeBack;//返回按鈕
	private JTextArea instructionWords;
	
	public Instruction(Color from_color, Color to_color, int mode) {
		this.setLayout(null);//不設定LayoutManager，因為要使用setBounds
		
		//初始化顏色;
		this.from_color = (from_color!=null)? from_color:Color.WHITE;
	    this.to_color = (to_color!=null)? to_color:Color.WHITE;
	    this.mode = (mode == SwingConstants.VERTICAL || mode == SwingConstants.HORIZONTAL)? mode:SwingConstants.VERTICAL;
	    //close的圖
		ImageIcon closeIcon = new ImageIcon(getClass().getResource("close.PNG"));
		closeIcon.setImage(closeIcon.getImage().getScaledInstance(40,45,Image.SCALE_DEFAULT));
		//返回的圖
		ImageIcon comeBackIcon = new ImageIcon(getClass().getResource("comeBack.PNG"));
		comeBackIcon.setImage(comeBackIcon.getImage().getScaledInstance(150,60,Image.SCALE_DEFAULT));
		
		close = new JButton(closeIcon);
		comeBack = new JButton(comeBackIcon);
		instructionWords = new JTextArea();
		
		close.setBorder(null);//不繪製按鈕的邊
		close.setContentAreaFilled(false);//不會自行繪製按鈕背景
		close.setFocusable(false);
		close.setBounds(1470,3,40,45);//固定位置及大小
		//滑鼠移到該Button上方時換成的圖片
		ImageIcon newClose = new ImageIcon(getClass().getResource("close-2.PNG")); 
		newClose.setImage(newClose.getImage().getScaledInstance(40,45,Image.SCALE_DEFAULT));
		close.setRolloverIcon(newClose);//滑鼠移到該Button上方時換成的圖片
		
		comeBack.setBorder(null);
		comeBack.setContentAreaFilled(false);//不會自行繪製按鈕背景
		comeBack.setFocusable(false);
		comeBack.setBounds(700,700,150,60);//固定位置及大小
		//滑鼠移到該Button上方時換成的圖片
		ImageIcon newComeBack = new ImageIcon(getClass().getResource("comeBack-2.PNG")); 
		newComeBack.setImage(newComeBack.getImage().getScaledInstance(150,60,Image.SCALE_DEFAULT));
		comeBack.setRolloverIcon(newComeBack);//滑鼠移到該Button上方時換成的圖片
		
		instructionWords.setText(instructionString);
		instructionWords.setBounds(400,50,875,550);//固定位置及大小
		instructionWords.setOpaque(false);//設定為透明
		instructionWords.setBorder(null);//不繪製按鈕的邊
		instructionWords.setEditable(false);//不能編輯
		instructionWords.setLineWrap(true);//自動換行
		instructionWords.setFont(new Font("TimesRoman",Font.BOLD,18));//設定字型
		add(close);
		add(close);
		add(comeBack);
		add(instructionWords);

	}
	
	//設定JPanel的漸層
	public void paintComponent( Graphics cur_gra )
	  {
	    Dimension cur_size = this.getSize();
	    Graphics2D gra_2D = (Graphics2D) cur_gra;
	    gra_2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    switch(mode)
	    {
	      case SwingConstants.VERTICAL:
	      {
	        GradientPaint grad_paint = new GradientPaint(0, 0, from_color, 0, cur_size.height, to_color);
	        gra_2D.setPaint(grad_paint);
	        gra_2D.fill(new RoundRectangle2D.Double(0, 0, cur_size.width, cur_size.height, 0, 0));
	        break;
	      }
	      case SwingConstants.HORIZONTAL:
	      {
	        GradientPaint grad_paint = new GradientPaint(0, 0, from_color, cur_size.width, 0, to_color);
	        gra_2D.setPaint(grad_paint);
	        gra_2D.fill(new RoundRectangle2D.Double(0, 0, cur_size.width, cur_size.height, 0, 0));
	        break;
	      }
	    }
	  }
}
