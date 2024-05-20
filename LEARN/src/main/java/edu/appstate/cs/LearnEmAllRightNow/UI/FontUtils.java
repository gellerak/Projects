package edu.appstate.cs.LearnEmAllRightNow.UI;

import java.awt.*;
import javax.swing.*;

public class FontUtils {

    public static void updateComponentTreeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container)
            for (Component child : ((Container) component).getComponents())
                updateComponentTreeFont(child, font);
    }

    public static void applyFontToAll(JFrame frame, Font font) {
        updateComponentTreeFont(frame.getContentPane(), font);
        SwingUtilities.updateComponentTreeUI(frame.getContentPane());
    }
}

