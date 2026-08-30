class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIdx = 0;
        int maxIdx = 0;

        // Find indices of minimum and maximum
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }

            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);

        // Three possible strategies
        int fromFront = right + 1;
        int fromBack = n - left;
        int fromBothSides = (left + 1) + (n - right);

        return Math.min(fromFront,
                Math.min(fromBack, fromBothSides));
    }
}