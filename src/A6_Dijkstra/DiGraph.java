package A6_Dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class DiGraph implements DiGraph_Interface {
	// in here go all your data and methods for the graph
	// and the topo sort operation
	public HashMap<String, Node> nodeMap;
	public HashMap<Long, Edge> edgeMap; // Purely for better efficiency
	public HashSet<Long> nodeIdSet; // Purely for better efficiency
	public HashSet<Long> edgeIdSet; // Purely for better efficiency

	public DiGraph() { // default constructor
		this.nodeMap = new HashMap<>();
		this.edgeMap = new HashMap<>();
		this.nodeIdSet = new HashSet<>();
		this.edgeIdSet = new HashSet<>();
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
	}

	@Override
	public boolean addNode(long idNum, String label) {
		if (idNum < 0) {
			return false;
		}
		// Duplicate check
		if (this.nodeIdSet.contains(idNum) || this.nodeMap.containsKey(label) || label == null) {
			return false;
		}
		// Adding the new node to the node hash map and id set.
		Node nodeToAdd = new Node(label, idNum);
		this.nodeMap.put(label, nodeToAdd);
		this.nodeIdSet.add(idNum);
		return true;
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		if (idNum < 0) {
			return false;
		}

		if (sLabel == null || dLabel == null) {
			return false;
		}
		// Null pointer check
		if (this.getNode(sLabel) == null || this.getNode(dLabel) == null) {
			return false;
		}
		// Duplicate currNode and destNode check
		if (this.getNode(sLabel).outEdgeMap.containsKey(dLabel) || this.getNode(dLabel).inEdgeMap.containsKey(sLabel)) {
			return false;
		}
		// Initiate the new edge and put it into the edge hash map
		Edge edge = new Edge(this.getNode(sLabel), this.getNode(dLabel), idNum, weight, eLabel);
		this.edgeMap.put(idNum, edge);
		// Duplicate id number check
		if (this.edgeIdSet.add(idNum) == false) {
			return false;
		}
		// Put the new edge into the inedge of the destination node and outedge
		// of the current(source) node
		this.getNode(dLabel).addInEdge(edge);
		this.getNode(sLabel).addOutEdge(edge);
		return true;
	}

	@Override
	public boolean delNode(String label) {
		if (label == null) {
			return false;
		}
		// Label existence check
		if (this.nodeMap.containsKey(label) == false) {
			return false;
		}
		Node nodeToDelete = this.getNode(label);

		// Remove the in and out edges linked to that node
		for (Edge edge : nodeToDelete.getInList().values()) {
			this.edgeMap.remove(edge.id);
			this.edgeIdSet.remove(edge.id);
			edge.currNode.outEdgeMap.remove(label);
			edge.currNode.outNode--;
		}
		for (Edge edge : nodeToDelete.getOutList().values()) {
			this.edgeMap.remove(edge.id);
			this.edgeIdSet.remove(edge.id);
			edge.destNode.inEdgeMap.remove(label);
			edge.destNode.inNode--;
		}
		// Remove the node from the nodeMap and node id set
		this.nodeMap.remove(label);
		this.nodeIdSet.remove(nodeToDelete.id);
		return true;
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		// Find the node which has their labels sLabel and dLabel
		if (sLabel == null || dLabel == null) {
			return false;
		}
		Node current = this.getNode(sLabel);
		Node destination = this.getNode(dLabel);
		// Null pointer check
		if (current == null || destination == null) {
			return false;
		}
		// Find the edge that links those two nodes by looking into the current
		// node's out edge hash map
		Edge edge = current.outEdgeMap.get(dLabel);
		// If there is no such edge or such edge's key does not exist in the
		// edge hash map, then return false to indicate remove failure
		if (edge == null || this.edgeMap.containsKey(edge.id) == false) {
			return false;
		}
		// Finally, remove the edge from the edge map, edge ID set, and then
		// remove the edge from the our edge map of the 'currNode' and int edge
		// map of the 'destNdode' and bring the outEdge and inEdge count down by
		// one respectively
		this.edgeMap.remove(edge.id);
		this.edgeIdSet.remove(edge.id);
		current.outEdgeMap.remove(dLabel);
		current.outNode--;
		destination.inEdgeMap.remove(sLabel);
		destination.inNode--;
		return true;
	}

	@Override
	public long numNodes() {
		// TODO Auto-generated method stub
		return this.nodeMap.size();
	}

	@Override
	public long numEdges() {
		// TODO Auto-generated method stub
		return this.edgeMap.size();
	}

	@Override
	public String[] topoSort() {

		Queue<Node> nodeList = new LinkedList<>();
		// We first build a hash set containing all the zero in degree nodes
		HashSet<Node> zeroInDegreeSet = new HashSet<>();

		// To avoid concurrent exception, I used iterator to remove the node
		// that has 0 inNode from the node hash map
		for (Iterator<HashMap.Entry<String, Node>> nodeIterator = this.nodeMap.entrySet().iterator(); nodeIterator
				.hasNext();) {
			Node node = nodeIterator.next().getValue();
			if (node.inNode == 0) {
				zeroInDegreeSet.add(node);
				nodeIterator.remove();
			}
		}

		while (!zeroInDegreeSet.isEmpty()) {
			Node node = zeroInDegreeSet.iterator().next();
			zeroInDegreeSet.remove(node);
			nodeList.add(node);
			for (Iterator<HashMap.Entry<String, Edge>> edgeIterator = node.outEdgeMap.entrySet()
					.iterator(); edgeIterator.hasNext();) {
				Edge edge = edgeIterator.next().getValue();
				Node destinationNode = edge.destNode;
				edgeIterator.remove();
				destinationNode.inEdgeMap.remove(node.label);

				if (destinationNode.inEdgeMap.isEmpty()) {
					zeroInDegreeSet.add(destinationNode);
					this.nodeMap.remove(destinationNode.getLabel());
				}

			}
		}

		// If the node hash map is not empty after all topo sort is completed,
		// then there is a cycle and thus no final topo sort.
		if (this.nodeMap.isEmpty() == false) {
			return null;
		}

		String[] resultArray = new String[nodeList.size()];
		for (int i = 0; i < resultArray.length; i++) {
			resultArray[i] = nodeList.remove().label;
		}
		return resultArray;
	}

	// Helper method to get the node from the hash map
	private Node getNode(String label) {

		return this.nodeMap.get(label);
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		ShortestPathInfo[] shortestPath = new ShortestPathInfo[this.nodeMap.size()];
		int i = 0;

		// Initialize the source node from the label provided
		Node source = this.nodeMap.get(label);
		source.dist = 0;

		// Initialize the minimum binary heap which stores the entry pair with
		// the node's label as the value and the node's distance to the source
		// as the priority number; at this time, the distance of the source to
		// all nodes are the same, which is maximum integer
		MinBinHeap pathLengthHeap = new MinBinHeap();
		EntryPair[] nodeAndDistance = new EntryPair[this.nodeMap.size()];
		int j = 0;
		for (Node node : this.nodeMap.values()) {
			nodeAndDistance[j++] = new EntryPair(node.label, node.dist);
		}
		pathLengthHeap.build(nodeAndDistance);

		// Loop through the node map
		while (i < this.nodeMap.size()) {
			// "node" here is the node currently in the heap with the shortest
			// distance from the source node
			Node node = this.nodeMap.get(pathLengthHeap.getMin().value);
			// If we have not found the shortest path from the source to the
			// node
			if (!node.exist) {
				node.exist = true;
				// If such path exists, then add it to the shortestPath array
				if (node.dist < 0x7ffffff) {
					shortestPath[i++] = new ShortestPathInfo(node.label, node.dist);
				} else {
					// If a path from source node to the destination node does
					// not exist, set totalWeight to -1 add add it to the array
					shortestPath[i++] = new ShortestPathInfo(node.label, -1);
				}
			}
			// Delete the current node in the minBinHeap with the shortest
			// distance from the source node
			pathLengthHeap.delMin();
			// Loop through the edges in the current node's out edge map
			for (Edge edge : node.outEdgeMap.values()) {
				// If we have not found the shortest path from the source to the
				// destination node of the edge of the current node (the child
				// node of the current node)
				if (!edge.destNode.exist) {
					// then get this edge's weight
					long edgeWeight = edge.weight;
					// and if the edge's weight plus the current node's distance
					// (at first it is the maximum integer, but later it will be
					// updated by adding edges weights to that maximum integer,
					// thus we need to make edgeWeight a long value, not
					// integer) from the source node is less then the current
					// edge's destination node's distance from the source node
					if (node.dist + edgeWeight < edge.destNode.dist) {
						// then we have found a shorter path from the source
						// node to the current node's child node; thus we update
						// the distance from the source node to
						// the edge's destination node (current node's child)
						// recorded in this current node's child node
						edge.destNode.dist = node.dist + edgeWeight;
						// and put this new path to this destination node
						// (current node's child) into the minBinHeap by
						// updating the old record in the minBinHeap
						pathLengthHeap.insert(new EntryPair(edge.destNode.label, edge.destNode.dist));
					}
				}
			}
		}

		return shortestPath;
	}

	// rest of your code to implement the various operations

}