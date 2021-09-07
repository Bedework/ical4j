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
package net.fortuna.ical4j.model.parameter;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * $Id$ [18-Apr-2004]
 * <p/>
 * Defines a Relationship Type parameter.
 *
 * @author benfortuna
 */
public class RelType extends Parameter {

    private static final long serialVersionUID = 5346030888832899016L;

    /* rfc5545 */

    private static final String VALUE_PARENT = "PARENT";

    private static final String VALUE_CHILD = "CHILD";

    private static final String VALUE_SIBLING = "SIBLING";

    /* relations draft temporal */

    private static final String VALUE_FINISHTOSTART = "FINISHTOSTART";

    private static final String VALUE_FINISHTOFINISH = "FINISHTOFINISH";

    private static final String VALUE_STARTTOFINISH = "STARTTOFINISH";

    private static final String VALUE_STARTTOSTART = "STARTTOSTART";

    /* relations draft others */

    private static final String VALUE_DEPENDS_ON = "DEPENDS-ON";

    private static final String VALUE_REFID = "REFID";

    private static final String VALUE_CONCEPT = "CONCEPT";

    /**
     * Parent.
     */
    public static final RelType PARENT = new RelType(VALUE_PARENT);

    /**
     * Child.
     */
    public static final RelType CHILD = new RelType(VALUE_CHILD);

    /**
     * Sibling.
     */
    public static final RelType SIBLING = new RelType(VALUE_SIBLING);

    /* relations draft temporal */

    /**
     * Finish to start.
     */
    public static final RelType FINISHTOSTART =
            new RelType(VALUE_FINISHTOSTART);

    /**
     * Finish to finish.
     */
    public static final RelType FINISHTOFINISH =
            new RelType(VALUE_FINISHTOFINISH);

    /**
     * Start to finish.
     */
    public static final RelType STARTTOFINISH =
            new RelType(VALUE_STARTTOFINISH);

    /**
     * Start to start.
     */
    public static final RelType STARTTOSTART =
            new RelType(VALUE_STARTTOSTART);

    /* relations draft others */

    /**
     * depends-on.
     */
    public static final RelType DEPENDS_ON =
            new RelType(VALUE_DEPENDS_ON);

    /**
     * refid.
     */
    public static final RelType REFID = new RelType(VALUE_REFID);

    /**
     * concept.
     */
    public static final RelType CONCEPT = new RelType(VALUE_CONCEPT);

    private final static Map<String, RelType> reltypes = new HashMap<>();

    static {
        reltypes.put(VALUE_PARENT, PARENT);
        reltypes.put(VALUE_CHILD, CHILD);
        reltypes.put(VALUE_SIBLING, SIBLING);

        reltypes.put(VALUE_FINISHTOSTART, FINISHTOSTART);
        reltypes.put(VALUE_FINISHTOFINISH, FINISHTOFINISH);
        reltypes.put(VALUE_STARTTOFINISH, STARTTOFINISH);
        reltypes.put(VALUE_STARTTOSTART, STARTTOSTART);

        reltypes.put(VALUE_DEPENDS_ON, DEPENDS_ON);
        reltypes.put(VALUE_REFID, REFID);
        reltypes.put(VALUE_CONCEPT, CONCEPT);
    }

    private String value;

    /**
     * @param aValue a string representation of a relationship type
     */
    public RelType(final String aValue) {
        super(RELTYPE, new Factory());
        this.value = Strings.unquote(aValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory
            implements ParameterFactory<Parameter> {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(RELTYPE);
        }

        @Override
        public Parameter createParameter(final String value)
                throws URISyntaxException {
            final RelType parameter = reltypes.get(value.toUpperCase());

            if (parameter != null) {
                return parameter;
            }

            return new RelType(value);
        }
    }

}
