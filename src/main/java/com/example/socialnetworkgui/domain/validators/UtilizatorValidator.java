package com.example.socialnetworkgui.domain.validators;
import com.example.socialnetworkgui.domain.Utilizator;
public class UtilizatorValidator implements Validator<Utilizator>{
    @Override
    public void validate(Utilizator user) throws ValidationException{
        String firstName = user.getFirstName().trim();
        String lastName = user.getLastName().trim();
        if (firstName == null || firstName.isEmpty() || firstName.charAt(0) < 'A' || firstName.charAt(0) > 'Z')
            throw new ValidationException("Prenume invalid!");
        if (lastName == null || lastName.isEmpty() || lastName.charAt(0) < 'A' || lastName.charAt(0) > 'Z')
            throw new ValidationException("Nume invalid!");

    }
}
