package redblacktree;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

public class Main {

    private static final int MAX_SIZE = 500;
    private static final int REPITITIONS = 500;

    public static void main(String[] args) throws FileNotFoundException {

        InsertComplexities util = new InsertComplexities(new Random("Macavity"
                .hashCode()));

        RedBlackTree<InsertComplexities.InstrumentedKey, Integer> bll
            = new RedBlackTree<InsertComplexities.InstrumentedKey, Integer>();

        int[] rbtComplexities = util.getInsertComplexities(bll, MAX_SIZE,
                REPITITIONS);

        output("RedBlackTree", rbtComplexities);

        RedBlackTree<Integer,Integer> RBT = new RedBlackTree<>();
        RBT.put(1,1);
        RBT.put(3,3);
        RBT.put(6,6);
        RBT.put(4,4);
        RBT.put(7,7);
        RBT.put(8,8);
        RBT.put(10,10);
        RBT.put(14,14);
        RBT.put(13,13);

        Iterator itr = RBT.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    private static void output(String string, int[] complexities)
            throws FileNotFoundException {
        File f = new File(string + ".dat");
        PrintWriter pw = new PrintWriter(f);
        for (int i = 0; i < complexities.length; i++) {
            pw.println(String.format("%d\t%d", i, complexities[i]));
        }
        pw.flush();
    }

}
