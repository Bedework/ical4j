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
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id$
 * <p/>
 * Created: [Jan 26, 2021]
 * <p/>
 * Defines a CONCEPT iCalendar relations property.
 * <p/>
 * <pre>
 Property name:  CONCEPT

 Purpose:  This property defines the formal categories for a calendar
 component.

 Value type:  URI

 Property Parameters:  IANA, and non-standard parameters can be
 specified on this property.

 Conformance:  This property can be specified zero or more times in
 any iCalendar component.

 Description:  This property is used to specify formal categories or
 classifications of the calendar component.  The values are useful
 in searching for a calendar component of a particular type and
 category.

 Within the "VEVENT", "VTODO", or "VJOURNAL" calendar components,
 more than one formal category can be specified by using multiple
 CONCEPT properties.

 This categorization is distinct from the more informal "tagging"
 of components provided by the existing CATEGORIES property.  It is
 expected that the value of the CONCEPT property will reference an
 external resource which provides information about the
 categorization.

 In addition, a structured URI value allows for hierarchical
 categorization of events.

 Possible category resources are the various proprietary systems,
 for example Library of Congress, or an open source of
 categorisation data.

 Format Definition:

 This property is defined by the following notation:

 concept        =  "CONCEPT" conceptparam ":"
                   uri CRLF

 conceptparam = *(";" other-param)


 Example:

 The following is an example of this property.  It points to a server
 acting as the source for the calendar object.

 CONCEPT:http://example.com/event-types/arts/music
 * </pre>
 *
 * @author Ben Fortuna
 */
public class Concept extends Property {

    private static final long serialVersionUID = 1092576402256525737L;

    private URI uri;

    /**
     * Default constructor.
     */
    public Concept() {
        super(URL, new Factory());
    }

    /**
     * @param aList  a list of parameters for this component
     * @param aValue a value string for this component
     * @throws URISyntaxException where the specified value string is not a valid uri
     */
    public Concept(final ParameterList aList, final String aValue)
            throws URISyntaxException {
        super(CONCEPT, aList, new Factory());
        setValue(aValue);
    }

    /**
     * @param aUri a URI
     */
    public Concept(final URI aUri) {
        super(CONCEPT, new Factory());
        uri = aUri;
    }

    /**
     * @param aList a list of parameters for this component
     * @param aUri  a URI
     */
    public Concept(final ParameterList aList, final URI aUri) {
        super(CONCEPT, aList, new Factory());
        uri = aUri;
    }

    /**
     * @return Returns the uri value.
     */
    public final URI getConcept() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String aValue) throws URISyntaxException {
        uri = Uris.create(aValue);
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return Uris.decode(Strings.valueOf(getConcept()));
    }

    /**
     * @param uri The uri to set.
     */
    public final void setUri(final URI uri) {
        this.uri = uri;
    }

    @Override
    public void validate() throws ValidationException {

    }

    public static class Factory extends Content.Factory
            implements PropertyFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(CONCEPT);
        }

        public Property createProperty(final ParameterList parameters,
                                       final String value)
                throws IOException, URISyntaxException, ParseException {
            return new Concept(parameters, value);
        }

        public Property createProperty() {
            return new Concept();
        }
    }

}
