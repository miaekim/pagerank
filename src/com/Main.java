import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: miaekim ( beautiful.mimikim@gmail.com )
 * Date: 13. 10. 13.
 * It is the homework for purdue search engine class.
 * Inspired by PageRank Algorithm.
 * See also : https://googledrive.com/host/0B2GQktu-wcTiaWw5OFVqT1k3bDA/
 */
public class Main {
    public static void main(String args[]){
       Pagerank pr = new Pagerank();
       Map<Integer, Double> pg = new HashMap<Integer, Double>();
       for(int i=0; i<3; i++) {
	       double[] x = pr.makeGoogle(pr.input[i]);
			for (int j=0; j< x.length; j++) {
				if( pg.containsKey( pr.input[i][j] ) == true )
					pg.put(pr.input[i][j], pg.get( pr.input[i][j] )+x[j]);
				else
					pg.put(pr.input[i][j], x[j]);			
			}
		}

		printMap(pg);
    }	

    public static void printMap(Map mp) {
    Iterator it = mp.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        System.out.println(pairs.getKey() + " = " + pairs.getValue());
        it.remove(); // avoids a ConcurrentModificationException
    }
}
}
