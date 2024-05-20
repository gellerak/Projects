package edu.appstate.cs.LearnEmAllRightNow;

import java.util.ArrayList;
import java.util.Collections;

public class MatchQuizQuestion extends Question
{
    private ArrayList<Card> front;
    private ArrayList<String> back;
    private String type;

    /*
     * Constructor
     *
     */
    public MatchQuizQuestion(Card[] cards)
    {
        this.front = new ArrayList<Card>();
        this.back = new ArrayList<String>();
        this.type = "MatchQuizQuestion";

        for (int i = 0; i < cards.length; i++)
        {
            // System.out.println(cards[i]);
            if (cards[i] == null)
            {
                continue;
            }
            this.front.add(cards[i]);
            this.back.add(cards[i].getBack());
        }

        Collections.shuffle(this.front);
        Collections.shuffle(this.back);
    }

    public String getType()
    {
        return this.type;
    }

    public ArrayList<Card> getFront()
    {
        return this.front;
    }

    public ArrayList<String> getBack()
    {
        return this.back;
    }

    public String toString()
    {
      String s = "";
      for (int i = 0; i < front.size(); i++)
      {
        // System.out.printf("%s                %s\n", front.get(i), back.get(i));
        s += front.get(i).getFront() + "\t\t" + back.get(i) + "\n";
      }

      return s;
    }

}
