import java.io.*;
import java.util.Random;

public class Match {
    
    private boolean fileOpened = false;
    private BufferedReader br = null;
    private int size = 0;
    private int[] res;
    private PrintWriter pw = null;

    private int[] parse(String s) throws IOException{
        String line = "";
        if(!fileOpened) {
            br = new BufferedReader(new FileReader(s));
            fileOpened = true;
            line = br.readLine();
            if(line == null){
                System.out.println("Erreur, fichier vide.");
                System.exit(1);
            }else {
                String[] tmp = line.split(" ");
                size = Integer.parseInt(tmp[1]);
                res = new int[Integer.parseInt(tmp[0])];
                return null;
            }
        }else {
            line = br.readLine();
            if (line == null) {
                closeFile();
                return null;
            }else{
                String[] tab = line.split(" ");
                if(tab.length > 2){
                    System.out.println("Erreur, fichier mal formé.");
                    System.exit(1);
                }else{
                    return new int[]{Integer.parseInt(tab[0]), Integer.parseInt(tab[1])};
                }
            }
        }
        return null;
    }

    private void closeFile(){
        try {
            br.close();
            fileOpened = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void res_match(String s) throws IOException{
        int[] tab = parse(s);
        for(int i = 0; i < size; i++){
            tab = parse(s);
            if(res[tab[0]-1] > res[tab[1]-1]){
                res[tab[1]-1] += 1;
                create_res_file(s, tab[1]);
            }else if(res[tab[0]-1] < res[tab[1]-1]){
                res[tab[0]-1] += 1;
                create_res_file(s, tab[0]);
            }else{
                Random rand = new Random();
                switch(rand.nextInt(2)){
                    case 0:
                        res[tab[0]-1] += 1;
                        create_res_file(s, tab[0]);
                        break;
                    case 1:
                        res[tab[1]-1] += 1;
                        create_res_file(s, tab[1]);
                        break;
                }
            }
        }
        closeFile();
        pw.close();
    }

    private void create_res_file(String s, int res) throws IOException{
        if(pw == null){
            String baseName = s.substring(0, s.lastIndexOf('.'));
            String outputFileName = baseName + ".sol";
            pw = new PrintWriter(outputFileName, "UTF-8");
        }
        pw.println(res);
    }

    private void print_res(){
        for(int i = 0; i < res.length; i++){
            System.out.print(res[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException{
        if(args.length != 1){
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        Match match = new Match();
        match.res_match(args[0]);
        match.print_res();
    }
}
