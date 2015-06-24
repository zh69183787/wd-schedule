package test;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTest extends TestCase{
    private test.Test test;

    @Before
    public void setUp() throws Exception {
        test = new test.Test();
    }

    @After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testAdd() throws Exception {
        Assert.assertEquals(5,test.add(1,4));
    }

    @Test
    public void testAdd1() throws Exception {
        this.assertEquals(5,test.add(1,4));
    }
}