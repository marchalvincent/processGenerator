package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import fr.lip6.move.processGenerator.Filter;
import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;

/**
 * Cette classe permet de simplifier la manipulation des {@link UmlProcess}.
 * 
 * @author Vincent
 * 
 */
public class UmlChangePatternHelper {
	
	public static UmlChangePatternHelper instance = new UmlChangePatternHelper();
	
	private UmlChangePatternHelper() {}
	
	/**
	 * Renvoie un {@link ActivityEdge} au hasard contenu dans le process.
	 * 
	 * @param process
	 *            {@link UmlProcess}.
	 * @param rng
	 *            un source de random.
	 * @return {@link ActivityEdge}.
	 * @throws UmlException
	 *             si le process ne contient aucun arc.
	 */
	public ActivityEdge getRandomActivityEdge(UmlProcess process, Random rng) throws UmlException {
		List<ActivityEdge> list = process.getActivity().getEdges();
		
		// si la liste est vide (cela ne devrait jamasi arriver)
		if (list.isEmpty())
			throw new UmlException("The UmlProcess does not contains any ActivityEdge.");
		
		return list.get(rng.nextInt(list.size()));
	}
	
	/**
	 * Compte le nombre d'{@link Action} d'un process UML2.0.
	 * 
	 * @param process
	 *            {@link UmlProcess}.
	 * @return int.
	 */
	public int countAction(UmlProcess process) {
		return Filter.byType(Action.class, process.getActivity().getNodes()).size();
	}
	
	/**
	 * Compte le nombre de {@link ControlNode} d'un process uml2.0.
	 * 
	 * @param process
	 * @return
	 */
	public int countControlNode(UmlProcess process) {
		return Filter.byType(ControlNode.class, process.getActivity().getNodes()).size();
	}
	
	/**
	 * Compte le nombre d'arc présents dans le process.
	 * 
	 * @param process
	 * @return
	 */
	public int countEdges(UmlProcess process) {
		return process.getActivity().getNodes().size();
	}
	
