import java.util.*;
public class MinAndTimesSegmentTree {
    public class Tuple{
        int val;
        int count;
        public Tuple(int _val, int _count){
            val = _val;
            count = _count;
        }
    }

    int max;
    Tuple[] arr;
    int[] nums;

    public MinAndTimesSegmentTree(int[] nums){
        max = nums.length;
        arr = new Tuple[4*max];
        this.nums = nums;
        build(0, 0, max-1);
    }

    public void build(int pos, int l, int r){
        if(l == r){
            arr[pos] = new Tuple(nums[l], 1);
        }else{
            int mid = (l+r)/2;
            build(pos*2+1, l, mid);
            build(pos*2+2, mid+1, r);
            if(arr[pos*2+1].val > arr[pos*2+2].val){
                arr[pos] = new Tuple(arr[pos*2+2].val, arr[pos*2+2].count);
            }else if(arr[pos*2+1].val < arr[pos*2+2].val){
                arr[pos] = new Tuple(arr[pos*2+1].val, arr[pos*2+1].count);
            }else{
                arr[pos] = new Tuple(arr[pos*2+1].val, arr[pos*2+1].count + arr[pos*2+2].count);
            }
        }
    }

    public void update(int pos, int val){
        nums[pos] = val;
        updateUtil(0, 0, max-1, pos, val);
    }

    public void updateUtil(int v, int l, int r, int pos, int val){
        if(v >= arr.length || arr[v] == null) return;
        if(l == r){
            arr[v] = new Tuple(val, 1);
        }else{
            int mid = (l+r)/2;
            if(pos <= mid){
                updateUtil(v*2+1, l, mid, pos, val);
            }else{
                updateUtil(v*2+2, mid+1, r, pos, val);
            }
            if(arr[v*2+1].val > arr[v*2+2].val){
                arr[v] = new Tuple(arr[v*2+2].val, arr[v*2+2].count);
            }else if(arr[v*2+1].val < arr[v*2+2].val){
                arr[v] = new Tuple(arr[v*2+1].val, arr[v*2+1].count);
            }else{
                arr[v] = new Tuple(arr[v*2+1].val, arr[v*2+1].count + arr[v*2+2].count);
            }
        }
    }

    public int[] getMin(int ql, int qr){
        if(ql < 0 || ql > max-1 || qr < 0 || qr > max-1 || ql > qr){
            return new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE};
        }
        Tuple t = getMinUtil(0, 0, max-1, ql, qr);
        return new int[]{t.val, t.count};
    }

    public Tuple getMinUtil(int pos, int l, int r, int ql, int qr){
        if(r < ql || l > qr){
            return new Tuple(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        if(l == ql && r == qr){
            return arr[pos];
        }
        int mid = (l+r)/2;
        Tuple left = getMinUtil(pos*2+1, l, mid, ql, Math.min(mid, qr));
        Tuple right = getMinUtil(pos*2+2, mid+1, r, Math.max(mid+1, ql), qr);
        if(left.val > right.val){
            return right;
        }else if(left.val < right.val){
            return left;
        }else{
            return new Tuple(left.val, left.count + right.count);
        }
    }
}