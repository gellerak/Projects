package edu.appstate.cs.LearnEmAllRightNow.UI;

import java.awt.Font;

public class Config {
	
	static Font MAIN_FONT = null;

	public static void SetUp()
	{
		MAIN_FONT = new Font("Arial", Font.PLAIN, 20);
	}

	public static Font getMainFont()
	{
		return MAIN_FONT;
	}

	public static Font getMainFontSizeAltered(int size) 
	{
		return new Font(getMainFont().getFontName(), getMainFont().getStyle(), size);
	}

	public static void setMainFont(Font font)
	{
		MAIN_FONT = font;
	}
	
}