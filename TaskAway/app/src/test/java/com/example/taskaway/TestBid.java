package com.example.taskaway;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Tests all methods contained in the Bid class
 */

public class TestBid extends ActivityInstrumentationTestCase2 {
    public TestBid() {super(Bid.class);}

    /**
     * Test the constructor
     */
    @Test
    public void testBid(){
        Bid bid = new Bid(new Float(3.14));
        assertEquals(bid.getAmount(), 3.14);
    }

    /**
     * Test getAmount()
     */
    @Test
    public void testGetAmount(){
        Bid bid = new Bid(new Float(1.23));
        assertEquals(bid.getAmount(), 1.23);
    }

    /**
     * Test setAmount()
     */
    @Test
    public void testSetAmount(){
        Bid bid = new Bid( null);
        bid.setAmount(new Float(45.67));
        assertEquals(bid.getAmount(), 45.67);
    }

}
