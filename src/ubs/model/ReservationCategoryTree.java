package ubs.model;

public class ReservationCategoryTree {
	public ReservationCategoryNode rootNode;

	public ReservationCategoryTree(ReservationCategoryNode rootNode) {
		this.rootNode = rootNode;
	}

	public ReservationCategoryNode getRootNode() {
		return rootNode;
	}
	
}
