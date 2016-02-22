package menu;

import java.awt.event.*;
public class buttonActionListener implements ActionListener{
    boolean buttonInfo;
    public buttonActionListener(){
    buttonInfo = false;
    }
    public void actionPerformed(ActionEvent e)
    {
        if(buttonInfo==false){
            buttonInfo = true;
        }else{
            buttonInfo = false;
        }
    }
    public boolean accButtonInfo(){
        return buttonInfo;
    }
    public void modButton(){
        buttonInfo = false;
    }
}