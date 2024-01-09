package org.example.model;

public class AudioUS {
    private int id;
    private String audio_source;

    public AudioUS(int id, String audio_source) {
        this.id = id;
        this.audio_source = audio_source;
    }

    public AudioUS(){}

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
