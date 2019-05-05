/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class TestCaseGenerator {

    public static final String[] POSSIBLE_CHARS = {"A", "T", "C", "G"};
    public static final String STARTING_GENE = "TGATCAACTAGCAGTTCCGA";
    public static final String INVALID_GENE = "AAAAAAAAAAAAAAAAAAAA";
    public static final int GENE_LENGTH = 20;
    public static final int NUM_VALID_GENES = 2_000_000;
    public static final int MAX_MUTATIONS = 14;
    public static final int NUM_QUERIES = 20;

    private static String swapAdjGene(String gene, int leftIndex) {
        assert leftIndex < GENE_LENGTH - 1 : "leftIndex must be less than L-1";
        return gene.substring(0, leftIndex) + gene.charAt(leftIndex + 1) + gene.charAt(leftIndex) + gene.substring(leftIndex + 2);
    }

    private static LinkedList<String> generatePossibleMutations(String gene) {
        LinkedList<String> posMutations = new LinkedList<>();

        // for each character, substitute it with each of the four of the possible letters and add it to the list of
        // possible mutations if it is a valid gene
        for (int i = 0; i < GENE_LENGTH; i++) {
            for (String posChar : POSSIBLE_CHARS) {
                String testGene = gene.substring(0, i) + posChar + gene.substring(i + 1);
                if (!testGene.equals(gene)) {
                    posMutations.add(testGene);
                }
            }
        }

        // for each character in the gene from index 0 to index length-2, swap that character with the character on
        // the right of it and add it to the list of possible mutations if it is a valid gene
        for (int i = 0; i < GENE_LENGTH - 1; i++) {
            String testGene = swapAdjGene(gene, i);
            if (!testGene.equals(gene)) {
                posMutations.add(testGene);
            }
        }

        return posMutations;
    }

    public static void main(String[] args) throws IOException {

        Random random = new Random();

        HashSet<String> validGenesHash = new HashSet<>();
        HashSet<String> newGenes = new HashSet<>();
        HashSet<String> nextNewGenes = new HashSet<>();
        validGenesHash.add(STARTING_GENE);
        newGenes.add(STARTING_GENE);

        addGeneLoop:
        while (validGenesHash.size() < NUM_VALID_GENES) {
            for (String newGene : newGenes) {
                LinkedList<String> posGenes = generatePossibleMutations(newGene);
                int numToAdd = random.nextInt(posGenes.size()) / 4;
                while (numToAdd > 0 && posGenes.size() > 0) {
                    String testGene = posGenes.remove(random.nextInt(posGenes.size()));
                    if (!validGenesHash.contains(testGene) && !testGene.equals(INVALID_GENE)) {
                        validGenesHash.add(testGene);
                        nextNewGenes.add(testGene);
                        numToAdd--;
                        if (validGenesHash.size() >= NUM_VALID_GENES) {
                            break addGeneLoop;
                        }
                    }
                }
            }
            newGenes.clear();
            newGenes.addAll(nextNewGenes);
            nextNewGenes.clear();
        }

        ArrayList<String> validGenes = new ArrayList<>();
        validGenes.addAll(validGenesHash);

        FileWriter fileWriter = new FileWriter(new File("DATA_OUT.TXT"));
        fileWriter.write(Integer.toString(GENE_LENGTH) + "\n");
        fileWriter.write(Integer.toString(NUM_VALID_GENES) + "\n");
        for (String gene : validGenes) {
            fileWriter.write(gene + "\n");
        }

        fileWriter.write(Integer.toString(MAX_MUTATIONS) + "\n");
        fileWriter.write(Integer.toString(NUM_QUERIES) + "\n");

        for (int i = 0; i < NUM_QUERIES - 1; i++) {
            String gene1 = random.nextInt(10) > 9 ? INVALID_GENE : validGenes.get(random.nextInt(validGenes.size()));
            String gene2 = random.nextInt(10) > 9 ? INVALID_GENE : validGenes.get(random.nextInt(validGenes.size()));
            fileWriter.write(gene1 + " " + gene2 + "\n");
        }
        fileWriter.write(INVALID_GENE + " " + INVALID_GENE + "\n");

        fileWriter.flush();
        fileWriter.close();
    }
}
