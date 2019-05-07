/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/6
 *  Course: ICS4U
 */

import data_types.BoolIntPair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Wraps around the GeneGraph class to handle input and output.
 */
public class Main {

    private static final String FILE_NAME = "DATA_1M.TXT";

    public static void main(String[] args) throws IOException {
        // read data from file
        BufferedReader in = new BufferedReader(new FileReader(FILE_NAME));
        int L = Integer.parseInt(in.readLine().trim());
        int S = Integer.parseInt(in.readLine().trim());

        // array to store valid genes
        String[] allowedGenesArr = new String[S];

        for (int i = 0; i < S; i++) {
            allowedGenesArr[i] = in.readLine().trim();
        }

        int M = Integer.parseInt(in.readLine().trim());
        int G = Integer.parseInt(in.readLine().trim());

        // create graph
        GeneGraph geneGraph = new GeneGraph(allowedGenesArr, L, M);

        // loop through all queries
        for (int i = 0; i < G; i++) {
            String[] line = in.readLine().split(" ");
            String gene1 = line[0].trim();
            String gene2 = line[1].trim();

            // get if reachable and minimum distance for query
            BoolIntPair result = geneGraph.getFastestMutation(gene1, gene2);
            System.out.println(result.first ? "YES" : "NO");
            System.out.println(result.second);
        }
    }
}
