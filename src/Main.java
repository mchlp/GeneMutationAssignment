/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/3
 *  Course: ICS4U
 */

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Wraps around the GeneGraphBinary class to handle input and output.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("DATA.TXT");
        Scanner in = new Scanner(file);
        int L = in.nextInt();
        int S = in.nextInt();
        in.nextLine();

        // create GeneList to store alllowed genes
        GeneList allowedGenes = new GeneList();

        for (int i = 0; i < S; i++) {
            String gene = in.nextLine();
            allowedGenes.add(gene);
        }

        int M = in.nextInt();
        int G = in.nextInt();
        in.nextLine();

        // create graph
        GeneGraphBinary geneGraphBinary = new GeneGraphBinary(allowedGenes, L, M);

        // loop through all queries
        for (int i = 0; i < G; i++) {
            String[] line = in.nextLine().split(" ");
            String gene1 = line[0];
            String gene2 = line[1];

            // get minimum distance for query
            BoolIntPair result = geneGraphBinary.getFastestMutation(gene1, gene2);
            System.out.println(result.first ? "YES" : "NO");
            System.out.println(result.second);
        }
    }
}
