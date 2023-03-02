package com.example.socialnetworkgui.repository.file;

import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repository.memory.InMemoryRepository0;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 'Template' Abstract Class for all type E .csv files used to store data
 * @param <ID> -
 * @param <E> -
 */
public abstract class AbstractFileRepo0<ID, E extends Entity<ID>> extends InMemoryRepository0<ID, E> {
    String fileName;
    public AbstractFileRepo0(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    /**
     * Load data from the .csv files
     * In general, BufferedReader comes in handy if we want to read text from any kind of input source
     * whether that be files, sockets, or something else.
     *
     * Simply put, it enables us to minimize the number of I/O operations by reading chunks of characters and storing
     * them in an internal buffer. While the buffer has data, the reader will read from it instead of directly from
     * the underlying stream.
     */
    private void loadData(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String linie;
            while ((linie=br.readLine())!= null && !linie.equals("")){
                List<String> attr = Arrays.asList(linie.split(";"));
                E e=extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extract the necessary information (from a String) to create the entity.
     * @param attributes - List of strings that contain the data needed for creating the entity of type E
     * @return Entity of type E
     */
    public abstract E extractEntity(List<String> attributes);

    /**
     * Transforms the type E entity in a String value with relevant data about it.
     * @param entity - the entity of type E that is going to be transformed in a String
     * @return String - contains the data of entity in String format
     */
    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity){
        E e=super.save(entity);
        if (e==null){
            writeToFile(entity);
        }
        return e;
    }
    @Override
    public E delete (ID id){
        E e = super.delete(id);
        rewriteFile();
        return e;
    }
    @Override
    public void update(E entity){
        super.update(entity);
        rewriteFile();
    }

    /**
     * Append the entity to the storage file.
     * @param entity - the entity to be written in the .csv files.
     */
    protected void writeToFile(E entity){
        try(BufferedWriter bW = new BufferedWriter(new FileWriter(fileName, true))){
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Completely rewrite the storage file (used for the delete function)
     */
    protected void rewriteFile(){
        FileOutputStream fileStream = null;
        try{
            fileStream = new FileOutputStream(this.fileName, false);
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        Iterable<E> entities = super.findAll();
        for (E e: entities) {
            String linie = createEntityAsString(e) + '\n';
            try {
                if (fileStream != null) {
                    fileStream.write(linie.getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            if (fileStream != null){
                fileStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
