import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;
public class ComputerNetworkGraphTest {
    private ComputerNetworkGraph computerNetworkGraph;

    @Test
    public void addNotExistingIpTrue()
    {
        setEmptyGraph();
        String ip = "192.168.0.1";
        Assert.assertTrue(computerNetworkGraph.addVertex(ip));
    }

    @Test
    public void addExistingIpFalse()
    {
        setOneElementGraph();
        String ip = "192.168.0.1";
        computerNetworkGraph.addVertex(ip);
        Assert.assertFalse(computerNetworkGraph.addVertex(ip));
    }

    @Test
    public void getExistingIpTrue()
    {
        setOneElementGraph();
        String ip = "192.168.0.1";
        computerNetworkGraph.addVertex(ip);
        Assert.assertEquals(computerNetworkGraph.getVertex(ip).getIp(),ip);
    }

    @Test
    public void getNotExistingIpNull()
    {
        setEmptyGraph();
        String ip = "192.168.0.1";
        Assert.assertNull(computerNetworkGraph.getVertex(ip));
    }

    @Test
    public void addEdgeNotExistingElementsGraphFalse()
    {
        setEmptyGraph();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Assert.assertFalse(computerNetworkGraph.addEdge(firstIp,secondIp,40L));
    }

    @Test
    public void addEdgeNotExistingFirstIpFalse()
    {
        setOneElementGraph();
        String firstIp = "127.0.1.0";
        String secondIp = "192.168.0.1";
        Assert.assertFalse(computerNetworkGraph.addEdge(firstIp,secondIp,40L));
    }

    @Test
    public void addEdgeNotExistingSecondIpFalse()
    {
        setOneElementGraph();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Assert.assertFalse(computerNetworkGraph.addEdge(firstIp,secondIp,40L));
    }

    @Test
    public void addEdgeExistingElementsNotConnectedTrue()
    {
        setTwoElementsNotConnectedGraph();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Assert.assertTrue(computerNetworkGraph.addEdge(firstIp,secondIp,40L));
    }

    @Test
    public void addEdgeExistingElementsAlreadyConnectedFalse()
    {
        setTwoElementsDirectlyConnectedGraph();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Assert.assertFalse(computerNetworkGraph.addEdge(firstIp,secondIp,50L));
    }

    @Test
    public void getEdgeEmptyGraphEmptyList()
    {
        setEmptyGraph();
        Assert.assertTrue(computerNetworkGraph.getEdge(40L).isEmpty());
    }

    @Test
    public void getEdgeNotExistingPingValueEmptyList()
    {
        setTwoElementsDirectlyConnectedGraph();
        Assert.assertTrue(computerNetworkGraph.getEdge(1L).isEmpty());
    }

    @Test
    public void getEdgeExistingOnePingValueOneElementList()
    {
        int expectedSize = 1;
        setTwoElementsDirectlyConnectedGraph();
        Assert.assertEquals(expectedSize, computerNetworkGraph.getEdge(40L).size());
    }

    @Test
    public void getEdgeExistingTwoPingValuesThreeElementsList()
    {
        setThreeElementsNonDirectlyConnectedGraph();
        int expectedSize = 2;
        Assert.assertEquals(expectedSize, computerNetworkGraph.getEdge(40L).size());
    }


