package fr.lip6.move.processGenerator.bpmn2;

/**
 * Cette classe s'assure que chaque identifiant attribué aux éléments d'un process BPMN sera différent.
 * 
 * @author Vincent
 * 
 */
public class BpmnNameManager {
	
	public static BpmnNameManager instance = new BpmnNameManager();
	
	private BpmnNameManager() {}
	
	private int process = 0;
	private int count = 0;
	
	public String getProcessName() {
		process++;
		return "process_" + process;
	}
	
	public String getTaskName() {
		count++;
		return "task_" + count;
	}
	
	public String getParallelName(String direction) {
		count++;
		return "parallel" + direction + "_" + count;
	}
	
	public String getExclusiveName(String direction) {
		count++;
		return "exclusive" + direction + "_" + count;
	}
	
	public String getInclusiveName(String direction) {
		count++;
		return "inclusive" + direction + "_" + count;
	}
	
	public String getSequenceName() {
		count++;
		return "sequence_" + count;
	}
	
	public String getStartName() {
		count++;
		return "start_" + count;
	}
	
	public String getEndName() {
		count++;
		return "end_" + count;
	}
}
