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

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import lombok.Getter;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.LoggerFactory;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * @author <a href="mailto:alfredo.diaz@therore.net">Alfredo Diaz</a>
 */
public class LogbackRule implements TestRule {

    private Appender appender;

    @Getter
    private Log log;

    private final Logger rootLogger;

    public LogbackRule() {
        this(((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)));
    }

    public LogbackRule(Logger rootLogger) {
        this.rootLogger = rootLogger;
        reset();
    }

    private void reset() {
        appender = null;
        log = null;
    }

    @Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
                rootLogger.addAppender(appender = mock(Appender.class));
                log = mock(Log.class);
                Mockito.doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        log.contains((ILoggingEvent) invocation.getArguments()[0]);
                        return null;
                    }
                }).when(appender).doAppend(any(ILoggingEvent.class));

                try {
					base.evaluate();
				} finally {
                    rootLogger.detachAppender(appender);
                    reset();
				}
			}
		};
	}

}

