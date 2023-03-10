import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private Hashtable<Integer, List<String>> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                List<String> mylist;
                if (!elems.containsKey(hashFunc(query.s))) {
                    mylist = new ArrayList<>();
                    mylist.add(0, query.s);
                    elems.put(hashFunc(query.s), mylist);
                }
                else {
                    mylist = elems.get(hashFunc(query.s));
                    if(!mylist.contains(query.s))
                        mylist.add(0, query.s);
                }
                    //elems.replace(hashFunc(query.s), elems.get(hashFunc(query.s)),mylist);

                break;
            case "del":
                List<String> newList;
                if (elems.containsKey(hashFunc(query.s))) {
                   // out.println(elems);
                    //out.println(elems.get(hashFunc(query.s)));
                    if (elems.get(hashFunc(query.s)).contains(query.s)) {
                        newList = elems.get(hashFunc(query.s));
                        newList.remove(query.s);
                        elems.replace(hashFunc(query.s), elems.get(hashFunc(query.s)), newList);

                    }
                }
                break;
            case "find":
                if (elems.get(hashFunc(query.s)) == null)
                    writeSearchResult(false);
                else
                    writeSearchResult(elems.get(hashFunc(query.s)).contains(query.s));
                break;
            case "check":
                if(elems.containsKey(query.ind)) {
                    for (String cur : elems.get(query.ind))
                        if (hashFunc(cur) == query.ind)
                            out.print(cur + " ");
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {

        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        elems = new Hashtable<>(bucketCount,1);
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    public int getMultiplier() {
        return multiplier;
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}