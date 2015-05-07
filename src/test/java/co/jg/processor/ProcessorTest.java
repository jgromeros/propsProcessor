package co.jg.processor;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessorTest {

    @Test
    public void testMerge() {
        Processor.mergeFiles("src/main/resources/es_CO.properties", "src/main/resources/en_US.properties");
    }

    @Test
    public void testMergeWithComments() {
        Processor.mergeFiles("src/main/resources/comments-es_CO.properties", "src/main/resources/comments-en_US.properties");
    }

}
