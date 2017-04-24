package com.sinpaientertainment.kblock.managers;

/**
 * Created by Sinpai on 9/26/2016.
 */
public class ChatMessage {
    public boolean left;
    public String sender;
    public String message;

    public ChatMessage(boolean left , String sender, String message) {
        // TODO Auto-generated constructor stub
        super();
        this.left=left;
        this.sender = sender;
        this.message = message;
    }
}
