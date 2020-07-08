package me.bang.springboot2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AutoconfigurationApplicationTests {

    @Value("${property.test.name}")
    private String propertyTestName;

    @Value("${propertyTest}")
    private String propertyTest;

    @Value("${noKey:default value}")
    private String defaultValue;

    @Value("${propertyTestList}")
    private String[] propertyTestArray;

    @Value("#{'${propertyTestList}'.split(',')}")
    private List<String> propertyTestList;

    @Test
    public void testValue() {
        assertThat(propertyTestName).isEqualTo("property depth test");
        assertThat(propertyTest).isEqualTo("test");
        assertThat(defaultValue).isEqualTo("default value");

        assertThat(propertyTestArray[0]).isEqualTo("a");
    }
}
