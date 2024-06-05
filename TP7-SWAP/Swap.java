import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Swap {
    
    private BufferedReader br = null;
    private int nb_disk, capacity, new_stockage;
    private int[] disk_before, disk_after;

    private void parse(String path) throws IOException {
        String line = "";
        br = new BufferedReader(new FileReader(path));
        line = br.readLine();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            nb_disk = Integer.parseInt(line);
            disk_before = new int[nb_disk];
            disk_after = new int[nb_disk];

            for (int i = 0; i < nb_disk; i++) {
                line = br.readLine();
                String[] parts = line.split(" ");
                disk_before[i] = Integer.parseInt(parts[0]);
                disk_after[i] = Integer.parseInt(parts[1]);
            }
        }
        br.close();
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        pw.println(capacity);
        pw.close();
    }

    public static void tri_insertion(int size, int[] tab_before, int[] tab_after) {
        
        for (int i = 1; i < size; i++) {
            int indexB = tab_before[i];
            int indexA = tab_after[i];
            int j = i - 1;

            while (j >= 0 && tab_before[j] > indexB) {
                tab_before[j + 1] = tab_before[j];
                tab_after[j + 1] = tab_after[j];
                j--;
            }
            tab_before[j + 1] = indexB;
            tab_after[j + 1] = indexA;
        }
    }

    public static void insertionSortDescending(int[] tab_before, int[] tab_after, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            int key = tab_before[i];
            int keyP = tab_after[i];
            int j = i - 1;
    
            while (j >= start && tab_after[i] > tab_after[j]) {
                tab_before[j + 1] = tab_before[j];
                tab_after[j + 1] = tab_after[j];
                j = j - 1;
            }
            tab_before[j + 1] = key;
            tab_after[j + 1] = keyP;
        }
    }

    private int[] getPos(int i, int[] tab){
        int[] res = new int[2];
        res[0] = -1;
        res[1] = -1;
        for (int j = 0; j < nb_disk; j++) {
            if(tab[j] == i){
                if(res[0] == -1){
                    res[0] = j;
                }else if(res[1] == -1 && (j == nb_disk-1 || tab[j] < tab[j+1])){
                    res[1] = j;
                    return res;
                }

            }
        }
        return res; 
    }

    public static void afficher(int[] tab){
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
        System.out.println();
    } 

    // Brouillon
    private void methode1(){
        tri_insertion(nb_disk, disk_before, disk_after);
        // afficher(disk_before);
        int tmp = 0;
        for (int i = 0; i < nb_disk; i++) {
            if(disk_before[i] != tmp){
                // System.out.println("disk_before " + disk_before[i] + " " + tmp);
                tmp = disk_before[i];
                int[] pos = getPos(disk_before[i], disk_before);
                // System.out.println("index " + pos[0] + " " + pos[1]);
                insertionSortDescending(disk_before, disk_after, pos[0], pos[1]);
            }
        }

        // afficher(disk_before);
        // afficher(disk_after);

        for (int i = 0; i < nb_disk; i++) {
            if(disk_after[i] - disk_before[i] > 0){
                // System.out.println("1");
                if(new_stockage != 0){
                    capacity = Math.max(capacity, (disk_before[i] - new_stockage));
                }else{
                    capacity = Math.max(capacity, disk_before[i]);
                }
                new_stockage += disk_after[i] - disk_before[i];
            }else{
                // System.out.println("2");
                if(capacity == 0){
                    capacity = disk_before[i];
                }else{
                    if((new_stockage - (disk_before[i] - disk_after[i])) <= 0){
                        capacity = Math.max(capacity, (disk_before[i] - new_stockage));
                    }else{

                    }
                    new_stockage = Math.max(0,Math.min(new_stockage, (new_stockage + disk_after[i] - disk_before[i])));
                }
                
            }
            // System.out.println("capacity " + capacity + " new_stockage " + new_stockage);
        }
    }

    private void methode2(){
        tri_insertion(nb_disk, disk_before, disk_after);
        for(int i = 0; i < nb_disk; i++){
            if(disk_after[i] - disk_before[i] > 0){
                if(capacity == 0){
                    capacity = disk_before[i];
                    new_stockage = disk_after[i] - disk_before[i];
                }else{
                    if(disk_before[i] - new_stockage > 0){
                        capacity = Math.max(capacity, disk_before[i] - new_stockage);
                    }
                    new_stockage += disk_after[i] - disk_before[i];
                }
            }
        }

        for(int i = nb_disk-1; i >= 0; i--){
            if(disk_after[i] - disk_before[i] < 0){
                if(capacity == 0){
                    capacity = disk_before[i];
                    new_stockage = disk_after[i] - disk_before[i];
                }else{
                    if(disk_before[i] - new_stockage > 0){
                        capacity = Math.max(capacity, disk_before[i] - new_stockage);
                    }
                    new_stockage += disk_after[i] - disk_before[i];
                }
            }else{
                if(disk_after[i] - disk_before[i] == 0){
                    if(capacity == 0){
                        capacity = disk_before[i];
                    }else{
                        if(disk_before[i] - new_stockage > 0){
                            capacity = Math.max(capacity, disk_before[i] - new_stockage);
                        }
                    }
                }
            }
        }
    }

    // Brouillon
    private void methode3(){
        int[] tab = new int[nb_disk];
        int index_min = Integer.MAX_VALUE;
    
        for(int i=0;i<nb_disk;i++){
            tab[i] = disk_after[i] - disk_before[i];
            if(tab[i] > 0 && index_min == Integer.MAX_VALUE){
                index_min = i;
            }
        }


        if(index_min != Integer.MAX_VALUE){
            while(verif2(tab)){
                // System.out.println("test");
                for(int i = 0; i<nb_disk; i++){
                    if(tab[i] > 0){
                        // System.out.println("test");
                        if(disk_before[i] < disk_before[index_min]){
                            // System.out.println("");
                            // System.out.println("disk_before[i] " + disk_before[i] + " disk_before[index_min] " + disk_before[index_min]);
                            index_min = i;
                        }
                        
                    }
                }
                if(disk_before[index_min] > capacity){
                    int tmp = disk_before[index_min] - capacity;
                    disk_before[index_min] = Integer.MAX_VALUE;
                    new_stockage += tmp;
                    capacity += tmp;
                }
                capacity += tab[index_min];
                tab[index_min] = Integer.MIN_VALUE; 
            }
        }



        for(int i = 0; i<nb_disk; i++){
            if(tab[i] == 0){
                if(disk_before[i] > capacity){
                    int tmp = disk_before[i] - capacity;
                    new_stockage += tmp;
                    capacity += tmp;
                }

                tab[i] = Integer.MIN_VALUE;
            }
        }


        while(verif(tab)){
            // System.out.println("test");
            int index_min_neg = 0;
            for(int i=1; i<nb_disk; i++){
                if(tab[i] > tab[index_min_neg]){
                    index_min_neg = i;
                }
            }

            if(disk_before[index_min_neg] > capacity){
                int tmp = disk_before[index_min_neg] - capacity;
                new_stockage += tmp;
                capacity += tmp;
                
            }
            capacity += tab[index_min_neg];
            tab[index_min_neg] = Integer.MIN_VALUE;
        }        
        
        capacity = new_stockage;

        
    }

    private boolean verif(int tab[]){
        for(int i = 0; i < nb_disk; i++){
            if(tab[i] != Integer.MIN_VALUE){
                return true;
            }   
        }
        return false;
    }
    private boolean verif2(int tab[]){
        for(int i = 0; i < nb_disk; i++){
            if(tab[i] > 0){
                return true;
            }   
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        Swap swap = new Swap();
        swap.parse(args[0]);
        //swap.methode1();
        swap.methode2();
        // swap.methode3();
        swap.create_res_file(args[0]);
    }
}
