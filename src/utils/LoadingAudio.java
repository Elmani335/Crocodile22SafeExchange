package utils;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class LoadingAudio {

     // Method to play an audio file
    public static void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }
    
}
