package Sound;

import javax.sound.sampled.*;

public class Sound {
	
    private Clip clip;

    public static Sound song1 = new Sound("/Sounds/Music/UnholyConfessions.wav");
    public static Sound song2 = new Sound("/Sounds/Music/Laser Canon.wav");
    public static Sound menuSong = new Sound("/Sounds/Music/LittlePieceOfHeaven.wav");

    public Sound (String fileName) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
        	e.printStackTrace();
        }
     }
    
    public void play() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                           clip.stop();
                           clip.setFramePosition(0);
                           clip.start();
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        if(clip == null) return;
        clip.stop();
    }

    public void loop() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                  }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } boolean isActive() {
        return clip.isActive();
    }
}
