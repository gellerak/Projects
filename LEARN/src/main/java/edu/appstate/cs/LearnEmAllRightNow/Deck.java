package edu.appstate.cs.LearnEmAllRightNow;

import java.io.Serializable;

// Class that stores the deck name, deck size, and all of the cards in the deck.
public class Deck implements Serializable
{

  private Card[] cards;
  private String name;
  private int size;

  // 1-arg constructor that sets
  public Deck(String name)
  {
    this.name = name;
    this.cards = new Card[256];
    this.size = 0;
   }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return this.name;
  }

  // Adds a singulare card to the end of the array.
  public void addCard(Card card)
  {
    // check if the card is already in the deck
    // TODO: Think about how to handle duplicates
    // better than this.
    for (int i = 0; i < this.size; i++)
    {
      if (this.cards[i].equals(card))
      {
        return;
      }
    }

    this.cards[getSize()] = card;
    this.size++;
    System.out.print("Added Card: " + card + " | " + size + "\n");
  }

  // Accessor that returns a reference to the array of cards.
  public Card[] getCards()
  {
    return this.cards;
  }

  // searches for and removes a matching card the card array.
  public void removeCard(Card card)
  {
    int gap = 0;
    // remove card from Card array
    for (int i = 0; i < this.size; i++)
    {
      if (this.cards[i].equals(card))
      {
        this.cards[i] = null;
        this.size--;
        gap = i;
        break;
      }
    }

    // fill gap created from removing a card
    for (int i = gap; i < this.size; i++)
    {
        this.cards[i] = this.cards[i + 1];
    }
  }

  // returns size of the array. 
  public int getSize()
  {
    return this.size;
  }
  public void setSize(int newSize)
  { 
    this.size = newSize;
  }

  public String toString()
  {
    String deckString = "Deck Name: " + this.name + "\n";
    for (int i = 0; i < this.size; i++)
    {
      deckString += "Card " + i + ":\n";
      deckString += this.cards[i].toString() + "\n";
    }
    return deckString;
  }
}
