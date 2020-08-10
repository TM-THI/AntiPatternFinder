package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import uml.UMLObject;
import uml.XMI.read.ReadXMI;
import uml.XMI.write.WriteXMI;
import uml.sequenceDiagramm.SearchSequenceDiagramm;
/**
 * Main
 */
public class Main {
	
	/**
	 * Main
	 * 
	 * @param args Sequencediagramm, Anti-Pattern-Folder, Result-Folder
	 */
	public static void main(String[] args) {
		
		if(args.length != 3) {
			throw new RuntimeException("Three Arguments Required. Sequencediagramm, Anti-Pattern Folder, Result Folder");
		}
		
		var sequencePath = args[0];
		var antiPatternPath = args[1];
		var resultPath = args[2];
		
		var sequenceDiagramm = ReadXMI.readFile(sequencePath);
		var patterns = readAntiPatterns(antiPatternPath);
		
		generateFolderIfNotExists(resultPath);
		
		// Parallel für alle Anti-Pattern in dem Diagramm suchen und Ergebnisse speichern
		patterns.entrySet().parallelStream().forEach(e -> searchAndStoreResults(sequenceDiagramm.get(0), e.getValue(), e.getKey(), resultPath));
		

	}
	
	/**
	 * Erstellt einen Ordner falls dieser noch nicht existiert
	 * 
	 * @param path
	 */
	private static void generateFolderIfNotExists(String path) {
		var file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * Sucht Anti-Pattern in einem Diagramm und speichert die Ergebnisse unter dem resultPath
	 * 
	 * @param diagramm
	 * @param pattern
	 * @param patternName
	 * @param resultPath
	 */
	private static void searchAndStoreResults(UMLObject diagramm, UMLObject pattern, String patternName, String resultPath) {
		System.out.println("START: " + patternName);
		var resList = SearchSequenceDiagramm.searchPattern(diagramm, pattern);
		int number = 1;
		for(var result : resList) {
			var fileName = patternName + number + ".uml";
			var findingPath = Path.of(resultPath, fileName);
			WriteXMI.writeFile(findingPath.toString(), result);
		}
		System.out.println("END: "+ patternName + " FINDINGS: " + resList.size());
	}
	
	/**
	 * Liest alle Anti-Pattern unter dem antiPatternPath ein
	 * 
	 * @param antiPatternPath
	 * @return Sammlung von Namen und dem zugehörigen Anti-Pattern
	 */
	private static Map<String, UMLObject> readAntiPatterns(String antiPatternPath){
		Map<String, UMLObject> result = new HashMap<>();
		
		List<Path> files = new ArrayList<>();
		try{
			Stream<Path> paths = Files.walk(Paths.get(antiPatternPath));
		    paths.filter(Files::isRegularFile).forEach(f -> files.add(f));
		    paths.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		for(var p : files) {
			var fileName = p.getFileName();
			var object = ReadXMI.readFile(p.toString());
			result.put(fileName.toString(), object.get(0));
		}
		
		return result;
	}

}
