package A6_Dijkstra;

import java.util.HashMap;

public class Node {
	String label;
	int inNode;
	int outNode;
	HashMap<String, Edge> inEdgeMap;
	HashMap<String, Edge> outEdgeMap;
	long id;
	// dist is the total weight from the source node to this node
	long dist;
	boolean exist;

	public Node(String l, long id) {
		this.label = l;
		this.inNode = 0;
		this.outNode = 0;
		this.id = id;
		this.inEdgeMap = new HashMap<>();
		this.outEdgeMap = new HashMap<>();
		this.exist = false;
		this.dist = Integer.MAX_VALUE;
	}

	public void addInEdge(Edge edge) {
		this.inNode++;
		this.inEdgeMap.put(edge.getCurrent().getLabel(), edge);
	}

	public void addOutEdge(Edge edge) {
		this.outNode++;
		this.outEdgeMap.put(edge.getDestination().getLabel(), edge);
	}

	public String getLabel() {
		return this.label;
	}

	public int getInNode() {
		return this.inNode;

	}

	public int getOutNode() {
		return this.outNode;
	}

	public long getId() {
		return this.id;
	}

	public HashMap<String, Edge> getInList() {
		return this.inEdgeMap;
	}

	public HashMap<String, Edge> getOutList() {
		return this.outEdgeMap;
	}

	@Override
	public String toString() {
		String s = "";
		s += "NodeName: " + this.label + " NodeID: " + this.id;
		return s;
	}

}
