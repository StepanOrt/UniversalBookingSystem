package ubs.core;

import java.util.HashSet;
import java.util.Set;

public class ReservationCategoryNode {
	private String name;
	private Set<ReservationCategoryNode> subNodes;
	public ReservationCategoryNode(String name) {
		this.name = name;
		subNodes = new HashSet<ReservationCategoryNode>();
	}
	public String getName() {
		return name;
	}
	public Set<ReservationCategoryNode> getSubNodes() {
		return subNodes;
	}
	public void addSubNode(String name) {
		ReservationCategoryNode subNode = new ReservationCategoryNode(name);
		subNodes.add(subNode);
	}
	public void removeSubNode(ReservationCategoryNode subNode) {
		subNodes.remove(subNode);
	}
}
