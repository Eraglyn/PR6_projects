import java.io.*;
import java.util.*;

public class Chaines {

    private String[] pattern = {"ad","da","ab"}, pattern_bis = null;
    private String chaine = "", res = "addaab";
    private int size_param = 0, size_param_bis = 0;
    private int resPos = 0, resSize = 0;

    // Parse le fichier d'entree
    private void parse(String s) throws IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(s));
        line = br.readLine();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            String[] line_bis = line.split(" ");
            size_param = Integer.parseInt(line_bis[0]);
            if(line_bis.length > 1){
                size_param_bis = Integer.parseInt(line_bis[1]);
            }
            pattern = new String[size_param];
            for (int i = 0; i < size_param; i++) {
                line = br.readLine();
                if (line == null) {
                    System.out.println("Erreur, fichier mal formé.");
                    System.exit(1);
                } else {
                    pattern[i] = line.substring(0, line.length()-1);
                    //System.out.println(pattern[i]);
                }
            }
            pattern_bis = new String[size_param];
            for (int i = 0; i < size_param_bis; i++) {
                line = br.readLine();
                if (line == null) {
                    System.out.println("Erreur, fichier mal formé.");
                    System.exit(1);
                } else {
                    pattern_bis[i] = line.substring(0, line.length()-1);
                    //System.out.println(pattern[i]);
                }
            }
            line = br.readLine();
            while(line != null){
                chaine += line;
                line = br.readLine();
            }
            chaine = chaine.substring(0, chaine.length()-1);
        }
        br.close();
    }


    /**
     * Counts the occurrences of patterns in a given string.
     * 
     * @param s The input string to search for patterns.
     * @param p An array of patterns to search for in the input string.
     * @return A map containing the patterns as keys and their respective counts as values.
     */
    private Map<String, Integer> countPattern(String s, String[] p){
        Map<String, Integer> res = new HashMap<String, Integer>();
        for(String word : p){
            res.put(word, res.getOrDefault(word, 0));
        }
        for(int i = 0; i < s.length(); i++){
            for(int j = 0; j < size_param; j++){
                
                if((i+p[j].length() <= s.length()) && s.substring(i, i+p[j].length()).equals(p[j])){

                    res.put(p[j], res.get(p[j])+1);
                }
            }
        }
        // for(int i = 0; i<size_param; i++){
        //     System.out.print(pattern[i] + " " + res.get(pattern[i]) + ", ");
        // }
        // System.out.println();
        return res;
    }

    // Verifie si le pattern est bien present
    private boolean verifyCountPattern(Map<String, Integer> m){
        for(String word : pattern){
            if(m.get(word) == 0){
                return false;
            }
        }
        return true;
    }
    // Verifie si le pattern est bien absent
    private boolean verifyCountPatternInv(Map<String, Integer> m){
        for(String word : pattern_bis){
            if(m.get(word) != 0){
                return false;
            }
        }
        return true;
    }

    /* 
     * Probleme 1 et 3
     * mode: true -> probleme 1
     * mode: false -> probleme 3
     * 
     * Effectue la recherche de la sous chaine
     * 
     */
    private void searchRes(boolean mode){
        String chaine_tmp;
        // Determiner les lettres uniques
        Set<String> set = new HashSet<String>();
        for (String string : pattern) {
            for(int i = 0; i < string.length(); i++){
                set.add(string.substring(i, i+1));
            }
        }
        int i = 0;
        /* 
         * Parcours la chaines de caractere à partir
         * d'une taille minimal obtenu par les lettres
         * unique du pattern, puis s'agrandit d'une unite
         * au fur à mesure, jusqu'à trouver la sous chaine.
         */
        while(set.size() + i <= chaine.length()){
            boolean end = false;
            for(int j = 0; j < chaine.length(); j++){
                if(set.size()+i+j >= chaine.length()){
                    chaine_tmp = chaine.substring(j, chaine.length());
                    end = true;
                }else{
                    chaine_tmp = chaine.substring(j, set.size()+i+j);
                    //System.out.println(chaine_tmp);
                }
                //System.out.println(set.size()+i);
                boolean status = verifyCountPattern(countPattern(chaine_tmp, pattern));
                if(!mode){
                    status = status && verifyCountPatternInv(countPattern(chaine_tmp, pattern_bis));
                }
                // Attribution des resultats
                if(status){
                    res = chaine_tmp;
                    resSize = chaine_tmp.length();
                    resPos = j;
                    return;
                }
                if(end){break;}
            }
            i++;

        }
        // if (verifyCountPattern(m)){
            // for(int i = 0; i<size_param; i++){
            //     System.out.println(pattern[i] + " " + m.get(pattern[i]));
            // }
        // }
    }

    // Generateur de combinaisons
    private void generateCombinations(char[] chars, int length, String current, List<String> result) {
        if (current.length() == length) {
            result.add(current);
            return;
        }

        for (char c : chars) {
            generateCombinations(chars, length, current + c, result);
        }
    }

    // Probleme 2
    private void createRes(){
        // Determiner les lettres uniques
        Set<String> set = new HashSet<String>();
        for (String string : pattern) {
            for(int i = 0; i < string.length(); i++){
                set.add(string.substring(i, i+1));
            }
        }
        // Conversion en tableau de char
        char[] chars = new char[set.size()];
        int i = 0;
        for (String string : set) {
            chars[i] = string.charAt(0);
            i++;
        }
        // Test tout les combinaisons possibles
        i = 0;
        while(true){
            List<String> result = new ArrayList<>();
            this.generateCombinations(chars, chars.length+i, "", result);

            for (String combination : result) {
                if(verifyCountPattern(countPattern(combination, pattern))){
                    res = combination;
                    resSize = combination.length();
                    resPos = 0;
                    //System.out.println(res);
                    return;
                }
            }
            i++;
        }
        
    }

    private void generateCombinations(List<List<Integer>> lists, List<Integer> current, int depth, List<List<Integer>> result) {
        if (depth == lists.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i : lists.get(depth)) {
            current.add(i);
            generateCombinations(lists, current, depth + 1, result);
            current.remove(current.size() - 1);
        }
    }

    private void positionPattern(String s, String[] p){
        Map<String, ArrayList<Integer>> res = new HashMap<String, ArrayList<Integer>>();
        for(String word : p){
            res.put(word, new ArrayList<Integer>());
        }

        for(int i = 0; i < s.length(); i++){
            for(int j = 0; j < size_param; j++){
                if((i+p[j].length() <= s.length()) && s.substring(i, i+p[j].length()).equals(p[j])){
                    res.get(p[j]).add(i);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        List<List<Integer>> lists = new ArrayList<>();
        for(int i = 0; i<size_param; i++){
            lists.add(new ArrayList<>(res.get(pattern[i])));
        }
        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinations(lists, new ArrayList<>(), 0, combinations);
        for (List<Integer> combination : combinations) {
            int max = Collections.max(combination);
            for(int i = 0; i<size_param; i++){
                if(res.get(pattern[i]).contains(max)){
                    max += pattern[i].length();
                }
            }
            int min_tmp = Collections.min(combination);
            
            if(max-min_tmp < min){
                System.out.println(min_tmp + ", " + max);
                min = max-min_tmp;
                resPos = min_tmp;
                resSize = max-min_tmp;
                this.res = s.substring(min_tmp, max);
            }
        }
        // for(int i = 0; i<size_param; i++){
        //     System.out.print(pattern[i] + " ");
        //     for(int j = 0; j<res.get(pattern[i]).size(); j++){
        //         System.out.print(res.get(pattern[i]).get(j) + ", ");
        //     }
        //     System.out.println();
        // }
    }

    private boolean verifyContainPattern(String s, String p){
        for(int i = 0; i < s.length(); i++){
            if((i+p.length() <= s.length()) && s.substring(i, i+p.length()).equals(p)){
                return true;
            }
        }
        return false;
    }

    private boolean verifTrue(boolean[] b){
        for(boolean bool : b){
            if(!bool){
                return false;
            }
        }
        return true;
    }

    private int getPosPattern(String s, String p){
        for(int i = 0; i < s.length(); i++){
            if((i+p.length() <= s.length()) && s.substring(i, i+p.length()).equals(p)){
                return i;
            }
        }
        return -1;
    }

    private void createRes2(){
        boolean[] index = new boolean[size_param];
        for(int i = 0; i<size_param; i++){
            System.out.println(index[i]);
        }
        Random rand = new Random();
        while(true){
            if(verifTrue(index)){
                break;
            }
            int random = rand.nextInt(size_param);
            if(!index[random]){
                index[random] = true;
                res += pattern[random];
            }
        }

    }

    // Création du fichier de sortie
    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        pw.println(resSize + " " + resPos);
        if(res.equals("")){
            pw.println("#");
        }else{
            pw.println(res);
        }
        pw.close();
    }



    public String algo2(String principaleString,String add){
        int first = 0, last = principaleString.length();
        int max = 0, position = 0;boolean sens=true;
        int pos_add_sens_direct=0;int pos_add_sens_indirect=0;

        if (!verifyContainPattern(principaleString, add)) {
            int i = 0;
            int j = add.length()-1;

            while (first < principaleString.length() && last >= 0) {//recherche du sous préfixe add dans principalestring
                // Recherche dans le sens direct
                if (i < add.length() && add.charAt(i) == principaleString.charAt(first)) {
                    int pos = first,pos_bis=0;
                    int m = 0;

                    while (i < add.length() && add.charAt(i) == principaleString.charAt(first + i)) {
                        i++;pos_bis++;
                        m++;
                        pos++;
                    }

                    if (m > max) {
                        max = m;
                        position = pos;
                        sens=true;// sens direct
                        pos_add_sens_direct=pos_bis;
                    }

                    i = 0;
                }
                System.out.println("test milieu de while \n");
                // Recherche dans le sens inverse
                if (j < add.length() && add.charAt(j) == principaleString.charAt(last - 1)) {
                    int pos = last - 1,pos_bis=0;
                    int m = 0;

                    while (j < add.length() && add.charAt(j) == principaleString.charAt(last - 1 - j)) {
                        j++;pos_bis++;
                        m++;
                    }

                    if (m > max) {
                        max = m;
                        position = pos;
                        sens=false;//sens indirect
                        pos_add_sens_indirect=pos_bis;
                    }

                    j = 0;
                }
                System.out.println("test dans le while \n");
                first++;
                last--;
            }
            System.out.println("test fin de while sens: "+sens+" max,position: "+max+", "+position);
            if(max>0){
                String news="";
                if(sens==true){//sens direct
                    for(int p=0;p<principaleString.length();p++){
                        if(p==position){
                            for(int z=pos_add_sens_direct;z<add.length();z++){
                                news+=add.charAt(z);
                            }
                        }else{
                            news+=principaleString.charAt(p);
                        }
                    }
                }else{
                    for(int p=0;p<principaleString.length();p++){
                        if(p==position){
                            for(int z=add.length();z>pos_add_sens_indirect;z--){
                                System.out.println(add.charAt(z-1)+"\n");
                                news+=add.charAt(z-1);
                            }
                        }else{
                            news+=principaleString.charAt(p);
                        }
                        
                    }
                }
                return news;
            }

        }
        return principaleString;

    }

    /* 
     * Se placer dans TP3-CHAINES
     * 
     * javac Chaines.java
     * Exemple:
     * java Chaines ./sample1/1.in 1
     * 
     * Le dernier parametre est le numero du probleme
     * et sa fonction qui va avec:
     * 1 -> Pb n°1
     * 2 -> Pb n°2
     * 3 -> Pb n°3
     */
    public static void main(String[] args) {
        Chaines c = new Chaines();
        // try {
            // c.parse(args[0]);
            c.createRes2();
            //test pour l'algo idée de Juan
            String a="abcedf";String b="ba";
            String d=c.algo2(a,b);
            System.out.println(d);



            // if(Integer.parseInt(args[1]) == 1){
            //     c.searchRes(true);
            // }else if(Integer.parseInt(args[1]) == 2){
            //     c.createRes();
            // }else{
            //     c.searchRes(false);
            // }
            //System.out.println(c.res + ", " + c.resSize + ", " + c.resPos);
            // c.create_res_file(args[0]);
            // c.positionPattern(c.chaine, c.pattern);
            // System.out.println(c.res + ", " + c.resSize + ", " + c.resPos);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

    }

}
