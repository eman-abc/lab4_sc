package twitter;

import static org.junit.Assert.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /**
     * Test Strategy:
     * 
     * Partitioning of the input space:
     * 1. Empty List: No tweets provided, expect an exception.
     * 2. Single Tweet: The timespan should start and end at the same time.
     * 3. Two Tweets:
     *      - If tweets have different timestamps, the timespan should reflect the interval between the earliest and latest timestamps.
     *      - If tweets have the same timestamps, the timespan should reflect that exact time.
     * 4. Multiple Tweets:
     *      - Check with unordered timestamps to ensure getTimespan() correctly identifies the earliest start time and latest end time.
     * 
     * Each test case is designed to test a specific partition of the input space, ensuring that the method meets the specification.
     */

//    test case 1: 2 tweets
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    /**
     * Test Case 2: Empty list of tweets.
     * 
     * Description:
     * This test case verifies that the getTimespan() method handles an empty list of tweets 
     * appropriately. Since there are no tweets to compute a timespan from, an 
     * IllegalArgumentException is expected.
     * 
     * Input:
     * - Empty list (i.e., no tweets).
     * 
     * Expected Output:
     * - An IllegalArgumentException should be thrown, since the method cannot compute a 
     * timespan from an empty list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetTimespanEmptyList() {
        Extract.getTimespan(Collections.emptyList());
    }
    
    /**
     * Test Case 3: Single tweet.
     * 
     * Description:
     * This test case verifies that the getTimespan() method works correctly when there 
     * is only one tweet. In this case, the start and end of the timespan should both 
     * be the timestamp of that single tweet.
     * 
     * Input:
     * - tweet1 (Timestamp: d1)
     * 
     * Expected Output:
     * - The start and end of the timespan should both be the timestamp of tweet1.
     */
    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));

        // Assert that the start and end times are equal to tweet1's timestamp
        assertEquals("Expected start time to be tweet1's timestamp", tweet1.getTimestamp(), timespan.getStart());
        assertEquals("Expected end time to be tweet1's timestamp", tweet1.getTimestamp(), timespan.getEnd());
    }

    /**
     * Test Case 4: Multiple tweets with identical timestamps.
     * 
     * Description:
     * This test case verifies that the getTimespan() method handles the scenario where 
     * multiple tweets have the exact same timestamp. In this case, the timespan should 
     * start and end at the same time, which is the timestamp of the tweets.
     * 
     * Input:
     * - tweet1 (Timestamp: 2023-09-26T10:15:30Z)
     * - tweet2 (Timestamp: 2023-09-26T10:15:30Z)
     * 
     * Expected Output:
     * - The start and end of the timespan should both be the identical timestamp.
     */
    @Test
    public void testGetTimespanIdenticalTimestamps() {
        Tweet tweet1 = new Tweet(1, "user1", "Tweet 1", Instant.parse("2023-09-26T10:15:30Z"));
        Tweet tweet2 = new Tweet(2, "user2", "Tweet 2", Instant.parse("2023-09-26T10:15:30Z"));
        
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

        // Assert that both start and end times are the identical timestamp of the tweets
        assertEquals("Expected start time to be the identical timestamp", tweet1.getTimestamp(), timespan.getStart());
        assertEquals("Expected end time to be the identical timestamp", tweet1.getTimestamp(), timespan.getEnd());
    }

    /**
     * Test Case 5: Multiple tweets with unordered timestamps.
     * 
     * Description:
     * This test case verifies that the getTimespan() method works correctly when 
     * the tweets are not in chronological order. The timespan should still 
     * be computed based on the earliest and latest timestamps, regardless of the input order.
     * 
     * Input:
     * - tweet1 (Timestamp: 2023-09-26T14:15:30Z)
     * - tweet2 (Timestamp: 2023-09-26T10:15:30Z)
     * - tweet3 (Timestamp: 2023-09-26T12:15:30Z)
     * 
     * Expected Output:
     * - The start of the timespan should be the earliest timestamp (tweet2).
     * - The end of the timespan should be the latest timestamp (tweet1).
     */
    @Test
    public void testGetTimespanUnorderedTweets() {
        Tweet tweet1 = new Tweet(1, "user1", "Tweet 1", Instant.parse("2023-09-26T14:15:30Z"));
        Tweet tweet2 = new Tweet(2, "user2", "Tweet 2", Instant.parse("2023-09-26T10:15:30Z"));
        Tweet tweet3 = new Tweet(3, "user3", "Tweet 3", Instant.parse("2023-09-26T12:15:30Z"));

        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));

        // Assert that the start time is the earliest tweet's timestamp
        assertEquals("Expected start time to be the earliest tweet", tweet2.getTimestamp(), timespan.getStart());

        // Assert that the end time is the latest tweet's timestamp
        assertEquals("Expected end time to be the latest tweet", tweet1.getTimestamp(), timespan.getEnd());
    }

    
    
    /**
     * Test Strategy for getMentionedUsers:
     * 
     * Partitioning of the input space:
     * 1. No Mentions: 
     * 2. Single Mention:
     * 3. Multiple Mentions:
     * 4. Case Insensitivity:
     * 5. Invalid Mentions:
     * 6. Mixed Mentions:
     * 7. Special Characters:
     * Each test case is designed to ensure comprehensive coverage of the method's functionality.
     */


 // Sample tweets for testing
    Tweet tweet11 = new Tweet(1, "userA", "Hello world!", Instant.now()); // No mentions
    Tweet tweet21 = new Tweet(2, "userB", "Hey @userC, how are you?", Instant.now()); // Single mention
    Tweet tweet3 = new Tweet(3, "userC", "Testing @UserC and @userD!", Instant.now()); // Multiple mentions, case insensitivity
    Tweet tweet4 = new Tweet(4, "userD", "Contact me at user@domain.com @userE.", Instant.now()); // Invalid mention (email)
    Tweet tweet5 = new Tweet(5, "userE", "No mentions here!", Instant.now()); // No mentions
    Tweet tweet6 = new Tweet(6, "userF", "What's up @user_123? @user2 and @user_123", Instant.now()); // Special characters and duplicates

    
    /**
     * Test case 1: No mentions present in the tweet.
     * Input: A tweet without any mentions.
     * Expectd Output: An empty set.
     */
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet11));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /**
     * Test case 2: Single valid mention in the tweet.
     * Input: A tweet with one valid mention.
     * Expected Output: A set containing that username (userC).
     */
    @Test
    public void testGetMentionedUsersSingleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet21));
        Set<String> expected = new HashSet<>(Arrays.asList("userC"));
        assertEquals("expected mentioned user to be userC", expected, mentionedUsers);
    }

    /**
     * Test case 3: Multiple valid mentions with case insensitivity.
     * Input: A tweet with multiple valid mentions in different cases.
     * Expected Output: A set containing all unique mentioned usernames (userC, userD).
     */
    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        Set<String> expected = new HashSet<>(Arrays.asList("userD", "UserC"));
        assertEquals("expected mentioned users to be userC and userD", expected, mentionedUsers);
    }

    /**
     * Test case 4: Invalid mentions included in the tweet.
     * Input: A tweet with an email address and a valid mention.
     * Expected Output: A set containing only the valid mentioned username (userE).
     */
    @Test
    public void testGetMentionedUsersInvalidMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        Set<String> expected = new HashSet<>(Arrays.asList("userE"));
        assertEquals("expected mentioned user to be userE", expected, mentionedUsers);
    }

    /**
     * Test case 5: No mentions present in the tweet.
     * Input: A tweet without any mentions.
     * Expected Output: An empty set.
     */
    @Test
    public void testGetMentionedUsersNoMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /**
     * Test case 6: Special characters and duplicate mentions in the tweet.
     * Input: A tweet with a special character in the username and duplicates.
     * Expected Output: A set containing the unique mentioned usernames (user_123, user2).
     */
    @Test
    public void testGetMentionedUsersSpecialCharacters() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        Set<String> expected = new HashSet<>(Arrays.asList("user_123", "user2"));
        assertEquals("expected mentioned users to be user_123 and user2", expected, mentionedUsers);
    }

    /**
     * Test case 7: Mixed valid and invalid mentions.
     * Input: A set of tweets with a mix of valid and invalid mentions.
     * Expected Output: A set containing only valid usernames (userC, userD, userE).
     */

    @Test
    public void testGetMentionedUsersMixedMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet21, tweet4));
        Set<String> expected = new HashSet<>(Arrays.asList("userC","userE")); // Only valid mention from tweet21
        assertEquals("Expected mentioned user to be userC", expected, mentionedUsers);
    }


    
    
//    @Test
//    public void testGetMentionedUsersNoMention() {
//        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
//        
//        assertTrue("expected empty set", mentionedUsers.isEmpty());
//    }

    /*
     * LAB5 CODE
     *  public void setUp() {
        tweetNoMention = new Tweet(1, "userA", "Hello world!", Instant.now());
        tweetSingleMention = new Tweet(2, "userB", "Hey @userC, how are you?", Instant.now());
        tweetMultipleMention = new Tweet(3, "userC", "Testing @UserC and @userD!", Instant.now());
        tweetInvalidMention = new Tweet(4, "userD", "Contact me at user@domain.com @userE.", Instant.now());
        tweetSpecialCharacters = new Tweet(6, "userF", "What's up @user_123? @user2 and @user_123", Instant.now());
        tweetEmpty = new Tweet(5, "userE", "No mentions here!", Instant.now());
    }

  private Tweet tweetSpecialCharacters;
   private Tweet tweetInvalidMention;

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetMultipleMention));
        Set<String> expected = new HashSet<>(Arrays.asList("userC", "userD"));
        assertEquals("expected mentioned users to be userC and userD", expected, mentionedUsers);
    }

     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */
    
 

}