    @Test
    public void getPathEmptyGraphMinusOneEmptyList()
    {
        setEmptyGraph();
        Pair<Long,List<Vertex>> emptyPair = new Pair<>(-1L,new LinkedList<>());
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Assert.assertEquals(emptyPair, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    @Test
    public void getPathNotExistingFirstIpMinusOneEmptyList()
    {
        setOneElementGraph();
        Pair<Long,List<Vertex>> emptyPair = new Pair<>(-1L,new LinkedList<>());
        String secondIp = "192.168.0.1";
        String firstIp = "127.0.1.0";
        Assert.assertEquals(emptyPair, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    @Test
    public void getPathNotExistingSecondIpMinusOneEmptyList()
    {
        setOneElementGraph();
        Pair<Long,List<Vertex>> emptyPair = new Pair<>(-1L,new LinkedList<>());
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Assert.assertEquals(emptyPair, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    @Test
    public void getPathNotConnectedIpsMinusOneEmptyList()
    {
        setTwoElementsNotConnectedGraph();
        Pair<Long,List<Vertex>> emptyPair = new Pair<>(-1L,new LinkedList<>());
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Assert.assertEquals(emptyPair, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    @Test
    public void getPathDirectlyConnectedIpsOk()
    {
        setTwoElementsDirectlyConnectedGraph();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Pair<Long,List<Vertex>> expected = new Pair<>(40L,Arrays.asList(new Vertex(secondIp),new Vertex(firstIp)));
        Assert.assertEquals(expected, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    @Test
    public void getPathNonDirectlyConnectedIpsOk()
    {
        setThreeElementsNonDirectlyConnectedGraph();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        String middleIp = "168.0.10.1";
        Pair<Long,List<Vertex>> expected = new Pair<>(80L,Arrays.asList(new Vertex(secondIp),new Vertex(middleIp),new Vertex(firstIp)));
        Assert.assertEquals(expected, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    @Test
    public void getPathNonDirectWayShorterOk()
    {
        setThreeElementsGraphNonDirectWayShorter();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        String middleIp = "168.0.10.1";
        Pair<Long,List<Vertex>> expected = new Pair<>(80L,Arrays.asList(new Vertex(secondIp),new Vertex(middleIp),new Vertex(firstIp)));
        Assert.assertEquals(expected, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    @Test
    public void getPathDirectWayShorterOk()
    {
        setThreeElementsGraphDirectWayShorter();
        String firstIp = "192.168.0.1";
        String secondIp = "127.0.1.0";
        Pair<Long,List<Vertex>> expected = new Pair<>(30L,Arrays.asList(new Vertex(secondIp),new Vertex(firstIp)));
        Assert.assertEquals(expected, computerNetworkGraph.getPath(firstIp,secondIp));
    }

    /*
    ComputerNetworkGraph-building methods
     */
    private void setEmptyGraph()
    {
        computerNetworkGraph = new ComputerNetworkGraph();
    }

    private void setOneElementGraph()
    {
        Set<Vertex> vertices = new HashSet<>();
        vertices.add(new Vertex("192.168.0.1"));
        computerNetworkGraph = new ComputerNetworkGraph(vertices);
    }

    private void setTwoElementsNotConnectedGraph()
    {
        Set<Vertex> vertices = new HashSet<>();
        vertices.add(new Vertex("192.168.0.1"));
        vertices.add(new Vertex("127.0.1.0"));
        computerNetworkGraph = new ComputerNetworkGraph(vertices);
    }

    private void setTwoElementsDirectlyConnectedGraph()
    {
        Set<Vertex> vertices = new HashSet<>();
        Vertex firstVertex = new Vertex("192.168.0.1");
        Vertex secondVertex = new Vertex("127.0.1.0");
        firstVertex.getAdjacencyMap().put(secondVertex,40L);
        vertices.add(firstVertex);
        vertices.add(secondVertex);
        computerNetworkGraph = new ComputerNetworkGraph(vertices);
    }

    private void setThreeElementsNonDirectlyConnectedGraph()
    {
        Set<Vertex> vertices = new HashSet<>();
        Vertex firstVertex = new Vertex("192.168.0.1");
        Vertex secondVertex = new Vertex("127.0.1.0");
        Vertex middleVertex = new Vertex("168.0.10.1");
        firstVertex.getAdjacencyMap().put(middleVertex,40L);
        middleVertex.getAdjacencyMap().put(secondVertex,40L);
        vertices.add(firstVertex);
        vertices.add(secondVertex);
        vertices.add(middleVertex);
        computerNetworkGraph = new ComputerNetworkGraph(vertices);
    }

    private void setThreeElementsGraphNonDirectWayShorter()
    {
        Set<Vertex> vertices = new HashSet<>();
        Vertex firstVertex = new Vertex("192.168.0.1");
        Vertex secondVertex = new Vertex("127.0.1.0");
        Vertex middleVertex = new Vertex("168.0.10.1");
        firstVertex.getAdjacencyMap().put(middleVertex,40L);
        middleVertex.getAdjacencyMap().put(secondVertex,40L);
        firstVertex.getAdjacencyMap().put(secondVertex,100L);
        vertices.add(firstVertex);
        vertices.add(secondVertex);
        vertices.add(middleVertex);
        computerNetworkGraph = new ComputerNetworkGraph(vertices);
    }

    private void setThreeElementsGraphDirectWayShorter()
    {
        Set<Vertex> vertices = new HashSet<>();
        Vertex firstVertex = new Vertex("192.168.0.1");
        Vertex secondVertex = new Vertex("127.0.1.0");
        Vertex middleVertex = new Vertex("168.0.10.1");
        firstVertex.getAdjacencyMap().put(middleVertex,40L);
        middleVertex.getAdjacencyMap().put(secondVertex,40L);
        firstVertex.getAdjacencyMap().put(secondVertex,30L);
        vertices.add(firstVertex);
        vertices.add(secondVertex);
        vertices.add(middleVertex);
        computerNetworkGraph = new ComputerNetworkGraph(vertices);
    }
}