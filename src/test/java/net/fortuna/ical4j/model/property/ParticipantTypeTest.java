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
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyTest;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.Calendars;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * $Id$
 *
 * Created on 17/03/2007
 *
 * Unit testing for {@link ParticipantType}.
 * @author Ben Fortuna
 */
public class ParticipantTypeTest extends PropertyTest {

    private ParticipantType value;
    private int expectedParticipantTypes;


    /**
     * @param property
     * @param expectedValue
     */
    public ParticipantTypeTest(ParticipantType property,
                               String expectedValue) {
        super(property, expectedValue);
    }

    /**
     * @param testMethod
     * @param property
     */
    public ParticipantTypeTest(String testMethod,
                               ParticipantType property) {
        super(testMethod, property);
    }

    /**
     * @param testMethod
     * @param property
     */
    public ParticipantTypeTest(String testMethod,
                               ParticipantType property,
                               int expectedCategories) {
        super(testMethod, property);
        this.value = property;
        this.expectedParticipantTypes = expectedCategories;
    }

    /**
     * Test escaping of commas in categories.
     */
    public void testCommaEscaping() throws ValidationException, IOException,
            ParserException {
        final ParticipantType ptype = new ParticipantType("test1,test2,test 1\\,2\\,3");

        final VEvent event = new VEvent();
        event.getProperties().add(ptype);

        Calendar calendar = new Calendar();
        calendar.getComponents().add(event);

        final StringWriter tempOut = new StringWriter();
        final CalendarOutputter cout = new CalendarOutputter(false);
        cout.output(calendar, tempOut);

        final CalendarBuilder builder = new CalendarBuilder();
        calendar = builder.build(
                new StringReader(tempOut.getBuffer()
                .toString()));

        final ParticipantType evptype =
                calendar.getComponent(Component.VEVENT)
                .getProperty(Property.PARTICIPANT_TYPE);

        assertEquals(evptype, ptype);
        assertEquals(3, ptype.getTypes().size());
    }

    /**
     * Test escaping of commas in categories.
     */
    public void testCommaEscapingCount() throws ValidationException, IOException,
            ParserException {

        assertEquals(expectedParticipantTypes,
                     value.getTypes().size());
    }

    /**
     * @return
     * @throws ValidationException
     * @throws IOException
     * @throws ParserException
     */
    public static TestSuite suite() throws IOException, ValidationException,
            ParserException {
        final TestSuite suite = new TestSuite();
        final String list = "voter,owner,attendee";
        final ParticipantType ptypes = new ParticipantType(list);
        suite.addTest(new ParticipantTypeTest(ptypes, list));

        // Test escaping of ParticipantType string representation..
        Calendar calendar = Calendars.load(
                ParticipantTypeTest.class.getResource("/samples/valid/participanttype.ics"));
        final ParticipantType orig = calendar.getComponent(
                Component.VEVENT).getProperty(
                        Property.PARTICIPANT_TYPE);

        final StringWriter tempOut = new StringWriter();
        final CalendarOutputter cout = new CalendarOutputter();
        cout.output(calendar, tempOut);

        final CalendarBuilder builder = new CalendarBuilder();
        calendar = builder.build(
                new StringReader(tempOut.getBuffer()
                .toString()));

        final ParticipantType copy = calendar.getComponent(
                Component.VEVENT).getProperty(Property.PARTICIPANT_TYPE);
        assertEquals(orig, copy);
        suite.addTest(new ParticipantTypeTest(copy, orig.getValue()));

        // other tests..
        suite.addTest(new ParticipantTypeTest("testCommaEscaping", null));
        suite.addTest(new ParticipantTypeTest("testCommaEscapingCount", new ParticipantType("a\\,b"), 1));
        suite.addTest(new ParticipantTypeTest("testCommaEscapingCount", new ParticipantType("a,b\\,c"), 2));

        return suite;
    }
}
