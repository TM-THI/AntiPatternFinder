package uml.XMI.write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import uml.UMLObject;

/**
 * Klasse stellt statische Fuktionen zum schreiben von XMI-Dateien (.uml) bereit.
 *
 */
public class WriteXMI {
	
	private WriteXMI() {
		// kein Konstruktor da nur statische Methoden bereitgestellt werden.
	}

	/**
	 * Funktion schreibt das {@link UMLObject} im XMI-Format and die Stelle path.
	 */
	public static void writeFile(String path, UMLObject diagramm) {
		
		var helper = new WriteXMIHelper();
		
		List<String> output = helper.getOutput(diagramm);
		
		try {
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		for(String line : output) {
			bw.write(line);
			bw.newLine();
		}
		bw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
