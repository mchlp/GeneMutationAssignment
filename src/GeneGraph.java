/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

import data_types.BoolIntPair;
import data_types.Queue;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stores a graph with possible genes as the vertices and possible mutations as edges. Can calculate the fastest
 * mutation from one gene to another. This implementation assigns an integer ID to each gene and that ID is used to
 * refer to the gene internally.
 */
public class GeneGraph {

    private static final String[] POSSIBLE_CHARS = {"A", "G", "C", "T"};
    private int geneLength;
    private int maxMutations;

    // using char[] is faster than String
    private char[][] posGenes;
    private ArrayList<Integer>[] adjList;

    public GeneGraph(String[] genes, int L, int M) {
        this.geneLength = L;
        this.maxMutations = M;
        this.posGenes = new char[genes.length][];
        Arrays.sort(genes);
        // convert Strings to char[]
        for (int i = 0; i < genes.length; i++) {
            posGenes[i] = genes[i].toCharArray();
        }
        generateGraph();
    }

    /**
     * Returns the lowest number of mutation from startGene to endGene (-1 if unreachable) and if it can be accomplished
     * within the max number of mutations.
     */
    public BoolIntPair getFastestMutation(String startGeneStr, String endGeneStr) {

        // convert strings to char[]
        char[] startGene = startGeneStr.toCharArray();
        char[] endGene = endGeneStr.toCharArray();

        // if the starting gene is a valid gene
        boolean firstGeneValid;

        ArrayList<Integer> posStartingList = new ArrayList<>();

        // get id of starting and ending gene
        int startGeneId = getGeneId(startGene);
        int endGeneId = getGeneId(endGene);

        // if ending gene is not valid
        if (endGeneId == -1) {
            return new BoolIntPair(false, -1);
        }

        if (startGeneId != -1) {
            // if starting gene is a valid gene, set that as the starting point
            posStartingList.add(startGeneId);
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
        for (int posStarting : posStartingList) {
            int dis = getShortestPath(posStarting, endGeneId);
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
    private char[] swapAdjGene(char[] gene, int leftIndex) {
        assert leftIndex < geneLength - 1 : "leftIndex must be less than L-1";
        // create copy of char[] to modify
        char[] newGene = Arrays.copyOf(gene, gene.length);
        // swap letters
        char temp = gene[leftIndex];
        newGene[leftIndex] = newGene[leftIndex + 1];
        newGene[leftIndex + 1] = temp;
        return newGene;
    }

    /**
     * Compares two char arrays lexicographically. Follows the specifications of {@link
     * java.util.Comparator#compare(Object, Object)}.
     *
     * @param arr1 first char array.
     * @param arr2 second char array.
     */
    private int compareCharArr(char[] arr1, char[] arr2) {
        for (int i = 0; i < Math.min(arr1.length, arr2.length); i++) {
            int diff = Character.compare(arr1[i], arr2[i]);
            if (diff == 0) {
                continue;
            }
            return diff;
        }
        return Integer.compare(arr1.length, arr2.length);
    }

    /**
     * Retrieves the id corresponding to the gene, or -1 if not found.
     */
    private int getGeneId(char[] gene) {
        // binary search for gene in the posGenes array. when the gene is found, its index is its id and is returned.
        int start = 0;
        int end = this.posGenes.length - 1;

        while (start <= end) {
            int middle = (start + end) / 2;
            int diff = compareCharArr(gene, this.posGenes[middle]);
            if (diff > 0) {
                start = middle + 1;
            } else if (diff < 0) {
                end = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * Generates all valid mutations of a gene.
     */
    private ArrayList<Integer> generatePossibleMutations(char[] gene) {
        assert gene.length == geneLength : "Gene length must be L";

        ArrayList<Integer> posMutations = new ArrayList<>();

        // for each character, substitute it with each of the four of the possible letters and add it to the list of
        // possible mutations if it is a valid gene
        for (int i = 0; i < geneLength; i++) {
            for (String posChar : POSSIBLE_CHARS) {
                if (posChar.charAt(0) != gene[i]) {
                    // create new char[] to modify
                    char[] newGene = Arrays.copyOf(gene, gene.length);
                    newGene[i] = posChar.charAt(0);
                    // check if new gene is a valid gene
                    int testGeneId = getGeneId(newGene);
                    if (!Arrays.equals(gene, newGene) && testGeneId != -1) {
                        posMutations.add(testGeneId);
                    }
                }
            }
        }
        // for each character in the gene from index 0 to index length-2, swap that character with the character on
        // the right of it and add it to the list of possible mutations if it is a valid gene
        for (int i = 0; i < geneLength - 1; i++) {
            if (gene[i] != gene[i + 1]) {
                char[] newGene = swapAdjGene(gene, i);
                // check if new gene is a valid gene
                int testGeneId = getGeneId(newGene);
                if (!Arrays.equals(gene, newGene) && testGeneId != -1) {
                    posMutations.add(testGeneId);
                }
            }
        }

        return posMutations;
    }

    /**
     * Generates the graph from the list of possible genes by creating an adjacency list.
     */
    private void generateGraph() {
        // initialize adjacency list
        adjList = new ArrayList[this.posGenes.length];

        // fill adjacency list with connected genes for each gene in the list of valid genes
        for (int i = 0; i < posGenes.length; i++) {
            adjList[i] = generatePossibleMutations(posGenes[i]);
        }
    }

    /**
     * Returns the lowest number of mutations to reach endGene from startGene, or -1 if it is not possible using BFS.
     */
    private int getShortestPath(int startGeneId, int endGeneId) {
        // initialize queue for breath first search
        Queue queue = new Queue();

        // initialize distance array with -1 (not visited)
        int[] disArray = new int[this.posGenes.length];
        Arrays.fill(disArray, -1);

        // add starting gene to queue and distance array
        queue.enqueue(startGeneId);
        disArray[startGeneId] = 0;

        // while not all nodes have been explored
        while (!queue.isEmpty()) {
            // retrieve next node to explore from queue
            int curGeneId = queue.dequeue();

            // if next node is endGene
            if (curGeneId == endGeneId) {
                return disArray[endGeneId];
            } else {
                int curDis = disArray[curGeneId];
                // loop through connections in adjacency list of next node and explore all unexplored nodes
                for (int connection : adjList[curGeneId]) {
                    int nodeDis = disArray[connection];
                    // if node is unvisited or current distance is less than distance in disArray, visit the node
                    if (nodeDis == -1 || nodeDis > curDis + 1) {
                        // update distance in distance array
                        disArray[connection] = curDis + 1;
                        // add node to queue to be explored
                        queue.enqueue(connection);
                    }
                }
            }
        }
        // path not found, return -1
        return -1;
    }
}