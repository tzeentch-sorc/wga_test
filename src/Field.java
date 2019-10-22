import java.util.*;

public class Field {
    private List<BlockType> playArea;
    private final int size = 5;
    private boolean[] rowsCheck;
    private BlockType[] rowsType;

    public List<BlockType> getPlayArea() {
        return playArea;
    }

    public int getSize() {
        return size;
    }

    public Field(){
        playArea = new ArrayList<>(size*size);
        rowsCheck = new boolean[3];
        rowsType = new BlockType[3];

        Arrays.fill(rowsCheck, Boolean.FALSE);

        generate();
    }

    public void generate(){
        playArea.clear();


        int[] blocksLeft = new int[3];
        Arrays.fill(blocksLeft, 5);

        //random?
        rowsType[0] = BlockType.RED;
        rowsType[1] = BlockType.YELLOW;
        rowsType[2] = BlockType.GREEN;

        Arrays.fill(rowsCheck, Boolean.FALSE);


        for (int i = 0; i < size*size; i++) {
            if((i / 5) % 2 == 0){
                addBlock(BlockType.BLOCK, blocksLeft, i);
            } else {
                addBlock(BlockType.EMPTY, blocksLeft, i);
            }
        }
        randomize();
        if (checkWin()){ //if so lucky, that after randomize win conditions happen
            Collections.swap(playArea, 0, 24); //make them suffer
        }
    }

    private void addBlock(BlockType filler, int[] blocksLeft, int i) {
        if((i % 5) % 2 == 0){
            if(blocksLeft[1] > 0) {
                createBlock(blocksLeft, BlockType.GREEN);
                playArea.add(BlockType.GREEN);
            } else if (blocksLeft[2] > 0) {
                createBlock(blocksLeft, BlockType.YELLOW);
                playArea.add(BlockType.YELLOW);
            } else {
                createBlock(blocksLeft, BlockType.RED);
                playArea.add(BlockType.RED);
            }
        }
        else  playArea.add(filler);
    }


    public void showField(){
        for (int i = 0; i < this.playArea.size(); i++){
            System.out.print(playArea.get(i).toString() + " | ");
            if(i % 5 == 4 ) System.out.println();
        }
    }

    private void randomize(){
        Random r = new Random();
        int swaps = r.nextInt(10) + 5;
        int pos1 = 0, pos2 = 0;
        for (int i = 0; i < swaps; i++) {
            pos1 = r.nextInt(5) * size +  r.nextInt(3) * 2;
            pos2 = r.nextInt(5) * size +  r.nextInt(3) * 2;
            Collections.swap(playArea, pos1, pos2);
        }
    }

    private void createBlock(int[] types, BlockType type){
        types[BlockType.valueOf(type.toString()).ordinal()]--;
    }

    public int moveBlock(int srcPos, int destPos){
        BlockType srcBlockType = playArea.get(srcPos);
        if (playArea.get(destPos).equals(BlockType.EMPTY) && !(srcBlockType.equals(BlockType.BLOCK) || srcBlockType.equals(BlockType.EMPTY))){
            Collections.swap(playArea, srcPos, destPos);
            if(destPos % size % 2 == 0) {
                checkRow(destPos % size);
            }
            if(checkWin()) return 1;
            else return 0;
        } else {
                return -1;
            }
    }

    public boolean checkWin(){
        return  rowsCheck[0] && rowsCheck[1] && rowsCheck[2];
    }

    public void checkRow(int rowNumber){ //rowNum = 0/2/4
        boolean check = true;
        for (int i = rowNumber; i < 25; i += 5) {
            if(!playArea.get(i).equals(rowsType[rowNumber / 2])){
                check = false;
            }
        }
        rowsCheck[rowNumber/2] = check;
    }

}