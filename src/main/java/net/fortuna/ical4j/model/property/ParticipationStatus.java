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
import net.fortuna.ical4j.model.property.immutable.ImmutableParticipationStatus;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import static net.fortuna.ical4j.model.property.immutable.ImmutableParticipationStatus.ACCEPTED;
import static net.fortuna.ical4j.model.property.immutable.ImmutableParticipationStatus.DECLINED;
import static net.fortuna.ical4j.model.property.immutable.ImmutableParticipationStatus.DELEGATED;
import static net.fortuna.ical4j.model.property.immutable.ImmutableParticipationStatus.IN_PROCESS;
import static net.fortuna.ical4j.model.property.immutable.ImmutableParticipationStatus.TENTATIVE;

/**
 * $Id$
 * <p/>
 * Created: [May 26, 2024]
 * <p/>
 * Defines a STATUS iCalendar component property.
 * <p/>
 * <pre>
     participation-status = "PARTICIPATION-STATUS"

     participation-statusparams ":"
             NEEDS-ACTION / ; No status
                            ; has yet been set by the participant.

             ACCEPTED /     ; The invited
                            ; participant will participate.

             DECLINED /     ; The invited
                            ; participant will not participate.

             TENTATIVE /    ; The invited participant
                            ; may participate.

             DELEGATED /    ; The invited participant
                            ; has delegated their attendance to
                            ; another participant, as specified
                            ; in the PARTICIPATION-DELEGATED-TO property.

             iana-token ("," iana-token) CRLF

     participation-statusparams = *(";" other-param)
 * </pre>
 *
 * @author Mike Douglass
 */
public class ParticipationStatus extends Property {

    private static final long serialVersionUID = 7401102230299289898L;

    public static final String VALUE_NEEDS_ACTION = "NEEDS-ACTION";

    public static final String VALUE_ACCEPTED = "ACCEPTED";

    public static final String VALUE_DECLINED = "DECLINED";

    public static final String VALUE_TENTATIVE = "TENTATIVE";

    public static final String VALUE_DELEGATED = "DELEGATED";

    public static final String VALUE_COMPLETED = "COMPLETED";

    public static final String VALUE_IN_PROCESS = "IN-PROCESS";

    private String value;

    /**
     * Default constructor.
     */
    public ParticipationStatus() {
        super(PARTICIPATION_STATUS, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public ParticipationStatus(final String aValue) {
        super(PARTICIPATION_STATUS, new Factory());
        this.value = aValue;
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public ParticipationStatus(final ParameterList aList, final String aValue) {
        super(PARTICIPATION_STATUS, aList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<ParticipationStatus> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(PARTICIPATION_STATUS);
        }

        @Override
        public ParticipationStatus createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            if (parameters.isEmpty()) {
                switch (value) {
                    case VALUE_ACCEPTED: return ACCEPTED;
                    case VALUE_DECLINED: return DECLINED;
                    case VALUE_TENTATIVE: return TENTATIVE;
                    case VALUE_DELEGATED: return DELEGATED;
                    case VALUE_COMPLETED: return ImmutableParticipationStatus.COMPLETED;
                    case VALUE_IN_PROCESS: return IN_PROCESS;
                }
            }
            return new ParticipationStatus(parameters, value);
        }

        @Override
        public ParticipationStatus createProperty() {
            return new ParticipationStatus();
        }
    }

}
