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
import net.fortuna.ical4j.model.property.immutable.ImmutableKind;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import static net.fortuna.ical4j.model.property.immutable.ImmutableKind.*;
import static net.fortuna.ical4j.model.property.immutable.ImmutableKind.GROUP;
import static net.fortuna.ical4j.model.property.immutable.ImmutableKind.INDIVIDUAL;

/**
 * $Id$
 * <p/>
 * Created: [May 21, 2024]
 * <p/>
 * Defines a KIND iCalendar component property.
 * <p/>
 * <pre>
 kind = "KIND" kindparams ":"
                 "INDIVIDUAL"   ; An individual
                 / "GROUP"        ; A group of individuals
                 / "RESOURCE"     ; A physical resource
                 / "LOCATION"     ; A location resource e.g a room
                 / iana-token CRLF

 kindparams = *(";" other-param) * </pre>
 *
 * @author Mike Douglass
 */
public class Kind extends Property {

    private static final long serialVersionUID = 7401102230299289898L;

    public static final String VALUE_INDIVIDUAL = "INDIVIDUAL";
    public static final String VALUE_GROUP = "GROUP";
    public static final String VALUE_RESOURCE = "RESOURCE";
    public static final String VALUE_LOCATION = "LOCATION";

    private String value;

    /**
     * Default constructor.
     */
    public Kind() {
        super(KIND, new Factory());
    }

    /**
     * @param aValue a value string for this component
     */
    public Kind(final String aValue) {
        super(KIND, new Factory());
        this.value = aValue;
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     */
    public Kind(final ParameterList aList, final String aValue) {
        super(KIND, aList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<Kind> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(KIND);
        }

        @Override
        public Kind createProperty(final ParameterList parameters, final String value)
                throws IOException, URISyntaxException, ParseException {

            if (parameters.isEmpty()) {
                switch (value.toUpperCase()) {
                    case VALUE_INDIVIDUAL: return INDIVIDUAL;
                    case VALUE_GROUP: return GROUP;
                    case VALUE_RESOURCE: return RESOURCE;
                    case VALUE_LOCATION: return ImmutableKind.LOCATION;
                }
            }
            return new Kind(parameters, value);
        }

        @Override
        public Kind createProperty() {
            return new Kind();
        }
    }

}