	/**
	 * Compte le nombre de {@link DecisionNode} linké avec un autre noeud (typiquement un {@link MergeNode}).
	 * 
	 * @param process
	 * @return
	 */
	public int countConditionalLinked(UmlProcess process) {
		int count = 0;
		List<DecisionNode> list = UmlFilter.byType(DecisionNode.class, process.getActivity().getNodes());
		for (DecisionNode decisionNode : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, decisionNode) != null)
				count ++;
		}
		return count;
	}
	
	/**
	 * Compte le nombre de {@link ForkNode} linké avec un autre noeud (typiquement un {@link JoinNode}).
	 * 
	 * @param process
	 * @return
	 */
	public int countForkLinked(UmlProcess process) {
		int count = 0;
		List<ForkNode> list = UmlFilter.byType(ForkNode.class, process.getActivity().getNodes());
		for (ForkNode fork : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, fork) != null)
				count ++;
		}
		return count;
	}
	
	/**
	 * Renvoie un {@link DecisionNode} linké avec un autre noeuds au hasard parmis ceux du process passé en paramètre.
	 * 
	 * @param process
	 * @param rng
	 * @return
	 * @throws UmlException 
	 */
	public DecisionNode getRandomDecisionNodeLinked(UmlProcess process, Random rng) throws UmlException {
		List<DecisionNode> elus = new ArrayList<>();
		List<DecisionNode> list = UmlFilter.byType(DecisionNode.class, process.getActivity().getNodes());
		for (DecisionNode decisionNode : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, decisionNode) != null)
				elus.add(decisionNode);
		}
		if (elus.isEmpty())
			throw new UmlException("The process does not contains any DecisionNode linked.");
		return elus.get(rng.nextInt(elus.size()));
	}
	
	/**
	 * Renvoie un {@link ForkNode} linké avec un autre noeuds au hasard parmis ceux du process passé en paramètre.
	 * 
	 * @param process
	 * @param rng
	 * @return
	 * @throws UmlException 
	 */
	public ForkNode getRandomForkNodeLinked(UmlProcess process, Random rng) throws UmlException {
		List<ForkNode> elus = new ArrayList<>();
		List<ForkNode> list = UmlFilter.byType(ForkNode.class, process.getActivity().getNodes());
		for (ForkNode forkNode : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, forkNode) != null)
				elus.add(forkNode);
		}
		if (elus.isEmpty())
			throw new UmlException("The process does not contains any ForkNode linked.");
		return elus.get(rng.nextInt(elus.size()));
	}
	
	/**
	 * Renvoie une {@link Action} tirée au hasard dans le process.
	 * 
	 * @param process
	 * @param rng
	 * @return
	 * @throws UmlException
	 */
	public Action getRandomAction(UmlProcess process, Random rng) throws UmlException {
		List<Action> list = UmlFilter.byType(Action.class, process.getActivity().getNodes());
		if (list.isEmpty())
			throw new UmlException("The process does not contains any Action.");
		return list.get(rng.nextInt(list.size()));
	}
	
	/**
	 * Nettoie le process des éventuelles incohérences qui seraient apparues lors d'une suppression d'Action par exemple.
	 * 
	 * @param process
	 */
	public void cleanProcess(UmlProcess process) {
		this.cleanUselessFork(process);
		this.cleanUselessDecision(process);
	}
	
	/**
	 * Corrige les éventuelles incohérences du process concernant les noeuds fork/join.
	 * 
	 * @param process
	 */
	private void cleanUselessFork(UmlProcess process) {
		
		ControlNode join;
		
		// on récupère la liste des fork
		List<ForkNode> forks = UmlFilter.byType(ForkNode.class, process.getActivity().getNodes());
		
		// pour chaque fork
		for (ForkNode forkNode : forks) {
			
			// on cherche la twins
			join = ControlNodeManager.instance.findControlNodeTwin(process, forkNode);
			if (join == null) {
				// si on en a pas, il sagit peut être d'un nouveau thread
				this.cleanUselessThread(process, forkNode);
				continue;
			}
			
			// ici, on a le join. Si on a plusieurs chemin dans le fork/join
			if (forkNode.getOutgoings().size() > 1) {
				
				ActivityEdge toRemove = null;
				for (ActivityEdge edge : forkNode.getOutgoings()) {
					if (edge.getTarget() == join) {
						// on ajoute l'arc dans la liste de ceux a supprimer
						toRemove = edge;
						// ici on fait un break sinon on risque de supprimer trop d'arc et couper le process en deux...
						break;
					}
				}
				
				// puis on fait les removes nécessaires
				if (toRemove != null)
					process.removeActivityEdge(toRemove);
			} // fin de : Si on a plusieurs chemin dans le fork/join
			
			// et enfin on gère les cas où il n'y a qu'un seul chemin
			if (forkNode.getOutgoings().size() == 1) {
				
				if (join.getIncomings().size() != 1)
					System.err.println(this.getClass().getSimpleName()
							+ " : The joinNode have more than 1 incoming sequence flow.");
				
				// ici on peut faire la suppression du couple fork/join
				forkNode.getIncomings().get(0).setTarget(forkNode.getOutgoings().get(0).getTarget());
				process.removeActivityEdge(forkNode.getOutgoings().get(0));
				process.removeActivityNode(forkNode);
				
				join.getIncomings().get(0).setTarget(join.getOutgoings().get(0).getTarget());
				process.removeActivityEdge(join.getOutgoings().get(0));
				process.removeActivityNode(join);
			} // fin de : il n'y a qu'un seul chemin
		} // fin de : pour chaque fork
	}
	
	/**
	 * Corrige les éventuelles incohérences du process concernant les fork aboutissant aux nouveaux threads.
	 * 
	 * @param process
	 * @param forkNode
	 */
	private void cleanUselessThread(UmlProcess process, ForkNode fork) {
		
		// si le fork a plusieurs arcs sortants
		if (fork.getOutgoings().size() > 1) {
			
			/*
			 * on vérifie le cas où un des arcs arrive directement à la fin du process. C'est illogique, car le process se
			 * finira automatiquement. Il faut dont le supprimer.
			 */
			List<ActivityEdge> listToRemove = new ArrayList<>();
			for (ActivityEdge edge : fork.getOutgoings()) {
				if (edge.getTarget() instanceof FlowFinalNode || edge.getTarget() instanceof ActivityFinalNode)
					listToRemove.add(edge);
			}
			
			// on supprime tous les arcs et fin de process
			for (ActivityEdge edge : listToRemove) {
				process.removeActivityNode(edge.getTarget());
				process.removeActivityEdge(edge);
			}
		}
		
		// si le fork n'a qu'une seule sortie, on peut la supprimer
		if (fork.getOutgoings().size() == 1) {
			
			if (fork.getIncomings().size() != 1)
				System.err.println("Error, the fork has not only 1 incoming edge : " + fork.getIncomings().size());
			
			ActivityEdge edgeBefore = fork.getIncomings().get(0);
			ActivityEdge edgeAfter = fork.getOutgoings().get(0);
			
			edgeBefore.setTarget(edgeAfter.getTarget());
			process.removeActivityEdge(edgeAfter);
			process.removeActivityNode(fork);
		}
	}
	
	/**
	 * Supprime les couples Decision/Merge inutiles du process.
	 * 
	 * @param process
	 */
	private void cleanUselessDecision(UmlProcess process) {
		
		ControlNode merge;
		
		// on récupère la liste des Decision
		List<DecisionNode> decisions = UmlFilter.byType(DecisionNode.class, process.getActivity().getNodes());
		
		// pour chaque decision
		for (DecisionNode decision : decisions) {
			boolean removed = false;
			
			// on cherche le merge qui referme le chemin
			merge = ControlNodeManager.instance.findControlNodeTwin(process, decision);
			if (merge == null)
				continue;
			
			// on vérifie si on a plusieurs arc vide allant de la decision au merge
			if (decision.getOutgoings().size() > 1) {
				
				// cette liste contiendra les arc éventuels à supprimer s'il y en a trop
				List<ActivityEdge> listeToRemove = new ArrayList<>();
				for (ActivityEdge edge : decision.getOutgoings()) {
					if (edge.getTarget() == merge)
						listeToRemove.add(edge);
				}
				
				// ici si notre liste est de taille n >= 2, alors on peut supprimer n-1 arc.
				if (listeToRemove.size() >= 2) {
					for (int i = 0; i < (listeToRemove.size() - 1); i++) {
						process.removeActivityEdge(listeToRemove.get(i));
					}
				}
				
			} // fin de : si on a plusieurs arcs partant de la decision
			
			// si la decision n'a qu'une seule sortie, on peut simplifier
			if (decision.getOutgoings().size() == 1) {
				
				// petites vérifications
				if (merge.getIncomings().size() != 1) {
					System.err.println(this.getClass().getSimpleName()
							+ " : The join does not contains only 1 incoming edge. Number : "
							+ merge.getIncomings().size() + ", id : " + merge.getName());
					
					if (Utils.DEBUG)
						process.save(System.getProperty("user.home") + Utils.bugPathUml);
				}
				
				// ici on peut faire la suppression du couple decision/merge
				decision.getIncomings().get(0).setTarget(decision.getOutgoings().get(0).getTarget());
				process.removeActivityEdge(decision.getOutgoings().get(0));
				process.removeActivityNode(decision);
				
				
				merge.getIncomings().get(0).setTarget(merge.getOutgoings().get(0).getTarget());
				process.removeActivityEdge(merge.getOutgoings().get(0));
				process.removeActivityNode(merge);
				
				removed = true;
			} // fin de : si la decision n'a qu'une sortie
			
			// et on supprime les loop potentielles si on n'a encore rien supprimé
			if (!removed)
				this.cleanPotentialUselessLoop(process, decision, merge);
			
		} // fin de : pour chaque decision
	}
	
	/**
	 * 
	 * @param process
	 * @param decision
	 * @param merge
	 */
	private void cleanPotentialUselessLoop(UmlProcess process, DecisionNode decision, ControlNode merge) {
		
		List<ActivityEdge> listToRemove = new ArrayList<>();
		
		// on récupère les arcs vides allant du merge vers la decision
		for (ActivityEdge edge : merge.getOutgoings()) {
			if (edge.getTarget() == decision)
				listToRemove.add(edge);
		}
		
		// si on en a n >= 2, alors on peut en supprimer n - 1
		if (listToRemove.size() >= 2) {
			for (int i = 0; i < (listToRemove.size() - 1); i++) {
				process.removeActivityEdge(listToRemove.get(i));
			}
		}
		
		// ici, soit il nous reste 1 arc vide (merge->decision), soit on n'en a pas.
		if (!listToRemove.isEmpty()) {
			// on récupère le dernier arc vide (merge->decision)
			ActivityEdge mergeToDecisionEmpty = listToRemove.get(listToRemove.size() - 1);
			
			// et on cherche s'il n'y a pas d'arc vide (decision->merge)
			ActivityEdge decisionToMergeEmpty = null;
			for (ActivityEdge edge : decision.getOutgoings()) {
				if (edge.getTarget() == merge) {
					decisionToMergeEmpty = edge;
				}
			}
			
			// si on a trouvé un arc vide, alors on peut simplifier le tout
			if (decisionToMergeEmpty != null) {
				
				// dans un premier temps on va compter le nombre de branche qu'a cette boucle
				int nbBranches = merge.getOutgoings().size() + decision.getOutgoings().size() - 1;
				
				if (nbBranches == 1)
					System.err.println("Warning, the loop contains only " + nbBranches + " different path(s).");
				// si on a 2 branches, on peut tout enlever car elles sont vides
				else if (nbBranches == 2) {
					
					// on récupère les arcs avant et après la loop
					List<ActivityEdge> liste = merge.getIncomings();
					if (liste.size() != 2) {
						System.err.println("Warning, the merge does not contain 2 incoming edges. Number : "
								+ liste.size() + ", id : " + merge.getName());
					}
					ActivityEdge edgeBefore = null;
					for (ActivityEdge edge : liste) {
						if (edge != decisionToMergeEmpty)
							edgeBefore = edge;
					}
					
					liste = decision.getOutgoings();
					if (liste.size() != 2)
						System.err.println("Warning, the decision does not contain 2 outgoings edges. Number : "
								+ liste.size() + ", id : " + decision.getName());
					
					ActivityEdge edgeAfter = null;
					for (ActivityEdge edge : liste) {
						if (edge != decisionToMergeEmpty)
							edgeAfter = edge;
					}
					
					// on peut faire la suppression si on a trouvé les arcs
					if (edgeBefore != null && edgeAfter != null) {
						edgeBefore.setTarget(edgeAfter.getTarget());
						
						process.removeActivityEdge(decisionToMergeEmpty);
						process.removeActivityEdge(mergeToDecisionEmpty);
						process.removeActivityEdge(edgeAfter);
						process.removeActivityNode(merge);
						process.removeActivityNode(decision);
					} else {
						System.err.println("Warning, we can't remove the useless loop.");
					}
				} // fin de : si on a 2 branches
					// si on a trois branches on ne supprime qu'un arc
				else if (nbBranches == 3) {
					// attention a bien supprimer l'arc (decision->merge) car l'autre pourrait générer un deadlock
					process.removeActivityEdge(decisionToMergeEmpty);
				}
			} // fin de : si on a trouvé un arc vide
		}
	}
}
