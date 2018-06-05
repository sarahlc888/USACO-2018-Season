import java.io.*;
import java.util.*;

//different input method

public class GraphImp5 {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("graph2.in"));
		int N = Integer.parseInt(br.readLine()); // number of nodes
		
		Node[] nodes = new Node[N];
		for (int i = 0; i < N; i++) {
			nodes[i] = new Node(i);
		}
		Adjac[] adjs = new Adjac[N];
		Node[] a = new Node[1];
		a[0] = new Node(1);
		for (int i = 0; i < N; i++) {
			a[0].val = i+1;
			System.out.println(a[0].val);
			adjs[i] = new Adjac(a);
		}
	
		Graph g = new Graph(N, nodes, adjs);
		System.out.println("nodes");
		for (int i = 0; i < N; i++) {
			System.out.println(g.nodes[i].val);
		}
		System.out.println("adjs");
		for (int i = 0; i < N; i++) {
			System.out.println("from " + i + " to ");
			for (Node x : g.adjacencies[i].adj) {
				System.out.println(x.val);
			}
			
		}
	}
	public static class Graph {
		Node[] nodes;
		Adjac[] adjacencies;
		
		public Graph(int n) { // make a graph with n elements
			nodes = new Node[n];
			adjacencies = new Adjac[n];
		}
		public Graph(int n, Node[] a) { // make a graph with n elements listed in a[]
			nodes = new Node[n];
			for (int i = 0; i < n; i++) {
				nodes[i] = (a[i]);
			}
			adjacencies = new Adjac[n];
		}
		public Graph(int n, Node[] a, Adjac[] b) { // make a graph with n elements listed in int[] a
			// and edges listed in Adjac[] b
			nodes = new Node[n];
			for (int i = 0; i < n; i++) {
				nodes[i] = (a[i]);
			}
			adjacencies = new Adjac[n];
			for (int i = 0; i < n; i++) {
				adjacencies[i] = new Adjac(b[i]);
			}
		}
	}
	public static class Node {
		int val;
		public Node(int v) {
			val = v;
		}
	}
	public static class Adjac {
		ArrayList<Node> adj;
		public Adjac() {
			adj = new ArrayList<Node>();
		}
		public Adjac(Adjac a) {
			adj = new ArrayList<Node>();
			for (int i = 0; i < a.adj.size(); i++) {
				adj.add(a.adj.get(i));
			}
		}
		public Adjac(Node[] a) {
			adj = new ArrayList<Node>();
			for (int i = 0; i < a.length; i++) {
				adj.add(new Node(a[i].val));
			}
		}
	}
}
