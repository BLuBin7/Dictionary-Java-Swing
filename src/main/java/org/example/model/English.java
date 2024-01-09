package org.example.model;

public class English {
    private int id;
    private String name;
    private int audioUS_id;
    private int audioUK_id;

    public English (){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAudioUS_id() {
        return audioUS_id;
    }

    public void setAudioUS_id(int audioUS_id) {
        this.audioUS_id = audioUS_id;
    }

    public int getAudioUK_id() {
        return audioUK_id;
    }

    public void setAudioUK_id(int audioUK_id) {
        this.audioUK_id = audioUK_id;
    }
}
