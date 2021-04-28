package core.basesyntax.services.impl;

import core.basesyntax.services.FileServiceReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileServiceReaderImpl implements FileServiceReader {
    @Override
    public List<String> read(String fromFile) {
        List<String> dataFromFile = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFile))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                dataFromFile.add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File is missing" + fromFile, e);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file" + fromFile, e);
        }
        return dataFromFile;
    }
}