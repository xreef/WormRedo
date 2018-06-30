package it.reef.level;

import java.io.InputStream;

public class Level {
	public static InputStream getImmagineNonTrovata(int l) {
		return Level.class.getResourceAsStream(l+".txt");
	} 
}
