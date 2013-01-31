package Project.NLP.Tests;

import Project.NLP.BasicConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test class
 */
public class BasicConverterTest {
    private BasicConverter converter;

    String testMessages[] = {
            "Dominate the area inbetween your forward base and Blue's artillery",
            "Take your new group of units and attack the red's large base south of you",
            "How long until you complete this?",
            "When will you be finished with Order 32?",
            "How many laser boats do you have?",
            "Construct a small army and when my army attacks the left flank of GV-34, start your attack on the right"
    };
    @Before
    public void setUp() {
        converter = new BasicConverter();
    }

    @Test
    public void testGetType() {
        String testResults[] = {"Attack", "Attack", "Query", "Query", "Query", "Attack"};
        for(int i = 0; i < testMessages.length; i++){
            String testMessage = testMessages[i];
            assertEquals(testResults[i], converter.getType(testMessage));
        }
    }



}
