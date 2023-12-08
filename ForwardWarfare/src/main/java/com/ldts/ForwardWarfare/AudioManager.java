package com.ldts.ForwardWarfare;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager {
    private static AudioManager audioManager;
    private static Clip currentClip;

    public static AudioManager get() {
        if (audioManager == null)
            audioManager = new AudioManager();
        return audioManager;
    }

    public void play(String url) {
        try {
            currentClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    this.getClass().getResourceAsStream("/sounds/" + url));
            currentClip.open(inputStream);
            currentClip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void pause() {
        currentClip.stop();
    }

    public void unpause() {
        currentClip.start();
    }

    public void stop() {
        currentClip.stop();
        currentClip.close();
    }
}
