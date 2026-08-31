class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        
        int first = -1;       // First critical point
        int prevCritical = -1; // Previous critical point
        
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = -1;
        
        ListNode prev = head;
        ListNode curr = head.next;
        
        int position = 1;
        
        while (curr != null && curr.next != null) {
            
            ListNode next = curr.next;
            
            // Check if curr is a local maximum or minimum
            boolean isCritical = 
                (curr.val > prev.val && curr.val > next.val) ||
                (curr.val < prev.val && curr.val < next.val);
            
            if (isCritical) {
                
                // First critical point
                if (first == -1) {
                    first = position;
                }
                
                // We already have a previous critical point
                if (prevCritical != -1) {
                    int distance = position - prevCritical;
                    minDistance = Math.min(minDistance, distance);
                }
                
                prevCritical = position;
                
                // Distance between first and current critical point
                maxDistance = position - first;
            }
            
            prev = curr;
            curr = next;
            position++;
        }
        
        // Fewer than two critical points
        if (maxDistance == 0 || maxDistance == -1) {
            return new int[]{-1, -1};
        }
        
        return new int[]{minDistance, maxDistance};
    }
}