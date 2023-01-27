
import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    public class Blocks implements Comparable<Blocks> {
        long start_T;
        int worker_Index;

        public Blocks(long time, int worker) {
            start_T = time;
            worker_Index = worker;
        }

        @Override
        public int compareTo(Blocks o1) {
           if (this.start_T < o1.start_T){
               return -1;
           }
           else if(this.start_T == o1.start_T){
               if(this.worker_Index < o1.worker_Index)
                   return -1;
               else
                   return 1;
           }
           else return 1;
        }
    }
    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        /**
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }

         **/
        PriorityQueue<Blocks> nextFreeTime = new PriorityQueue<Blocks>(numWorkers);
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        for (int i = 0; i < numWorkers && i < jobs.length; i++){
            assignedWorker[i] = i;
            startTime[i] = 0;
            Blocks new_Work = new Blocks(0+jobs[i],i );
            nextFreeTime.add(new_Work);


        }
        for (int i = numWorkers; i < jobs.length; i+= numWorkers){
            for (int j = 0; j < (numWorkers) && (i+j) < (jobs.length); j++){
                int index = i+j;
                Blocks cur = nextFreeTime.poll();
                long cur_time = cur.start_T;
                int cur_worker = cur.worker_Index;
                assignedWorker[index] = cur_worker;
                startTime[index] = cur_time;
                nextFreeTime.add(new Blocks(cur_time + jobs[index], cur_worker));
            }

        }

    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
