package com.huangwu.etcd.other;

import org.junit.Test;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/27 19:52
 * @Description:
 * @LastModify:
 */
public class SearchTest {
    private static int[] arr = new int[]{1, 3, 5, 7, 9, 14, 16, 18};

    @Test
    public void halfSearchTest() {
        System.out.println(halfSearch(19, arr));
    }

    @Test
    public void halfSearchTest2() {
        System.out.println(halfSearch2(3, 0, arr.length - 1, arr));
    }

    public int halfSearch(int key, int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        int middle = 0;
        if (key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        while (low <= high) {
            middle = (low + high) / 2;
            if (key > array[middle]) {
                low = middle + 1;
            } else if (key < array[middle]) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public int halfSearch2(int key, int low, int high, int[] array) {
        if (array == null || array.length == 0 || key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        int middle = (low + high) / 2;
        if (key < array[middle]) {
            return halfSearch2(key, low, middle - 1, array);
        } else if (key > array[middle]) {
            return halfSearch2(key, middle + 1, high, array);
        } else
            return middle;
    }
}
