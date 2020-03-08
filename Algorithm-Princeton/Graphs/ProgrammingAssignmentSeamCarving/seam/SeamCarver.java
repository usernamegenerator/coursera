/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(
            Picture picture) {

        if (picture == null)
            throw new java.lang.IllegalArgumentException();


        Picture newP = new Picture(picture.width(), picture.height());

        for (int col = 0; col < newP.width(); col++)
            for (int row = 0; row < newP.height(); row++)
                newP.setRGB(col, row, picture.getRGB(col, row));

        this.picture = newP;
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    // energy of pixel at column col and row row
    public double energy(int col, int row) {

        if (row < 0 || row >= this.height() || col < 0 || col >= this.width())
            throw new java.lang.IllegalArgumentException();

        if (isBoardPixel(col, row)) {
            return 1000.0;
        }

        int upRGB = this.picture().getRGB(col, row - 1);
        int rUp = (upRGB >> 16) & 0xFF;
        int gUp = (upRGB >> 8) & 0xFF;
        int bUp = (upRGB) & 0xFF;

        int downRGB = this.picture().getRGB(col, row + 1);
        int rDown = (downRGB >> 16) & 0xFF;
        int gDown = (downRGB >> 8) & 0xFF;
        int bDown = (downRGB) & 0xFF;

        int leftRGB = this.picture().getRGB(col - 1, row);
        int rLeft = (leftRGB >> 16) & 0xFF;
        int gLeft = (leftRGB >> 8) & 0xFF;
        int bLeft = (leftRGB) & 0xFF;


        int rightRGB = this.picture().getRGB(col + 1, row);
        int rRight = (rightRGB >> 16) & 0xFF;
        int gRight = (rightRGB >> 8) & 0xFF;
        int bRight = (rightRGB) & 0xFF;


        int rX = Math.abs(rRight - rLeft);
        int gX = Math.abs(gRight - gLeft);
        int bX = Math.abs(bRight - bLeft);

        double deltaXSquare = Math.pow(rX, 2) + Math.pow(gX, 2) + Math.pow(bX, 2);
        // StdOut.println(deltaXSquare);

        int rY = Math.abs(rDown - rUp);
        int gY = Math.abs(gDown - gUp);
        int bY = Math.abs(bDown - bUp);

        double deltaYSquare = Math.pow(rY, 2) + Math.pow(gY, 2) + Math.pow(bY, 2);
        // StdOut.println(deltaYSquare);

        double res = Math.sqrt(deltaXSquare + deltaYSquare);

        // StdOut.println(res);

        return res;

    }

    private boolean isBoardPixel(int col, int row) {
        if (col == 0 || col == this.width() - 1 || row == 0 || row == this.height() - 1) {
            return true;
        }
        return false;
    }

    // sequence of indices for horizontal seam
    // To write findHorizontalSeam(), transpose the image,
    // call findVerticalSeam(), and transpose it back.
    public int[] findHorizontalSeam() {
        Picture oldP = this.picture();
        // create a transpose picture
        /*
        Picture newP = new Picture(oldP.height(), oldP.width());
        for (int row = 0; row < newP.height(); row++) {
            for (int col = 0; col < newP.width(); col++) {
                // definition of setRGB(int col, int row, int rgb);
                // definition of get(int col, int row);
                newP.setRGB(col, row, oldP.getRGB(row, col));
            }
        }
        */

        this.picture = createTranposeThisPicture();

        // get the results
        int[] indices;
        indices = findVerticalSeam();

        // set back to the original picture
        this.picture = oldP;

        return indices;
    }

    private Picture createTranposeThisPicture() {
        Picture newP = new Picture(this.height(), this.width());
        for (int row = 0; row < newP.height(); row++) {
            for (int col = 0; col < newP.width(); col++) {
                // definition of setRGB(int col, int row, int rgb);
                // definition of get(int col, int row);
                newP.setRGB(col, row, this.picture.getRGB(row, col));
            }
        }
        return newP;
    }

    // sequence of indices for vertical seam
    /*
        Do not create an EdgeWeightedDigraph .
        Instead construct a 2d energy array using the energy() method
        that you have already written.
        Your algorithm can traverse this matrix treating some select entries
        as reachable from (col, row) to calculate where the seam is located.
    */

    // edges always pointing down so
    // scan the vertex row by row is the topological order
    // relax every vertex until reach the end
    // by relaxing, every layer's vertex ganrantee has a shortest path from upper layers
    // then for every vetex at the end row, should have its shortest path
    // then get the shortest path among all the shortest path (find the col among all cols in last row)
    // trace back from last row to the fist row using that col
    // 1. edgeTo[][]
    // 2. distTo[][]


    // or (?)
    // do BFS for every vertex in the first row
    // always go the smallest to the end
    // get the shortest path among all the shortest path
    public int[] findVerticalSeam() {
        // edgeTo only needs to store the col (column), because row is always row-1;
        int[][] edgeTo = new int[height()][width()];
        double[][] distTo = new double[height()][width()];
        // distTo need to pre-set to infinity
        for (int col = 0; col < width(); col++) {
            distTo[0][col] = 1000.0;
        }
        for (int row = 1; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                distTo[row][col] = Double.POSITIVE_INFINITY;
            }
        }

        // do the relax for every vertex (pixel)
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                relax(row, col, edgeTo, distTo);
            }
        }

        // for the end row, find the smallest distTo
        double smallestDistTo = Double.POSITIVE_INFINITY;
        int smallestCol = 0;
        for (int col = 0; col < width(); col++) {
            int lastRow = height() - 1;
            if (distTo[lastRow][col] < smallestDistTo) {
                smallestDistTo = distTo[lastRow][col];
                smallestCol = col;
            }
        }

        // get the edgeTo to this smallest pixel in the end row
        // indices[i] is the col of the row i;
        int[] indices = new int[height()];
        indices[height() - 1] = smallestCol;
        for (int row = height() - 2; row >= 0; row--) {
            int col = edgeTo[row + 1][indices[row + 1]];
            indices[row] = col;
        }

        return indices;
    }

    private void relax(int row, int col, int[][] edgeTo, double[][] distTo) {
        // v is (col,row)
        // w is all the reachable vectex from v,
        // that is, bottomleft (col-1,row+1), bottom (col,row+1), bottomright (col+1,row+1)
        for (int i = 0; i < 3; i++) {
            int colPrime = col + i - 1;
            int rowPrime = row + 1;
            if (colPrime < 0 || colPrime >= width() || rowPrime < 0 || rowPrime >= height())
                continue;
            // when calling the algs4 function, need to swap col and row
            if (distTo[rowPrime][colPrime] > distTo[row][col] + energy(colPrime, rowPrime)) {
                distTo[rowPrime][colPrime] = distTo[row][col] + energy(colPrime, rowPrime);
                edgeTo[rowPrime][colPrime] = col;
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != this.width() || this.height() <= 1)
            throw new IllegalArgumentException();
        for (int col = 0; col < this.width(); col++) {
            if (col > 0 && (Math.abs(seam[col] - seam[col - 1])) > 1) {
                throw new IllegalArgumentException();
            }
            if (seam[col] < 0 || seam[col] >= this.height()) {
                throw new IllegalArgumentException();
            }
        }

        Picture newP = new Picture(this.width(), this.height() - 1);
        Picture oldP = this.picture();

        for (int col = 0; col < oldP.width(); col++) {
            for (int row = 0; row < seam[col]; row++) {
                newP.setRGB(col, row, oldP.getRGB(col, row));
            }
            for (int row = seam[col]; row < newP.height(); row++) {
                newP.setRGB(col, row, oldP.getRGB(col, row + 1));
            }
        }

        this.picture = newP;
    }

    // remove vertical seam from current picture
    // Typically, this method will be called with the output of findVerticalSeam(),
    // but be sure that they work for any seam.
    // To test that your code words,
    // use the client ResizeDemo described in the testing section above.
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != this.height() || this.width() <= 1)
            throw new IllegalArgumentException();

        for (int row = 0; row < this.height(); row++) {
            if (row > 0 && (Math.abs(seam[row] - seam[row - 1])) > 1) {
                throw new IllegalArgumentException();
            }
            if (seam[row] < 0 || seam[row] >= this.width()) {
                throw new IllegalArgumentException();
            }
        }


        Picture newP = new Picture(this.width() - 1, this.height());
        Picture oldP = this.picture();

        // iterate through the old picutre
        // keep putting the pixels into the same position in the new picture
        // if the pixel is in the saem, then just skip putting to the new picture
        for (int row = 0; row < oldP.height(); row++) {
            for (int col = 0; col < seam[row]; col++) {
                newP.setRGB(col, row, oldP.getRGB(col, row));
            }
            for (int col = seam[row]; col < newP.width(); col++) {
                newP.setRGB(col, row, oldP.getRGB(col + 1, row));
            }
        }

        this.picture = newP;
    }

    public static void main(String[] args) {
    }
}
