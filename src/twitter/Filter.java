/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
//import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> filteredTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equalsIgnoreCase(username)) {
                filteredTweets.add(tweet);
            }
        }
        return filteredTweets;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        // Filter tweets based on the timespan
        return tweets.stream()
            .filter(tweet -> !tweet.getTimestamp().isAfter(timespan.getEnd())) // Exclude tweets after end
            .filter(tweet -> !tweet.getTimestamp().isBefore(timespan.getStart())) // Include tweets after start
            .collect(Collectors.toList());
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> filteredTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            String tweetText = tweet.getText();
            for (String word : words) {
                if (tweetText.toLowerCase().contains(word.toLowerCase())) {
                    filteredTweets.add(tweet);
                    break; // No need to check other words for this tweet
                }
            }
        }
        return filteredTweets;
    }

    // IMPLEMENTATION 1
  /* public static List<Tweet> containingWithRegex(List<Tweet> tweets, List<String> words) {
        List<Tweet> filteredTweets = new ArrayList<>();
        // Create a regex pattern from the words
        String regex = String.join("|", words.stream().map(String::toLowerCase).collect(Collectors.toList()));
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        for (Tweet tweet : tweets) {
            if (pattern.matcher(tweet.getText()).find()) {
                filteredTweets.add(tweet);
            }
        }
        return filteredTweets;
    } 

   
   
   
   
   
    // IMPLEMENTATION 2
    public static List<Tweet> containingWithHashSet(List<Tweet> tweets, List<String> words) {
        List<Tweet> filteredTweets = new ArrayList<>();
        // Create a HashSet from the words for faster look-up
        HashSet<String> wordSet = new HashSet<>(words.stream().map(String::toLowerCase).collect(Collectors.toList()));

        for (Tweet tweet : tweets) {
            String tweetText = tweet.getText().toLowerCase();
            for (String word : wordSet) {
                if (tweetText.contains(word)) {
                    filteredTweets.add(tweet);
                    break; // No need to check other words for this tweet
                }
            }
        }
        return filteredTweets;
    } 

    // IMPLEMENTATION 3
    public static List<Tweet> containingWithStreams(List<Tweet> tweets, List<String> words) {
        List<Tweet> filteredTweets = new ArrayList<>();

        for (Tweet tweet : tweets) {
            boolean containsWord = words.stream()
                .anyMatch(word -> tweet.getText().toLowerCase().contains(word.toLowerCase()));

            if (containsWord) {
                filteredTweets.add(tweet);
            }
        }
        return filteredTweets;
    } */
}


























