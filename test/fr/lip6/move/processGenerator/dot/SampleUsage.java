package fr.lip6.move.processGenerator.dot;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.dot.DotGraph;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;


public class SampleUsage {
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		DotGraph graph = new DotGraph(shell, SWT.NONE);
//		graph.add("2->3").add("2->4");
		graph.add("node[label=zested]; edge[style=dashed]; 3->5; 4->6");
		
		open(shell);
		graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(), false);
		System.out.println(graph.toDot());
	}
	
	private static void open(final Shell shell) {
		shell.setText(DotGraph.class.getSimpleName());
		shell.setLayout(new FillLayout());
		shell.setSize(600, 300);
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
}
