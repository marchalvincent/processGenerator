package fr.lip6.move.processGenerator;

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
	private int nbGeneration;
	
	public Benchmarker() {
		super();
		numbers = new TreeMap<>();
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public void tic(String bestS, int nbGeneration) {
		long estimatedTimeMillis = (System.nanoTime() - startTime) / 1000000;
		numbers.put(estimatedTimeMillis, bestS);
		this.nbGeneration = nbGeneration;
	}
	
	public void stop(String string) {
		
		if (!Utils.BENCH)
			return;
		
		Path path = Paths.get(string);
		// append to an existing file, create file if it doesn't initially exist
		try {
			OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			
			Set<Long> keys = numbers.keySet();
			StringBuilder sb = new StringBuilder();
			for (Long key : keys) {
				if (numbers.get(key).equals("100")) {
					sb.append(key);
					sb.append(";");
					sb.append(nbGeneration);
//					sb.append(";");
//					sb.append(numbers.get(key));
					sb.append("\n");
				}
			}
//			sb.append("\n\n\n");
			out.write(sb.toString().getBytes());
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
