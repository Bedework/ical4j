/**
 * Copyright (c) 2010, Ben Fortuna
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
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 *
 * Defines an POLL-PROPERTIES iCalendar component property.
 * <pre>
 * </pre>
 * @author benf
 * @author mike douglass
 */
public class PollProperties extends Property {

    private static final long serialVersionUID = -7769987073466681634L;

    private TextList properties;

    /**
     * Default constructor.
     */
    public PollProperties() {
        super(POLL_PROPERTIES, new ParticipantType.Factory());
        properties = new TextList();
    }

    /**
     * @param aValue a value string for this component
     */
    public PollProperties(final String aValue) {
        super(POLL_PROPERTIES, new ParticipantType.Factory());
        setValue(aValue);
    }

    /**
     * @param aList a list of parameters for this component
     * @param aValue a value string for this component
     */
    public PollProperties(final ParameterList aList, final String aValue) {
        super(POLL_PROPERTIES, aList, new ParticipantType.Factory());
        setValue(aValue);
    }

    /**
     * @param cList a list of component names
     */
    public PollProperties(final TextList cList) {
        super(POLL_PROPERTIES, new ParticipantType.Factory());
        properties = cList;
    }

    /**
     * @param aList a list of parameters for this Property
     * @param val a list of Property names
     */
    public PollProperties(final ParameterList aList, 
    		final TextList val) {
        super(POLL_PROPERTIES, aList, new ParticipantType.Factory());
        properties = val;
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) {
    	properties = new TextList(aValue);
    }

    /**
     * {@inheritDoc}
     */
    public final void validate() throws ValidationException {
        /*
         * ; the following is optional, ; and MAY occur more than once (";" xparam)
         */
    }

    /**
     * @return Returns the Property names.
     */
    public final TextList getPropertyNames() {
        return properties;
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return getPropertyNames().toString();
    }

    public static class Factory extends Content.Factory implements
            PropertyFactory<PollProperties> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(POLL_PROPERTIES);
        }

        public PollProperties createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new PollProperties(parameters, value);
        }

        public PollProperties createProperty() {
            return new PollProperties();
        }
    }
}
