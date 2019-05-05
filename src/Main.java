/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/3
 *  Course: ICS4U
 */

import data_structures.BoolIntPair;
import data_structures.GeneList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TODO: Only valid characters are ATGC?

/**
 * Wraps around the GeneGraphBinary class to handle input and output.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        long startTime = System.nanoTime();

        File file = new File("DATA_OUT.TXT");
        Scanner in = new Scanner(file);
        int L = in.nextInt();
        int S = in.nextInt();
        in.nextLine();

        // create data_structures.GeneList to store alllowed genes
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
//        testing.GeneGraphHash geneGraphBinary = new testing.GeneGraphHash(allowedGenes, L, M);

        // loop through all queries
        for (int i = 0; i < G; i++) {
            String[] line = in.nextLine().split(" ");
            String gene1 = line[0];
            String gene2 = line[1];

            // get if reachable and minimum distance for query
            BoolIntPair result = geneGraphBinary.getFastestMutation(gene1, gene2);
            System.out.println(result.first ? "YES" : "NO");
            System.out.println(result.second);
        }

        System.out.println("Total Time: " + (System.nanoTime() - startTime) / 1E9);
    }
}
