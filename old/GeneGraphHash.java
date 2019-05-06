/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

package old;

import BoolIntPair;
import old.data_structures.GeneList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Stores a graph with possible genes as the vertices and possible mutations as edges. Can calculate the fastest
 * mutation from one gene to another.
 */
@SuppressWarnings("Duplicates")
public class GeneGraphHash {

    private static final String[] POSSIBLE_CHARS = {"A", "G", "C", "T"};

    private int geneLength;
    private int maxMutations;

    private HashSet<String> posGenes;
    private HashMap<String, ArrayList<String>> adjList;

    public GeneGraphHash(GeneList genes, int L, int M) {
        this.geneLength = L;
        this.maxMutations = M;
        this.posGenes = new HashSet<>();
        this.posGenes.addAll(genes);
        generateGraph();
    }

    public BoolIntPair getFastestMutation(String startGene, String endGene) {

        if (!posGenes.contains(endGene)) {
            return new BoolIntPair(false, -1);
        }

        // if the starting gene is a valid gene
        boolean firstGeneValid;
        ArrayList<String> posStartingList = new ArrayList<>();
        if (posGenes.contains(startGene)) {
            // if starting gene is a valid gene, set that as the starting point
            posStartingList.add(startGene);
            firstGeneValid = true;
        } else {
            // if starting gene is not a valid gene, set all valid mutations of starting gene as starting points
            // and add 1 to the minimum distance at the end
            posStartingList.addAll(generatePossibleMutations(startGene));
            firstGeneValid = false;
        }

        // store minimum distance to ending point
        int minDis = Integer.MAX_VALUE;
        // if the end gene is reachable from the starting gene
        boolean reachable = false;
        // if the end gene is reachable from the starting gene within the maximum number of mutations
        boolean reachableWithin = false;
        // loop through starting genes
        for (String posStarting : posStartingList) {
            int dis = getShortestPath(posStarting, endGene);
            if (dis != -1) {
                dis += (firstGeneValid ? 0 : 1);
                if (!reachable) {
                    reachable = true;
                }
                if (dis < minDis) {
                    minDis = dis;
                }
                if (dis <= maxMutations) {
                    reachableWithin = true;
                }
            }
        }
        return new BoolIntPair(reachableWithin, reachable ? minDis : -1);
    }

    private String swapAdjGene(String gene, int leftIndex) {
        assert leftIndex < geneLength - 1 : "leftIndex must be less than L-1";
        return gene.substring(0, leftIndex) + gene.charAt(leftIndex + 1) + gene.charAt(leftIndex) + gene.substring(leftIndex + 2);
    }

    private ArrayList<String> generatePossibleMutations(String gene) {
        assert gene.length() == geneLength : "Gene length must be L";

        ArrayList<String> posMutations = new ArrayList<>();

        for (int i = 0; i < geneLength; i++) {
            for (String posChar : POSSIBLE_CHARS) {
                String testGene = gene.substring(0, i) + posChar + gene.substring(i + 1);
                if (!testGene.equals(gene) && posGenes.contains(testGene)) {
                    posMutations.add(testGene);
                }
            }
        }

        for (int i = 0; i < geneLength - 1; i++) {
            String testGene = swapAdjGene(gene, i);
            if (!testGene.equals(gene) && posGenes.contains(testGene)) {
                posMutations.add(testGene);
            }
        }

        return posMutations;
    }

    private void generateGraph() {
        // Initialize adjacency list
        adjList = new HashMap<>();
        for (String gene : posGenes) {
            adjList.put(gene, new ArrayList<>());
        }

        for (String gene : posGenes) {
            adjList.put(gene, generatePossibleMutations(gene));
        }
    }

    private int getShortestPath(String startGene, String endGene) {
        ArrayDeque<String> queue = new ArrayDeque<>();
        HashMap<String, Integer> disArray = new HashMap<>();

        queue.addFirst(startGene);
        disArray.put(startGene, 0);

        while (!queue.isEmpty()) {
            String curGene = queue.getLast();
            queue.removeLast();
            if (endGene.equals(curGene)) {
                return disArray.get(curGene);
            } else {
                int curDis = disArray.get(curGene);
                for (String connection : adjList.get(curGene)) {
                    if (!disArray.containsKey(connection)) {
                        disArray.put(connection, curDis + 1);
                        queue.addFirst(connection);
                    }
                }
            }
        }
        return -1;
    }

}
