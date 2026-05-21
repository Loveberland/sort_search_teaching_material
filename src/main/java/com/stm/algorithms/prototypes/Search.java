package main.java.com.stm.algorithms.prototypes;

public class Search {
        private static final int LEN = 100;
        private static int arr[] = new int[LEN];

        private static void createArray() {
                for (int i = 0; i < LEN; i++)
                        arr[i] = i + 1;
        }

        public static void linear(int find) {
                createArray();
                System.out.printf("Find: ");
                for (int i = 0; i < LEN; i++) {
                        if (arr[i] == find)
                                System.out.printf("\nFound: %d\n", arr[i]);
                        else
                                System.out.printf("%d, ", arr[i]);
                }
        }

        public static void binary(int find) {
                createArray(); 
                System.out.printf("Find: ");
                int l = 0, r = LEN - 1;
                while (l <= r) {
                        int mid = l + (r - l) / 2;
                        if (arr[mid] == find) {
                                System.out.printf("\nFound: %d\n", arr[mid]);
                                return;
                        } else if (arr[mid] > find) {
                                r = mid - 1;
                                System.out.printf("%d, ", arr[mid]);
                        } else {
                                l = mid + 1;
                                System.out.printf("%d, ", arr[mid]);
                        }
                }
        }
}
