package A6_Dijkstra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class DiGraphTests {
	public static final int NUM_NODES = 1000;

	@Test
	public void ShortestPathSimple1() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");

		graph.addEdge(0, "a", "b", 1, null);

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", 0);
		correct.put("b", 1);

		assertTrue(this.checkShortestPath(correct, graph, "a"));
	}

	@Test
	public void ShortestPathSimple2() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");

		graph.addEdge(0, "a", "b", 1, null);
		graph.addEdge(1, "b", "c", 1, null);

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", 0);
		correct.put("b", 1);
		correct.put("c", 2);

		assertTrue(this.checkShortestPath(correct, graph, "a"));
	}

	@Test
	public void ShortestPathSimple3() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");

		graph.addEdge(0, "a", "b", 1, null);
		graph.addEdge(1, "b", "c", 1, null);
		graph.addEdge(2, "a", "d", 2, null);

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", 0);
		correct.put("b", 1);
		correct.put("c", 2);
		correct.put("d", 2);

		assertTrue(this.checkShortestPath(correct, graph, "a"));
	}

	@Test
	public void ShortestPathSimple3b() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");

		graph.addEdge(0, "a", "b", 1, null);
		graph.addEdge(1, "b", "c", 1, null);
		graph.addEdge(2, "a", "d", 2, null);

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", -1);
		correct.put("b", 0);
		correct.put("c", 1);
		correct.put("d", -1);

		assertTrue(this.checkShortestPath(correct, graph, "b"));
	}

	@Test
	public void ShortestPathComplex() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");
		graph.addNode(5, "e");
		graph.addNode(6, "f");
		graph.addNode(7, "g");

		graph.addEdge(0, "a", "b", 2, null);
		graph.addEdge(1, "a", "d", 1, null);
		graph.addEdge(2, "b", "d", 3, null);
		graph.addEdge(3, "b", "e", 1, null);
		graph.addEdge(4, "d", "e", 3, null);
		graph.addEdge(5, "d", "g", 4, null);
		graph.addEdge(6, "d", "f", 8, null);
		graph.addEdge(7, "e", "g", 6, null);
		graph.addEdge(8, "g", "f", 1, null);
		graph.addEdge(9, "c", "f", 5, null);
		graph.addEdge(10, "c", "a", 4, null);
		graph.addEdge(11, "d", "c", 2, null);

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", 0);
		correct.put("d", 1);
		correct.put("b", 2);
		correct.put("c", 3);
		correct.put("e", 3);
		correct.put("g", 5);
		correct.put("f", 6);

		assertTrue(this.checkShortestPath(correct, graph, "a"));
	}

	@Test
	public void ShortestPathDisconnected1() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");
		graph.addNode(5, "e");
		graph.addNode(6, "f");
		graph.addNode(7, "g");

		graph.addEdge(0, "a", "b", 2, null);
		graph.addEdge(1, "a", "d", 1, null);

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", 0);
		correct.put("d", 1);
		correct.put("b", 2);
		correct.put("c", -1);
		correct.put("e", -1);
		correct.put("f", -1);
		correct.put("g", -1);

		assertTrue(this.checkShortestPath(correct, graph, "a"));
	}

	@Test
	public void ShortestPathDisconnected1c() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");
		graph.addNode(5, "e");
		graph.addNode(6, "f");
		graph.addNode(7, "g");

		graph.addEdge(0, "a", "b", 2, null);
		graph.addEdge(1, "a", "d", 1, null);

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", -1);
		correct.put("b", -1);
		correct.put("c", 0);
		correct.put("d", -1);
		correct.put("e", -1);
		correct.put("f", -1);
		correct.put("g", -1);

		assertTrue(this.checkShortestPath(correct, graph, "c"));
	}

	@Test
	public void ShortestPathIntegrationComplex1() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");
		graph.addNode(5, "e");
		graph.addNode(6, "f");
		graph.addNode(7, "g");

		graph.addEdge(0, "a", "b", 2, null);
		graph.addEdge(1, "a", "d", 1, null);
		graph.addEdge(2, "b", "d", 3, null);
		graph.addEdge(3, "b", "e", 1, null);
		graph.addEdge(4, "d", "e", 3, null);
		graph.addEdge(5, "d", "g", 4, null);
		graph.addEdge(6, "d", "f", 8, null);
		graph.addEdge(7, "e", "g", 6, null);
		graph.addEdge(8, "g", "f", 1, null);
		graph.addEdge(9, "c", "f", 5, null);
		graph.addEdge(10, "c", "a", 4, null);
		graph.addEdge(11, "d", "c", 2, null);

		graph.delEdge("d", "f");
		graph.delNode("b");

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", 0);
		correct.put("d", 1);
		correct.put("c", 3);
		correct.put("e", 4);
		correct.put("g", 5);
		correct.put("f", 6);

		assertTrue(this.checkShortestPath(correct, graph, "a"));
	}

	@Test
	public void ShortestPathIntegrationComplex2() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");
		graph.addNode(5, "e");
		graph.addNode(6, "f");
		graph.addNode(7, "g");

		graph.addEdge(0, "a", "b", 2, null);
		graph.addEdge(1, "a", "d", 1, null);
		graph.addEdge(2, "b", "d", 3, null);
		graph.addEdge(3, "b", "e", 1, null);
		graph.addEdge(4, "d", "e", 3, null);
		graph.addEdge(5, "d", "g", 4, null);
		graph.addEdge(6, "d", "f", 8, null);
		graph.addEdge(7, "e", "g", 6, null);
		graph.addEdge(8, "g", "f", 1, null);
		graph.addEdge(9, "c", "f", 5, null);
		graph.addEdge(10, "c", "a", 4, null);
		graph.addEdge(11, "d", "c", 2, null);

		graph.delNode("d");

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("a", 0);
		correct.put("b", 2);
		correct.put("e", 3);
		correct.put("g", 9);
		correct.put("f", 10);
		correct.put("c", -1);

		assertTrue(this.checkShortestPath(correct, graph, "a"));
	}

	@Test
	public void ShortestPathIntegrationComplex3() {
		DiGraph graph = new DiGraph();
		graph.addNode(1, "a");
		graph.addNode(2, "b");
		graph.addNode(3, "c");
		graph.addNode(4, "d");
		graph.addNode(5, "e");
		graph.addNode(6, "f");
		graph.addNode(7, "g");

		graph.addEdge(0, "a", "b", 2, null);
		graph.addEdge(1, "a", "d", 1, null);
		graph.addEdge(2, "b", "d", 3, null);
		graph.addEdge(3, "b", "e", 1, null);
		graph.addEdge(4, "d", "e", 3, null);
		graph.addEdge(5, "d", "g", 4, null);
		graph.addEdge(6, "d", "f", 8, null);
		graph.addEdge(7, "e", "g", 6, null);
		graph.addEdge(8, "g", "f", 1, null);
		graph.addEdge(9, "c", "f", 5, null);
		graph.addEdge(10, "c", "a", 4, null);
		graph.addEdge(11, "d", "c", 2, null);

		graph.delNode("g");

		HashMap<String, Integer> correct = new HashMap<>();

		correct.put("b", 0);
		correct.put("e", 1);
		correct.put("d", 3);
		correct.put("c", 5);
		correct.put("a", 9);
		correct.put("f", 10);

		assertTrue(this.checkShortestPath(correct, graph, "b"));
	}

	@Test
	public void ShortestPathStressTest() {
		DiGraph d = new DiGraph();
		for (int i = 0; i < NUM_NODES; i++) {
			d.addNode(i, Integer.toString(i));
			if (i > 2) {
				d.addEdge((int) (i * Math.random() * Math.random() * 100000), Integer.toString(i),
						Integer.toString(i - 1), 0, null);
				d.addEdge((int) (i * Math.random() * Math.random() * 100000), Integer.toString(i),
						Integer.toString(i - 2), 0, null);
			}
		}

		ShortestPathInfo[] results = d.shortestPath("3");

		// Verify that the code doesn't explode with large numbers
		assertEquals(d.numNodes(), results.length);
	}

	public boolean checkShortestPath(HashMap<String, Integer> correct, DiGraph D, String label) {
		ShortestPathInfo[] paths = D.shortestPath(label);
		if (D.numNodes() != paths.length || correct.size() != paths.length) {
			return false;
		}
		for (ShortestPathInfo obj : paths) {
			long ans = correct.get(obj.getDest());
			if (ans != obj.getTotalWeight()) {
				return false;
			}
		}
		return true;
	}
}