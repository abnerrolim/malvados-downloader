package org.abner.random;

public class Image {

    private final byte[] data;
    private final String name;

    public Image(byte[] data, String name){
        this.data = data;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public byte[] getData(){
        return this.data;
    }
}
