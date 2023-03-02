package com.example.socialnetworkgui.repository.file;

import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.validators.Validator;

import java.util.List;

public class PrietenieFile0 extends AbstractFileRepo0<Long, Prietenie> {
    public PrietenieFile0(String fileName, Validator<Prietenie> validator){super(fileName, validator);}
    @Override
    public Prietenie extractEntity(List<String> attributes){
        Prietenie prietenie = new Prietenie(Long.parseLong(attributes.get(1)),Long.parseLong(attributes.get(2)));
        prietenie.setId(Long.parseLong(attributes.get(0)));
        return prietenie;
    }
    @Override
    protected String createEntityAsString(Prietenie prietenie){
        return prietenie.getId()+";"+prietenie.getId1()+";"+prietenie.getId2();
    }

}

/**
 * @TODO: Add the LocalDateTime in the createEntityAsString
 */
