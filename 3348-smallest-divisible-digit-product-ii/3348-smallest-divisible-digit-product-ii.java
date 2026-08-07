import java.util.*;

class Solution {

    static final Map<Integer, Map<Integer, Integer>> FACTOR = new HashMap<>();

    static {
        FACTOR.put(0, Map.of());
        FACTOR.put(1, Map.of());
        FACTOR.put(2, Map.of(2, 1));
        FACTOR.put(3, Map.of(3, 1));
        FACTOR.put(4, Map.of(2, 2));
        FACTOR.put(5, Map.of(5, 1));
        FACTOR.put(6, Map.of(2, 1, 3, 1));
        FACTOR.put(7, Map.of(7, 1));
        FACTOR.put(8, Map.of(2, 3));
        FACTOR.put(9, Map.of(3, 2));
    }

    public String smallestNumber(String num, long t) {

        PrimeResult res = getPrimeCount(t);

        if (!res.ok)
            return "-1";

        Map<Integer, Integer> need = getFactorCount(res.count);

        if (sum(need) > num.length())
            return construct(need);

        Map<Integer, Integer> prefix = getPrimeCount(num);

        int firstZero = num.indexOf('0');

        if (firstZero == -1) {
            firstZero = num.length();

            if (isSubset(res.count, prefix))
                return num;
        }

        for (int i = num.length() - 1; i >= 0; i--) {

            int d = num.charAt(i) - '0';

            prefix = subtract(prefix, FACTOR.get(d));

            int space = num.length() - 1 - i;

            if (i > firstZero)
                continue;

            for (int bigger = d + 1; bigger <= 9; bigger++) {

                Map<Integer, Integer> remain =
                        getFactorCount(
                                subtract(
                                        subtract(res.count, prefix),
                                        FACTOR.get(bigger)));

                if (sum(remain) <= space) {

                    StringBuilder ans = new StringBuilder();

                    ans.append(num.substring(0, i));

                    ans.append((char) ('0' + bigger));

                    int ones = space - sum(remain);

                    while (ones-- > 0)
                        ans.append('1');

                    ans.append(construct(remain));

                    return ans.toString();
                }
            }
        }

        Map<Integer, Integer> remain = getFactorCount(res.count);

        StringBuilder ans = new StringBuilder();

        int ones = num.length() + 1 - sum(remain);

        while (ones-- > 0)
            ans.append('1');

        ans.append(construct(remain));

        return ans.toString();
    }

    static class PrimeResult {
        Map<Integer, Integer> count;
        boolean ok;

        PrimeResult(Map<Integer, Integer> c, boolean o) {
            count = c;
            ok = o;
        }
    }

    private PrimeResult getPrimeCount(long t) {

        Map<Integer, Integer> cnt = new HashMap<>();

        cnt.put(2, 0);
        cnt.put(3, 0);
        cnt.put(5, 0);
        cnt.put(7, 0);

        int[] p = {2, 3, 5, 7};

        for (int x : p) {
            while (t % x == 0) {
                cnt.put(x, cnt.get(x) + 1);
                t /= x;
            }
        }

        return new PrimeResult(cnt, t == 1);
    }

    private Map<Integer, Integer> getPrimeCount(String s) {

        Map<Integer, Integer> cnt = new HashMap<>();

        cnt.put(2, 0);
        cnt.put(3, 0);
        cnt.put(5, 0);
        cnt.put(7, 0);

        for (char c : s.toCharArray()) {

            int d = c - '0';

            for (Map.Entry<Integer, Integer> e : FACTOR.get(d).entrySet())
                cnt.put(e.getKey(), cnt.get(e.getKey()) + e.getValue());
        }

        return cnt;
    }

    private Map<Integer, Integer> getFactorCount(Map<Integer, Integer> cnt) {

        int c8 = cnt.get(2) / 3;
        int rem2 = cnt.get(2) % 3;

        int c9 = cnt.get(3) / 2;
        int c3 = cnt.get(3) % 2;

        int c4 = rem2 / 2;
        int c2 = rem2 % 2;

        int c6 = 0;

        if (c2 == 1 && c3 == 1) {
            c2 = 0;
            c3 = 0;
            c6 = 1;
        }

        if (c3 == 1 && c4 == 1) {
            c2 = 1;
            c6 = 1;
            c3 = 0;
            c4 = 0;
        }

        Map<Integer, Integer> res = new HashMap<>();

        for (int i = 2; i <= 9; i++)
            res.put(i, 0);

        res.put(2, c2);
        res.put(3, c3);
        res.put(4, c4);
        res.put(5, cnt.get(5));
        res.put(6, c6);
        res.put(7, cnt.get(7));
        res.put(8, c8);
        res.put(9, c9);

        return res;
    }

    private String construct(Map<Integer, Integer> f) {

        StringBuilder sb = new StringBuilder();

        for (int d = 2; d <= 9; d++) {

            int x = f.get(d);

            while (x-- > 0)
                sb.append((char) ('0' + d));
        }

        return sb.toString();
    }

    private boolean isSubset(Map<Integer, Integer> a,
                             Map<Integer, Integer> b) {

        for (int k : a.keySet())
            if (b.get(k) < a.get(k))
                return false;

        return true;
    }

    private Map<Integer, Integer> subtract(Map<Integer, Integer> a,
                                           Map<Integer, Integer> b) {

        Map<Integer, Integer> res = new HashMap<>(a);

        for (Map.Entry<Integer, Integer> e : b.entrySet()) {

            int k = e.getKey();

            res.put(k,
                    Math.max(0, res.get(k) - e.getValue()));
        }

        return res;
    }

    private int sum(Map<Integer, Integer> m) {

        int ans = 0;

        for (int x : m.values())
            ans += x;

        return ans;
    }
}