//THIS IS THE MODEL CODE WHICH DEALS WITH THE LOGIC OF THE PROGRAM, AND
//HANDLES WHICH PUZZLE IMAGES SHOULD BE USED.

public class GameModel {

    private int underscoreInd;
    private String heldTile;
    private String puzName;
    private String thumbnail;
    private String[][] fileNames = new String[4][4];
    private boolean gameCompleted;

    public GameModel(String n) {
        heldTile = "BLANK.png";
        puzName = n;
        thumbnail = puzName + "_Thumbnail.png";
        underscoreInd = puzName.length();
        gameCompleted = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String row = i + "";
                String col = j + "";

                fileNames[i][j] = puzName + "_" + row + col + ".png";
            }
        }
    }

    public GameModel() {
        puzName = "BLANK.png";
        thumbnail = puzName;
        heldTile = puzName;
        gameCompleted = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                fileNames[i][j] = puzName;
            }
        }

    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String t) {
        thumbnail = t;
    }

    public String getFileName(int i, int j) {
        return fileNames[i][j];
    }

    public boolean getGameCompleted() {
        return gameCompleted;
    }


    public void replace(int row, int col) {
        String aTile = heldTile;
        heldTile = fileNames[row][col];
        fileNames[row][col] = aTile;
    }

    public void swaps(int row, int col) {

        String blankImg = "BLANK.png";
        String temp = fileNames[row][col];

        int bR = 0;
        int bC = 0;
        boolean bSearch = false;

        if (bSearch == false) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (fileNames[i][j].equals(blankImg)) {
                        bR = i;
                        bC = j;
                        bSearch = true;
                    }
                }
            }
        }

        if (((row + 1) == bR) && (col == bC))
            swapDown(blankImg, temp, row, col);

        else if (((row - 1) == bR) && (col == bC))
            swapUp(blankImg, temp, row, col);

        else if ((row == bR) && ((col + 1) == bC))
            swapRight(blankImg, temp, row, col);

        else if ((row == bR) && ((col - 1) == bC))
            swapLeft(blankImg, temp, row, col);

    }

    public void swapLeft(String b, String t, int row, int col) {
        fileNames[row][col] = b;
        fileNames[row][col - 1] = t;
    }

    public void swapRight(String b, String t, int row, int col) {
        fileNames[row][col] = b;
        fileNames[row][col + 1] = t;
    }

    public void swapUp(String b, String t, int row, int col) {
        fileNames[row][col] = b;
        fileNames[row - 1][col] = t;
    }

    public void swapDown(String b, String t, int row, int col) {
        fileNames[row][col] = b;
        fileNames[row + 1][col] = t;
    }

    public void isDone() {
        String b = "BLANK.png";
        int bRow = 0;
        int bCol = 0;
        boolean bSearch = false;
        int count = 0;

        if (!bSearch) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if ((fileNames[i][j]).equals(b)) {
                        bRow = i;
                        bCol = j;
                        bSearch = true;
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!(i == bRow) || !(j == bCol)) {
                    if ((Integer.parseInt(fileNames[i][j].charAt(underscoreInd + 1) + "") == i) && (Integer.parseInt(fileNames[i][j].charAt(underscoreInd + 2) + "") == j)) {
                        count++;
                    }
                }
            }
        }

        if (count == 15) {
            replace(bRow, bCol);
            gameCompleted = true;
        }

    }
}




