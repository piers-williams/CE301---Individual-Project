package Project.NLP.Tests;

import Project.NLP.Pipeline;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PipelineTest {

    Pipeline pipeline;

    @Before
    public void setUp() {
        pipeline = new Pipeline();
    }

    @Test
    public void testStripSender() {
        String[] result = pipeline.extractSenderAndMessage("Blue: peanut");
        assertEquals("Blue", result[0]);
        assertEquals("peanut", result[1]);
    }
}
