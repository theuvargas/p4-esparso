import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class Graph {
    protected HashMap< Integer, Vertex> vertex_set;

    private static int currentId = 0;
    private float edges = 0;

    public int getEdges() {
    	return (int) this.edges;
	}

    public Graph() {
        vertex_set = new HashMap<>();
    }
    
	public void open_text( String arq_ent ) {
		String thisLine = null;
        vertex_set = new HashMap<Integer,Vertex>();
		String pieces[ ];

		try {
		    FileReader file_in = new FileReader( arq_ent );
		    BufferedReader br1 = new BufferedReader( file_in );
		    while ( (thisLine = br1.readLine( )) != null) {
			    // retira excessos de espaços em branco
			    thisLine = thisLine.replaceAll("\\s+", " ");
			    pieces = thisLine.split(" ");
			    int v1 = Integer.parseInt( pieces[0] );
			    this.add_vertex( v1 );
			    for( int i = 2; i < pieces.length; i++ ) {
   					int v2 = Integer.parseInt( pieces[ i ] );
   					// pode ser a primeira ocorrência do v2
					this.add_vertex( v2 );
					this.add_arc( v1, v2 );
				}
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    public void add_vertex( int id ) {
        if ( id < 1 || this.vertex_set.get( id ) == null ) {
            Vertex v = new Vertex( id );
            vertex_set.put( v.id, v );
        }
    }
    
    public void add_arc( Integer id1, Integer id2) {
        Vertex v1 = vertex_set.get( id1 );
        Vertex v2 = vertex_set.get( id2 );
        if ( v1 == null || v2 == null ) {
            System.out.println("Vértice inexistente!");
            return;
        }
        v1.add_neighbor( v2 );
        edges += 0.5;
    }

    public void add_edge( Integer id1, Integer id2) {
        Vertex v1 = vertex_set.get( id1 );
        Vertex v2 = vertex_set.get( id2 );
        if ( v1 == null || v2 == null ) {
            System.out.printf("Vértice inexistente!");
            return;
        }
        add_arc(id1, id2);
    }

    // retorna o subgrafo induzido pelos vértices de entrada
	public Graph inducedSubgraph(Integer[] vertices) {
		Graph g = new Graph();
		for (Integer v : vertices) {
			g.add_vertex(v);
		}

		for (Vertex v : g.vertex_set.values()) {
			Vertex originalVertex = vertex_set.get(v.id);
			for (Vertex neighbour : originalVertex.nbhood.values()) {
				if (g.vertex_set.containsValue(neighbour)) {
					if (!v.nbhood.containsValue(neighbour)) {
						g.add_edge(v.id, neighbour.id);
					}
				}
			}
		}

		return g;
	}

	// retorna todas as combinações de k em k dos vértices do grafo
	public Set<Integer[]> getAllCombinations(int k) {
    	Set<Integer[]> combinations = new HashSet<>();

    	int[] vertices = new int[vertex_set.size()];
    	Iterator<Integer> iterator = vertex_set.keySet().iterator();
    	for (int i = 0; i < vertex_set.size(); i++) {
    		vertices[i] = iterator.next();
		}

		Combination.getCombination(vertices, k, combinations);

		return combinations;
	}

	public boolean isP4() {
    	Set<Integer> degreeSet = new HashSet<>();
    	for (Vertex v : vertex_set.values()) {
    		degreeSet.add(v.degree());
		}
		// se o grafo tem os graus 1 2 2 1, então ele é isomorfo à P4
    	return degreeSet.equals(new HashSet<>(Arrays.asList(1, 2, 2, 1)));
	}

	public boolean isP4sparse() {
		Set<Integer[]> combinationsOf5 = getAllCombinations(5); // combinações de 5 dos vértices

		for (Integer[] combination : combinationsOf5) {
			Graph g = inducedSubgraph(combination); // transforma cada combinação em grafos induzidos

			Set<Integer[]> combinationsOf4 = g.getAllCombinations(4); // gera as combinações 4 a 4

			int numberOfP4s = 0;
			for (Integer[] internalCombination : combinationsOf4) {
				Graph g2 = inducedSubgraph(internalCombination); // subgrafo induzido por 4 vértices

				if (g2.isP4()) numberOfP4s++;

				if (numberOfP4s > 1) { // subgrafo tem mais que 1 p4, portanto o grafo não é esparso
					System.out.print("O subgrafo induzido pelos vértices ");
					for (Integer i : combination) {
						System.out.print(i + " ");
					}
					System.out.println("tem mais de um P4.");
					System.out.println("O grafo não é P4-esparso.");
					return false;
				}
			}
		}
		System.out.println("O grafo é P4-esparso.");
		return true;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(String.format("O grafo tem %d vértices e %d arestas.\n\n", vertex_set.size(), getEdges()));

		for (Vertex v : vertex_set.values()) {
			str.append("O vértice " + v.id + " é vizinho de " + v.nbhood.keySet() + "\n");
		}
		return str.substring(0, str.length()-1);
	}
}
