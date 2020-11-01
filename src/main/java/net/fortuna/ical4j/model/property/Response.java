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
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$
 * <p/>
 * Created: [Apr 6, 2004]
 * <p/>
 * Defines a RESPONSE iCalendar component property.
 *
 * @author benf
 */
public class Response extends Property {

    private static final long serialVersionUID = 7788138484983240112L;

    private int response;

    /**
     * Default constructor.
     */
    public Response() {
        super(RESPONSE, new ParticipantType.Factory());
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public Response(final ParameterList aList, final String aValue) {
        super(RESPONSE, aList, new ParticipantType.Factory());
        setValue(aValue);
    }

    /**
     * @param response an int 0-100
     */
    public Response(final int response) {
        super(RESPONSE, new ParticipantType.Factory());
        this.response = response;
    }

    /**
     * @param aList       a list of parameters for this component
     * @param response an int 0-100
     */
    public Response(final ParameterList aList, final int response) {
        super(RESPONSE, aList, new ParticipantType.Factory());
        this.response = response;
    }

    /**
     * @return Returns the response.
     */
    public final int getResponse() {
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) {
        response = Integer.parseInt(aValue);
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return String.valueOf(getResponse());
    }

    /**
     * @param val The response to set.
     */
    public final void setResponse(final int val) {
        response = val;
    }

    /**
     * {@inheritDoc}
     */
    public final void validate() throws ValidationException {
        if (response < 0 || response > 100) {
            throw new ValidationException(getName() + " with invalid value: " + response);
        }
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(RESPONSE);
        }

        public Property createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new Response(parameters, value);
        }

        public Property createProperty() {
            return new Response();
        }
    }

}
