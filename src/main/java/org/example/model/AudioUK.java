package org.example.model;

public class AudioUK {
    private int id;
    private String audio_source;


    public AudioUK() {}

    public AudioUK(int id, String audio_source) {
        this.id = id;
        this.audio_source = audio_source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAudio_source() {
        return audio_source;
    }

    public void setAudio_source(String audio_source) {
        this.audio_source = audio_source;
    }
}
