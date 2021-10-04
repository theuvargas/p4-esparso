import java.util.Set;

class Combination {
    static void combinationUtil(int[] arr, int n, int k, int index, int[] data, int i, Set<Integer[]> combinations) {
        if (index == k) { // combinação tem o tamanho certo
            Integer[] combination = new Integer[k];
            for (int j=0; j<k; j++) {
                combination[j] = data[j];
            }
            combinations.add(combination);
            return;
        }

        if (i >= n)
            return;

        data[index] = arr[i];
        combinationUtil(arr, n, k, index+1, data, i+1, combinations);

        combinationUtil(arr, n, k, index, data, i+1, combinations);
    }

    static void getCombination(int[] arr, int k, Set<Integer[]> combinations)  {
        int n = arr.length;

        int[] data = new int[k];

        combinationUtil(arr, n, k, 0, data, 0, combinations);
    }
}