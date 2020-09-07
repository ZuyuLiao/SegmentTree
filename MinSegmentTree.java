import java.util.*;
public class MinSegmentTree {
    int max;
    int[] arr;
    int[] nums;

    public MinSegmentTree(int[] nums){
        max = nums.length;
        arr = new int[4*max];
        this.nums = nums;
        build(0, 0, max-1);
    }

    public void build(int pos, int l, int r){
        if(l == r){
            arr[pos] = nums[l];
        }else{
            int mid = (l+r)/2;
            build(pos*2+1, l, mid);
            build(pos*2+2, mid+1, r);
            arr[pos] = Math.min(arr[pos*2+1], arr[pos*2+2]);
        }
    }

    public void update(int pos, int val){
        arr[pos] = val;
        updateUtil(0, 0, max-1, pos, val);
    }

    public void updateUtil(int v, int l, int r, int pos, int val){
        if(l == r){
            arr[v] = val;
        }else{
            int mid = (l+r)/2;
            if(pos <= mid){
                updateUtil(2*v+1, l, mid, pos, val);
            }else{
                updateUtil(2*v+2, mid+1, r, pos, val);
            }
            arr[v] = Math.min(arr[2*v+1], arr[2*v+2]);
        }
    }

    public int getMin(int ql, int qr){
        if(ql < 0 || ql > max-1 || qr < 0 || qr > max-1 || ql > qr){
            return Integer.MAX_VALUE;
        }
        return getMinUtil(0, 0, max-1, ql, qr);
    }

    public int getMinUtil(int pos, int l, int r, int ql, int qr){
        if(r < ql || l > qr){
            return Integer.MAX_VALUE;
        }
        if(l == ql && r == qr){
            return arr[pos];
        }
        int mid = (l+r)/2;
        return Math.min(getMinUtil(pos*2+1, l, mid, ql, Math.min(mid, qr)), getMinUtil(pos*2+2, mid+1, r, Math.max(mid+1, ql), qr));
    }
}