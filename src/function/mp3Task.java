package function;

import java.util.TimerTask;

public class mp3Task extends TimerTask{
	@ Override
	public void run() {
		String filename = "C:/Users/Chen/Desktop/水族箱音樂.mp3";
		mp3Player mp3 = new mp3Player(filename);
        mp3.play();
	}

}