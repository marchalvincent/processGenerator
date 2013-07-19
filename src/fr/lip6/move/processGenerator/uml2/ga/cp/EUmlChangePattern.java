package fr.lip6.move.processGenerator.uml2.ga.cp;

import fr.lip6.move.processGenerator.IHierarchicalEnum;
import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.ga.IEnumChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette énumération représente les change patterns que peut sélectionner l'utilisateur sur l'interface graphique.
 * Chaque change pattern est associé à un {@link IChangePattern}.
 * 
 * @author Vincent
 * 
 */
public enum EUmlChangePattern implements IEnumChangePattern<UmlProcess> {
	
	// TODO workflow insert
	
	CONDITIONAL_INSERT(null, UmlConditionalInsertRandom.class),
	CONDITIONAL_ON_EDGE(CONDITIONAL_INSERT, UmlConditionalInsertEdge.class),
	CONDITIONAL_ON_DECISION_MERGE(CONDITIONAL_INSERT, UmlConditionalInsertDecisionMerge.class),
	
	PARALLEL_INSERT(null, UmlParallelInsertRandom.class),
	PARALLEL_ON_ACTION(PARALLEL_INSERT, UmlParallelInsertAction.class),
	PARALLEL_ON_FORK_JOIN(PARALLEL_INSERT, UmlParallelInsertForkJoin.class),
	
	SERIAL_INSERT(null, UmlSerialInsert.class),
	// TODO remove
	LOOP_INSERT(null, UmlLoopInsert.class),
	
	THREAD_INSERT(null, UmlThreadInsertRandom.class),
	THREAD_IMPLICITE_INSERT(THREAD_INSERT, UmlThreadInsertImplicit.class),
	THREAD_EXPLICITE_INSERT(THREAD_INSERT, UmlThreadInsertExplicit.class);;
	
	private Class<? extends IChangePattern<UmlProcess>> clazz;
	private IHierarchicalEnum parent;
	
	private EUmlChangePattern(IHierarchicalEnum parent, Class<? extends IChangePattern<UmlProcess>> clazz) {
		this.clazz = clazz;
		this.parent = parent;
	}
	
	@Override
	public IChangePattern<UmlProcess> newInstance(String proba) throws Exception {
		IChangePattern<UmlProcess> cp = this.clazz.newInstance();
		cp.setProba(proba);
		return cp;
	}
	
	@Override
	public IHierarchicalEnum getParent() {
		return parent;
	}
}
