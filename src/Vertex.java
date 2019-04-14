import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private String ip;
    private Map<Vertex,Long> adjacencyMap;

    public Vertex(String ip) {
        this.ip = ip;
        this.adjacencyMap = new HashMap<>();
    }
    public int hashCode() {
        return this.ip.hashCode();
    }
    public boolean equals(Object object) {
        if(object instanceof Vertex) {
            return this.ip.equals(((Vertex) object).ip);
        }
        return false;
    }
    public String getIp()
    {
        return ip;
    }
    public Map<Vertex, Long> getAdjacencyMap() {
        return adjacencyMap;
    }
    public void setAdjacencyMap(Map<Vertex, Long> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
    }
    public String toString() {
        return ip;
    }
}
