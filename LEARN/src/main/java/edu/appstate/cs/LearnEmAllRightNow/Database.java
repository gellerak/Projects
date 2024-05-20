package edu.appstate.cs.LearnEmAllRightNow;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class Database {

    final static String DECKS_PATH = "./LocalStorage/Decks";

    public static void saveDeckToFile(Deck deck, String filename) {
        File directory = new File(DECKS_PATH);

        if (!directory.exists())
            directory.mkdirs();

        String filePath = DECKS_PATH + File.separator + filename;

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(deck);
            System.out.println("Serialized data is saved in " + filePath);

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Deck loadDeckFromFile(String filename) {
        Deck deck = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deck = (Deck) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return deck;
    }

    public static String getDecksPath() {
        return DECKS_PATH;
    }

    public static List<Deck> loadAllDecks() {
        File folder = new File(DECKS_PATH);
        File[] listOfFiles = folder.listFiles();
        List<Deck> decks = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    Deck deck = loadDeckFromFile(file.getAbsolutePath());
                    if (deck != null) {
                        decks.add(deck);
                    }
                }
            }
        }

        return decks;
    }

    public static void deleteDeck(String filename) {
        String filePath = DECKS_PATH + File.separator + filename;
        File fileToDelete = new File(filePath);
        
        if (fileToDelete.exists()) 
        {
            if (fileToDelete.delete()) 
            {
                System.out.println("Deck " + filename + " deleted successfully.");
            } 
            else 
            {
                System.out.println("Failed to delete deck " + filename + ".");
            }
        } 
        else 
        {
            System.out.println("Deck " + filename + " does not exist.");
        }
    }
}

