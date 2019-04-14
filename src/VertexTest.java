import org.junit.Assert;
import org.junit.Test;

public class VertexTest {

    @Test
    public void constructorProperIpSet()
    {
        String ip = "192.168.0.1";
        Vertex vertex = new Vertex("192.168.0.1");
        Assert.assertEquals(vertex.getIp(),ip);
    }

    @Test
    public void constructorEmptyHashMap()
    {
        Vertex vertex = new Vertex("192.168.0.1");
        Assert.assertTrue(vertex.getAdjacencyMap().isEmpty());
    }

    @Test
    public void sameIpEqualsTrue()
    {
        Vertex firstVertex = new Vertex("192.168.0.1");
        Vertex secondVertex = new Vertex("192.168.0.1");
        Assert.assertEquals(firstVertex, secondVertex);
    }

    @Test
    public void differentIpEqualsFalse()
    {
        Vertex firstVertex = new Vertex("192.168.0.1");
        Vertex secondVertex = new Vertex("192.168.1.1");
        Assert.assertNotEquals(firstVertex, secondVertex);
    }

    @Test
    public void notVertexInstanceEqualsFalse()
    {
        Vertex vertex = new Vertex("192.168.0.1");
        Object object = new Object();
        Assert.assertNotEquals(vertex,object);
    }

    @Test
    public void sameObjectSameHashcodeTrue()
    {
        Vertex vertex = new Vertex("192.168.0.1");
        Assert.assertEquals(vertex.hashCode(),vertex.hashCode());
    }

    @Test
    public void sameIpSameHashcodeTrue()
    {
        Vertex firstVertex = new Vertex("192.168.0.1");
        Vertex secondVertex = new Vertex("192.168.0.1");
        Assert.assertEquals(firstVertex.hashCode(), secondVertex.hashCode());
    }
}
