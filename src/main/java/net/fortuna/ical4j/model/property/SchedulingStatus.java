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
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a STATUS iCalendar component property.
 * <p/>
 * <pre>
 Property name
 SCHEDULING-STATUS

 Purpose
     This is a list of status codes, returned from the processing of the most recent scheduling message sent to this participant. The status codes MUST be valid statcode values as defined in the ABNF in Section 3.8.8.3 of [RFC5545].

     Servers MUST only add or change this property when they send a scheduling message to the participant. Clients SHOULD NOT change or remove this property if it was provided by the server. Clients MAY add, change, or remove the property for participants where the client is handling the scheduling.This property MUST NOT be included in scheduling messages.

 Property Parameters
     Non-standard or iana parameters can be specified on this property.

 Conformance
     This property MAY be specified in any appropriate component.

 Format Definition
     This property is defined by the following notation:

 scheduling-status = "SCHEDULING-STATUS"

 scheduling-statusparams ":" TEXT CRLF

 scheduling-statusparams = *(";" other-param)
 * </pre>
 *
 * @author Ben Fortuna
 */
public class SchedulingStatus extends Property {

    private String value;

    /**
     * Default constructor.
     */
    public SchedulingStatus() {
        super(SCHEDULING_STATUS, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public SchedulingStatus(final String aValue) {
        super(SCHEDULING_STATUS, new Factory());
        this.value = aValue;
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public SchedulingStatus(final ParameterList aList, final String aValue) {
        super(SCHEDULING_STATUS, aList, new Factory());
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final String aValue) {
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getValue() {
        return value;
    }

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory implements PropertyFactory<SchedulingStatus> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(STATUS);
        }

        @Override
        public SchedulingStatus createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            return new SchedulingStatus(parameters, value);
        }

        @Override
        public SchedulingStatus createProperty() {
            return new SchedulingStatus();
        }
    }

}
