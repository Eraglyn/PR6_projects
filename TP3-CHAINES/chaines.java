import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class chaines {

    private boolean fileOpened = false;
    private BufferedReader br = null;
    private int nb_chaine = 0, res_pos = 0, nb_chaine_inv = 0;
    private int[] indices, indices_inv;
    private String[] tab, tab_inv;
    private String s = "", res = "";
    private boolean inv = false;

    private void parse(String path) throws IOException {

        String line = "";
        if (!fileOpened) {
            br = new BufferedReader(new FileReader(path));
            fileOpened = true;
            line = br.readLine();
            if (line == null) {
                System.out.println("Erreur, fichier vide.");
                System.exit(1);
            } else {
                String[] parts = line.split(" ");
                nb_chaine = Integer.parseInt(parts[0]);
                if(parts.length == 2){
                    inv = true;
                    nb_chaine_inv = Integer.parseInt(parts[1]);
                }
            }

        }
        tab = new String[nb_chaine];
        

        for (int i = 0; i < nb_chaine; i++) {
            line = br.readLine();
            tab[i] = line;
            while (line != null && !line.substring(line.length() - 1, line.length()).equals("#")) {
                line = br.readLine();
                tab[i] += line;
            }
            tab[i] = tab[i].substring(0, tab[i].length() - 1);
            // if(tab[i].indexOf("xvu") != -1){
            //     System.out.println(i);
            // }   
        }
        if(inv){
            tab_inv = new String[nb_chaine_inv];
            for (int i = 0; i < nb_chaine_inv; i++) {
                line = br.readLine();
                tab_inv[i] = line;
                while (line != null && !line.substring(line.length() - 1, line.length()).equals("#")) {
                    line = br.readLine();
                    tab_inv[i] += line;
                }
                tab_inv[i] = tab_inv[i].substring(0, tab_inv[i].length() - 1); 
            }
        }

        s = br.readLine();
        line = s;
        while (line != null && !line.substring(line.length() - 1, line.length()).equals("#")) {

            line = br.readLine();
            s += line;
        }
        s = s.substring(0, s.length() - 1);
        // System.out.println(s);
        indices = new int[nb_chaine];
        for (int i = 0; i < nb_chaine; i++) {
            // System.out.println(tab[i]);
            indices[i] = s.indexOf(tab[i]);
            //System.out.println(indices[i]);
        }
        if(inv){
            indices_inv = new int[nb_chaine_inv];
            for (int i = 0; i < nb_chaine_inv; i++) {
                indices_inv[i] = s.indexOf(tab_inv[i]);
            }
        }

        br.close();
    }

    private int getMin(int[] l) {
        int min = 0;
        while (l[min] == -1) {
            min++;
        }

        for (int i = min+1; i < l.length; i++) {
            if (l[i] < l[min] && l[i] != -1) {
                min = i;
            }
        }
        return min;
    }

    private int getMax(int[] l) {
        int max = 0;
        for (int i = 1; i < l.length; i++) {
            // if(i == 410 || i == 330){
            //     System.out.println(i + " " + l[i] + " " + l[max] + " " + tab[i].length() + "/" + tab[i] + " " + tab[max].length());
            // }
            if (l[i] > l[max] && l[i] + tab[i].length() > l[max] + tab[max].length()){
                max = i;
            }
        }
        return max;
    }

    private boolean is_done() {
        for (int i = 0; i < nb_chaine; i++) {
            if (indices[i] == -1) {
                return true;
            }
        }
        return false;
    }

    private void shortest_string(boolean mode) throws InterruptedException {
        if (is_done()) {
            // System.out.println(0 + " " + 0);
            // System.out.println("#");
            return;
        }

        int min = getMin(indices);
        int max = getMax(indices);

        int min2 = 0, max2 = 0;
        if (mode) {
            min2 = getMin(indices_inv);
            max2 = getMax(indices_inv);
        }
        // System.out.println(max);
        // res = s.substring(indices[min], indices[max] + tab[max].length());
        // System.out.println(res);
        do {
            String tmp = s.substring(indices[min], indices[max] + tab[max].length());
            if (tmp.length() < res.length() || res.equals("")) {
                // System.out.println("test");
                if(mode){
                    if((min2 >= min && min2 <= max) || (max2 >= min && max2 <= max)){
                        
                    }else{
                        res = tmp;
                        res_pos = indices[min];
                    }
                }else{
                    res = tmp;
                    res_pos = indices[min];
                }
            }


            int x = (s.substring(indices[min] + tab[min].length(), s.length())).indexOf(tab[min]);
            // System.out.println(x);
            if (x != -1) {
                indices[min] = indices[min] + tab[min].length() + x;
            } else {
                // break;
                return;
            }
            
            max = getMax(indices);
            min = getMin(indices);
            if(mode){
                while(min2 < min && min2 != -1){
                    x = (s.substring(indices_inv[min2] + tab_inv[min2].length(), s.length())).indexOf(tab_inv[min2]);
                    if (x != -1) {
                        indices_inv[min2] = indices_inv[min2] + tab_inv[min2].length() + x;
                        min2 = getMin(indices_inv);
                        max2 = getMax(indices_inv);
                    } else {
                        indices_inv[min2] = -1;
                        min2 = getMin(indices_inv);
                        max2 = getMax(indices_inv);
                    }
                }
            }
            

        }while (!is_done());
        // for (int i = 0; i < nb_chaine; i++) {
        //     System.out.println(indices[i]);
        // }
        // System.out.println(res.length() + " " + res_pos);
        // System.out.print(res);
        // System.out.println("#");
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        pw.println(res.length() + " " + res_pos);
        pw.print(res);
        pw.println("#");
        pw.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        chaines c = new chaines();
        c.parse(args[0]);
        if(args.length > 1){
            c.shortest_string(true);
        }else{
            c.shortest_string(false);
        }
        c.create_res_file(args[0]);
    }

}
