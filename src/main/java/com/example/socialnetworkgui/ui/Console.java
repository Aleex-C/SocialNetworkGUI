//package com.example.socialnetworkgui.ui;
//
//import com.example.socialnetworkgui.domain.Pair;
//import com.example.socialnetworkgui.domain.Prietenie;
//import com.example.socialnetworkgui.domain.Utilizator;
//import com.example.socialnetworkgui.domain.validators.ValidationException;
//import com.example.socialnetworkgui.service.PrietenieService;
//import com.example.socialnetworkgui.service.UtilizatorService;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Scanner;
//import java.util.stream.StreamSupport;
//
//public class Console {
//    private final UtilizatorService utilizatorService;
//    private final PrietenieService prietenieService;
//    public Console(UtilizatorService utilizatorService, PrietenieService prietenieService){
//        this.utilizatorService = utilizatorService;
//        this.prietenieService = prietenieService;
//
//    }
//    public void utilizatorMeniu(){
//        /**
//         * if we are parsing individual tokens in a file, then !Scanner! will feel a bit more natural than BufferedReader.
//         * But, just reading a line at a time is where BufferedReader shines.
//         */
//        Scanner scanner = new Scanner(System.in);
//        while(true){
//            System.out.println("1 -> Adauga user:");
//            System.out.println("2 -> Remove user:");
//            System.out.println("3 -> Stabilește relație de prietenie: ");
//            System.out.println("4 -> Sterge relație de prietenie: ");
//            System.out.println("5 -> Afiseaza numar de comunitati: ");
//            System.out.println("6 -> Afiseaza cea mai sociabila comunitate: ");
//            System.out.println("7 -> Afiseaza relatiile de prietenie: ");
//            System.out.println("8 -> Update user: ");
//            System.out.println("9 -> Update prietenie: ");
//            System.out.println("exit");
//            System.out.println("----");
//            System.out.println(">>> ");
//            String cmd;
//            cmd = scanner.next();
//            if(Objects.equals(cmd, "1")) {
//                try {
////                    System.out.println("ID: ");
////                    String id = scanner.next();
//                    System.out.println("Nume: ");
//                    String lastName = scanner.next();
//                    System.out.println("Prenume: ");
//                    String firstName = scanner.next();
//                    try {
//                        this.utilizatorService.addUtilizator(Long.valueOf("1"), firstName, lastName);
//                        System.out.println("Utilizator adaugat cu succes!");
//                    }
//                    catch (NumberFormatException ex){
//                        System.out.println("ID-ul este invalid!");
//                    }
//                } catch (ValidationException ex) {
//                    System.out.println(ex);
//                }
//            } else if (Objects.equals(cmd, "2")) {
//                System.out.println("ID-ul user-ului pe care doriti sa-l stergeti: ");
//                String id = scanner.next();
//                try{
//                    utilizatorService.deleteUtilizator(Long.valueOf(id));
//                    List<Pair> deSters = prietenieService.findAllUsersWhoWereFriends(Long.valueOf(id));
//                    for (Pair p :
//                            deSters) {
//                        prietenieService.deletePrietenie(p.getId1(), p.getId2());
//                    }
//                    System.out.println("Utilizator sters cu succes!");
//                }catch(NumberFormatException ex){
//                    System.out.println("ID-ul este invalid!");
//                }
//                catch (ValidationException ex){
//                    System.out.println(ex.getMessage());
//                }
//            } else if(Objects.equals(cmd, "exit")){
//                break;
//            } else if (Objects.equals(cmd, "3")) {
//                System.out.println("Introduceti id-ul primului utilizator!");
//                String id1 = scanner.next();
//                System.out.println("Introduceti id-ul celui de-al doilea utilizator!");
//                String id2 = scanner.next();
//                try{
//                    this.prietenieService.addPrietenie(Long.valueOf(id1+id2),Long.valueOf(id1), Long.valueOf(id2));
//                    System.out.println("Prietenie stabilită cu succes!");
//                }catch(NumberFormatException ex){
//                    System.out.println("ID-ul este invalid!");
//                } catch (ValidationException ex){
//                    System.out.println(ex.getMessage());
//                }
//
//            } else if (Objects.equals(cmd, "4")) {
//                System.out.println("id1: ");
//                Long id1 = scanner.nextLong();
//                System.out.println("id2: ");
//                Long id2 = scanner.nextLong();
//                try{
//                    prietenieService.deletePrietenie(id1, id2);
//                    System.out.println("Prietenie stearsa cu succes!");
//                }catch (ValidationException ex){
//                    System.out.println(ex.getMessage());
//                }
//
//
//            } else if (Objects.equals(cmd, "5")) {
//                Long count = StreamSupport.stream(utilizatorService.getAll().spliterator(), false).count();
//                System.out.println("Numarul de comunitati este: ");
//                System.out.println(prietenieService.componenteConexe(count));
//            } else if (Objects.equals(cmd, "6")) {
//                System.out.println("Cea mai sociabila comunitate este: ");
//                Long count = StreamSupport.stream(utilizatorService.getAll().spliterator(), false).count();
//                List<Long> useri = prietenieService.celMaiLungDrum(count);
//                for (Long id : useri){
//                    System.out.print(utilizatorService.getOne(id).get().getFirstName() + " | ");
//                }
//                System.out.println("");
//            } else if (Objects.equals(cmd, "7")) {
//                List <Prietenie> prieteni = prietenieService.printFriendships();
//                for (Prietenie prietenie : prieteni){
//                    System.out.println(utilizatorService.getOne(prietenie.getId1()).get().getFirstName() + " -> " + utilizatorService.getOne(prietenie.getId2()).get().getFirstName() + " data: " + prietenie.getFriendsFrom().toString());
//                }
//            } else if (Objects.equals(cmd, "8")) {
//                try {
//                    System.out.println("ID: ");
//                    String id = scanner.next();
//                    System.out.println("Nume nou: ");
//                    String lastName = scanner.next();
//                    System.out.println("Prenume nou: ");
//                    String firstName = scanner.next();
//                    try {
//                        this.utilizatorService.update(Long.valueOf(id), firstName, lastName);
//                        System.out.println("Utilizator modificat cu succes!");
//                    }
//                    catch (NumberFormatException ex){
//                        System.out.println("ID-ul este invalid!");
//                    }
//                } catch (ValidationException ex) {
//                    System.out.println(ex);
//                }
//                catch (IllegalArgumentException ex){
//                    System.out.println(ex);
//                }
//
//            } else if (Objects.equals(cmd, "9")) {
//                System.out.println("Introduceti intre cine doriti sa modificati prietenia: id_prietenie=");
//                String id_p = scanner.next();
////                String[] arr = id_p_raw.split("/");
////                String id_p = arr[0]+arr[1];
//                System.out.println("Introduceti id-ul primului utilizator!");
//                String id1 = scanner.next();
//                System.out.println("Introduceti id-ul celui de-al doilea utilizator!");
//                String id2 = scanner.next();
//                try{
//                    this.prietenieService.update(Long.valueOf(id_p),Long.valueOf(id1), Long.valueOf(id2));
//                    System.out.println("Prietenie stabilită cu succes!");
//                }catch(NumberFormatException ex){
//                    System.out.println("ID-ul este invalid!");
//                } catch (ValidationException ex){
//                    System.out.println(ex.getMessage());
//                }catch (IllegalArgumentException ex){
//                    System.out.println(ex.getMessage());
//                }
//            }
//        }
//    }
//
//}
