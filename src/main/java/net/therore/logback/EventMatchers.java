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

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.mockito.ArgumentMatcher;

import static ch.qos.logback.classic.Level.WARN;

/**
 * @author <a href="mailto:alfredo.diaz@therore.net">Alfredo Diaz</a>
 */
public class EventMatchers {
    private EventMatchers() {
    }

    static public ArgumentMatcher<ILoggingEvent> errorWithException() {
        return new ArgumentMatcher<ILoggingEvent>() {
            @Override
            public boolean matches(ILoggingEvent event) {
                return event.getLevel().isGreaterOrEqual(WARN) && event.getThrowableProxy() != null;
            }
        };
    };

	static public ArgumentMatcher<ILoggingEvent> text(final String text) {
        return new ArgumentMatcher<ILoggingEvent>() {

            @Override
            public boolean matches(ILoggingEvent event) {
                return event.getFormattedMessage().contains(text);
            }
        };
	}

}

