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

import net.fortuna.ical4j.model.AddressList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$ [18-Apr-2004]
 * <p/>
 * Defines a Group or List Membership property.
 *
 * @author benfortuna
 */
public class ParticipationDelegatedTo extends Property {

    private static final long serialVersionUID = 287348849443687499L;

    private AddressList groups;

    public ParticipationDelegatedTo() {
        super(PARTICIPATION_DELEGATED_TO, new Factory());
    }

    /**
     * @param aValue a string representation of a group or list membership
     * @throws URISyntaxException when the specified string is not a valid list of (quoted) cal-addresses
     */
    public ParticipationDelegatedTo(final String aValue) throws URISyntaxException {
        super(PARTICIPATION_DELEGATED_TO, new Factory());
        groups = new AddressList(aValue);
    }

    /**
     * @param aValue a string representation of a group or list membership
     * @throws URISyntaxException when the specified string is not a valid list of (quoted) cal-addresses
     */
    public ParticipationDelegatedTo(final ParameterList aList, final String aValue) throws URISyntaxException {
        this(new AddressList(aValue));
    }

    /**
     * @param aList a list of groups
     */
    public ParticipationDelegatedTo(final AddressList aList) {
        super(PARTICIPATION_DELEGATED_TO, new Factory());
        groups = aList;
    }

    /**
     * @return Returns the group addresses.
     */
    public final AddressList getGroups() {
        return groups;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final String aValue)
            throws URISyntaxException {
        groups = new AddressList(aValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getValue() {
        return getGroups().toString();
    }

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory implements
            PropertyFactory<ParticipationDelegatedTo> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(PARTICIPATION_DELEGATED_TO);
        }

        @Override
        public ParticipationDelegatedTo createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            return new ParticipationDelegatedTo(parameters, value);
        }

        @Override
        public ParticipationDelegatedTo createProperty() {
            return new ParticipationDelegatedTo();
        }
    }

}
