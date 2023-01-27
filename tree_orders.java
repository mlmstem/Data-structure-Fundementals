import javax.swing.tree.TreeNode;
import java.util.*;
import java.io.*;

public class tree_orders {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class TreeOrders {
        int n;
        int[] key, left, right;


        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) {
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
        }
        class node{
            node parent;
            int current;
            int left_index;
            int right_index;

            int is_check;

            public node(int current){
                this.current = current;
            }
        }


        List<Integer> inOrder() {

            // Finish the implementation
            List < Integer > result = new ArrayList<Integer>();
            // You may need to add a new recursive method to do that


            Stack<Integer> results = new Stack<>();

            node root = new node(key[0]);
            root.parent = null;
            root.right_index = right[0];
            root.left_index = left[0];
            root.is_check = 0;

            check_inorder(root,results);

            int n =0;
            while(!results.isEmpty() && n < key.length){
                result.add(results.pop());
            }

            return result;
        }

        List<Integer> preOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            // Finish the implementation
            // You may need to add a new recursive method to do that
            node root = new node(key[0]);
            root.parent = null;
            root.right_index = right[0];
            root.left_index = left[0];
            root.is_check = 0;

            check_preorder(root, result);



            return result;
        }

        List<Integer> postOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            // Finish the implementation
            // You may need to add a new recursive method to do that

            node root = new node(key[0]);
            root.parent = null;
            root.right_index = right[0];
            root.left_index = left[0];
            root.is_check = 0;

            check_postorder(root,result);
            return result;
        }

        void check_inorder(node root, Stack result){

            if (root == null)
                return;

            if (root.is_check == 1)
                return;

            node current = root;
            while(current.right_index != -1){
                node parent = current;
                int l = current.left_index;
                int r = current.right_index;
                current = new node(key[r]);
                current.parent = parent;
                current.left_index = left[r];
                current.right_index = right[r];
            }
            current.is_check = 1;
            result.push(current.current);
            //System.out.println(current.current);

            if(current.parent != null) {
                current.parent.right_index = -1;
                //current.parent.left_index = -1;
            }


            if (current.left_index != -1){
                node left_N = new node(key[current.left_index]);
                //System.out.println(key[current.left_index]);
                left_N.parent = current;
                left_N.left_index = left[current.left_index];
                left_N.right_index = right[current.left_index];
                left_N.parent.left_index = -1;

                //System.out.println("The index is " + left_N.current + "the left index is " + left_N.left_index +
                        //"the right index is " + left_N.right_index);
                check_inorder(left_N,result);
            }
            //System.out.println("The index is " + current.parent.current + "the left index is " +
                    //current.parent.left_index  + "the right index is " + current.parent.right_index);

            check_inorder(current.parent, result);

        }

        void check_preorder(node root, List result){

            if(root == null){
                return;
            }
            node current = root;

            result.add(current.current);

            if (current.left_index != -1){
                node parent = current;
                int l = current.left_index;
                current = new node(key[l]);
                current.parent = parent;
                current.left_index = left[l];
                current.right_index = right[l];
                check_preorder(current, result);
            }

            current = root;

            if (current.right_index != -1){
                node parent = current;
                int r = current.right_index;
                current = new node(key[r]);
                current.parent = parent;
                current.left_index = left[r];
                current.right_index = right[r];
                check_preorder(current, result);
            }
        }
        void check_postorder(node root, List result){
            if (root == null)
                return;
            node current = root;

            if (current.left_index != -1){
                node parent = current;
                int l = current.left_index;
                node leftN = new node(key[l]);
                leftN.parent = parent;
                leftN.left_index = left[l];
                leftN.right_index = right[l];
                check_postorder(leftN, result);
            }

            if (current.right_index != -1){
                node parent = current;
                int r = current.right_index;
                node rightN = new node(key[r]);
                rightN.parent = parent;
                rightN.left_index = left[r];
                rightN.right_index = right[r];
                check_postorder(rightN, result);
            }

            result.add(current.current);

        }



    }


    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_orders().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        TreeOrders tree = new TreeOrders();
        tree.read();
        print(tree.inOrder());
        print(tree.preOrder());
        print(tree.postOrder());
    }
}
