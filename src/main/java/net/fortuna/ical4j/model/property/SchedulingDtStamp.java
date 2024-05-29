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

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a PARTICIPANT SCHEDULING-DTSTAMP iCalendar component property.
 * <p/>
 * <pre>
 scheduling-dtstamp = "SCHEDULING-DTSTAMP"

 scheduling-dtstampparams ":" DATE-TIME CRLF

 scheduling-dtstampparams = *(";" other-param)
 * </pre>
 *
 * @author Ben Fortuna
 */
public class SchedulingDtStamp extends UtcProperty {

    private static final long serialVersionUID = 7581197869433744070L;

    /**
     * Default constructor. Initialises the dateTime value to the time of instantiation.
     */
    public SchedulingDtStamp() {
        super(SCHEDULING_DTSTAMP, new Factory());
    }

    /**
     * @param aValue a string representation of a DTSTAMP value
     * @throws ParseException if the specified value is not a valid representation
     */
    public SchedulingDtStamp(final String aValue) throws ParseException {
        this(new ParameterList(), aValue);
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     * @throws ParseException where the specified value string is not a valid date-time/date representation
     */
    public SchedulingDtStamp(final ParameterList aList, final String aValue)
            throws ParseException {
        super(SCHEDULING_DTSTAMP, aList, new Factory());
        setValue(aValue);
    }

    /**
     * @param aDate a date representing a date-time
     */
    public SchedulingDtStamp(final DateTime aDate) {
        super(SCHEDULING_DTSTAMP, new Factory());
        // time must be in UTC..
        aDate.setUtc(true);
        setDate(aDate);
    }

    /**
     * @param aList a list of parameters for this component
     * @param aDate a date representing a date-time
     */
    public SchedulingDtStamp(final ParameterList aList, final DateTime aDate) {
        super(SCHEDULING_DTSTAMP, aList, new Factory());
        // time must be in UTC..
        aDate.setUtc(true);
        setDate(aDate);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(SCHEDULING_DTSTAMP);
        }

        @Override
        public Property createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new SchedulingDtStamp(parameters, value);
        }

        @Override
        public Property createProperty() {
            return new SchedulingDtStamp();
        }
    }

}
