/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Testing Strategy for writtenBy():
     * - Valid cases: filter by a username that exists in the tweets.
     * - Case insensitivity: ensure filtering works regardless of case.
     * - Empty list: ensure it handles an empty list without errors.
     * - Non-existing username: check behavior when no tweets match.
     */

    /*
     * Testing Strategy for inTimespan():
     * - Valid cases: ensure correct tweets are returned within a specified timespan.
     * - Check behavior with an empty tweet list.
     * - Edge cases: ensure behavior when the timespan is exactly at the boundaries of tweet timestamps.
     */

    /*
     * Testing Strategy for containing():
     * - Valid cases: filter tweets that contain specified words.
     * - Case insensitivity: ensure that filtering is not case-sensitive.
     * - Empty list: check behavior with an empty tweet list.
     * - No matches: ensure it returns an empty list when no tweets match the specified words.
     */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
   /* private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2); */
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest taalk in 30 minutes #hype", d2);



    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // Test case for filtering tweets by a specific author
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    // Test case for filtering by a non-existing username
    @Test
    public void testWrittenByNoMatchingAuthor() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "nonexistent");

        assertTrue("expected empty list for nonexistent author", writtenBy.isEmpty());
    }

    // Test case for case insensitivity in author filtering
    @Test
    public void testWrittenByCaseInsensitivity() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "ALYSSA");

        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    // Test case for filtering tweets within a specified timespan
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    // Test case for filtering with an empty tweet list
    @Test
    public void testInTimespanEmptyList() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(), new Timespan(testStart, testEnd));

        assertTrue("expected empty list for no tweets", inTimespan.isEmpty());
    }

    // Test case for filtering tweets containing a specific word
 // Test case for filtering tweets containing a specific word
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweet1", containing.contains(tweet1));
        assertFalse("expected list not to contain tweet2", containing.contains(tweet2));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }


    
    // Test case for case insensitivity in tweet content filtering
    @Test
    public void testContainingCaseInsensitivity() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("RIVEST"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
    }

    // Test case for filtering with an empty tweet list
    @Test
    public void testContainingEmptyList() {
        List<Tweet> containing = Filter.containing(Arrays.asList(), Arrays.asList("talk"));

        assertTrue("expected empty list for no tweets", containing.isEmpty());
    }

    // Test case for filtering when no matches are found
    @Test
    public void testContainingNoMatches() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("nonexistent"));

        assertTrue("expected empty list when no matches are found", containing.isEmpty());
    }

    
    
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
