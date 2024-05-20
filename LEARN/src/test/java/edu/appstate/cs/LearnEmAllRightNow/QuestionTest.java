package edu.appstate.cs.LearnEmAllRightNow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class QuestionTest 
{
    /*
     * Tests the Fill-in-the-Blank questions–
     * These questions should only present the front of the card.
     */
    @Test
    public void testFIBQuestions() 
    {
        Card card = new Card("Question", "Answer");
        FillInTheBlankQuestion question = new FillInTheBlankQuestion(card);

        assertEquals("Question", question.toString(), "FillInTheBlank toString() should return the front of the card, but did not.");
    }

    /*
     * Tests the Matching questions–
     * The questions/answer choices should be randomized.
     */
    @Test
    public void testMatchQuestions()
    {
        Card[] cards = new Card[3]; 
        cards[0] = new Card("Question 1", "Answer 1");
        cards[1] = new Card("Question 2", "Answer 2");
        cards[2] = new Card("Question 3", "Answer 3");
    

        MatchQuizQuestion question = new MatchQuizQuestion(cards);

        String[] fronts = new String[3];
        fronts[0] = "Question 1";
        fronts[1] = "Question 2";
        fronts[2] = "Question 3";
        String[] backs = new String[3];
        backs[0] = "Answer 1";
        backs[1] = "Answer 2";
        backs[2] = "Answer 3";

        String actual = question.toString();

        for (int i = 0; i < cards.length; i++) 
        {
            assertTrue(actual.contains(fronts[i]));
            assertTrue(actual.contains(backs[i]));
        }
    }

    /*
     * Tests Multiple Choice questions–
     * Makes sure that the questions are randomized. 
     */
    @Test
    public void testMultipleChoiceQuestions()
    {
        Card questionCard = new Card("Correct Question", "Correct Answer");
        Card choice1 = new Card("Question Choice 1", "Answer Choice 1");
        Card choice2 = new Card("Question Choice 2", "Answer Choice 2");
        Card choice3 = new Card("Question Choice 3", "Answer Choice 3");

        ArrayList<Card> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(questionCard);

        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(questionCard, choices);

        String firstTry = mcq.toString();

        assertFalse(firstTry.toString().equals("Correct Question\na. Answer Choice 1\nb. Answer Choice 2\nc. Answer Choice 3\nd. Correct Answer"), "Multiple choice questions should be randomized.");

        choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(questionCard);

        mcq = new MultipleChoiceQuestion(questionCard, choices);

        String secondTry = mcq.toString();

        assertFalse(secondTry.toString().equals("Correct Question\na. Answer Choice 1\nb. Answer Choice 2\nc. Answer Choice 3\nd. Correct Answer"), "Multiple choice questions should be randomized.");
    }
}
