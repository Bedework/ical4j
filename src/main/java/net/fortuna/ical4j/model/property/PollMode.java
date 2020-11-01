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
 *
 * Defines a POLL-MODE iCalendar property.
 * 
 * <pre>
 * </pre>
 * 
 * @author Ben Fortuna
 */
public class PollMode extends Property {

    private static final long serialVersionUID = 4939943639175551481L;

    /**
     * Constant for basic poll-mode.
     */
    public static final PollMode BASIC = new ImmutablePollMode("BASIC");

    /**
     * @author Ben Fortuna An immutable instance of PollMode.
     */
    private static final class ImmutablePollMode extends PollMode {

        private static final long serialVersionUID = 5978394762293365042L;

        /**
         * @param value
         */
        private ImmutablePollMode(final String value) {
            super(new ParameterList(true), value);
        }

        /**
         * {@inheritDoc}
         */
        public void setValue(final String aValue) {
            throw new UnsupportedOperationException(
                    "Cannot modify constant instances");
        }
    }

    private String value;

    /**
     * Default constructor.
     */
    public PollMode() {
        super(POLL_MODE, new ParticipantType.Factory());
    }

    /**
     * @param aValue a value string for this property
     */
    public PollMode(final String aValue) {
        super(POLL_MODE, new ParticipantType.Factory());
        this.value = aValue;
    }

    /**
     * @param aList a list of parameters for this property
     * @param aValue a value string for this property
     */
    public PollMode(final ParameterList aList, final String aValue) {
        super(POLL_MODE, aList, new ParticipantType.Factory());
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    public void setValue(final String aValue) {
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public final void validate() throws ValidationException {
        // TODO: Auto-generated method stub
    }

    public static class Factory extends Content.Factory implements
            PropertyFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(POLL_ITEM_ID);
        }

        public Property createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new PollMode(parameters, value);
        }

        public Property createProperty() {
            return new PollMode();
        }
    }
}
