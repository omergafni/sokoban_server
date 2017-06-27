package com.server.utils;

import com.server.model.loader.level.*;
import com.server.model.loader.worldObjects.WorldObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {

    private LevelLoader loader;
    private Level level = null;
    private FilePathUtil fp = new FilePathUtil();

    public ArrayList<char[]> fileToGrid(String filePath) {

        try {
            // Get the file's extension and instantiate with the proper loader
            String fileExtension = fp.getExtension(filePath);

            switch (fileExtension) {
                case "txt":
                    loader = new MyTextLevelLoader();
                    break;
                case "obj":
                    loader = new MyObjectLevelLoader();
                    break;
                case "xml":
                    loader = new MyXMLLevelLoader();
                    break;
                default:
                    throw new IOException("Unsupported file extension");
            }

            level = loader.loadLevel(new FileInputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (level != null)
            return getGridFromLevel(level);

        return null;
    }

    private ArrayList<char[]> getGridFromLevel(Level level) {

        ArrayList<ArrayList<WorldObject>> levelGrid = level.getGrid();

        ArrayList<char[]> grid = new ArrayList<>();

        for (ArrayList<WorldObject> row : levelGrid) {
            grid.add(rowToCharArray(row));
        }

        return grid;
    }

    private char[] rowToCharArray(ArrayList<WorldObject> row) {

        char[] newRow = new char[row.size()];
        int i = 0;

        for (WorldObject tile : row) {
            newRow[i] = tile.getObjRep();
            i++;
        }

        return newRow;
    }


}
