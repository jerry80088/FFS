package fishStore;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import database.DataBaseManager;
import fish.Fish;
import fish.FishButton;
import simulate.SimulateInterface;

public class FishStore extends JPanel {
	public FishButton fishButton;
	private int categoryChoice;//將魚名換算成陣列的index
	
	//抓電腦螢幕
	private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	//JPanel SI;
	
	public JTextArea textArea;
	public JButton back;
	public FishStore(JFrame frame,ArrayList<FishButton> fishs,SimulateInterface SI){
		
		setLayout(null);
		//抓title按鈕，並且設置大小
		ImageIcon titleIcon = new ImageIcon(getClass().getResource("title.PNG"));
		titleIcon.setImage(titleIcon.getImage().getScaledInstance(600,150,Image.SCALE_DEFAULT));
		//抓back按鈕，並且設置大小
		ImageIcon backIcon = new ImageIcon(getClass().getResource("back.PNG"));
		backIcon.setImage(backIcon.getImage().getScaledInstance(371,168,Image.SCALE_DEFAULT));
		//抓in按鈕，並且設置大小
		ImageIcon inIcon = new ImageIcon(getClass().getResource("in.PNG"));
		inIcon.setImage(inIcon.getImage().getScaledInstance(200,100,Image.SCALE_DEFAULT));
		
		//新增Label及Button，並且將圖片塞入
		JLabel title = new JLabel(titleIcon);
		JLabel name = new JLabel("名字");
		name.setFont(new Font("標楷體", Font.BOLD, 35));
		JLabel fishC = new JLabel("魚種");
		fishC.setFont(new Font("標楷體", Font.BOLD, 35));
		JLabel fishG = new JLabel("性別");
		fishG.setFont(new Font("標楷體", Font.BOLD, 35));
		JLabel fishA = new JLabel("年齡");
		fishA.setFont(new Font("標楷體", Font.BOLD, 35));
		JTextField typeName = new JTextField("");
		typeName.setFont(new Font("標楷體", Font.BOLD, 35));
		typeName.setBackground(Color.lightGray);
		
		String[] fishList = {"紅十字魚","孔雀魚","小丑魚","斑馬魚","麗麗魚","紅劍魚","紅龍魚","紅綠燈魚","三角燈魚","銀屏燈魚","迷你燈魚","寶蓮燈魚","接吻魚","非洲慈鯛","黑尾紅月光"};
		String[] fishGender = {"雄","雌"};
		String[] ages = {"1","2","3","4","5","6","7","8","9","10"};
		String a = String.format("初始長度: 4cm\n初始重量: 47g\n適合溫度: 23~28°C\n適合pH值: 5.5~7.5\n適合含氧量: 5.5ppm\n適合乾淨度: 60%s\n性格: 兇猛","%");
		String b = String.format("初始長度: 3cm\n初始重量: 34g\n適合溫度: 12~26°C\n適合pH值: 6.0~7.5\n適合含氧量: 5.0ppm\n適合乾淨度: 65%s\n性格: 溫馴","%");
		String c = String.format("初始長度: 5cm\n初始重量: 56g\n適合溫度: 26~27°C\n適合pH值: 8.0~6.6\n適合含氧量: 5.1ppm\n適合乾淨度: 60%s\n性格: 溫馴","%");
		String d = String.format("初始長度: 3cm\n初始重量: 36g\n適合溫度: 20~26°C\n適合pH值: 6.5~7.5\n適合含氧量: 6.3ppm\n適合乾淨度: 55%s\n性格: 溫馴","%");
		String e = String.format("初始長度: 5cm\n初始重量: 53g\n適合溫度: 22~26°C\n適合pH值: 6.8~7.2\n適合含氧量: 6.2ppm\n適合乾淨度: 70%s\n性格: 溫馴","%");
		String f = String.format("初始長度: 7cm\n初始重量: 79g\n適合溫度: 24~26°C\n適合pH值: 6.8~8.0\n適合含氧量: 5.8ppm\n適合乾淨度: 57%s\n性格: 溫馴","%");
		String g = String.format("初始長度: 5cm\n初始重量: 55g\n適合溫度: 24~28°C\n適合pH值: 6.0~7.0\n適合含氧量: 6.5ppm\n適合乾淨度: 72%s\n性格: 溫馴","%");
		String h = String.format("初始長度: 2cm\n初始重量: 26g\n適合溫度: 20~26°C\n適合pH值: 5.0~7.0\n適合含氧量: 5.5ppm\n適合乾淨度: 52%s\n性格: 溫馴","%");
		String i = String.format("初始長度: 4cm\n初始重量: 49g\n適合溫度: 24~28°C\n適合pH值: 5.0~6.5\n適合含氧量: 5.4ppm\n適合乾淨度: 54%s\n性格: 溫馴","%");
		String j = String.format("初始長度: 5cm\n初始重量: 58g\n適合溫度: 22~25°C\n適合pH值: 6.0~6.8\n適合含氧量: 5.0ppm\n適合乾淨度: 50%s\n性格: 溫馴","%");
		String k = String.format("初始長度: 2cm\n初始重量: 25g\n適合溫度: 23~28°C\n適合pH值: 5.8~7.2\n適合含氧量: 5.6ppm\n適合乾淨度: 50%s\n性格: 溫馴","%");
		String l = String.format("初始長度: 7cm\n初始重量: 69g\n適合溫度: 23~28°C\n適合pH值: 5.5~7.0\n適合含氧量: 5.8ppm\n適合乾淨度: 52%s\n性格: 溫馴","%");
		String m = String.format("初始長度: 3cm\n初始重量: 33g\n適合溫度: 22~26°C\n適合pH值: 6.8~7.4\n適合含氧量: 6.7ppm\n適合乾淨度: 62%s\n性格: 溫馴","%");
		String n = String.format("初始長度: 4cm\n初始重量: 42g\n適合溫度: 23~28°C\n適合pH值: 6.7~8.0\n適合含氧量: 7.0ppm\n適合乾淨度: 70%s\n性格: 兇猛","%");
		String o = String.format("初始長度: 2cm\n初始重量: 25g\n適合溫度: 23~26°C\n適合pH值: 6.3~7.4\n適合含氧量: 5.6ppm\n適合乾淨度: 56%s\n性格: 溫馴","%");
		String[] character = {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o};
		textArea = new JTextArea(a);
		textArea.setFont(new Font("標楷體", Font.BOLD, 40));
		textArea.setLineWrap(true);//自動換行
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		
		JComboBox fishCList = new JComboBox(fishList);
		fishCList.setFont(new Font("標楷體", Font.BOLD, 35));
		fishCList.setBackground(Color.lightGray);
		fishCList.addItemListener(
				new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent event) {
						// TODO 自動產生的方法 Stub
						if(event.getStateChange() == ItemEvent.SELECTED)
						{
							categoryChoice = fishCList.getSelectedIndex();
							textArea.setText(character[categoryChoice]);
						}
					}
				}
		);
		
		JComboBox fishGList = new JComboBox(fishGender) ;
		fishGList.setFont(new Font("標楷體", Font.BOLD, 35));
		fishGList.setBackground(Color.lightGray);
		
		JComboBox typeAge = new JComboBox(ages);
		typeAge.setFont(new Font("標楷體", Font.BOLD, 35));
		typeAge.setBackground(Color.lightGray);
		back = new JButton(backIcon);
		JButton in = new JButton(inIcon);
		in.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent event) {
						String fishName = typeName.getText();
						String fishCategory = fishCList.getSelectedItem().toString();
						String fishGender = fishGList.getSelectedItem().toString();
						String fishAge = typeAge.getSelectedItem().toString();
						
						fishButton = new FishButton(fishName,fishCategory,fishGender,fishAge,SI);
						SI.add(fishButton);
						fishs.add(fishButton);
						
					}
				}
				
			);
		back.setBorder(null);//不繪製按鈕的邊
		back.setContentAreaFilled(false);//不會自行繪製按鈕背景
		in.setBorder(null);//不繪製按鈕的邊
		in.setContentAreaFilled(false);//不會自行繪製按鈕背景
		//JTextField textArea = new JTextField("");

		//設定位置
		back.setBounds(0,0,371,168);
		title.setBounds(500,0,600,150);
		name.setBounds(200,80,200,100);
		fishC.setBounds(200,180,200,100);
		fishG.setBounds(200,280,200,100);
		fishA.setBounds(200,380,200,100);
		typeName.setBounds(330, 105, 270, 50);
		fishCList.setBounds(330, 205, 270, 50);
		fishGList.setBounds(330, 305, 270, 50);
		typeAge.setBounds(330, 405, 270, 50);
		textArea.setBounds(700, 105, 600, 460);
		in.setBounds(350, 480, 200, 100);
		
		//this.setBackground(frame); //調用背景方法
		this.setOpaque(false);
		
		//TOP
		JPanel TOP = new JPanel(); //創建個JPanel
		TOP.setBounds(0, 0, dimension.width, 200);
		TOP.setLayout(null);
		TOP.setOpaque(false); //把JPanel設置為透明 這樣就不會遮住後面的背景 這樣你就能在JPanel隨意加元件了
		//將Label及Button放入Panel中
		TOP.add(back);
		TOP.add(title);
		//將Panel放入視窗中
		this.add(TOP);
		
		//middle
		JPanel middle = new JPanel(); //創建個JPanel
		middle.setBounds(0, 200, dimension.width, dimension.height);
		middle.setLayout(null);
		middle.setOpaque(false); //把JPanel設置為透明 這樣就不會遮住後面的背景 這樣你就能在JPanel隨意加元件了
		
		//將Label及Button放入Panel中
		middle.add(name);
		middle.add(fishC);
		middle.add(fishCList);
		middle.add(fishG);
		middle.add(fishGList);
		middle.add(fishA);
		middle.add(typeName);
		middle.add(typeAge);
		middle.add(textArea);
		middle.add(in);
		
		//將Panel放入視窗中
		add(middle);
	}
}

