package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that models a mathematical set of integers.
 * <p>
 * This set does not allow duplicate elements and supports
 * standard set operations such as union, intersection,
 * difference, and complement.
 */
public class IntegerSet {
    /**
     * Internal list used to store the unique elements of the set.
     */
    private List<Integer> set = new ArrayList<Integer>();

    /**
     * Clears the internal representation of the set,
     * removing all elements.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the size of the set
     */
    public int length() {
        return set.size();
    }

    /**
     * Compares this set with another object for equality.
     * Two IntegerSet objects are considered equal if they contain
     * exactly the same elements, regardless of order.
     *
     * @param o the object to compare with
     * @return true if the two sets contain the same elements in any order,
     *         false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;  // same object reference
        }
        if (o == null || !(o instanceof IntegerSet)) {
            return false;
        }
        IntegerSet other = (IntegerSet) o;

        // Sets are equal if they are the same size and
        // each contains all elements of the other.
        return this.set.size() == other.set.size()
                && this.set.containsAll(other.set)
                && other.set.containsAll(this.set);
    }

    /**
     * Returns true if the set contains the specified value.
     *
     * @param value the integer value to look for
     * @return true if the value is present, false otherwise
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest item in the set.
     *
     * @return the maximum integer in the set
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot get largest element from an empty set.");
        }

        int max = set.get(0);
        for (int value : set) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * Returns the smallest item in the set.
     *
     * @return the minimum integer in the set
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot get smallest element from an empty set.");
        }

        int min = set.get(0);
        for (int value : set) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    /**
     * Adds an item to the set if it is not already present.
     * If the element already exists, this method does nothing.
     *
     * @param item the integer to add
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an item from the set if it is present.
     * If the element does not exist, this method does nothing.
     *
     * @param item the integer to remove
     */
    public void remove(int item) {
        // Use Integer.valueOf to remove by value instead of index.
        set.remove(Integer.valueOf(item));
    }

    /**
     * Performs the set union operation.
     * Modifies this set to contain all unique elements that are in
     * either this set or the other set.
     *
     * @param other the other IntegerSet to union with
     */
    public void union(IntegerSet other) {
        for (int value : other.set) {
            if (!this.set.contains(value)) {
                this.set.add(value);
            }
        }
    }

    /**
     * Performs the set intersection operation.
     * Modifies this set to retain only the elements that are present
     * in both this set and the other set.
     *
     * @param other the other IntegerSet to intersect with
     */
    public void intersect(IntegerSet other) {
        // keep only common elements
        set.retainAll(other.set);
    }

    /**
     * Performs the set difference (this \ other).
     * Modifies this set by removing all elements that are also found
     * in the other set.
     *
     * @param other the other IntegerSet whose elements will be removed
     *              from this set
     */
    public void diff(IntegerSet other) {
        set.removeAll(other.set);
    }

    /**
     * Performs the set complement operation.
     * Modifies this set to become (other \ this), that is, all elements
     * that are in the other set but not in this set.
     *
     * @param other the other IntegerSet used to compute the complement
     */
    public void complement(IntegerSet other) {
        List<Integer> result = new ArrayList<Integer>();
        for (int value : other.set) {
            if (!this.set.contains(value)) {
                result.add(value);
            }
        }
        this.set = result;
    }

    /**
     * Returns true if the set is empty (contains no elements).
     *
     * @return true if the set has size 0, false otherwise
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a string representation of the set, with elements enclosed
     * in square brackets and separated by commas, e.g. [1, 2, 3].
     *
     * @return a string representation of this set
     */
    @Override
    public String toString() {
        return set.toString();
    }
}
