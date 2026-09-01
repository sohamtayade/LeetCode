import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length, n = classroom[0].length();
        int sr = 0, sc = 0, k = 0;

        int[][] id = new int[m][n];
        for (int[] row : id) Arrays.fill(row, -1);

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);
                if (ch == 'S') {
                    sr = r;
                    sc = c;
                } else if (ch == 'L') {
                    id[r][c] = k++;
                }
            }
        }

        int target = (1 << k) - 1;
        if (target == 0) return 0;

        int[][][] best = new int[m][n][1 << k];
        for (int[][] a : best)
            for (int[] b : a)
                Arrays.fill(b, -1);

        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{sr, sc, energy, 0});
        best[sr][sc][0] = energy;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int moves = 0;

        while (!q.isEmpty()) {
            for (int size = q.size(); size > 0; size--) {
                int[] cur = q.poll();
                int r = cur[0], c = cur[1], e = cur[2], mask = cur[3];

                if (mask == target) return moves;
                if (e == 0) continue;

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d], nc = c + dc[d];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n ||
                        classroom[nr].charAt(nc) == 'X')
                        continue;

                    int ne = e - 1;
                    int nm = mask;
                    char ch = classroom[nr].charAt(nc);

                    if (ch == 'L')
                        nm |= 1 << id[nr][nc];

                    if (ch == 'R')
                        ne = energy;

                    if (best[nr][nc][nm] >= ne)
                        continue;

                    best[nr][nc][nm] = ne;
                    q.offer(new int[]{nr, nc, ne, nm});
                }
            }
            moves++;
        }

        return -1;
    }
}