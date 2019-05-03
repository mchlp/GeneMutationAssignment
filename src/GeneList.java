public class GeneList extends BinaryList<String> {
    @Override
    public int compare(String ele1, String ele2) {
        return ele1.compareTo(ele2);
    }
}
