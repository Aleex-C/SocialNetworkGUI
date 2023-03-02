package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.repository.Repository0;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.repository.database.FriendshipsDbRepository;

import java.io.LineNumberReader;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Service class for Prietenie
 */
public class PrietenieService {
    private Repository<Long, Prietenie> repoPrietenie;
    private static int maxLun = Integer.MIN_VALUE;
    public PrietenieService(Repository<Long, Prietenie> repoPrietenie){
        this.repoPrietenie = repoPrietenie;
    }

    /**
     * Adds a Prietenie between two Utilizatori.
     * @param  - id entity
     * @param id1 - id user1
     * @param id2 - id user 2
     */
    public void addPrietenie(Long id1, Long id2){
        Prietenie p = new Prietenie(id1,id2);
        //p.setId(id);
        Iterable<Prietenie> friendships = repoPrietenie.findAll();
        for (Prietenie friendship : friendships){
            if ((friendship.getId1() == id2 && friendship.getId2()==id1) || (friendship.getId1() == id1 && friendship.getId2()==id2)){
                throw new ValidationException("The friend request is still pending or it was already accepted!");
            }

        }
        if(id1==id2){
            throw new ValidationException("Can't send request to yourself!");
        }
        repoPrietenie.save(p);
    }
    public void update(Long id, Long id1, Long id2){
        //repoPrietenie.delete(id);
        Prietenie p = new Prietenie(id1, id2);
        p.setId(id);
        repoPrietenie.update(p);
        //p.setId(Long.valueOf(Long.toString(id1)+Long.toString(id2)));
        //repoPrietenie.save(p);
    }

    /**
     * Delete Prietenie
     * @param id1 - id user 1
     * @param id2 - id user 2
     */
    public void deletePrietenie(Long id1, Long id2) {
        Iterable<Prietenie> friendships = repoPrietenie.findAll();
        for (Prietenie friendship : friendships) {
            if ((friendship.getId1() == id2 && friendship.getId2() == id1) || (friendship.getId1() == id1 && friendship.getId2() == id2)) {
                repoPrietenie.delete(friendship.getId());
                return;
            }
        }
        throw new ValidationException("Nu exista prietenie intre acesti useri!");
    }

    /**
     * Function that finds all pairs of ids that had in common the id of a deleted user
     * @param id - id of the removed user
     * @return List of Pairs of IDs of friendships to be deleted when a user is removed.
     */
    public List<Pair> findAllUsersWhoWereFriends(Long id){
        Iterable<Prietenie> friendships = repoPrietenie.findAll();
        List<Pair> perechi = new ArrayList<Pair>();
        for (Prietenie friendship : friendships){
            if (friendship.getId1() == id){
                perechi.add(new Pair(friendship.getId2(), id));
            } else if (friendship.getId2() == id) {
                perechi.add(new Pair(friendship.getId1(), id));
            }
        }
        return perechi;
    }
    public Iterable<Prietenie> getAll(){
        return repoPrietenie.findAll();
    }

    public List<Prietenie> getFriendsOfUser(Utilizator user){
        Iterable <Prietenie> all = repoPrietenie.findAll();
        Optional<List<Prietenie>> result = Optional.of(StreamSupport.stream(all.spliterator(), false)
                .filter(p -> p.getId1().equals(user.getId()) || p.getId2().equals(user.getId()))
                .toList());
        if (result.isEmpty())
            throw new RuntimeException("User has no friends!");
        return result.get();
    }
    public List<Prietenie> getAcceptedFriends(Utilizator user){
        List<Prietenie> friends = getFriendsOfUser(user);
        Optional<List<Prietenie>> accepted = Optional.of(friends.stream()
                .filter(p -> p.getStatus().equals("accepted"))
                .toList());
        if (accepted.isEmpty())
            throw new RuntimeException("User has no accepted friends!");
        return accepted.get();
    }
    public List<Prietenie> getPendingFriends(Utilizator user){
        List<Prietenie> friends = getFriendsOfUser(user);
        Optional<List<Prietenie>> accepted = Optional.of(friends.stream()
                .filter(p -> p.getStatus().equals("waiting"))
                .toList());
        if (accepted.isEmpty())
            throw new RuntimeException("User has no accepted friends!");
        return accepted.get();
    }
    public void acceptFriend(Prietenie friendship){
        repoPrietenie.accept(friendship);
    }

