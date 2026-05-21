package main.java.com.stm.algorithms.prototypes;

import java.util.Random;

public class Sort {
        private static Random rand = new Random();
        private static final int LEN = 100;
        private static final int MIN = 1;
        private final static int MAX = 100;
        private static int[] arr = new int[100];

        private static void createArray() {
                for (int i = 0; i < LEN; i++) {
                        arr[i] = rand.nextInt(MAX - MIN + 1) + MIN;
                }
        }

        public static void display(String s) {
                System.out.printf("%s", s);
                for (int i : arr) {
                        System.out.printf("%d, ", i);
                }
                System.out.printf("\n");
        }

        public static void bubble() {
                createArray();
                display("Before: ");
                boolean swapped;
                int tmp;
                for (int i = 0; i < LEN - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < LEN - i - 1; j++) {
                                if (arr[j] > arr[j + 1]) {
                                        tmp = arr[j];
                                        arr[j] = arr[j + 1];
                                        arr[j + 1] = tmp;
                                        swapped = true;
                                }
                        }
                        if (!swapped)
                                break;
                }
                display("After: ");
        }

        public static void selection() {
                createArray();
                display("Before: ");
                for (int i = 0; i < LEN - 1; i++) {
                        int minIdx = i;
                        for (int j = i + 1; j < LEN; j++) {
                                if (arr[minIdx] > arr[j])
                                        minIdx = j;
                        }
                        int tmp = arr[i];
                        arr[i] = arr[minIdx];
                        arr[minIdx] = tmp;
                }
                display("After: ");
        }

        public static void insertion() {
                createArray();
                display("Before: ");
                for (int i = 1; i < LEN; i++) {
                        int key = arr[i];
                        int j = i - 1;
                        while (j >= 0 && arr[j] > key) {
                                arr[j + 1] = arr[j];
                                j--;
                        }
                        arr[j + 1] = key;
                }
                display("After: ");
        }

        public static void merge() {
                createArray();
                display("Before: ");
                subMerge0(0, LEN - 1);
                display("After: ");
        }

        private static void subMerge0(int l, int r) {
                if (l < r) {
                        int m = l + (r - l) / 2;
                        subMerge0(l, m);
                        subMerge0(m + 1, r);
                        subMerge1(l, m, r);
                }
        }

        private static void subMerge1(int l, int m, int r) {
                int n1 = m - l + 1, n2 = r - m;
                int L[] = new int[n1];
                int R[] = new int[n2];
                for (int i = 0; i < n1; i++)
                        L[i] = arr[l + i];
                for (int i = 0; i < n2; i++)
                        R[i] = arr[m + 1 + i];
                int i = 0, j = 0;
                int k = l;
                while (i < n1 && j < n2) {
                        if (L[i] < R[j]) {
                                arr[k] = L[i];
                                i++;
                        } else {
                                arr[k] = R[j];
                                j++;
                        }
                        k++;
                }
                while (i < n1) {
                        arr[k] = L[i];
                        i++;
                        k++;
                }
                while (j < n2) {
                        arr[k] = R[j];
                        j++;
                        k++;
                }
        }

        public static void quick() {
                createArray();
                display("Before: ");
                subQuick0(0, LEN - 1); 
                display("After: ");
        }

        private static void subQuick0(int l, int r) {
                if (l < r) {
                        int pi = subQuick1(l, r);
                        subQuick0(l, pi - 1);
                        subQuick0(pi + 1, r);
                }
        }

        private static int subQuick1(int l, int r) {
                int pivot = arr[r];
                int i = l - 1;
                for (int j = l; j <= r - 1; j++) {
                        if (arr[j] < pivot) {
                                i++;
                                subQuick2(i, j);
                        }
                }
                subQuick2(i + 1, r);
                return i + 1;
        }

        private static void subQuick2(int i, int j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
        }
}
