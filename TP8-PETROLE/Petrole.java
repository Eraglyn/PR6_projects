import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Petrole {

    private BufferedReader br = null;
    private int[][] coord_gisement;
    private int res_final, nb_gisement;

    private void parse(String path) throws IOException {
        String line = "";
        br = new BufferedReader(new FileReader(path));
        line = br.readLine();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            nb_gisement = Integer.parseInt(line);
            coord_gisement = new int[nb_gisement][3];
            for (int i = 0; i < nb_gisement; i++) {
                line = br.readLine();
                String[] parts = line.split(" ");
                for (int j = 0; j < 3; j++) {
                    coord_gisement[i][j] = Integer.parseInt(parts[j]);
                }
            }
        }
        br.close();
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".sol";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        pw.println(res_final);
        pw.close();
    }

    private void afficher() {
        for (int i = 0; i < coord_gisement.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(coord_gisement[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void tri_insertion() {
        for (int i = 1; i < nb_gisement; i++) {
            if (coord_gisement[i][0] > coord_gisement[i][1]) {
                int temp = coord_gisement[i][0];
                coord_gisement[i][0] = coord_gisement[i][1];
                coord_gisement[i][1] = temp;
            }
            int index0 = coord_gisement[i][0];
            int index1 = coord_gisement[i][1];
            int index2 = coord_gisement[i][2];
            int j = i - 1;

            while (j >= 0 && coord_gisement[j][2] > index2) {
                coord_gisement[j + 1][0] = coord_gisement[j][0];
                coord_gisement[j + 1][1] = coord_gisement[j][1];
                coord_gisement[j + 1][2] = coord_gisement[j][2];
                j--;
            }
            coord_gisement[j + 1][0] = index0;
            coord_gisement[j + 1][1] = index1;
            coord_gisement[j + 1][2] = index2;
        }
    }

    private float[] getAffine(float x, float y, float z, float t) {
        float[] res = new float[2];
        res[0] = (t - y) / (z - x);
        // System.out.println((t - y) / (z - x));
        // System.out.println((t - y) + " " + (z - x) + " " + res[0]);
        res[1] = y - res[0] * x;
        return res;

    }

    private int somme_gisement_trav(float[] aff) {
        int somme = 0;
        for (int i = 0; i < nb_gisement; i++) {
            float x;
            if (aff[0] == 0) {
                x = aff[1];
            } else {
                x = (coord_gisement[i][2] - aff[1]) / aff[0];
            }
            if (x >= (float) coord_gisement[i][0] && x <= (float) coord_gisement[i][1]) {
                somme += Math.abs(coord_gisement[i][1] - coord_gisement[i][0]);
            }
        }
        return somme;
    }

    public int[] MaxMinXY() {
        int[] res = { Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE };
        for (int i = 0; i < nb_gisement; i++) {
            if (coord_gisement[i][0] < res[0]) {
                res[0] = coord_gisement[i][0];
            }
            if (coord_gisement[i][0] > res[1]) {
                res[1] = coord_gisement[i][0];
            }
            if (coord_gisement[i][1] < res[0]) {
                res[0] = coord_gisement[i][1];
            }
            if (coord_gisement[i][1] > res[1]) {
                res[1] = coord_gisement[i][1];
            }
            if (coord_gisement[i][2] < res[2]) {
                res[2] = coord_gisement[i][2];
            }
            if (coord_gisement[i][2] > res[3]) {
                res[3] = coord_gisement[i][2];
            }
        }
        return res;

    }

    public void random_generation(int nbRun) { // Version Random mais drole :) 7/38
        int res = 0;
        int[] resMinMax = MaxMinXY();
        Random random = new Random();
        for (int i = 0; i < nbRun; i++) {
            System.out.println("Run : " + (i + 1));
            int randY = random.nextInt(resMinMax[3] - resMinMax[2] + 1) + resMinMax[2];
            int tmp = somme_gisement_trav(getAffine(random.nextInt(resMinMax[1] - resMinMax[0] + 1) - resMinMax[0],
                    randY,
                    random.nextInt(resMinMax[1] - resMinMax[0] + 1) - resMinMax[0],
                    random.nextInt(resMinMax[3] - randY + 1) + randY));
            if (tmp > res) {
                res = tmp;
            }
        }
        res_final = res;
    }

    private void methode1() { // Version 32 tests sur 38
        tri_insertion();
        if (nb_gisement == 1) {
            res_final = Math.abs(coord_gisement[0][1] - coord_gisement[0][0]);
            return;
        }
        for (int i = 0; i < nb_gisement; i++) {
            int tmp = Math.abs(coord_gisement[i][1] - coord_gisement[i][0]);
            if (tmp > res_final) {
                res_final = tmp;
            }
            for (int j = i + 1; j < nb_gisement; j++) {
                if (i != j && coord_gisement[i][2] != coord_gisement[j][2]) {
                    float[] aff = getAffine(coord_gisement[i][0], coord_gisement[i][2], coord_gisement[j][0],
                            coord_gisement[j][2]);
                    // System.out.println(aff[0] + " " + aff[1]);
                    int somme = somme_gisement_trav(aff);
                    // System.out.println("test1 " + somme + " " + res_final);
                    if (somme > res_final) {
                        res_final = somme;
                    }
                    aff = getAffine(coord_gisement[i][0], coord_gisement[i][2], coord_gisement[j][1],
                            coord_gisement[j][2]);
                    // System.out.println(aff[0] + " " + aff[1]);
                    somme = somme_gisement_trav(aff);
                    // System.out.println("test1 " + somme + " " + res_final);
                    if (somme > res_final) {
                        res_final = somme;
                    }
                    aff = getAffine(coord_gisement[i][1], coord_gisement[i][2], coord_gisement[j][0],
                            coord_gisement[j][2]);
                    somme = somme_gisement_trav(aff);
                    if (somme > res_final) {
                        res_final = somme;
                    }
                    aff = getAffine(coord_gisement[i][1], coord_gisement[i][2], coord_gisement[j][1],
                            coord_gisement[j][2]);
                    somme = somme_gisement_trav(aff);
                    if (somme > res_final) {
                        res_final = somme;
                    }
                    // System.out.println("test2 " + somme + " " + res_final);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Erreur, nombre d'arguments incorrect.");
            System.exit(1);
        }
        Petrole p = new Petrole();
        p.parse(args[0]);
        // p.random_generation(1000000);
        p.methode1();
        p.create_res_file(args[0]);
    }
}
