import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : invocations) {
            graph.get(edge[0]).add(edge[1]);
        }

        boolean[] suspicious = new boolean[n];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(k);
        suspicious[k] = true;

        while (!queue.isEmpty()) {

            int node = queue.poll();

            for (int next : graph.get(node)) {
                if (!suspicious[next]) {
                    suspicious[next] = true;
                    queue.offer(next);
                }
            }
        }

        for (int[] edge : invocations) {

            int from = edge[0];
            int to = edge[1];

            if (!suspicious[from] && suspicious[to]) {

                List<Integer> result = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    result.add(i);
                }

                return result;
            }
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                result.add(i);
            }
        }

        return result;
    }
}