package edu.appstate.cs.LearnEmAllRightNow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardAndDeckTest
{
    /*
     * Tests that a card is created correctly.
     */
    @Test
    public void testCardCreation() 
    {
        Card card = new Card("Question", "Answer");
        assertEquals("Question", card.getFront(), "Front of card was not set correctly.");
        assertEquals("Answer", card.getBack(), "Back of card was not set correctly.");
    }

    /*
     * Tests that you can correctly change the front and back of a card.
     */
    @Test
    public void testCardChanges()
    {
        Card card = new Card("Question", "Answer");
        card.setFront("New Question");
        card.setBack("New Answer");
        assertEquals("New Question", card.getFront(), "Front of card should have been changed but was not.");
        assertEquals("New Answer", card.getBack(), "Back of card should have been changed but was not.");
    }

    /*
     * Tests that the Card.equals() method works correctly.
     */
    @Test
    public void testCardEqualsMethod()
    {
        Card card = new Card("Question", "Answer");
        Card sameCard = new Card("Question", "Answer");
        assertTrue(card.equals(sameCard), "2 cards were said to not be equal when they are.");
        assertEquals("Question : Answer", card.toString(), "Card toString method not formatted correctly.");
    }

    /*
     * Tests that a deck is created correctly.
     */
    @Test
    public void testDeckCreation() {
        // test deck creation
        Deck deck = new Deck("Test Deck");
        assertEquals("Test Deck", deck.getName(), "Name of deck not set correctly.");
        assertEquals(0, deck.getSize(), "Size of deck not initialized correctly.");
    }

    /*
     * Tests that a card is added correctly–
     * The size of the deck should increase when you add a card.
     * Duplicate cards should not be added.
     */
    @Test
    public void testCardAddition()
    {
        Deck deck = new Deck("Test Deck");
        Card card1 = new Card("Question 1", "Answer 1");
        Card card2 = new Card("Question 2", "Answer 2");
        
        deck.addCard(card1);
        assertEquals(1, deck.getSize(), "Size of deck should have been 1 after a card was added.");
        assertEquals(card1, deck.getCards()[0], "Card was not added correctly.");
        
        deck.addCard(card2);
        assertEquals(2, deck.getSize(), "Size of deck should have been 2 after a card was added.");
        assertEquals(card2, deck.getCards()[1], "Card was not added correctly.");

        // Test to make sure duplicate cards cannot be added
        deck.addCard(card1);
        assertEquals(2, deck.getSize(), "Size of deck should have stayed as 2 since the card attempted to be added was a duplicate.");
        assertEquals(null, deck.getCards()[2], "Duplicate cards should not be added.");
    }

    /*
     * Tests that a card can be deleted correctly–
     * The size of the deck should decrease when you delete a card.
     */
    @Test
    public void testCardDeletion()
    {
        Deck deck = new Deck("Test Deck");
        Card card1 = new Card("Question 1", "Answer 1");
        Card card2 = new Card("Question 2", "Answer 2");

        deck.addCard(card1);
        deck.addCard(card2);
        deck.removeCard(card1);

        assertEquals(1, deck.getSize(), "Size of deck should be 1 after removing a card.");
        assertEquals(card2, deck.getCards()[0], "Card was not correctly removed.");
    }

    /*
     * Tests that the string representation of a deck is formatted correctly. 
     */
    @Test
    public void testDecktoString()
    {
        Deck deck = new Deck("Test Deck");
        Card card1 = new Card("Question 1", "Answer 1");

        deck.addCard(card1);

        assertEquals("Deck Name: Test Deck\nCard 0:\nQuestion 1 : Answer 1\n", deck.toString(), "Deck toString method not formatted correctly.");
    }
}
