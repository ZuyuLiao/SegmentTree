public class KthZero {
     int max;
    int[] arr;
    int[] nums;

    public KthZero(int[] nums){
        max = nums.length;
        arr = new int[4*max];
        this.nums = nums;
        build(0, 0, max-1);
    }

    public void build(int pos, int l, int r){
        if(l == r){
            arr[pos] = (nums[l] == 0 ? 1 : 0);
        }else{
            int mid = (l+r)/2;
            build(pos*2+1, l, mid);
            build(pos*2+2, mid+1, r);
            arr[pos] = arr[pos*2+1] + arr[pos*2+2];
        }
    }

    public void update(int pos, int val){
        nums[pos] = val;
        if(val == 0)
            updateUtil(0, 0, max-1, pos, val);
    }

    public void updateUtil(int v, int l, int r, int pos, int val){
        if(l == r){
            arr[v] = (nums[l] == 0 ? 1 : 0);
        }else{
            int mid = (l+r)/2;
            if(pos <= mid){
                updateUtil(2*v+1, l, mid, pos, val);
            }else{
                updateUtil(2*v+2, mid+1, r, pos, val);
            }
            arr[v] = arr[2*v+1] + arr[2*v+2];
        }
    }

    public int getCount(int l, int r){
        return getCountUtil(0, 0, max-1, l, r);
    }

    public int getCountUtil(int v, int l, int r, int ql, int qr){
        if(l > qr || r < ql) return 0;
        if(ql <= l && r <= qr){
            return arr[v];
        }
        int mid = (l+r)/2;
        return getCountUtil(2*v+1, l, mid, ql, qr) + getCountUtil(2*v+2, mid+1, r, ql, qr);
    }

    public int findKth(int k){
        return findKthUtil(0, 0, max-1, k);
    }

    public int findKthUtil(int v, int l, int r, int k){
        if(k > arr[v]){
            return -1;
        }
        if(l == r){
            return l;
        }
        int mid = (l+r)/2;
        if(arr[2*v+1] >= k){
            return findKthUtil(2*v+1, l, mid, k);
        }else{
            return findKthUtil(2*v+2, mid+1, r, k - arr[l]);
        }
    }
    
}
