package com.server.model.loader.level;

import java.io.IOException;

public interface LevelSaver {

	void save(Level level, String path) throws IOException;
	
}
