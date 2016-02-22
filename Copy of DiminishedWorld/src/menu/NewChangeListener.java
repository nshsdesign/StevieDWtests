package menu;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public abstract class NewChangeListener implements ChangeListener{
	int value;
	public NewChangeListener(){
		value = 0;
	}
	public void stateChanged(ChangeEvent e) {
		//changeValue((JSlider)e.getSource());
	}
	public int accValue(){
		return value;
	}
}
