/*
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
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a PARTICIPANT-TYPE iCalendar component property.
 *
 * @author benf
 * @author Mike Douglass
 */
public class ParticipantType extends Property implements Encodable {

    private static final long serialVersionUID = 7753849118575885600L;

    public static final String VALUE_OWNER = "OWNER";

    public static final String VALUE_ATTENDEE = "ATTENDEE";

    public static final String VALUE_OPTIONAL = "OPTIONAL";

    public static final String VALUE_INFORMATIONAL = "INFORMATIONAL";

    public static final String VALUE_CHAIR = "CHAIR";

    public static final String VALUE_ACTIVE = "ACTIVE";

    public static final String VALUE_INACTIVE = "INACTIVE";

    public static final String VALUE_SPONSOR = "SPONSOR";

    public static final String VALUE_CONTACT = "CONTACT";

    public static final String VALUE_BOOKING_CONTACT = "BOOKING-CONTACT";

    public static final String VALUE_EMERGENCY_CONTACT = "EMERGENCY-CONTACT";

    public static final String VALUE_PUBLICITY_CONTACT = "PUBLICITY-CONTACT";

    public static final String VALUE_PLANNER_CONTACT = "PLANNER-CONTACT";

    public static final String VALUE_PERFORMER = "PERFORMER";

    public static final String VALUE_SPEAKER = "SPEAKER";

    public static final String VALUE_VOTER = "VOTER";

    private TextList types;

    /**
     * Default constructor.
     */
    public ParticipantType() {
        super(PARTICIPANT_TYPE, new ParameterList(), new Factory());
        types = new TextList();
    }

    /**
     * @param aValue a value string for this component
     */
    public ParticipantType(final String aValue) {
        this(new ParameterList(), aValue);
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public ParticipantType(final ParameterList aList, final String aValue) {
        super(PARTICIPANT_TYPE, aList, new Factory());
        setValue(aValue);
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) {
        types = new TextList(aValue);
    }

    /**
     * @return Returns the types.
     */
    public final TextList getTypes() {
        return types;
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return types.toString();
    }

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory implements PropertyFactory<ParticipantType> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(PARTICIPANT_TYPE);
        }

        public ParticipantType createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new ParticipantType(parameters, value);
        }

        public ParticipantType createProperty() {
            return new ParticipantType();
        }
    }

}
