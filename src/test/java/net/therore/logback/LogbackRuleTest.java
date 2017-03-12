/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package net.therore.logback;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;

import static net.therore.logback.EventMatchers.errorWithException;
import static net.therore.logback.EventMatchers.text;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:alfredo.diaz@therore.net">Alfredo Diaz</a>
 */
@Slf4j
public class LogbackRuleTest {

	@Rule
	public LogbackRule rule = new LogbackRule();

	@Test
	public void happyPath() {
		log.info("info message");

        verify(rule.getLog(), never()).contains(argThat(errorWithException()));
	}

	@Test(expected = AssertionError.class)
	public void exceptionPath() {
		log.error("error message", new RuntimeException());

        verify(rule.getLog(), never()).contains(argThat(errorWithException()));
	}

	@Test(expected = AssertionError.class)
	public void combinedExceptionPath() {
		log.info("info message");
		log.error("error message", new RuntimeException());

        verify(rule.getLog(), never()).contains(argThat(errorWithException()));
	}

	@Test
	public void testContainingSpecificMessage() {
		log.info("specific message");

        verify(rule.getLog(), atLeastOnce()).contains(argThat(text("specific message")));
	}

	@Test
	public void testContainingCombinedSpecificMessage() {
		log.info("specific message");
		log.info("other message");

        verify(rule.getLog(), atLeastOnce()).contains(argThat(text("specific message")));
	}

	@Test(expected = AssertionError.class)
	public void testNotContainingSpecificMessage() {
		log.info("specific message");

        verify(rule.getLog(), never()).contains(argThat(text("specific message")));
	}

	@Test(expected = AssertionError.class)
	public void testNotContainingCombinedSpecificMessage() {
		log.info("other message");
		log.info("specific message");

        verify(rule.getLog(), never()).contains(argThat(text("specific message")));
	}

}
