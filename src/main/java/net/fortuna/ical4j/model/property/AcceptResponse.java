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
 * $Id: Categories.java,v 1.18 2010/03/06 12:57:23 fortuna Exp $
 * 
 * Created: [Apr 6, 2004]
 *
 * Defines an ACCEPT-RESPONSE iCalendar component property.
 * <pre>
 * </pre>
 * @author benf
 * @author mike douglass
 */
public class AcceptResponse extends Property {

    private static final long serialVersionUID = -7769987073466681634L;

    private TextList components;

    /**
     * Default constructor.
     */
    public AcceptResponse() {
        super(ACCEPT_RESPONSE, new Factory());
        components = new TextList();
    }

    /**
     * @param aValue a value string for this component
     */
    public AcceptResponse(final String aValue) {
        super(ACCEPT_RESPONSE, new Factory());
        setValue(aValue);
    }

    /**
     * @param aList a list of parameters for this component
     * @param aValue a value string for this component
     */
    public AcceptResponse(final ParameterList aList, final String aValue) {
        super(ACCEPT_RESPONSE, aList, new Factory());
        setValue(aValue);
    }

    /**
     * @param cList a list of component names
     */
    public AcceptResponse(final TextList cList) {
        super(ACCEPT_RESPONSE, new Factory());
        components = cList;
    }

    /**
     * @param aList a list of parameters for this component
     * @param cList a list of component names
     */
    public AcceptResponse(final ParameterList aList, 
    		final TextList cList) {
        super(ACCEPT_RESPONSE, aList, new Factory());
        components = cList;
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) {
    	components = new TextList(aValue);
    }

    /**
     * @return Returns the component names.
     */
    public final TextList getComponentNames() {
        return components;
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return getComponentNames().toString();
    }

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory implements
            PropertyFactory<AcceptResponse> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(ACCEPT_RESPONSE);
        }

        public AcceptResponse createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {
            return new AcceptResponse(parameters, value);
        }

        public AcceptResponse createProperty() {
            return new AcceptResponse();
        }
    }
}
