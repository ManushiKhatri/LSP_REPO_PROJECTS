package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test cases for the IntegerSet class.
 * Each public method of IntegerSet has at least one test.
 */
public class IntegerSetTest {

    @Test
    public void testClearAndIsEmpty() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        assertFalse(set.isEmpty());

        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    @Test
    public void testLength() {
        IntegerSet set = new IntegerSet();
        assertEquals(0, set.length());

        set.add(10);
        set.add(20);
        set.add(30);
        assertEquals(3, set.length());

        // Duplicate should not change length
        set.add(20);
        assertEquals(3, set.length());
    }

    @Test
    public void testEqualsSameElementsDifferentOrder() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);
        set1.add(3);

        set2.add(3);
        set2.add(1);
        set2.add(2);

        assertTrue(set1.equals(set2));
        assertTrue(set2.equals(set1));
    }

    @Test
    public void testEqualsDifferentSets() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set2.add(1);
        set2.add(3);

        assertFalse(set1.equals(set2));
        assertFalse(set2.equals(set1));
    }

    @Test
    public void testEqualsWithNonIntegerSet() {
        IntegerSet set = new IntegerSet();
        set.add(1);

        String notASet = "not a set";
        assertFalse(set.equals(notASet));
        assertFalse(set.equals(null));
    }

    @Test
    public void testContains() {
        IntegerSet set = new IntegerSet();
        set.add(5);

        assertTrue(set.contains(5));
        assertFalse(set.contains(10));
    }

    @Test
    public void testLargestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(1);
        set.add(10);
        set.add(7);

        assertEquals(10, set.largest());
    }

    @Test
    public void testLargestThrowsOnEmptySet() {
        IntegerSet emptySet = new IntegerSet();
        assertThrows(IllegalStateException.class, emptySet::largest);
    }

    @Test
    public void testSmallestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(-1);
        set.add(10);
        set.add(7);

        assertEquals(-1, set.smallest());
    }

    @Test
    public void testSmallestThrowsOnEmptySet() {
        IntegerSet emptySet = new IntegerSet();
        assertThrows(IllegalStateException.class, emptySet::smallest);
    }

    @Test
    public void testAddNoDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(2); // duplicate
        set.add(3);

        assertEquals(3, set.length());
        assertTrue(set.contains(2));
    }

    @Test
    public void testRemoveElementPresent() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);

        set.remove(2);
        assertFalse(set.contains(2));
        assertEquals(2, set.length());
    }

    @Test
    public void testRemoveElementNotPresent() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        set.remove(3);  // not in set, should do nothing
        assertEquals(2, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
    }

    @Test
    public void testUnionWithOverlap() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set2.add(2);
        set2.add(3);

        set1.union(set2);  // modifies set1

        assertEquals(3, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
    }

    @Test
    public void testUnionWithEmptySet() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set1.union(set2);  // union with empty should not change
        assertEquals(2, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
    }

    @Test
    public void testIntersectNormalCase() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);
        set1.add(3);

        set2.add(2);
        set2.add(3);
        set2.add(4);

        set1.intersect(set2);  // modifies set1

        assertEquals(2, set1.length());
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(4));
    }

    @Test
    public void testIntersectWithDisjointSets() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set2.add(3);
        set2.add(4);

        set1.intersect(set2);  // should become empty
        assertTrue(set1.isEmpty());
    }

    @Test
    public void testDiffNormalCase() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);
        set1.add(3);

        set2.add(2);
        set2.add(4);

        set1.diff(set2);  // set1 \ set2

        assertEquals(2, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(2));
    }

    @Test
    public void testDiffWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        set.diff(set);  // set \ set = empty
        assertTrue(set.isEmpty());
    }

    @Test
    public void testComplementNormalCase() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set2.add(1);
        set2.add(2);
        set2.add(3);
        set2.add(4);

        set1.complement(set2);  // becomes (set2 \ set1) = {3,4}

        assertEquals(2, set1.length());
        assertTrue(set1.contains(3));
        assertTrue(set1.contains(4));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(2));
    }

    @Test
    public void testComplementWhenOtherHasNoExtraElements() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set2.add(1);
        set2.add(2);

        set1.complement(set2);  // (set2 \ set1) = empty
        assertTrue(set1.isEmpty());
    }

    @Test
    public void testToStringFormat() {
        IntegerSet set = new IntegerSet();
        assertEquals("[]", set.toString());

        set.add(1);
        set.add(2);
        set.add(3);

        // Order is based on insertion, so this should hold
        assertEquals("[1, 2, 3]", set.toString());
    }
}
