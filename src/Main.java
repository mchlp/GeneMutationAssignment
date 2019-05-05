/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/3
 *  Course: ICS4U
 */

import data_structures.BoolIntPair;
import data_structures.GeneList;

import java.io.*;
import java.util.Scanner;

//TODO: Only valid characters are ATGC?

/**
 * Wraps around the old.GeneGraphBinary class to handle input and output.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        long startTime = System.nanoTime();

        BufferedReader in = new BufferedReader(new FileReader("DATA_OUT.TXT"));
        int L = Integer.parseInt(in.readLine());
        int S = Integer.parseInt(in.readLine());

        String[] allowedGenesArr = new String[S];

        for (int i = 0; i < S; i++) {
            String gene = in.readLine();
            allowedGenesArr[i] = gene;
        }

        int M = Integer.parseInt(in.readLine());
        int G = Integer.parseInt(in.readLine());

        // create graph
//        GeneGraphBinary geneGraphBinary = new old.GeneGraphBinary(allowedGenes, L, M);
//        GeneGraphHash geneGraphBinary = new old.GeneGraphHash(allowedGenes, L, M);
        GeneGraphId geneGraphBinary = new GeneGraphId(allowedGenesArr, L, M);

        System.out.println("Generate Graph Time: " + (System.nanoTime() - startTime) / 1E9);

        // loop through all queries
        for (int i = 0; i < G; i++) {
            String[] line = in.readLine().split(" ");
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
