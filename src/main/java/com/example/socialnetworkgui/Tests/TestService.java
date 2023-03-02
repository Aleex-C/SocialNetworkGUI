package com.example.socialnetworkgui.Tests;

import com.example.socialnetworkgui.config.Config;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.PrietenieValidator;
import com.example.socialnetworkgui.domain.validators.UtilizatorValidator;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.repository.Repository0;
import com.example.socialnetworkgui.repository.file.PrietenieFile0;
import com.example.socialnetworkgui.repository.file.UtilizatorFile0;
import com.example.socialnetworkgui.service.PrietenieService;
import com.example.socialnetworkgui.service.UtilizatorService;

import java.util.List;
import java.util.stream.StreamSupport;

public class TestService {
    public static void run(){
        String utilFile = Config.getProperties().getProperty("friends");
        Repository<Long, Prietenie> repoPrieten = (Repository<Long, Prietenie>) new PrietenieFile0(utilFile, new PrietenieValidator());
        PrietenieService prietenieService = new PrietenieService(repoPrieten);
       //prietenieService.addPrietenie(4L,4L,5L);
        try{
            //prietenieService.addPrietenie(5L, 5L, 4L);
        }catch (ValidationException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("===TEST ADD===");
        prietenieService.getAll().forEach(System.out::println);
        System.out.println("===TEST DELETE===");
        prietenieService.deletePrietenie(4L,5L);
        prietenieService.getAll().forEach(System.out::println);

    }
    public static void run_comp_conex(){
        String utilFile = Config.getProperties().getProperty("friends");
        Repository0<Long, Prietenie> repoPrieten = new PrietenieFile0(utilFile, new PrietenieValidator());
        PrietenieService prietenieService = new PrietenieService((Repository<Long, Prietenie>) repoPrieten);
        String utilFile2 = Config.getProperties().getProperty("Utilizatori-teste");
        Repository0<Long, Utilizator> repoUtil =new UtilizatorFile0(utilFile2, new UtilizatorValidator());
        UtilizatorService utilizatorService = new UtilizatorService((Repository<Long, Utilizator>) repoUtil);
        Long count = StreamSupport.stream(utilizatorService.getAll().spliterator(), false).count();
        System.out.println("Numarul de componente conexe este: ");
        System.out.println(prietenieService.componenteConexe(count));
        System.out.println("Cea mai sociabila comunitate este: ");

        List<Long> useri = prietenieService.celMaiLungDrum(count);
        for (Long id : useri){
            System.out.print(repoUtil.findOne(id).getFirstName() + " | ");
        }
        utilizatorService.deleteUtilizator(2L);
        List<Pair> deSters = prietenieService.findAllUsersWhoWereFriends(2L);
        for (Pair p :
                deSters) {
            prietenieService.deletePrietenie(p.getId1(), p.getId2());
        }
    }
}
