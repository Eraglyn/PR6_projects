import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class Azulejos {

    private BufferedReader br = null;
    private int n;
    private int[] p1, h1, p2, h2, indexH1, indexH2;
    private List<Tripple> listTripplesFront;
    private List<Tripple> listTripplesBack;
    private boolean impossible = false;

    private void parse(String path) throws IOException {
        String line = "";
        br = new BufferedReader(new FileReader(path));
        line = br.readLine();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            String[] parts = null;
            n = Integer.parseInt(line);
            p1 = new int[n];
            h1 = new int[n];
            p2 = new int[n];
            h2 = new int[n];
            indexH1 = new int[n];
            indexH2 = new int[n];
            listTripplesFront = new java.util.ArrayList<>();
            listTripplesBack = new java.util.ArrayList<>();

            line = br.readLine();
            parts = line.split(" ");
            for (int i = 0; i < n; i++) {
                p1[i] = Integer.parseInt(parts[i]);
                indexH1[i] = i;
                indexH2[i] = i;
            }
            line = br.readLine();
            parts = line.split(" ");
            for (int i = 0; i < n; i++) {
                h1[i] = Integer.parseInt(parts[i]);
                listTripplesBack.add(new Tripple(p1[i], h1[i], i));
            }

            line = br.readLine();
            parts = line.split(" ");
            for (int i = 0; i < n; i++) {
                p2[i] = Integer.parseInt(parts[i]);
            }
            line = br.readLine();
            parts = line.split(" ");
            for (int i = 0; i < n; i++) {
                h2[i] = Integer.parseInt(parts[i]);
                listTripplesFront.add(new Tripple(p2[i], h2[i], i));
            }

        }
        br.close();
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        if (impossible) {
            pw.print("impossible");
        } else {
            for (int i = 0; i < n; i++) {
                if(i != n-1)
                    pw.print(indexH1[i]+1 + " ");
                else
                    pw.print(indexH1[i]+1);
            }
            pw.println();
            for (int i = 0; i < n; i++) {
                if(i != n-1)
                    pw.print(indexH2[i]+1 + " ");
                else
                    pw.print(indexH2[i]+1);
            }
        }
        pw.println();
        pw.close();
    }

    public static void tri_insertion(int tab[], int[] tabPrix, int[] tabIndex) {
        int taille = tab.length;

        for (int i = 1; i < taille; i++) {
            int index = tab[i];
            int indexP = tabPrix[i];
            int indexH = tabIndex[i];
            int j = i - 1;

            while (j >= 0 && tabPrix[j] > indexP) {
                tab[j + 1] = tab[j];
                tabIndex[j + 1] = tabIndex[j];
                tabPrix[j + 1] = tabPrix[j];
                j--;
            }
            tab[j + 1] = index;
            tabIndex[j + 1] = indexH;
            tabPrix[j + 1] = indexP;
        }
    }

    private void triTripple() {
        listTripplesBack.sort(new Comparator<Tripple>() {

            @Override
            public int compare(Azulejos.Tripple arg0, Azulejos.Tripple arg1) {
                if (arg0.p > arg1.p) {
                    return 1;
                }
                if (arg0.p < arg1.p) {
                    return -1;
                }
                if (arg0.h > arg1.h) {
                    return 1;
                }
                if (arg0.h < arg1.h) {
                    return -1;
                }
                if (arg0.i > arg1.i) {
                    return 1;
                }
                if (arg0.i < arg1.i) {
                    return -1;
                }
                return 0;
            }

        });
        listTripplesFront.sort(new Comparator<Tripple>() {

            @Override
            public int compare(Azulejos.Tripple arg0, Azulejos.Tripple arg1) {
                if (arg0.p > arg1.p) {
                    return 1;
                }
                if (arg0.p < arg1.p) {
                    return -1;
                }
                if (arg0.h > arg1.h) {
                    return 1;
                }
                if (arg0.h < arg1.h) {
                    return -1;
                }
                if (arg0.i > arg1.i) {
                    return 1;
                }
                if (arg0.i < arg1.i) {
                    return -1;
                }
                return 0;
            }
        });
        System.out.println("Front:");
        for (Tripple t : listTripplesFront) {
            t.printTripple();
        }
        System.out.println("Back:");
        for (Tripple t : listTripplesBack) {
            t.printTripple();
        }
    }

    private void naive() {
        tri_insertion(h1, p1, indexH1);
        tri_insertion(h2, p2, indexH2);
        // System.out.println("P1:");
        // afficher(p1);
        // System.out.println("H1:");
        // afficher(h1);
        // System.out.println("I1:");
        // afficher(indexH1);
        // System.out.println("P2:");
        // afficher(p2);
        // System.out.println("H2:");
        // afficher(h2);
        // System.out.println("I2:");
        // afficher(indexH2);
        // for (int i = 0; i < n; i++) {
        //     // System.out.println(h1[i] + " " + h2[i]);
        //     if(h1[i] <= h2[i]){
        //         int[] pos = getPos(p1[i], p1);
        //         int[] pos2 = getPos(p2[i], p2);
        //         // System.out.println((pos[1]-pos[0]) + " " + (pos2[1]-pos2[0]));
        //         if(pos[1]-pos[0] > pos2[1]-pos2[0]){
        //             permut(h1, p1, indexH1, pos[0], pos[1], p1[i], false);
        //         }else{
        //             // System.out.println(pos2[0] + " " + pos2[1]);
        //             permut(h2, p2, indexH2, pos2[0], pos2[1], p2[i], true);
        //         }
                
        //     }
        // }
        int tmp = 0;
        for (int i = 0; i < n; i++) {
            if(p1[i] != tmp){
                // System.out.println("p1 " + p1[i] + " " + tmp);
                tmp = p1[i];
                int[] pos = getPos(p1[i], p1);
                // System.out.println("index " + indexH1[i] + " " + pos[0] + " " + pos[1]);
                insertionSortDescending(h1, p1, indexH1, pos[0], pos[1]);
            }
        }
        tmp = 0;
        for (int i = 0; i < n; i++) {
            if(p2[i] != tmp){
                // System.out.println("p2 " + p2[i] + " " + tmp);
                tmp = p2[i];
                int[] pos = getPos(p2[i], p2);
                // System.out.println("index " + indexH2[i] + " " + pos[0] + " " + pos[1]);
                insertionSortDescending(h2, p2, indexH2, pos[0], pos[1]);
            }
        }
        for (int i = 0; i < n; i++) {
            if(h1[i] <= h2[i]){
                impossible = true;
                return;
            }
        }

    }

    private int[] getPos(int i, int[] tabPrix){
        int[] res = new int[2];
        res[0] = -1;
        res[1] = -1;
        for (int j = 0; j < n; j++) {
            if(tabPrix[j] == i){
                if(res[0] == -1){
                    res[0] = j;
                }else if(res[1] == -1 && (j == n-1 || tabPrix[j] < tabPrix[j+1])){
                    res[1] = j;
                    return res;
                }

            }
        }
        return res; 
    }

    public static void permut(int tab[], int[] tabPrix, int[] tabIndex, int pos1, int pos2, int prix, boolean status){

        for (int i = pos1+1; i < pos2+1; i++){ 
            int index = tab[i];  
            int indexP = tabPrix[i];
            int indexH = tabIndex[i];
            // System.out.println(index + " " + indexP + " " + indexH);
            int j = i-1;  
            // System.out.println(j + " " + tab[j] + " " + index + " " + tabPrix[j] + " " + prix);
            // System.out.println((j >= 0) + " " + (tab[j] > index) + " " + (tabPrix[j] == prix));
            if(status){
                while(j >= 0 && tab[j] > index && tabPrix[j] == prix)  {
                    // System.out.println("here");
                    // System.out.println(tab[j] + " " + index);
                    tab[j+1] = tab[j];  
                    tabIndex[j+1] = tabIndex[j];
                    tabPrix[j+1] = tabPrix[j];
                    j--;  
                }
            }else{
                while(j >= 0 && tab[j] < index && tabPrix[j] == prix)  {
                // System.out.println("here");
                // System.out.println(tab[j] + " " + index);
                tab[j+1] = tab[j];  
                tabIndex[j+1] = tabIndex[j];
                tabPrix[j+1] = tabPrix[j];
                j--;  
                }  
            }
            
            tab[j+1] = index; 
            tabIndex[j+1] = indexH;
            tabPrix[j+1] = indexP;
        }  
    }

    private void afficher(int[] tab){
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
        System.out.println();
    } 

    public static void insertionSortDescending(int[] array, int[] tabPrix, int[] tabIndex, int start, int end) {
        // for (int i = 0; i < tabIndex.length; i++) {
        //     System.out.print(tabIndex[i] + " ");
        // }
        // System.out.println();
        for (int i = start + 1; i <= end; i++) {
            int key = array[i];
            int keyP = tabPrix[i];
            int keyI = tabIndex[i];
            int j = i - 1;
    
            while (j >= start && array[j] < key) {
                array[j + 1] = array[j];
                tabPrix[j + 1] = tabPrix[j];
                tabIndex[j + 1] = tabIndex[j];
                j = j - 1;
            }
            array[j + 1] = key;
            tabPrix[j + 1] = keyP;
            tabIndex[j + 1] = keyI;
            
        }
    }

    private class Tripple {
        private int p;
        private int h;
        private int i;

        Tripple(int p, int h, int i) {
            this.p = p;
            this.h = h;
            this.i = i;
        }

        public void printTripple() {
            System.out.println("(p: " + p + " h: " + h + " i: " + i + ")");
        }

        public void setH(int h) {
            this.h = h;
        }

        public void setP(int p) {
            this.p = p;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getH() {
            return h;
        }

        public int getI() {
            return i;
        }

        public int getP() {
            return p;
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        Azulejos a = new Azulejos();
        a.parse(args[0]);
        a.naive();
        // System.out.println("P1:");
        // a.afficher(a.p1);
        // System.out.println("H1:");
        // a.afficher(a.h1);
        // System.out.println("I1:");
        // a.afficher(a.indexH1);
        // System.out.println("P2:");
        // a.afficher(a.p2);
        // System.out.println("H2:");
        // a.afficher(a.h2);
        // System.out.println("I2:");
        // a.afficher(a.indexH2);
        a.create_res_file(args[0]);
    }

}
