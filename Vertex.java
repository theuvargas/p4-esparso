import java.util.*;

public class Vertex {
    protected Integer id, componentId;
    protected HashMap< Integer, Vertex> nbhood;

    public Vertex ( int id ) {
        this.id = id;
        nbhood = new HashMap<Integer,Vertex>();
    }

    public void add_neighbor( Vertex viz ) {
        nbhood.put(viz.id, viz);
    }
    
    public int degree() {
        return nbhood.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id.equals(vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
