package com.udacity.maluleque.jokelib;


import org.junit.Assert;
import org.junit.Test;

public class JokeProviderTest {

    @Test
    public void getJokeRandomly() {
        JokeProvider jokeProvider = new JokeProvider();
        String jokeRandomly = jokeProvider.getJokeRandomly();

        Assert.assertFalse(jokeRandomly.isEmpty());
    }
}