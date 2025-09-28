package com.nhuthao02.social_network;

import java.util.*;

public class MainTest {
    public static int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while(l<r){
            int m =(l+r)/2;
            if (nums[m] == target) return m;
            if (nums[m] < target){
                l = m+1;
            } else {
                r = m-1;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] nums ={-1,0,3,5,9,12};
      System.out.println(search(nums, 9));
    }
}

