/******************************************************************
 *
 *   Lizy Agbakahi COMP 400C Spring 2025
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/

import java.util.Arrays;


public class ProblemSolutions {

    /**
     * Method SelectionSort
     * <p>
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     * <p>
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     *
     * @param values    - int[] array to be sorted.
     * @param ascending - if true,method performs an ascending sort, else descending.
     *                  There are two method signatures allowing this parameter
     *                  to not be passed and defaulting to 'true (or ascending sort).
     */

    public void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending) {

        int n = values.length;

        for (int i = 0; i < n - 1; i++) {
            // Initialize position of the min/max found so far
            int index = i;

            for (int j = i + 1; j < n; j++) {
                if (ascending) {
                    // If we want smallest numbers first (ascending)
                    if (values[j] < values[index]) {
                        // Find the new smallest number
                        index = j;
                    }
                } else {
                    // If we want largest numbers first (descending)
                    if (values[j] > values[index]) {
                        // Find the new largest number
                        index = j;
                    }
                }
            }

            // Swap the element at position i with our found smallest or largest number if needed
            if (index != i) {
                int swap = values[i];
                values[i] = values[index];
                values[index] = swap;
            }
        }
    } // End class selectionSort


    /**
     * Method mergeSortDivisibleByKFirst
     * <p>
     * This method will perform a merge sort algorithm. However, all numbers
     * that are divisible by the argument 'k', are returned first in the sorted
     * list. Example:
     * values = { 10, 3, 25, 8, 6 }, k = 5
     * Sorted result should be --> { 10, 25, 3, 6, 8 }
     * <p>
     * values = { 30, 45, 22, 9, 18, 39, 6, 12 }, k = 6
     * Sorted result should be --> { 30, 18, 6, 12, 9, 22, 39, 45 }
     * <p>
     * As shown above, this is a normal merge sort operation, except for the numbers
     * divisible by 'k' are first in the sequence.
     *
     * @param values - input array to sort per definition above
     * @param k      - value k, such that all numbers divisible by this value are first
     */

    public void mergeSortDivisibleByKFirst(int[] values, int k) {

        // Protect against bad input values
        if (k == 0) return;
        if (values.length <= 1) return;

        mergeSortDivisibleByKFirst(values, k, 0, values.length - 1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {

        if (left >= right)
            return;

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    /*
     * The merging portion of the merge sort, divisible by k first
     */

    private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right) {
        // Initialize pointers to start of left and right subarray
        int leftIndex = left;
        int rightIndex = mid + 1;
        int startPos = left;

        // Create temp array to store merged results
        int[] tempArray = new int[right - left + 1];
        int tempIndex = 0;

        // Main merging loop - processing both subarrays
        while(leftIndex <= mid && rightIndex <= right){
            // Give priority to elements divisible by k
            if(arr[leftIndex] % k == 0 && arr[rightIndex] % k != 0){
                tempArray[tempIndex++] = arr[leftIndex++];
            }
            // If left is divisible but right is not
            else if(arr[rightIndex] % k == 0 && arr[leftIndex] % k != 0){
                tempArray[tempIndex++] = arr[rightIndex++];
            }
            // If right is divisible but left is not
            else if(arr[leftIndex] % k == 0){
                tempArray[tempIndex++] = arr[leftIndex++];
            }
            // If both elements are divisible, take left first
            else if(arr[rightIndex] % k == 0){
                tempArray[tempIndex++] = arr[rightIndex++];
            }
            else {
                // If neither is divisible by k. sort normally
                if(arr[leftIndex] <= arr[rightIndex]){
                    tempArray[tempIndex++] = arr[leftIndex++];
                } else {
                    tempArray[tempIndex++] = arr[rightIndex++];
                }
            }
        }

        // Handle any remaining elements in left subarray
        while(leftIndex <= mid){
            tempArray[tempIndex++] = arr[leftIndex++];
        }

        // Handle any remaining elements in right subarray
        while(rightIndex <= right){
            tempArray[tempIndex++] = arr[rightIndex++];
        }

        // Copy elements from temp array back to original array
        for(int i = 0; i < tempArray.length; i++){
            arr[startPos + i] = tempArray[i];
        }
    }


    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a planet.
     * You are further given an integer array 'asteroids', where asteroids[i] is the mass
     * of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any arbitrary order.
     * If the mass of the planet is greater than or equal to the mass of the asteroid, the
     * asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the
     * planet is destroyed.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise, return false.
     *
     * Example 1:
     *   Input: mass = 10, asteroids = [3,9,19,5,21]
     *   Output: true
     *
     * Explanation: One way to order the asteroids is [9,19,5,3,21]:
     * - The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
     * - The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
     * - The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
     * - The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
     * - The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
     * All asteroids are destroyed.
     *
     * Example 2:
     *   Input: mass = 5, asteroids = [4,9,23,4]
     *   Output: false
     *
     * Explanation:
     * The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
     * After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
     * This is less than 23, so a collision would not destroy the last asteroid.
     *
     * Constraints:
     *     1 <= mass <= 105
     *     1 <= asteroids.length <= 105
     *     1 <= asteroids[i] <= 105
     *
     * @param mass          - integer value representing the mass of the planet
     * @param asteroids     - integer array of the mass of asteroids
     * @return              - return true if all asteroids destroyed, else false.
     */

    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {

        // Sort the asteroids in ascending order
        Arrays.sort(asteroids);

        // Store the planet's current mass
        int currentMass = mass;

        // Try to destroy asteroids in ascending order
        for (int asteroid : asteroids) {
            // If our planet is large enough to destroy the asteroid
            if (currentMass >= asteroid) {
                // Planet destroys asteroid and gains its mass
                currentMass += asteroid;
            } else {
                // If not, the planet is destroyed by the asteroid
                return false;
            }
        }
        // If the planet survives all asteroids, return true
        return true;
    }


    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith person,
     * and an infinite number of rescue sleds where each sled can carry a maximum weight
     * of limit. Each sled carries at most two people at the same time, provided the
     * sum of the weight of those people is at most limit. Return the minimum number
     * of rescue sleds to carry every given person.
     *
     * Example 1:
     *    Input: people = [1,2], limit = 3
     *    Output: 1
     *    Explanation: 1 sled (1, 2)
     *
     * Example 2:
     *    Input: people = [3,2,2,1], limit = 3
     *    Output: 3
     *    Explanation: 3 sleds (1, 2), (2) and (3)
     *
     * Example 3:
     *    Input: people = [3,5,3,4], limit = 5
     *    Output: 4
     *    Explanation: 4 sleds (3), (3), (4), (5)
     *
     * @param people    - an array of weights for people that need to go in a sled
     * @param limit     - the weight limit per sled
     * @return          - the minimum number of rescue sleds required to hold all people
     */

    public static int numRescueSleds(int[] people, int limit) {

        // Pair the lightest and heaviest
        Arrays.sort(people);

        // Front passenger starts at the lightest person
        int frontPassenger = 0;

        // Back passenger starts at the heaviest person
        int backPassenger = people.length - 1;

        // Keep track of how many sleds needed
        int sledCount = 0;

        // Load passengers until everyone is rescued
        while (frontPassenger <= backPassenger) {
            // If the lightest and heaviest person left can share a sled
            if (people[frontPassenger] + people[backPassenger] <= limit) {
                // Move to the next lightest person
                frontPassenger++;
            }
            // Heaviest person boards a sled either way
            backPassenger--;

            // One more sled is used
            sledCount++;
        }
        return sledCount;
    }
} // End Class ProblemSolutions

