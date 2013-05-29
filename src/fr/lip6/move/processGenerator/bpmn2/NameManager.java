package fr.lip6.move.processGenerator.bpmn2;


public class NameManager {
	
	private static int count = 0;
	
	public static String getTaskName() {
		count++;
		return "task_" + count;
	}

	public static String getParallelName(String direction) {
		count++;
		return "parallel" + direction + "_" + count;
	}

	public static String getExclusiveName(String direction) {
		count++;
		return "exclusive" + direction + "_" + count;
	}

	public static String getSequenceName() {
		count++;
		return "sequence_" + count;
	}

	public static String getStartName() {
		count++;
		return "start_" + count;
	}

	public static String getEndName() {
		count++;
		return "end_" + count;
	}
}
