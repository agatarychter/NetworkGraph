import javafx.util.Pair;
import java.util.*;

public class ComputerNetworkGraph {
    private Set<Vertex> vertices;
    public ComputerNetworkGraph()
    {
        vertices = new HashSet<>();
    }
    public ComputerNetworkGraph(Set<Vertex> vertices) {this.vertices = vertices;}
    public boolean addVertex(String ip) {
        return vertices.add(new Vertex(ip));
    }

    public Vertex getVertex(String ip){
        for(Vertex vertex: vertices){
            if(vertex.getIp().equals(ip))
                return vertex;
        }
        return null;
    }

    public boolean addEdge(String firstServer, String secondServer, long ping) {
        Vertex firstVertex;
        Vertex secondVertex;
        if((firstVertex=getVertex(firstServer))==null || (secondVertex=getVertex(secondServer))==null)
            return false;
        if(firstVertex.getAdjacencyMap().containsKey(secondVertex))
            return false;
        firstVertex.getAdjacencyMap().put(secondVertex,ping);
        return true;
    }

    public List<Pair<Vertex,Vertex>> getEdge(long ping) {
        List<Pair<Vertex,Vertex>> edges = new LinkedList<>();
        for (Vertex vertex: vertices
        ) {
            for (Map.Entry<Vertex,Long> entry: vertex.getAdjacencyMap().entrySet()
            ) {
                if(entry.getValue().equals(ping))
                    edges.add(new Pair<>(vertex,entry.getKey()));
            }
        }
        return edges;
    }

    public Pair<Long,List<Vertex>> getPath(String firstServer, String secondServer) {
        Vertex source = getVertex(firstServer);
        Vertex destination = getVertex(secondServer);
        if(source==null || destination ==null)
            return new Pair<>(-1L,new LinkedList<>());
        Vertex[] verticesArray = new Vertex[vertices.size()];
        long [] distanceArray = new long[verticesArray.length];
        Vertex [] predecessors = new Vertex[verticesArray.length];
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(verticesArray.length, Comparator.comparingLong(o->source.getAdjacencyMap().getOrDefault(o,Long.MAX_VALUE)));
        Iterator<Vertex> iterator = vertices.iterator();
        for(int i=0;i<verticesArray.length;i++){
            Vertex temp = iterator.next();
            verticesArray[i]=temp;
            if(temp.equals(source))
                distanceArray[i]= 0L;
            else
                distanceArray[i] =source.getAdjacencyMap().getOrDefault(temp,Long.MAX_VALUE);;
            priorityQueue.add(temp);
        }

        for(Vertex vertex: source.getAdjacencyMap().keySet()) {
            predecessors[indexOf(vertex,verticesArray)]= source;
        }

        boolean found = false;
        while(!priorityQueue.isEmpty() &&!found) {
            Vertex dequeued = priorityQueue.poll();
            for(Vertex vertex: dequeued.getAdjacencyMap().keySet()) {
                int tempIndex = indexOf(vertex,verticesArray);
                int dequeuedIndex = indexOf(dequeued,verticesArray);
                long weight = dequeued.getAdjacencyMap().get(vertex);
                if (distanceArray[dequeuedIndex] + weight
                        < distanceArray[tempIndex]) {
                    distanceArray[tempIndex] = distanceArray[dequeuedIndex] + weight;
                    predecessors[tempIndex] = dequeued;
                }
                if(vertex.equals(destination))
                    found=true;
            }
        }
        if(!found)
            return new Pair<>(-1L,new LinkedList<>());
        LinkedList<Vertex> servers = new LinkedList<>();
        servers.add(destination);
        Vertex predecessor = predecessors[indexOf(destination,verticesArray)];
        while(!predecessor.equals(source)){
            servers.add(predecessor);
            predecessor = predecessors[indexOf(predecessor,verticesArray)];
        }
        servers.add(source);
        return new Pair<>(distanceArray[indexOf(destination,verticesArray)],servers);
    }

    private static int indexOf(Vertex vertex,Vertex[] array){
        for(int i=0;i<array.length;i++){
            if(array[i].equals(vertex))
                return i;
        }
        return -1;
    }
}
