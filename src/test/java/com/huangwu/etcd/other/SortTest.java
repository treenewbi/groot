package com.huangwu.etcd.other;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/27 17:05
 * @Description:
 * @LastModify:
 */
public class SortTest {
    private static int[] arr = new int[]{3, 6, 2, 7, 9, 1, 26, 4};

    public static void print() {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "、");
        }
        System.out.println();
    }

    @Test
    public void bubbleSort() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        print();
    }

    @Test
    public void quicklySortTest() {
        int[] ls = quicklySort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(ls));
    }

    public int[] quicklySort(int[] arr, int left, int right) {
        int i, j, t, temp;
        if (left > right) {
            return arr;
        } else {
            temp = arr[left];
            i = left;
            j = right;
            //从右边往左遍历,获取第一个小于基准数的下标
            while (arr[j] >= temp && i < j) {
                j--;
            }
            //从左往右遍历，获取第一个大于基准数的下标
            while (arr[i] <= temp && i < j) {
                i++;
            }

            //然后交换两个数在数组中的位置
            if (i < j) {
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                return quicklySort(arr, left, right);
            } else {
                arr[left] = arr[i];
                arr[i] = temp;
                quicklySort(arr, left, i - 1);
                quicklySort(arr, j + 1, right);
                return arr;
            }
        }
    }

    @Test
    public void mergerSortTest() {
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] array, int start, int end) {
        if (start >= end)
            return;

        int mid = (start + end) >> 1;
        // 递归实现归并排序
        sort(array, start, mid);
        sort(array, mid + 1, end);
        mergerSort(array, start, mid, end);
    }

    // 将两个有序序列归并为一个有序序列(二路归并)
    private static void mergerSort(int[] array, int start, int mid, int end) {
        // TODO Auto-generated method stub
        int[] arr = new int[end + 1]; // 定义一个临时数组，用来存储排序后的结果
        int low = start; // 临时数组的索引
        int left = start;

        int center = mid + 1;
        // 取出最小值放入临时数组中
        while (start <= mid && center <= end) {
            arr[low++] = array[start] > array[center] ? array[center++] : array[start++];
        }

        // 若还有段序列不为空，则将其加入临时数组末尾

        while (start <= mid) {
            arr[low++] = array[start++];
        }
        while (center <= end) {
            arr[low++] = array[center++];
        }

        // 将临时数组中的值copy到原数组中
        for (int i = left; i <= end; i++) {
            array[i] = arr[i];
        }
    }
}
