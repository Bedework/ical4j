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

import net.fortuna.ical4j.model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a TZUNTIL iCalendar component property defined in tzdist - rfc7808.
 * <p/>
 * <pre>
 *     7.1.  Time Zone Upper Bound
 *
 *     Property Name:  TZUNTIL
 *
 *     Purpose:  This property specifies an upper bound for the validity
 *     period of data within a &quot;VTIMEZONE&quot; component.
 *
 *     Value Type:  DATE-TIME
 *
 *     Property Parameters:  IANA and non-standard property parameters can
 *     be specified on this property.
 *
 *     Conformance:  This property can be specified zero times or one time
 *     within &quot;VTIMEZONE&quot; calendar components.
 *
 *     Description:  The value MUST be specified in the UTC time format.
 *
 *     Time zone data in a &quot;VTIMEZONE&quot; component might cover only a fixed
 *     period of time.  The start of such a period is clearly indicated
 *     by the earliest observance defined by the &quot;STANDARD&quot; and
 *     &quot;DAYLIGHT&quot; subcomponents.  However, an upper bound on the validity
 *     period of the time zone data cannot be simply derived from the
 *     observance with the latest onset time, and [RFC5545] does not
 *     define a way to get such an upper bound.  This specification
 *     introduces the &quot;TZUNTIL&quot; property for that purpose.  It specifies
 *     an &quot;exclusive&quot; UTC date-time value that indicates the last time at
 *     which the time zone data is to be considered valid.
 *
 *     This property is also used by time zone data distribution servers
 *     to indicate the truncation range end point of time zone data (as
 *     described in Section 3.9).
 *
 *     Format Definition:  This property is defined by the following
 *     notation in ABNF [RFC5234]:
 *
 *     tzuntil      = &quot;TZUNTIL&quot; tzuntilparam &quot;:&quot; date-time CRLF
 *
 *     tzuntilparam = *(&quot;;&quot; other-param)
 * </pre>
 *
 * @author Mike Douglass
 */
public class TzUntil extends UtcProperty {

    private static final long serialVersionUID = 7581197869433744070L;

    /**
     * Default constructor. Initialises the dateTime value to the time of instantiation.
     */
    public TzUntil() {
        super(TZUNTIL, new Factory());
    }

    /**
     * @param aValue a string representation of a DTSTAMP value
     * @throws ParseException if the specified value is not a valid representation
     */
    public TzUntil(final String aValue) throws ParseException {
        this(new ParameterList(), aValue);
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     * @throws ParseException where the specified value string is not a valid date-time/date representation
     */
    public TzUntil(final ParameterList aList, final String aValue)
            throws ParseException {
        super(TZUNTIL, aList, new Factory());
        setValue(aValue);
    }

    /**
     * @param aDate a date representing a date-time
     */
    public TzUntil(final DateTime aDate) {
        super(TZUNTIL, new Factory());
        // time must be in UTC..
        aDate.setUtc(true);
        setDate(aDate);
    }

    /**
     * @param aList a list of parameters for this component
     * @param aDate a date representing a date-time
     */
    public TzUntil(final ParameterList aList, final DateTime aDate) {
        super(TZUNTIL, aList, new Factory());
        // time must be in UTC..
        aDate.setUtc(true);
        setDate(aDate);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(TZUNTIL);
        }

        public Property createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new TzUntil(parameters, value);
        }

        public Property createProperty() {
            return new TzUntil();
        }
    }

}
