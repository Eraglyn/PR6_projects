import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Signature {
    private BufferedReader br = null;
    private String chaine = "", result = "";

    private void parse(String path) throws IOException {
        String line = "";
        br = new BufferedReader(new FileReader(path));
        line = br.readLine();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            do {
                chaine += line;
                line = br.readLine();
            } while (line != null);
        }
        br.close();
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        pw.println(result);
        pw.close();
    }

    // @author O. Carton
    /** 
     * Recherche d'un motif avec l'algorithme de Knuth, Morris et Pratt.
     * @param pattern motif à rechercher
     * @param text texte dans lequel le motif est recherché
     * @return position de la première occurrence si le motif a au moins
     *	  une occurrence dans le texte et -1 sinon.
     */

    static int knuthMorrisPratt(String pattern, String text) {
        int m = pattern.length();// Longueur du motif
        int n = text.length(); // Longueur du texte
        int[] r = new int[m]; // Fonction de suppléance
        int i; // Position dans le motif
        int j; // Position dans le texte
        // Calcul de la fonction de suppléance r
        // Pour tout i > 0, r[i] est la longueur du bord maximal compatible
        // du prefixe de longueur i du motif. Rappelons qu'un bord d'un
        // préfixe est compatible si son occurrence préfixe est suivie d'une
        // lettre différente de la lettre qui suit le préfixe.
        r[0] = i = -1;
        for (j = 1; j < m; j++) {
            // Ici i = s[j-1]
            while (i >= 0 && pattern.charAt(i) != pattern.charAt(j - 1))
                i = r[i];
            i++;
            // Ici i = s[j]
            if (pattern.charAt(i) != pattern.charAt(j))
                r[j] = i;
            else
                r[j] = r[i];
        }
        // Recherche du motif
        i = 0;
        j = 0;
        while (i < m && j < n) {
            if (pattern.charAt(i) == text.charAt(j)) {
                // Si les deux caractères coïncident,
                // les deux curseurs avancent d'une position vers la droite.
                j++;
                i++;
            } else {
                // Version précédente
                // if (i == 0)
                // j++;
                // La nouvelle version prend en compte l'absence de bord
                // compatible c'est-à-dire le cas r[i] == -1
                if (r[i] == -1) {
                    i = 0;
                    j++;
                } else
                    i = r[i];
            }
        }
        if (i == m)
            // Occurrence trouvée en position j-i
            return j - i;
        else
            // Aucune occurrence
            return -1;
    }

    private void DichotomicSearch(int s) {
        // TODO : Finir recherche Dichotomique
        int nb_pattern = chaine.length() - s;
        boolean status = false;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < nb_pattern-1; i++) {
            String pattern = chaine.substring(i, i+s);

            // System.out.println(pattern);
            // if(pattern.length()>chaine.substring(i+1).length()){
            //     break;
            // }
            int occ = chaine.substring(i+1).indexOf(pattern);
            if (occ == -1 && !set.contains(pattern)) {
                // System.out.println(("-1"));
                status = true;
                result = pattern;
                break;
                // String tmp = DichotomicSearch(s / 2);
                // if (tmp.length() < pattern.length() && !tmp.equals("")) {
                //     return tmp;
                // } else {
                //     return pattern;
                // }

            }else{
                set.add(pattern);
            }
        }
        if(s == 1 || s == chaine.length()){
            return;
        }
        if(!status){
            DichotomicSearch((chaine.length()-s) / 2 + s);
        }else{
            // System.out.println("here");
            if(s%2 == 0)
                DichotomicSearch(s/2);
            else
                DichotomicSearch(s/2+1);
        }


    }

    private void search() {
        Set<String> dico = new HashSet<String>();
        int nb_pattern = 1;
        while (true) {
            boolean end = false;
            for (int i = 0; i < chaine.length(); i++) {
                String chaine_tmp = "";
                if (i + nb_pattern <= chaine.length()) {
                    chaine_tmp = chaine.substring(i, i + nb_pattern);
                    // System.out.println("11");
                } else {
                    chaine_tmp = chaine.substring(i, chaine.length());
                    end = true;
                    // System.out.println("22");
                }

                // System.out.println(chaine_tmp);
                if (!dico.contains(chaine_tmp)) {
                    // System.out.println("here");
                    if (end) {
                        result = chaine_tmp;
                        return;
                    }
                    dico.add(chaine_tmp);
                    int occ = chaine.substring(i+1, chaine.length()).indexOf(chaine_tmp);
                    // System.out.println(occ);
                    if (occ == -1) {
                        result = chaine_tmp;
                        return;
                    }
                }
            }
            nb_pattern++;
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        Signature s = new Signature();
        s.parse(args[0]);
        s.search();
        // if(s.chaine.length() == 1){
        //     s.result = s.chaine;
        // }else{
        //     s.DichotomicSearch(s.chaine.length() / 2);
        // }
        
        s.create_res_file(args[0]);
    }
}
