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

import static net.fortuna.ical4j.model.property.immutable.ImmutableSchedulingAgent.CLIENT;
import static net.fortuna.ical4j.model.property.immutable.ImmutableSchedulingAgent.NONE;
import static net.fortuna.ical4j.model.property.immutable.ImmutableSchedulingAgent.SERVER;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a STATUS iCalendar component property.
 * <p/>
 * <pre>
 Property name
 SCHEDULING-AGENT

 Purpose
     This is who is responsible for sending scheduling messages with this calendar object to the participant.

 Property Parameters
     Non-standard or iana parameters can be specified on this property.

 Conformance
     This property MAY be specified once in the PARTICIPANT component.

 Format Definition
     This property is defined by the following notation:

     scheduling-agent = "SCHEDULING-AGENT"

     scheduling-agentparams ":"
                         ( "SERVER" /
                         "CLIENT" /
                         "NONE") CRLF

 scheduling-agentparams = *(";" other-param)

     The value MUST be one of the following values, another value registered in the IANA "JSCalendar Enum Values" registry, or a vendor-specific value.

 SERVER
     The calendar server will send the scheduling messages.

 CLIENT
     The calendar client will send the scheduling messages.

 NONE
     No scheduling messages are to be sent to this participant.
 * </pre>
 *
 * @author Ben Fortuna
 */
public class SchedulingAgent extends Property {

    public static final String VALUE_SERVER = "SERVER";
    public static final String VALUE_CLIENT = "CLIENT";
    public static final String VALUE_NONE = "NONE";

    private String value;

    /**
     * Default constructor.
     */
    public SchedulingAgent() {
        super(SCHEDULING_AGENT, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public SchedulingAgent(final String aValue) {
        super(SCHEDULING_AGENT, new Factory());
        this.value = aValue;
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public SchedulingAgent(final ParameterList aList, final String aValue) {
        super(SCHEDULING_AGENT, aList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<SchedulingAgent> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(SCHEDULING_AGENT);
        }

        @Override
        public SchedulingAgent createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            if (parameters.isEmpty()) {
                switch (value) {
                    case VALUE_SERVER: return SERVER;
                    case VALUE_CLIENT: return CLIENT;
                    case VALUE_NONE: return NONE;
                }
            }
            return new SchedulingAgent(parameters, value);
        }

        @Override
        public SchedulingAgent createProperty() {
            return new SchedulingAgent();
        }
    }

}
