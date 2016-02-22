package objInfoSaver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class objInfoSave {
	File tempFile;
    FileWriter fileWrite;
    BufferedWriter bufferWrite;
    String[] infoSaved;
    public objInfoSave() {

    }
    public void objInfoAddInfo(String[] info){
    	System.out.println("test 1");
    	try {
    		tempFile = new File("savObj/"+info[0]+".txt");
			fileWrite = new FileWriter(tempFile.getAbsoluteFile());
			bufferWrite = new BufferedWriter(fileWrite);
			System.out.println("test 2");
			for(int x=0; x<info.length; x++){
				System.out.println("test 3");
				bufferWrite.write(""+info[x]); //A IS NAME
				bufferWrite.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    

}
