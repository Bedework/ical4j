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
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TemporalAmountAdapter;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.temporal.TemporalAmount;
import java.util.Date;

/**
 * $Id$
 * <p/>
 * Created: [Jul 5, 2021]
 * <p/>
 * Defines an ESTIMATED-DURATION iCalendar component property.
 * <p/>
 * <pre>

 Property Name  ESTIMATED-DURATION

 Purpose  This property specifies the estimated positive duration of
 time the corresponding task will take to complete.

 Value Type  DURATION

 Property Parameters  IANA and non-standard property parameters can be
 specified on this property.

 Conformance  This property can be specified in "VTODO" calendar
 components.

 Format Definition  This property is defined by the following
 notation:

 est-duration  = "ESTIMATED-DURATION" durparam ":" dur-value CRLF
 ;consisting of a positive duration of time.

 durparam      = *(";" other-param)

 Description  In a "VTODO" calendar component the property MAY be used
 to specify the estimated duration for the to-do, with or without
 an explicit time window in which the event should be started and
 completed.  When present, DTSTART and DUE/DURATION represent the
 window in which the task can be performed.  ESTIMATED-DURATION
 SHOULD be passed from ORGANIZER to ATTENDEE in iTIP [RFC5546]
 messages.



 Apthorp, et al.         Expires 10 November 2021               [Page 16]

 Internet-Draft               iCalendar tasks                    May 2021


 Example  The following is an example of this property that specifies
 an interval of time of exactly one hour:

 ESTIMATED-DURATION:PT1H
 * </pre>
 *
 * @author Mike Douglass
 */
public class EstimatedDuration extends Property {

    private static final long serialVersionUID = 9144969653829796798L;

    private TemporalAmountAdapter duration;

    /**
     * Default constructor.
     */
    public EstimatedDuration() {
        super(ESTIMATED_DURATION, new Factory());
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public EstimatedDuration(final ParameterList aList, final String aValue) {
        super(ESTIMATED_DURATION, aList, new Factory());
        setValue(aValue);
    }

    /**
     * @param duration a duration  value
     */
    @Deprecated
    public EstimatedDuration(final Dur duration) {
        this(TemporalAmountAdapter.from(duration).getDuration());
    }

    /**
     * @param duration a duration  value
     */
    public EstimatedDuration(final TemporalAmount duration) {
        super(ESTIMATED_DURATION, new Factory());
        this.duration = new TemporalAmountAdapter(duration);
    }

    /**
     * @param aList    a list of parameters for this component
     * @param duration a duration value
     */
    @Deprecated
    public EstimatedDuration(final ParameterList aList, final Dur duration) {
        this(aList, TemporalAmountAdapter.from(duration).getDuration());
    }

    /**
     * @param aList    a list of parameters for this component
     * @param duration a duration value
     */
    public EstimatedDuration(final ParameterList aList, final TemporalAmount duration) {
        super(ESTIMATED_DURATION, aList, new Factory());
        setDuration(duration);
    }

    /**
     * Constructs a new duration representing the time between the specified start date and end date.
     *
     * @param start the starting time for the duration
     * @param end   the end time for the duration
     */
    public EstimatedDuration(final Date start, final Date end) {
        super(ESTIMATED_DURATION, new Factory());
        setDuration(TemporalAmountAdapter.fromDateRange(start, end).getDuration());
    }

    /**
     * @return Returns the duration.
     */
    public final TemporalAmount getDuration() {
        return duration.getDuration();
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) {
        duration = TemporalAmountAdapter.parse(aValue);
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return duration.toString();
    }

    /**
     * @param duration The duration to set.
     */
    public final void setDuration(final TemporalAmount duration) {
        this.duration = new TemporalAmountAdapter(duration);
    }

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(ESTIMATED_DURATION);
        }

        public Property createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new EstimatedDuration(parameters, value);
        }

        public Property createProperty() {
            return new EstimatedDuration();
        }
    }

}
