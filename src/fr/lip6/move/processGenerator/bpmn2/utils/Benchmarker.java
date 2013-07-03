package fr.lip6.move.processGenerator.bpmn2.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Benchmarker {

	private long startTime;
	private Map<Long, String> numbers;

	public Benchmarker() {
		super();
		numbers = new TreeMap<>();  
	}

	public void start() {
		startTime = System.nanoTime();
	}

	public void tic(String bestS) {
		long estimatedTimeMillis = (System.nanoTime() - startTime) / 1000000;
		numbers.put(estimatedTimeMillis, bestS);
	}

	public void stop(String string) {
		
		Path path = Paths.get(string);
		// append to an existing file, create file if it doesn't initially exist
		try {
			OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			
			Set<Long> keys = numbers.keySet();
			StringBuilder sb = new StringBuilder();
			for (Long key : keys) {
				sb.append(key);
				sb.append(";");
				sb.append(numbers.get(key));
				sb.append("\n");
			}
			sb.append("\n\n\n");
			out.write(sb.toString().getBytes());
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
