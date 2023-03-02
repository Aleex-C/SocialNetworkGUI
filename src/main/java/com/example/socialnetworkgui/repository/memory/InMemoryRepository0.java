package com.example.socialnetworkgui.repository.memory;

import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repository.Repository0;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository that handles the data in memory.
 * Salveaza datele in memorie (nu intr-o baza de date, spre exemplu)
 * @param <ID> - type E entities must have an ID.
 * @param <E> - type of entities saved in repo.
 */
public class InMemoryRepository0 <ID, E extends Entity<ID>> implements Repository0<ID, E> {
    private Validator<E> validator;
    Map<ID, E> entities;

    public InMemoryRepository0(Validator<E> validator){
        this.validator = validator;
        entities = new HashMap<ID, E>();
    }
    @Override
    public E findOne(ID id){
        if (id == null){
            throw new IllegalArgumentException("id must not be null!");
        }
        return entities.get(id);
    }
    @Override
    public Iterable<E> findAll() {return entities.values();}

    @Override
    public E save (E entity){
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null!");
        validator.validate(entity);
        if (entities.get(entity.getId()) != null){
            throw new ValidationException("entitate cu acest ID exista deja!");
        }
        else entities.put(entity.getId(), entity);
        return null;
    }
    @Override
    public void update(E entity){
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        if (entities.get(entity.getId()) != null)
            entities.put(entity.getId(), entity);
        else
            throw new IllegalArgumentException("there is no current entity in the list with this particular ID!");
    }
    @Override
    public E delete(ID id){
        if (id == null){
            throw new IllegalArgumentException("id must not be null!");
        }
        E u = entities.remove(id);
        if ( u == null){
            throw new ValidationException("Acest entity nu exista!");
        }
        return u;
    }
/**
 */
}
