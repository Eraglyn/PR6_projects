import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.math.BigInteger;

class Graph {
    private LinkedList<JoueurDistance> adj[], adjBis[];
    private int v = 0, nb_Joueur = 0, nb_Equipe = 0, nb_Cote = 0, num_arbitre = 0;
    private boolean fileOpened = false;
    private BufferedReader br = null;
    private BigInteger[] tabAdd = null;
    private BigInteger[][] matrice = null;

    class JoueurDistance {
        private final int joueur;
        private BigInteger distance;

        public JoueurDistance(int joueur, BigInteger distance) {
            this.joueur = joueur;
            this.distance = distance;
        }

        public BigInteger getDistance() {
            return distance;
        }

        public int getJoueur() {
            return joueur;
        }

        public void setDistance(BigInteger distance) {
            this.distance = distance;
        }

        public JoueurDistance copy() {
            return new JoueurDistance(joueur, distance);
        }

    }

    /**
     * A method to parse the given string and initialise the graph accordingly.
     *
     * @param s the string to be parsed
     * @throws IOException if an I/O error occurs
     */
    private void parse(String s) throws IOException {
        String line = "";
        if (!fileOpened) {
            br = new BufferedReader(new FileReader(s));
            fileOpened = true;
            line = br.readLine();
            if (line == null) {
                System.out.println("Erreur, fichier vide.");
                System.exit(1);
            } else {
                String[] tmp = line.split(" ");
                v = Integer.parseInt(tmp[0]);
                nb_Joueur = Integer.parseInt(tmp[1]);
                nb_Equipe = Integer.parseInt(tmp[2]);
                nb_Cote = Integer.parseInt(tmp[3]);
                num_arbitre = Integer.parseInt(tmp[1])+1;
                adj = new LinkedList[v];
                adjBis = new LinkedList[v];
                for (int i = 0; i < v; ++i){
                    adj[i] = new LinkedList<JoueurDistance>();
                    adjBis[i] = new LinkedList<JoueurDistance>();
                }
            }

        }

        for (int i = 0; i < nb_Cote; i++) {
            line = br.readLine();
            String[] tab = line.split(" ");
            if (tab.length > 4) {
                System.out.println("Erreur, fichier mal formé.");
                System.exit(1);
            }
            adj[Integer.parseInt(tab[0]) - 1]
                .add(new JoueurDistance(Integer.parseInt(tab[1]) - 1, BigInteger.valueOf(Integer.parseInt(tab[2]))));
            adjBis[Integer.parseInt(tab[1]) - 1]
                .add(new JoueurDistance(Integer.parseInt(tab[0]) - 1, BigInteger.valueOf(Integer.parseInt(tab[2]))));
        }
        br.close();
    }

    private BigInteger init(){
        tabAdd = new BigInteger[nb_Joueur];
        JoueurDistance[] arbitre = Dijkstra(num_arbitre - 1, adj);
        JoueurDistance[] arbitreInvers = Dijkstra(num_arbitre - 1, adjBis);
        for (int i = 0; i < nb_Joueur; i++){
            tabAdd[i] = arbitre[i].getDistance().add(arbitreInvers[i].getDistance());
            //System.out.println(arbitre[i].getDistance() + " " + arbitreInvers[i].getDistance() + " " + tabAdd[i]);
        }
        matrice = new BigInteger[nb_Equipe][nb_Joueur];
        BigInteger somme = new BigInteger("0");
        //System.out.println(Arrays.toString(tabAdd));
        for (int i = 0; i < nb_Joueur; i++){
            somme = somme.add(tabAdd[i]);
            matrice[0][i] = BigInteger.valueOf(i).multiply(somme);
        }
        for(int j = 1; j < nb_Equipe; j++){
            for(int i = 0; i < nb_Joueur; i++){
                BigInteger min = BigInteger.valueOf(Long.MAX_VALUE);
                //System.out.println((i+1)/(j+1));
                if((i+1)/(j+1) <= 0){
                    matrice[j][i] = new BigInteger("0");
                    continue;
                }
                for(int k = 1; k <= (i+1)/(j+1); k++){
                    min = min.min(matrice[j-1][i-k].add(BigInteger.valueOf(k-1).multiply(som(i-k+1, i))));
                }
                matrice[j][i] = min;
            }
        }
        // for(int i = 0; i < nb_Equipe; i++){
        //     System.out.println(Arrays.toString(matrice[i]));
        // }
        return matrice[nb_Equipe-1][nb_Joueur-1];
    }

    private BigInteger som(int i, int j){
        BigInteger somme = new BigInteger("0");
        if(i >= 0){
            for(int k = i; k <= j; k++){
                somme = somme.add(tabAdd[k]);
            }
        }
        return somme;
    }

    public JoueurDistance min(JoueurDistance[] l) {
        if (l.length == 0)
            return null;
        JoueurDistance min = l[0];
        for (JoueurDistance j : l) {
            if (min == null || (j != null && j.getDistance().compareTo(min.getDistance()) < 0))
                min = j;
        }
        return min;
    }

    public JoueurDistance[] Dijkstra(int source, LinkedList<JoueurDistance>[] l) {
        JoueurDistance[] result = new JoueurDistance[v];
        JoueurDistance[] queue = new JoueurDistance[v];
        int nb_done = 0;
        for (JoueurDistance j : l[source]) {
            queue[j.getJoueur()] = j.copy();
        }
        result[source] = new JoueurDistance(source, new BigInteger("0"));
        // Initialize the queue using the adj list of the source
        while (nb_done < v - 1) {
            // Sorting the queue to extract the node with the minimum distance
            JoueurDistance u = min(queue).copy(); // Not changing the original list
            result[u.getJoueur()] = u;
            queue[u.getJoueur()] = null;
            nb_done++;

            for (JoueurDistance v : l[u.getJoueur()]) {
                // Checking if the node is already in the result and if it's not the source
                if (result[v.getJoueur()] == null && v.getJoueur() != source) {
                    JoueurDistance w = v.copy(); // Copy to avoid modifying the original list
                    w.setDistance(u.getDistance().add(w.getDistance()));

                    // Checking if the node is already in the queue,
                    // if it is and the distance is higher, we replace it
                    JoueurDistance tmp = queue[w.getJoueur()];
                    if (tmp == null)
                        queue[w.getJoueur()] = w;
                    else if (tmp.getDistance().compareTo(w.getDistance()) > 0)
                        tmp.setDistance(w.getDistance());
                }
            }
        }
        return result;
    }

    private void printDijkstra(JoueurDistance[] l, int i) {
        System.out.println("Dijkstra from : " + (i + 1));
        for (JoueurDistance n : l) {
            if (n != null)
                System.out.println("J: " + (n.getJoueur() + 1) + " D: " + n.getDistance());
        }
    }

    private void printGraph() {
        for (int i = 0; i < v; i++) {
            System.out.print((i + 1) + "- Adjacency list: [");
            for (JoueurDistance n : adjBis[i]) {
                System.out.print("(J:" + (n.getJoueur() + 1) + ", D: " + n.getDistance() + "),");
            }
            System.out.println("]");
        }
    }

    private void create_res_file(String s, BigInteger res) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        pw.println(res);
        pw.close();
    }

    public static void main(String args[]) throws IOException {
        Graph g = new Graph();
        if (args.length != 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        g.parse(args[0]);
        g.create_res_file(args[0], g.init());
        //g.printGraph();
        //g.printDijkstra(g.Dijkstra(g.v-1, g.adjBis), 4);

    }

}
