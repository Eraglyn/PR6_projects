import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class Equilibre {

    private BufferedReader br = null;
    private int m, k, sum_f, nb_tuile_ajoute,maximum;
    private int[] l3;
    private double[] l2;
    private int[] nbr;
    private List<Integer> list = new ArrayList<Integer>();
    private boolean boucle_infinit = false;

    private void parse(String path) throws IOException {
        String line = "";
        br = new BufferedReader(new FileReader(path));
        line = br.readLine();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            String[] parts = line.split(" ");
            m = Integer.parseInt(parts[0]);
            k = Integer.parseInt(parts[1]);
            line = br.readLine();
            if (line == null) {
                System.out.println("Erreur, fichier vide.");
                System.exit(1);
            }
            parts = line.split(" ");
            l2 = new double[m];
            l3 = new int[m];
            nbr=new int[m];
            for (int i = 0; i < m; i++) {
                l2[i] = Integer.parseInt(parts[i]);
                sum_f += l2[i];
            }
            line = br.readLine();
            if (line != null && !line.equals("")) {
                parts = line.split(" ");
                for (int i = 0; i < parts.length; i++) {
                    list.add(Integer.parseInt(parts[i]));
                    l3[Integer.parseInt(parts[i]) - 1]++;
                }
            }

        }

        br.close();
    }
    private boolean verify_law2(int tuile,List<Integer> li) {
        double fi = (li.size() + 1) * (l2[tuile]) / sum_f;
        // System.out.println("fi " + fi + "sum_f " + sum_f + "l3 tuile " + l3[tuile]);
        if (!((fi - 1) < (l3[tuile] + 1) && (l3[tuile] + 1) < (fi + 1))) {
            // System.out.println("Fail test1 tuile n°" + tuile + " f:" + l2[tuile]);
            return false;
        }
        for (int i = 0; i < m; i++) {
            if (i != tuile) {
                fi = (li.size() + 1) * (l2[i]) / sum_f;
                if (!((fi - 1) < l3[i] && l3[i] < (fi + 1))) {

                    return false;
                }
            }
        }
        // System.out.println("Test pass for :" + tuile);
        return true;
    }
    private boolean verify_law(int tuile) {
        double fi = (list.size() + 1) * (l2[tuile]) / sum_f;
        // System.out.println(fi + " l3 " + l3[tuile] + " l2 " + l2[tuile] + " sum_f " + sum_f + " list size " + list.size());
        // System.out.println("fi " + fi + "sum_f " + sum_f + "l3 tuile " + l3[tuile]);
        if (!((fi - 1) < (l3[tuile] + 1) && (l3[tuile] + 1) < (fi + 1))) {
            // System.out.println("Fail test1 tuile n°" + tuile + " f:" + l2[tuile]);
            return false;
        }
        for (int i = 0; i < m; i++) {
            if (i != tuile) {
                fi = (list.size() + 1) * (l2[i]) / sum_f;
                if (!((fi - 1) < l3[i] && l3[i] < (fi + 1))) {
                    return false;
                }
            }
        }
        // System.out.println("Test pass for :" + tuile);
        return true;
    }

    
    public static void printArrayList(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();  // Pour passer à la ligne après l'impression de tous les éléments
    }
    private void equilibre_o(){
        // for(int i=0;i<list.size();i++){
        //     nbr[list.get(i)-1]++;
        // }
        /*for(int i=0;i<nbr.length;i++){
             System.out.print(nbr[i]);
        }*/
        boolean forever=true;
        while(true){
            for(int j=0;j<l2.length;j++){
                if((l3[j]%l2[j])!=0) forever=false;
            }
            if(forever==false){
                int choice=this.best_choice();
                // System.out.println(c);
                // System.out.println(verify_law(c));
                // System.out.println("choice "+choice);
                if(this.verify_law(choice)){
                    
                    this.nb_tuile_ajoute++;
                    list.add(choice+1);
                    l3[choice]++;
                }else{
                    break;
                }
                // System.out.println("choice "+choice);
                // printArrayList(list);
            }else{
                // System.out.println("forever");
                this.boucle_infinit=true;
                return;
            }
        }
    }


    private int best_choice(){// on choisit ici le nombre qui est le moins utilisé par rapport a ça fréquence demandé en 2éme lignes
        double p=0;double y=0;int a=0;
        for(int i=0;i<this.m;i++){
            // System.out.println("i " + i +" l3 "+this.l3[i]+" l2 "+this.l2[i]);
            y=Math.max(p, this.l2[i]-( this.l3[i] % this.l2[i] ));
            // System.out.println("test y " + y);
            if(y>p){
                // System.out.println("best choice "+i+1);
                p=y;
                a=i;
            }
        }
        // this.l3[a]++;
        // System.out.println("best choice " + a);
        return a;
    }


    private int Equilibre_backtracking(int max,List<Integer> li){
        for(int i=0;i<li.size();i++){
            if(verify_law2(i,li)){
                ArrayList<Integer> l =new ArrayList<>(li);
                //printArrayList(l);
                l.add(i);
                //printArrayList(l);
                max=Math.max(max,Equilibre_backtracking(max+1,l));
            }
        }

        
        return max;
    }
    private void equilibre_naive() {
        while (true) {
            double[] tab = new double[m];
            boolean flag = false;
            for (int i = 0; i < m; i++) {
                if (verify_law(i)) {
                    flag = true;
                    double fi = (list.size() + 1) * (l2[i]) / sum_f;
                    tab[i] = fi + 1 - l3[i];

                }
            }
            if (!flag) {
                break;
            }
            int max = 0;
            for (int i = 1; i < m; i++) {
                if (tab[i] > tab[max]) {
                    max = i;
                }
            }

            list.add(max + 1);
            l3[max]++;
            nb_tuile_ajoute++;

            // cpt++;
            // if (cpt >= 20) {
            //     boucle_infinit = true;
            //     break;
            // }   

            boolean forever=true;
            for(int j=0;j<l2.length;j++){
                if((l3[j]%l2[j])!=0) forever=false;
            }
            if(forever){
                boucle_infinit = true;
                return;
            }
        }
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        if (boucle_infinit) {
            pw.println("forever");
        } else {
            pw.println(nb_tuile_ajoute);
        }
        pw.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        Equilibre e = new Equilibre();
        e.parse(args[0]);
        e.equilibre_naive();
        //e.nb_tuile_ajoute=e.Equilibre_backtracking(0,e.list);
        // e.equilibre_o();
        // System.out.println(e.nb_tuile_ajoute);
        // ArrayList<Integer> t=new ArrayList<>(e.list);
        // printArrayList(e.list);
        e.create_res_file(args[0]);
    }
}
