package edu.appstate.cs.LearnEmAllRightNow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuizTest 
{
    /*
     * Tests that a quiz is created correctly.
     */
    @Test
    public void testQuizCreation() 
    {
        Card card1 = new Card("Question 1", "Answer 1");
        Card card2 = new Card("Question 2", "Answer 2");
        Card card3 = new Card("Question 3", "Answer 3"); 
        Card card4 = new Card("Question 4", "Answer 4");
        Card card5 = new Card("Question 5", "Answer 5");
        Card card6 = new Card("Question 6", "Answer 6");
        Deck deck = new Deck("Test Deck");

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(card6);
        
        
        Quiz quiz = new Quiz(deck);
        
        assertNotNull(quiz.getQuestions(), "A quiz should have questions.");
        assertTrue(quiz.getQuestions().length > 0, "A quiz should have questions.");
    }
}
