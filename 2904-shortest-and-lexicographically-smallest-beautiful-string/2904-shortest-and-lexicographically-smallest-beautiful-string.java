class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int left = 0, ones = 0;
        int minLen = Integer.MAX_VALUE;
        String ans = "";

        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }

            while (ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            if (ones == k) {
                while (s.charAt(left) == '0') {
                    left++;
                }

                int len = right - left + 1;
                String curr = s.substring(left, right + 1);

                if (len < minLen || (len == minLen && curr.compareTo(ans) < 0)) {
                    minLen = len;
                    ans = curr;
                }
            }
        }

        return ans;
    }
}