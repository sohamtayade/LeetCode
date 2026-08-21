import java.util.*;

class Solution {
    long[] lcm;
    int[] sign;
    int m;

    long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    public long findKthSmallest(int[] coins, int k) {
        Arrays.sort(coins);

        int[] temp = new int[coins.length];
        m = 0;

        for (int c : coins) {
            boolean redundant = false;

            for (int i = 0; i < m; i++) {
                if (c % temp[i] == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant)
                temp[m++] = c;
        }

        int total = 1 << m;
        lcm = new long[total];
        sign = new int[total];

        lcm[0] = 1;

        for (int mask = 1; mask < total; mask++) {
            int bit = Integer.numberOfTrailingZeros(mask);
            int prev = mask & (mask - 1);

            long a = lcm[prev];
            long b = temp[bit];
            long g = gcd(a, b);

            if (a > (long) k * temp[0] / (b / g))
                lcm[mask] = Long.MAX_VALUE;
            else
                lcm[mask] = a / g * b;

            sign[mask] = (Integer.bitCount(mask) & 1) == 1 ? 1 : -1;
        }

        long low = 1;
        long high = (long) temp[0] * k;

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (count(mid) >= k)
                high = mid;
            else
                low = mid + 1;
        }

        return low;
    }

    long count(long x) {
        long ans = 0;

        for (int mask = 1; mask < lcm.length; mask++) {
            long L = lcm[mask];

            if (L <= x)
                ans += sign[mask] * (x / L);
        }

        return ans;
    }
}