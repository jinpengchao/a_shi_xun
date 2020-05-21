package com.vhome.chat.domain;

import com.hyphenate.chat.EMConversation;

public class UserChatEntity {
    private EMConversation emConversation;
    private String nickName;
    public UserChatEntity(){

    }
    public UserChatEntity(EMConversation emConversation,String nickName){
        this.emConversation = emConversation;
        this.nickName = nickName;
    }
    public EMConversation getEmConversation() {
        return emConversation;
    }

    public void setEmConversation(EMConversation emConversation) {
        this.emConversation = emConversation;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
