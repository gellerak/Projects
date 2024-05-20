package edu.appstate.cs.LearnEmAllRightNow;

public class FillInTheBlankQuestion extends Question
{
    private Card card;
    private String front;
    private String type;

    public FillInTheBlankQuestion(Card card)
    {
        this.card = card;
        this.front = card.getFront();
        this.type = "FillInTheBlankQuestion";
    }

    public Card getCard()
    {
        return card;
    }

    public String toString()
    {
        return front;
    }
}
