import java.util.HashMap;
import java.util.Map;
import java.lang.Math.*;

/**
 * Author: miaekim ( beautiful.mimikim@gmail.com )
 * Date: 13. 10. 13.
 * It is the homework for purdue search engine class.
 * Inspired by PageRank Algorithm.
 * See also : https://googledrive.com/host/0B2GQktu-wcTiaWw5OFVqT1k3bDA/
 */

public class Pagerank {
    private Map<Integer, Integer> converter; // Making alias.
    private double d = 0.85; // Damping Factor
    private int len; // Number of Node
    
    public final int[][] input = {
            {13, 35, 23, 74}, // 0, 1, 2, 3
            {35, 23, 13, 74},
            {13, 74, 35, 23}
    };

    public int nameToidx(int vi ) {
        return converter.get(vi);
    }

    public double[] makeGoogle(int[] v) { // Main func
        int len = this.len = v.length;
        double hyper[][] = new double[len+1][len+1];
        double dang[][] = new double[len+1][len+1];
        double google[][] = new double[len+1][len+1];
        double d = this.d;

        // INIT CONVERTER
        converter = new HashMap<Integer, Integer>();
        for(int i=0; i<len; i++) {
            converter.put(v[i], i);
        }


        // MAKE HYPERLINK MATRIX
        for( int i = len-1; i>0; i--){
            for( int j=0; j<i; j++) {
                hyper[nameToidx(v[j])][nameToidx(v[i])] = 1.0/i;
            }
        }
        // MAKE DANGLING MATRIX
        for( int i=0 ; i<len; i++)
            dang[i][nameToidx(v[0])] = 1.0/len;
        // FOUMLATION OF PAGERANK
        google = addConstant( 
            mulMatrix(  addMatrix(hyper, dang ) , d) , (1-d)/len );
        // MAKE THE RANK
        double[] x = makeRankVector(google);


        return x;
    }

    public double[] makeRankVector(double[][] mat) {
        int len = this.len;
        double[] x = new double[len];
        for (int i=0; i<len; i++) {
            x[i] = 1.0/len;            
        }
        double[] newx = mulVector(mat, x);
        while( diffVector(newx, x)  > 0) {
            assignVector(x, newx);
            // x = newx; // make copy func
            newx = mulVector(mat, x);
        }
        return newx;
    }

    public double diffVector(double[] a, double[] b) {
        double size = 0.0;
        for(int i=0; i<this.len; i++){
            size += (a[i]-b[i])*(a[i]-b[i]);
        }
        return Math.sqrt(size);

    }
    public double[] mulVector(double[][] mat, double[] x) {
        int len = this.len;
        double[] ret = new double[len];
        // ret[i] = sum 1..j( mat[i][j] * x[j] );
        for (int i= 0; i<len; i++) {
            ret[i] = 0;
            for (int j=0; j<len; j++) {
                ret[i] += mat[i][j] * x[j];
            }            
        }
        return ret;

    }
    public void assignVector(double[] v1, double[] v2) {
        for (int i=0; i<this.len; i++) {
            v1[i] = v2[i];
        }
    }

    public double[][] mulMatrix(double[][] mat, double alpha) {
        double result[][] = new double[len][len];
                int len = this.len;
        for(int i=0; i<len; i++) {
            for( int j=0; j<len; j++) {
                result[i][j] = mat[i][j] *  alpha;
            }
        }
        return result;
    }
 
    public double[][] addMatrix(double[][] matA, double[][]matB) {
        double result[][] = new double[len][len];
        int len = this.len;
        for(int i=0; i<len; i++) {
            for( int j=0; j<len; j++) {
                result[i][j] = matA[i][j] +  matB[i][j];
            }
        }
        return result;
    }
    public double[][] addConstant(double[][] mat, double con) {
        double result[][] = new double[len][len];
        int len = this.len;
        for(int i=0; i<len; i++) {
            for( int j=0; j<len; j++) {
                result[i][j] = mat[i][j] + con;
            }
        }
        return result;
    }

    public void printMatrix(int[] v, double[][] matrix) {
        int len = this.len;

        for( int i=0 ; i<len; i++)
                System.out.print(v[i]+"  ");
        System.out.println();

        for(int i=0; i<len; i++) {
            for( int j=0; j<len; j++)
                System.out.print(matrix[i][j]+"  ");
            System.out.println();
        }
        System.out.println();
    }   
}
