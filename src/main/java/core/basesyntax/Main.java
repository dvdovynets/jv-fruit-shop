package core.basesyntax;

import core.basesyntax.data.DataAnalyzer;
import core.basesyntax.data.DataParser;
import core.basesyntax.data.ReportCreator;
import core.basesyntax.data.impl.DataAnalyzerImpl;
import core.basesyntax.data.impl.DataParserImpl;
import core.basesyntax.data.impl.ReportCreatorImpl;
import core.basesyntax.dto.Dto;
import core.basesyntax.handlers.FruitsDecrement;
import core.basesyntax.handlers.FruitsIncrement;
import core.basesyntax.handlers.Operations;
import core.basesyntax.services.FileServiceReader;
import core.basesyntax.services.FileServiceReaderImpl;
import core.basesyntax.services.FileServiceWritter;
import core.basesyntax.services.FileServiceWritterImpl;
import core.basesyntax.services.FruitsService;
import core.basesyntax.services.InputFileCreator;
import core.basesyntax.services.InputFileCreatorImpl;
import core.basesyntax.storage.FruitDataBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/input.csv";
    private static final String REPORT_FILE_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        InputFileCreator inputFileCreator = new InputFileCreatorImpl();
        inputFileCreator.create(INPUT_FILE_PATH);
        FruitDataBase fruitDataBase = new FruitDataBase();
        fruitDataBase.setFruitShopData("apple", 0);
        fruitDataBase.setFruitShopData("banana", 0);

        Map<Operations, FruitsService> fruitStrategies = new HashMap<>();
        FruitsService fruitsIncreasing = new FruitsIncrement();
        FruitsService fruitsDecreasing = new FruitsDecrement();
        fruitStrategies.put(Operations.getEnum("b"), fruitsIncreasing);
        fruitStrategies.put(Operations.getEnum("r"), fruitsIncreasing);
        fruitStrategies.put(Operations.getEnum("s"), fruitsIncreasing);
        fruitStrategies.put(Operations.getEnum("p"), fruitsDecreasing);

        FileServiceReader fileServiceReader = new FileServiceReaderImpl();
        List<String> dataFromFile = fileServiceReader.read(INPUT_FILE_PATH);

        DataParser dataParser = new DataParserImpl();
        List<Dto> listWithFruits = dataParser.convert(dataFromFile);

        DataAnalyzer dataAnalyzer = new DataAnalyzerImpl();
        dataAnalyzer.analyze(listWithFruits, fruitStrategies, fruitDataBase);

        ReportCreator reportCreator = new ReportCreatorImpl();
        FileServiceWritter fileServiceWritter = new FileServiceWritterImpl();
        fileServiceWritter.write(reportCreator.generateReport(fruitDataBase), REPORT_FILE_PATH);
    }
}
