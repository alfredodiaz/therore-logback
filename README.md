# Therore Logback: assert Logback calls with Mockito

therore-logback provides a convenient @Rule to assert logback calls with mockito. The use is more clear with some examples.


```java
@Slf4j
public class LogbackRuleTest {

    @Rule
    public LogbackRule rule = new LogbackRule();

    @Test
    public void happyPath() {
        log.info("info message");

        // verify that no error with exception has been logged
        verify(rule.getLog(), never()).contains(argThat(errorWithException()));
    }

    @Test(expected = AssertionError.class)
    public void exceptionPath() {
        log.error("error message", new RuntimeException());

        // verify that no error with exception has been logged
        verify(rule.getLog(), never()).contains(argThat(errorWithException()));
    }

    @Test(expected = AssertionError.class)
    public void combinedExceptionPath() {
        log.info("info message");
        log.error("error message", new RuntimeException());

        // verify that no error with exception has been logged
        verify(rule.getLog(), never()).contains(argThat(errorWithException()));
    }

    @Test
    public void testContainingSpecificMessage() {
        log.info("specific message");

        // verify that the message "specific message" has been logged
        verify(rule.getLog(), atLeastOnce()).contains(argThat(text("specific message")));
    }

    @Test
    public void testContainingCombinedSpecificMessage() {
        log.info("specific message");
        log.info("other message");

        // verify that the message "specific message" has been logged
        verify(rule.getLog(), atLeastOnce()).contains(argThat(text("specific message")));
    }

    @Test(expected = AssertionError.class)
    public void testNotContainingSpecificMessage() {
        log.info("specific message");

        // verify that the message "specific message" has not been logged
        verify(rule.getLog(), never()).contains(argThat(text("specific message")));
    }

    @Test(expected = AssertionError.class)
    public void testNotContainingCombinedSpecificMessage() {
        log.info("other message");
        log.info("specific message");

        // verify that the message "specific message" has not been logged
        verify(rule.getLog(), never()).contains(argThat(text("specific message")));
    }

}
```


Include the following dependency in your *pom* file in order to use it.

``` xml
<dependency>
    <groupId>net.therore.logback/groupId>
    <artifactId>therore-logback</artifactId>
    <version>1.0.0</version>
    <type>test</type>
</dependency>
```