    /**
     * Number of the components of the graph
     * @param nr - numbers of users registred
     * @return count (int) -> number of componenets in the network graph.
     */
    public int componenteConexe(Long nr){
        HashMap<Long, List<Long>> adj = new HashMap<Long, List<Long>>();
        buildListForDFS(adj, nr);
//        Long nr = StreamSupport.stream(getAll().spliterator(), false).count();
        boolean[] visited = new boolean[Math.toIntExact(nr+1)];
        int count = 0;
        for (Long i = 1L; i <= nr; i++){
            if (visited[Math.toIntExact(i)] == false){
                count++;
                dfs(adj, i, visited, 0);
            }
        }
        return count;
    }

    /**
     * Pentru a afla cea mai mare lungime, parcurgem in adancime toate nodurile din "reteaua" noastra
     *  (adica, inclusiv pentru cei aflati in aceeasi componenta, vedem care este lungimea maxima posibila)
     *
     * @param nr - number of users
     * @return - list of user IDs
     */
    public List<Long> celMaiLungDrum(Long nr){
        maxLun = Integer.MIN_VALUE;
        HashMap<Long, List<Long>> adj = new HashMap<Long, List<Long>>();
        buildListForDFS(adj, nr);
        boolean[] visited = new boolean[Math.toIntExact(nr+1)];
        int maxDrum = Integer.MIN_VALUE;
        int count = 0;
        Long y = null;
        for (Long i = 1L; i <= nr; i++){
            int lungime = dfs(adj, i, visited, 0);
            if (maxDrum < lungime){
                maxDrum = lungime;
                y = i;
            }
        }
//        dfsAfisare(adj,y,new boolean[Math.toIntExact(nr+1)]);
        List <Long> listaDeUseri = new ArrayList<>();
        dfsLista(adj,y, new boolean[Math.toIntExact(nr+1)], listaDeUseri);
        return listaDeUseri;
    }
//    private void dfsAfisare(HashMap<Long, List<Long>> adj, Long index, boolean[] visited){
//        visited[Math.toIntExact(index)]=true;
//        System.out.print(index + " -> ");
//        for (Long j: adj.get(index)){
//            if (visited[Math.toIntExact(j)] == false){
//                dfsAfisare(adj, j, visited);
//            }
//        }
//    }

    /**
     * Creates a list of IDs of the users that form the "tree" with the longest path in its component.
     * @param adj
     * @param index
     * @param visited
     * @param lista
     */
    private void dfsLista(HashMap<Long, List<Long>> adj, Long index, boolean[] visited, List<Long> lista){
        visited[Math.toIntExact(index)]=true;
        lista.add(index);
        for (Long j: adj.get(index)) {
            if (visited[Math.toIntExact(j)] == false) {
                dfsLista(adj, j, visited,lista);
            }
        }
    }

    /**
     * Depth-first Search:
     * So the basic idea is to start from the root or any arbitrary node and mark the node and move to the adjacent unmarked node
     * and continue this loop until there is no unmarked adjacent node.
     * Then backtrack and check for other unmarked nodes and traverse them.
     * The DFS also calculates the maximum length of the "tree" generated by the DFS
     * @param adj - HashMap<Long, List<Long>> -- list of adjacency
     * @param index - Long -- the "to-where" values of the source/start node
     * @param visited - boolean[]
     * @param lungime_anterioara - previous length
     * @return maxLun (int) -- maximum length of the current traversal
     */
    private int dfs(HashMap<Long, List<Long>> adj, Long index, boolean[] visited, int lungime_anterioara) {
        visited[Math.toIntExact(index)]=true;
        int lungime_curenta = 0;
        for (Long j:adj.get(index)) {
            if (visited[Math.toIntExact(j)] == false) {
                lungime_curenta = lungime_anterioara + 1;
                dfs(adj, j, visited, lungime_curenta);
            }
            if (maxLun < lungime_curenta){
                maxLun = lungime_curenta;
            }
        }
        return maxLun;
    }

    /**
     * Function that builds in 'lista' the adjacency list of the network graph
     * @param lista - adjacency list of the network graph
     *                  - initially, no values in 'lista'
     * @param nr - number of users
     */
    private void buildListForDFS(HashMap<Long, List<Long>> lista, Long nr){
        Iterable<Prietenie> friendships = getAll();
//        Long nr = StreamSupport.stream(friendships.spliterator(), false).count();
        for (Long i = 1L; i <= nr; i ++) {
            lista.put(i, new LinkedList<Long>());
        }
        for(Prietenie f: friendships){
            lista.get(f.getId1()).add(f.getId2());
            lista.get(f.getId2()).add(f.getId1());
        }
    }

    /**
     * Functie de creare de perechi de id-uri
     * @return List<Pair>
     */
    public List<Prietenie> printFriendships(){
        Iterable <Prietenie> friendships = repoPrietenie.findAll();
        List<Prietenie> lista = new ArrayList<Prietenie>();
        for (Prietenie p : friendships){
            lista.add(p);
        }
        return lista;
    }

}
