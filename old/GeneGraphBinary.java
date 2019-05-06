/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

package old;import old.data_structures.*;

import java.util.ArrayDeque;

/**
 * Stores a graph with possible genes as the vertices and possible mutations as edges. Can calculate the fastest
 * mutation from one gene to another. This implementation uses a sorted ArrayList to store the list of genes an
 * adjacency list to allow for log n search time when sorted.
 */
@SuppressWarnings("Duplicates")
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

    /**
     * Returns the lowest number of mutation from startGene to endGene (-1 if unreachable) and if it can be
     * accomplished within the max number of mutations.
     */
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
            // if endGene is reachable
            if (dis != -1) {
                // add 1 to minimum distance if starting gene is not valid gene
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
        // return if endGene is reachable within the limit and the minimum distance to the end gene
        return new BoolIntPair(reachableWithin, reachable ? minDis : -1);
    }

    /**
     * Swaps two adjacent letters in a gene and returns it.
     *
     * @param gene      the gene to be operated on.
     * @param leftIndex the index of the left letter to be swapped.
     * @return a gene with the indicated letters swapped.
     */
    private String swapAdjGene(String gene, int leftIndex) {
        assert leftIndex < geneLength - 1 : "leftIndex must be less than L-1";
        return gene.substring(0, leftIndex) + gene.charAt(leftIndex + 1) + gene.charAt(leftIndex) + gene.substring(leftIndex + 2);
    }

    /**
     * Generates all valid mutations of a gene.
     */
    private GeneList generatePossibleMutations(String gene) {
        assert gene.length() == geneLength : "Gene length must be L";

        GeneList posMutations = new GeneList();

        // for each character, substitute it with each of the four of the possible letters and add it to the list of
        // possible mutations if it is a valid gene
        for (int i = 0; i < geneLength; i++) {
            for (String posChar : POSSIBLE_CHARS) {
                String testGene = gene.substring(0, i) + posChar + gene.substring(i + 1);
                if (!testGene.equals(gene) && posGenes.contains(testGene)) {
                    posMutations.add(testGene);
                }
            }
        }

        // for each character in the gene from index 0 to index length-2, swap that character with the character on
        // the right of it and add it to the list of possible mutations if it is a valid gene
        for (int i = 0; i < geneLength - 1; i++) {
            String testGene = swapAdjGene(gene, i);
            if (!testGene.equals(gene) && posGenes.contains(testGene)) {
                posMutations.add(testGene);
            }
        }

        return posMutations;
    }

    private void generateGraph() {
        // initialize adjacency list
        adjList = new AdjList();

        // fill adjacency list with connected genes for each gene in the list of valid genes
        for (String gene : posGenes) {
            AdjListElement curElement = new AdjListElement();
            curElement.key = gene;
            curElement.list = generatePossibleMutations(gene);
            adjList.add(curElement);
        }

        // sort adjacency list to allow for binary search lookup
        adjList.sort();
    }

    /**
     * Returns the lowest number of mutations to reach endGene from startGene, or -1 if it is not possible using BFS.
     */
    private int getShortestPath(String startGene, String endGene) {
        // initialize queue for breath first search
        ArrayDeque<String> queue = new ArrayDeque<>();

        // initialize distance array
        DisList disArray = new DisList();
        for (AdjListElement adjListElement : adjList) {
            DisListElement disListElement = new DisListElement();
            disListElement.key = adjListElement.key;
            disListElement.value = -1;
            disArray.add(disListElement);
        }
        disArray.sort();

        // add starting gene to queue and distance array
        queue.addFirst(startGene);
        disArray.getElement(startGene).value = 0;


        // while not all nodes have been explored
        while (!queue.isEmpty()) {
            // retrieve next node to explore from queue
            String curGene = queue.getLast();
            queue.removeLast();

            // if next node is endGene
            if (endGene.equals(curGene)) {
                return disArray.getElement(curGene).value;
            } else {
                int curDis = disArray.getElement(curGene).value;
                // loop through connections in adjacency list of next node and explore all unexplored nodes
                for (String connection : adjList.getElement(curGene).list) {
                    int nodeDis = disArray.getElement(connection).value;
                    if (nodeDis == -1 || nodeDis > curDis + 1) {
                        // update distance in distance array
                        disArray.getElement(connection).value = curDis + 1;
                        // add node to queue to be explored
                        queue.addFirst(connection);
                    }
                }
            }
        }
        // path not found, return -1
        return -1;
    }
}