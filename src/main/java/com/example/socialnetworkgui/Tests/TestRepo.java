package com.example.socialnetworkgui.Tests;

import com.example.socialnetworkgui.config.Config;
import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.PrietenieValidator;
import com.example.socialnetworkgui.domain.validators.UtilizatorValidator;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repository.Repository0;
import com.example.socialnetworkgui.repository.file.PrietenieFile0;
import com.example.socialnetworkgui.repository.file.UtilizatorFile0;

public class TestRepo {
    public static void run_add(){
        String utilFile = Config.getProperties().getProperty("Utilizatori-teste");
        Repository0<Long, Utilizator> repoUtil =new UtilizatorFile0(utilFile, new UtilizatorValidator());
        Utilizator u = new Utilizator("Mircea", "Mirciulescu");
        u.setId(6L);
        repoUtil.save(u);
        repoUtil.findAll().forEach(System.out::println);
    }
    public static void run_delete(){
        String utilFile = Config.getProperties().getProperty("Utilizatori-teste");
        UtilizatorFile0 repoUtil =new UtilizatorFile0(utilFile, new UtilizatorValidator());
        repoUtil.delete(6L);
        repoUtil.findAll().forEach(System.out::println);
    }
    public static void run_update(){
        String utilFile = Config.getProperties().getProperty("Utilizatori-teste");
        UtilizatorFile0 repoUtil =new UtilizatorFile0(utilFile, new UtilizatorValidator());
        Utilizator nou = new Utilizator("David", "David");
        nou.setId(1L);
        repoUtil.update(nou);
        repoUtil.findAll().forEach(System.out::println);
        System.out.println("======================");
        Utilizator nou_2 = new Utilizator("Alexandru", "Croitor");
        nou_2.setId(1L);
        repoUtil.update(nou_2);
        repoUtil.findAll().forEach(System.out::println);
        nou_2.setId(213L);
        System.out.println("======================");
        try {
            repoUtil.update(nou_2);
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }
}
