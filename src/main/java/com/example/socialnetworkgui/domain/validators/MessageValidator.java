package com.example.socialnetworkgui.domain.validators;

import com.example.socialnetworkgui.domain.Message;

public class MessageValidator implements Validator<Message>{
    @Override
    public void validate(Message mesaj) throws ValidationException {
        if (mesaj.getText().equals("")){
            throw new ValidationException("You can't send a empty message!");
        }
    }
}
