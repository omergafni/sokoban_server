package com.server.model.loader.level;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MyObjectLevelSaver implements LevelSaver {

	@Override
	public void save(Level level, String path) throws IOException {
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
		out.writeObject(level);
		out.close();

	}
}
