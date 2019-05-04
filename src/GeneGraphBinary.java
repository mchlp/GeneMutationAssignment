/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/3
 *  Course: ICS4U
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores a graph with possible genes as the vertices and possible mutations as edges. Can calculate the fastest
 * mutation from one gene to another. This implementation uses a sorted ArrayList to store the list of genes an
 * adjacency list to allow for log n search time when sorted.
 */
public class GeneGraphBinary {

    private static final String[] POSSIBLE_CHARS = {"A", "G", "C", "T"};

    private int geneLength;
    private int maxMutations;

    private GeneList posGenes;
    private AdjList adjList;

    public GeneGraphBinary(GeneList genes, int L, int M) {
        this.geneLength = L;
        this.maxMutations = M;
        this.posGenes = genes;
        this.posGenes.sort();
        generateGraph();
    }

    public BoolIntPair getFastestMutation(String startGene, String endGene) {
        // if the starting gene is a valid gene
        boolean firstGeneValid;
        GeneList posStartingList = new GeneList();
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

    private GeneList generatePossibleMutations(String gene) {
        assert gene.length() == geneLength : "Gene length must be L";

        GeneList posMutations = new GeneList();

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
        adjList = new AdjList();
        for (String gene : posGenes) {
            AdjListElement curElement = new AdjListElement();
            curElement.key = gene;
            curElement.list = generatePossibleMutations(gene);
            adjList.add(curElement);
        }
        adjList.sort();
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
                for (String connection : adjList.getElement(curGene).list) {
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