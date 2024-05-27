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
import net.fortuna.ical4j.model.property.immutable.ImmutablePollCompletion;
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
 *      5.5.2. Poll-Completion
 *
 *   Property name
 *      POLL-COMPLETION
 *
 *   Purpose
 *      This property is used in VPOLL to indicate whether the client
 *      or server is responsible for choosing and/or submitting the
 *      winner(s).
 *
 *   Description
 *      When a VPOLL is stored on a server which is capable of
 *      handling choosing and submission of winning choices a value
 *      of SERVER indicates that the server should close the poll,
 *      choose the winner and submit whenever it is appropriate to
 *      do so.
 *
 *      For example, in BASIC poll-mode, reaching the DTEND of the
 *      poll could trigger this server side action.
 *
 *      Server initiated submission requires that the submitted choice
 *      MUST be a valid calendaring component.
 *
 *      POLL-COMPLETION=SERVER-SUBMIT allows the client to set the
 *      poll- winner, set the status to CONFIRMED and then store the
 *      poll on the server. The server will then submit the winning
 *      choice and set the status to SUBMITTED.
 *
 *   Format Definition
 *      This property is defined by the following notation:
 *
 *      poll-completion = "POLL-COMPLETION" pcparam ":" pcvalue CRLF
 *
 *      pcparam = *(";" other-param)
 *
 *     pcvalue = "SERVER"  ; The server is responsible for both choosing and
 *                         ; submitting the winner(s)
 *             / "SERVER-SUBMIT" ; The server is responsible for
 *                         ; submitting the winner(s). The client chooses.
 *             / "SERVER-CHOICE"  ; The server is responsible for
 *                         ; choosing the winner(s). The client will submit.
 *             / "CLIENT" ; The client is responsible for both choosing and
 *                         ; submitting the winner(s)
 *             / iana-token
 *             / x-name
 *                         ;Default is CLIENT
 *
 *    Example
 *
 *       The following is an example of this property:
 *
 *    POLL-COMPLETION: SERVER-SUBMIT
 *
 * </pre>
 *
 * @author Mike Douglass
 */
public class PollCompletion extends Property {

    private static final long serialVersionUID = 7401102230299289898L;

    // Poll completion values for a "VPOLL"
    /**
     * Server responsible for choosing and submitting winner.
     */
    public static final String VALUE_SERVER = "SERVER";

    /**
     * Server responsible for submitting winner.
     */
    public static final String VALUE_SERVER_SUBMIT = "SERVER-SUBMIT";

    /**
     * Server responsible for choosing winner.
     */
    public static final String VALUE_SERVER_CHOICE = "SERVER-CHOICE";
    /**
     * Client responsible for choosing and submitting winner.
     */
    public static final String VALUE_CLIENT = "CLIENT";

    private String value;

    /**
     * Default constructor.
     */
    public PollCompletion() {
        super(POLL_COMPLETION, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public PollCompletion(final String aValue) {
        super(POLL_COMPLETION, new Factory());
        this.value = aValue;
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public PollCompletion(final ParameterList aList, final String aValue) {
        super(POLL_COMPLETION, aList, new Factory());
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

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory implements PropertyFactory<PollCompletion> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(POLL_COMPLETION);
        }

        public PollCompletion createProperty(
                final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            final PollCompletion pollCompletion;
            if (ImmutablePollCompletion.POLL_COMPLETION_SERVER
                    .getValue().equals(value)) {
                pollCompletion = ImmutablePollCompletion.POLL_COMPLETION_SERVER;
            } else if (ImmutablePollCompletion.POLL_COMPLETION_SERVER_CHOICE
                    .getValue().equals(value)) {
                pollCompletion = ImmutablePollCompletion.POLL_COMPLETION_SERVER_CHOICE;
            } else if (ImmutablePollCompletion.POLL_COMPLETION_SERVER_SUBMIT
                    .getValue().equals(value)) {
                pollCompletion = ImmutablePollCompletion.POLL_COMPLETION_SERVER_SUBMIT;
            } else if (ImmutablePollCompletion.POLL_COMPLETION_CLIENT
                    .getValue().equals(value)) {
                pollCompletion = ImmutablePollCompletion.POLL_COMPLETION_CLIENT;
            } else {
                pollCompletion = new PollCompletion(parameters, value);
            }
            return pollCompletion;
        }

        public PollCompletion createProperty() {
            return new PollCompletion();
        }
    }

}
