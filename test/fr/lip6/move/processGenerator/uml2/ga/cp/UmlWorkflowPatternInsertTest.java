package fr.lip6.move.processGenerator.uml2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlArbitraryCycle;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlSequence;


public class UmlWorkflowPatternInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	private StructuralConstraintChecker checker;
	
	@Test
	public void test0() throws Exception {
		IStructuralConstraint constraint = new UmlSequence();
		
		UmlProcess process = UmlBuilder.instance.initialABFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		List<StructuralConstraintChecker> list = new ArrayList<>();
		list.add(new StructuralConstraintChecker(constraint));
		
		UmlWorkflowInsert insert = new UmlWorkflowInsert();
		process = insert.apply(process, rng, list);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 4);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 5);
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 3);
		assertTrue(checker.check(process));
		
	}
	
	@Test
	public void test1() throws Exception {
		IStructuralConstraint constraint = new UmlArbitraryCycle();
		
		UmlProcess process = UmlBuilder.instance.initialABFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		List<StructuralConstraintChecker> list = new ArrayList<>();
		list.add(new StructuralConstraintChecker(constraint));
		
		UmlWorkflowInsert insert = new UmlWorkflowInsert();
		process = insert.apply(process, rng, list);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 3);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 7);
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
	}
}
