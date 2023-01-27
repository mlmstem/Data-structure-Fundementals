import java.util.*;
import java.io.*;

public class is_bst {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            // Implement correct algorithm here
            List<Integer> checks = new ArrayList<>();

            if(nodes == 0)
                return true;

            Node root = tree[0];

            check_Node(root, checks, 0,10);

            for (int i = 0; i < checks.size(); i++) {
                if (checks.get(i) == 1)
                    return false;
            }

            return true;
        }

        void check_Node(Node root, List checks, int parent,int dir){
            if (root.left == -1 && root.right == -1)
                return;
            if (root.left != -1) {
                if (tree[root.left].key > root.key || (dir == 1 && tree[root.left].key < parent)){
                    checks.add(1);
                    return;
                }
                checks.add(0);
                check_Node(tree[root.left], checks, root.key, 0 );
            }
            if (root.right != -1) {
                if (tree[root.right].key < root.key || (dir == 0 && tree[root.right].key > parent)) {
                    checks.add(1);
                    return;
                }
                checks.add(0);
                check_Node(tree[root.right], checks, root.key, 1);


            }
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}