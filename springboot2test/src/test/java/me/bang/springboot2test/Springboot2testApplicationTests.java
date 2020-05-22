package me.bang.springboot2test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(value = "value=test",
        classes = {Springboot2testApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Springboot2testApplicationTests {

    @Value("${value}")
    private String value;

    @Test
    public void contextLoads() {
        assertThat(value, is("test"));
    }
}
