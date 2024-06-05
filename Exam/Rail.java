import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

public class Rail {

    private int long_2_ville, nb_type;
    private int[][] long_prix;
    private BufferedReader br = null;
    private int[] res;

    private void parse(String path) throws IOException {
        String line = "";
        br = new BufferedReader(new FileReader(path));
        line = br.readLine().trim();
        if (line == null) {
            System.out.println("Erreur, fichier vide.");
            System.exit(1);
        } else {
            long_2_ville = Integer.parseInt(line);
            line = br.readLine().trim();
            nb_type = Integer.parseInt(line);
            long_prix = new int[nb_type][2];
            res = new int[nb_type + 1];
            for (int i = 0; i < nb_type; i++) {
                line = br.readLine().trim();
                String[] parts = line.split(" ");
                long_prix[i][0] = Integer.parseInt(parts[0]);
                long_prix[i][1] = Integer.parseInt(parts[1]);
            }
        }
    }

    private void minCost() {
        int[] tab = new int[long_2_ville + 1];
        int[][] count = new int[long_2_ville + 1][long_prix.length];
        Arrays.fill(tab, Integer.MAX_VALUE);
        tab[0] = 0;
        for (int i = 1; i <= long_2_ville; i++) {
            for (int j = 0; j < long_prix.length; j++) {
                if (long_prix[j][0] <= i && tab[i] >= tab[i - long_prix[j][0]] + long_prix[j][1]) {
                    tab[i] = tab[i - long_prix[j][0]] + long_prix[j][1];
                    for (int k = 0; k < long_prix.length; k++) {
                        count[i][k] = count[i - long_prix[j][0]][k];
                    }
                    count[i][j]++;
                }
            }
        }
        for (int k = 0; k < long_prix.length; k++) {
            res[k] = count[long_2_ville][k];
        }
        res[nb_type] = tab[long_2_ville];
    }

    private boolean verif() {
        int sum = 0;
        for (int i = 0; i < nb_type; i++) {
            sum += long_prix[i][0] * res[i];
        }

        return sum == long_2_ville;
    }

    private void create_res_file(String s) throws IOException {
        String baseName = s.substring(0, s.lastIndexOf('.'));
        String outputFileName = baseName + ".out";
        PrintWriter pw = new PrintWriter(outputFileName, "UTF-8");
        if (!verif()) {
            pw.println("-");
        } else {
            for (int i = 0; i < res.length; i++) {
                pw.println(res[i]);
            }
        }
        pw.close();
    }

    private void methode_Backtracking() {
        LinkedList<int[]> result = new LinkedList<>();
        int[] path = new int[nb_type + 1];
        if (!isPrime(long_2_ville)) {
            backtracking_PathFinder(long_prix, long_2_ville, result, path);
            int minCost = Integer.MAX_VALUE;
            for (int[] p : result) {
                if (p[nb_type] < minCost) {
                    minCost = p[nb_type];
                    res = p;
                }
            }
        } else {
            res[nb_type] = -1;
        }

    }

    private void backtracking_PathFinder(int[][] long_prix, int long_2_ville, LinkedList<int[]> result,
            int[] path) {
        if (long_2_ville == 0) {
            if (result.isEmpty() || (!result.isEmpty() && path[nb_type] <= result.getLast()[nb_type])) {
                result.add(path.clone());
            }
            return;
        }

        boolean flag = false;
        for (int i = long_prix.length - 1; i >= 0; i--) {
            if (long_prix[i][0] <= long_2_ville) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return;
        }

        for (int i = long_prix.length - 1; i >= 0; i--) {

            if (long_2_ville % 2 == 1 && long_prix[i][0] % 2 == 0) {
                if (i == 0) {
                    return;
                }

                continue;
            }

            if (long_prix[i][0] <= long_2_ville) {
                path[i]++;
                path[nb_type] += long_prix[i][1];
                if (!result.isEmpty() && path[nb_type] >= result.getLast()[nb_type]) {
                    return;
                }
                backtracking_PathFinder(long_prix, long_2_ville - long_prix[i][0], result, path);
                path[i]--;
                path[nb_type] -= long_prix[i][1];
            }
        }
    }

    public boolean isPrime(int num) {

        for (int i = 0; i < long_prix.length; i++) {
            if (long_prix[i][0] == 1 || long_prix[i][0] == num) {
                return false;
            }
        }
        if (num < 2) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(num);
        for (int i = 3; i <= sqrt; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Rail r = new Rail();
        r.parse(args[0]);
        // r.minCost();
        r.methode_Backtracking();
        r.create_res_file(args[0]);
    }
}
