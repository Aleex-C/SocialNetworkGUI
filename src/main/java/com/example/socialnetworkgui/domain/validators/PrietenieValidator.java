package com.example.socialnetworkgui.domain.validators;

import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;

public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie prietenie) throws ValidationException{
        Long id1 = prietenie.getId1();
        Long id2 = prietenie.getId2();
        if (id1 < 0 || id2 <0){
            throw new ValidationException("ID-urile nu pot fi negative!");
        }
    }
}
