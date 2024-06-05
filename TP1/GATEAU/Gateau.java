import java.io.*;

public class Gateau {

    private boolean fileOpened = false;
    private BufferedReader br = null;
    private int size = 0;

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
                size = Integer.parseInt(line);
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

    private int nb_gateau_min(String s) throws IOException{
        int nb_gateau = 0;
        int[] tab = parse(s);
        for(int i = 0; i < size; i++){
            tab = parse(s);
            if(tab[0] <= 0 || tab[1] <= 0){
                return 0;
            }
            if(i == 0){
                nb_gateau = tab[1]/tab[0];
            }else{
                if(tab[1]/tab[0] < nb_gateau){
                    nb_gateau = tab[1]/tab[0];
                }
            }
            if(nb_gateau <= 0){
                return 0;
            }
        }
        closeFile();
        return nb_gateau;
    }

    private void create_res_file(String s, int res){
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outputFileName, "UTF-8");
            writer.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        if(args.length != 1){
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        int res = new Gateau().nb_gateau_min(args[0]);
        System.out.println("Nombre de gateau minimum : " + res);
        new Gateau().create_res_file(args[0], res);
    }
}