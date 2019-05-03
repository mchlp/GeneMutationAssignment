import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Wraps around the GeneGraphHash class to handle input and output.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("DATA.TXT");
        Scanner in = new Scanner(file);
        int L = in.nextInt();
        int S = in.nextInt();
        in.nextLine();

        // create HashSet to store alllowed genes
        GeneList allowedGenes = new GeneList();

        for (int i = 0; i < S; i++) {
            String gene = in.nextLine();
            allowedGenes.add(gene);
        }

        int M = in.nextInt();

        // create graph
        GeneGraphBinary geneGraphBinary = new GeneGraphBinary(allowedGenes, L, M);

        int G = in.nextInt();
        in.nextLine();

        // loop through all queries
        for (int i = 0; i < G; i++) {
            String[] line = in.nextLine().split(" ");
            String gene1 = line[0];
            String gene2 = line[1];

            // get minimum distance for query
            Pair<Boolean, Integer> result = geneGraphBinary.getFastestMutation(gene1, gene2);
            System.out.println(result.getKey() ? "YES" : "NO");
            System.out.println(result.getValue());
        }
    }
}
