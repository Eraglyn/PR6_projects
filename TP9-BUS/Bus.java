import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Bus {

    private class Ville {
        private String ville;
        private int busA, busB, busC;
        private boolean visite = false;
        private LinkedList<Arc> arcs;

        public LinkedList<Arc> getArcs() {
            return arcs;
        }

        public void setArcs(LinkedList<Arc> arcs) {
            this.arcs = arcs;
        }

        public boolean isVisite() {
            return visite;
        }

        public void setVisite(boolean visite) {
            this.visite = visite;
        }

        public Ville(String ville, int busA, int busB, int busC) {
            this.ville = ville;
            this.busA = busA;
            this.busB = busB;
            this.busC = busC;
            this.arcs = new LinkedList<Arc>();
        }

        @SuppressWarnings("unused")
        public String getVille() {
            return ville;
        }

        @SuppressWarnings("unused")
        public void setVille(String ville) {
            this.ville = ville;
        }

        @SuppressWarnings("unused")
        public int getBusA() {
            return busA;
        }

        @SuppressWarnings("unused")
        public void setBusA(int busA) {
            this.busA = busA;
        }

        @SuppressWarnings("unused")
        public int getBusB() {
            return busB;
        }

        @SuppressWarnings("unused")
        public void setBusB(int busB) {
            this.busB = busB;
        }

        @SuppressWarnings("unused")
        public int getBusC() {
            return busC;
        }

        @SuppressWarnings("unused")
        public void setBusC(int busC) {
            this.busC = busC;
        }
    }

    private class Arc {
        private Ville villeA;
        private int distance;

        public Arc(Ville villeA, int distance) {
            this.villeA = villeA;
            this.distance = distance;
        }

        @SuppressWarnings("unused")
        public Ville getVilleA() {
            return villeA;
        }

        @SuppressWarnings("unused")
        public void setVilleA(Ville villeA) {
            this.villeA = villeA;
        }

        @SuppressWarnings("unused")
        public int getDistance() {
            return distance;
        }

        @SuppressWarnings("unused")
        public void setDistance(int distance) {
            this.distance = distance;
        }
    }

    private BufferedReader br = null;
    private int nb_ville, busA, busB, busC;
    private Ville[] villes;
    private LinkedList<String> res_final;

    private void parse(String path) throws IOException {
        String line = "";
        res_final = new LinkedList<String>();
        br = new BufferedReader(new FileReader(path));
        line = br.readLine();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            nb_ville = Integer.parseInt(line);
            villes = new Ville[nb_ville];
            line = br.readLine();
            busA = Integer.parseInt(line);
            line = br.readLine();
            busB = Integer.parseInt(line);
            line = br.readLine();
            busC = Integer.parseInt(line);
            for (int i = 0; i < nb_ville; i++) {
                line = br.readLine();
                String[] parts = line.split(" ");
                villes[i] = new Ville(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]));
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                for (int i = 0; i < villes.length; i++) {
                    if (villes[i].getVille().equals(parts[0])) {
                        villes[i].getArcs().add(new Arc(getVille(parts[1]), Integer.parseInt(parts[2])));
                    }
                    if (villes[i].getVille().equals(parts[1])) {
                        villes[i].getArcs().add(new Arc(getVille(parts[0]), Integer.parseInt(parts[2])));
                    }
                }
            }
        }
    }

    private Ville getVille(String ville) {
        for (int i = 0; i < villes.length; i++) {
            if (villes[i].getVille().equals(ville)) {
                return villes[i];
            }
        }
        return null;
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        for (int i = 0; i < res_final.size(); i++) {
            pw.println(res_final.get(i));
        }
        pw.close();
    }

    private Ville searchMax(char c) {
        if (c == 'A') {
            int max = -1;
            Ville ville = null;
            for (int i = 0; i < villes.length; i++) {
                if (villes[i].getBusA() > max && !villes[i].isVisite()) {
                    max = villes[i].getBusA();
                    ville = villes[i];
                }
            }
            return ville;
        } else if (c == 'B') {
            int max = -1;
            Ville ville = null;
            for (int i = 0; i < villes.length; i++) {
                if (villes[i].getBusB() > max && !villes[i].isVisite()) {
                    max = villes[i].getBusB();
                    ville = villes[i];
                }
            }
            return ville;
        } else {
            int max = -1;
            Ville ville = null;
            for (int i = 0; i < villes.length; i++) {
                if (villes[i].getBusC() > max && !villes[i].isVisite()) {
                    max = villes[i].getBusC();
                    ville = villes[i];
                }
            }
            return ville;
        }
    }

    private void start() {
        for (int j = 0; j < busA; j++) {
            Ville ville = searchMax('A');
            if (ville == null) {
                break;
            }
            res_final.add("###");
            res_final.add("A");
            methode1(ville, 1);
        }
        for (int j = 0; j < busB; j++) {
            Ville ville = searchMax('B');
            if (ville == null) {
                break;
            }
            res_final.add("###");
            res_final.add("B");
            methode1(ville, 2);
        }
        for (int j = 0; j < busC; j++) {
            Ville ville = searchMax('C');
            if (ville == null) {
                break;
            }
            res_final.add("###");
            res_final.add("C");
            methode1(ville, 3);
        }

    }

    private Ville search_next_min(Ville v) {
        int min = Integer.MAX_VALUE;
        Ville tmp = null;
        for (int i = 0; i < v.getArcs().size(); i++) {
            if (v.getArcs().get(i).getDistance() < min && !v.getArcs().get(i).getVilleA().isVisite()) {
                min = v.getArcs().get(i).getDistance();
                tmp = v.getArcs().get(i).getVilleA();
            }
        }
        return tmp;
    }

    private boolean isAllVisite(Ville v) {
        for (Arc i : v.getArcs()) {
            if (i.getVilleA().isVisite() == false) {
                return false;
            }
        }
        return true;
    }

    private boolean notEnoughCredit(Ville v, int credit) {
        for (Arc i : v.getArcs()) {
            if (i.getDistance() <= credit && i.getVilleA().isVisite() == false) {
                return false;
            }
        }
        return true;
    }

    private void backtracking_PathFinder(Ville v, int credit, LinkedList<LinkedList<Ville>> result,
            LinkedList<Ville> path) {
        if (credit == 0 || v.getArcs().isEmpty() || notEnoughCredit(v, credit)) {
            result.add(new LinkedList<Ville>(path));
            return;
        }
        for (Arc voisins : v.getArcs()) {
            if (!voisins.getVilleA().isVisite() && voisins.getDistance() <= credit) {
                path.add(voisins.getVilleA());
                voisins.getVilleA().setVisite(true);
                backtracking_PathFinder(voisins.getVilleA(), credit - voisins.getDistance(), result, path);
                path.remove(path.size() - 1);
                voisins.getVilleA().setVisite(false);
            }
        }
    }

    public void methode1(Ville v, int typeBus) {
        int credit = 0;
        switch (typeBus) {
            case 1:
                credit = v.getBusA();
                break;
            case 2:
                credit = v.getBusB();
                break;
            default:
                credit = v.getBusC();
                break;
        }
        // while (true) {
        res_final.add(v.getVille());
        v.setVisite(true);
        // Ville next = search_next_min(v);
        // for (int i = 0; i < v.getArcs().size(); i++) {
        // if (v.getArcs().get(i).getVilleA().equals(next)) {
        // credit -= v.getArcs().get(i).getDistance();
        // }
        // }
        // // System.out.println(credit);
        // v = next;
        // if (next == null || credit <= 0) {
        // break;
        // }
        LinkedList<LinkedList<Ville>> result = new LinkedList<LinkedList<Ville>>();
        backtracking_PathFinder(v, credit, result, new LinkedList<Ville>());
        LinkedList<Ville> max = new LinkedList<Ville>();
        for (LinkedList<Ville> i : result) {
            if (max.size() < i.size()) {
                max = i;
            }
        }
        for (Ville i : max) {
            res_final.add(i.getVille());
            i.setVisite(true);
        }
        // }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        Bus bus = new Bus();
        bus.parse(args[0]);
        bus.start();
        bus.create_res_file(args[0]);
    }
}