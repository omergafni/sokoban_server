package com.server.model.loader.level;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class MyObjectLevelLoader implements LevelLoader {
		
	@Override
	public Level loadLevel(InputStream input) throws IOException {
		try 
		{
			Level newLevel = (Level) new ObjectInputStream(input).readObject();
			return newLevel;
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		return null;
	}

}
