package com.example.darkrai.appezmusic;

/**
 * Created by darkrai on 4/7/2018.
 */

public class Song {
    private String Title;
    private int File;

    public Song(String title, int file) {
        Title = title;
        File = file;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}
