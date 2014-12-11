package edu.ucsd.bolognese.src;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * Created by karenlo on 12/10/14.
 */
public class FreeTTS {
    private static final String VOICENAME = "kevin";
    private String text; // string to speech

    public FreeTTS(String text) {
        this.text = text;
    }

    public void speak() {
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICENAME);
        voice.allocate();
        voice.speak(text);
    }

    public void main(String[] args) {
        FreeTTS freeTTS = new FreeTTS(text);
        freeTTS.speak();
    }
}
