/**
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.model.property;

import junit.framework.TestSuite;
import net.fortuna.ical4j.model.ParameterTest;
import net.fortuna.ical4j.model.parameter.Gap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.fortuna.ical4j.model.Parameter.GAP;

/**
 * Created on 7/03/2005
 *
 * $Id$
 *
 * @author Ben Fortuna
 *
 */
public class GapTest extends ParameterTest {

    private final Logger log = LoggerFactory.getLogger(GapTest.class);

    private final Gap gap;

	/**
	 * @param testMethod
	 * @param param
	 */
	public GapTest(final String testMethod, final Gap param) {
		super(testMethod, param, GAP, "");
		this.gap = param;
	}

    /**
     * Unit test on a duration trigger.
     */
    public void testGapDuration() {
        assertNotNull(gap.getDuration());
    }

    /**
     * @return
     */
    public static TestSuite suite() {
      final TestSuite suite = new TestSuite();

    	suite.addTest(new GapTest("testGapDuration",
                                new Gap(java.time.Duration.ofDays(1))));
        
    	return suite;
    }
}
