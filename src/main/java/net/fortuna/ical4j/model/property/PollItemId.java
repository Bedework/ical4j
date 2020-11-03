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

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * $Id: PollItemId.java,v 1.14 2010/03/06 12:57:23 fortuna Exp $
 * 
 * Created: [Apr 6, 2004]
 *
 * Defines a POLL-ITEM-ID iCalendar component property.
 * @author benf
 */
public class PollItemId extends Property {

    private static final long serialVersionUID = -1765522613173314831L;

    private int pollitemid;

    /**
     * Default constructor.
     */
    public PollItemId() {
        super(POLL_ITEM_ID, new ParticipantType.Factory());
    }

    /**
     * @param aList a list of parameters for this component
     * @param aValue a value string for this component
     */
    public PollItemId(final ParameterList aList, final String aValue) {
        super(POLL_ITEM_ID, aList, new ParticipantType.Factory());
        setValue(aValue);
    }

    /**
     * @param val a poll item id
     */
    public PollItemId(final int val) {
        super(POLL_ITEM_ID, new ParticipantType.Factory());
        pollitemid = val;
    }

    /**
     * @param aList a list of parameters for this component
     * @param val a poll item id
     */
    public PollItemId(final ParameterList aList, final int val) {
        super(POLL_ITEM_ID, aList, new ParticipantType.Factory());
        pollitemid = val;
    }

    /**
     * @return Returns the poll item id.
     */
    public final int getPollitemid() {
        return pollitemid;
    }

    /**
     * {@inheritDoc}
     */
    public final void setValue(final String val) {
    	pollitemid = Integer.parseInt(val);
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return String.valueOf(getPollitemid());
    }

    /**
     * @param val The poll item id to set.
     */
    public final void setPollitemid(final int val) {
        this.pollitemid = val;
    }

    /**
     * {@inheritDoc}
     */
    public final void validate() throws ValidationException {
        // TODO: Auto-generated method stub
    }

  public static class Factory extends Content.Factory implements
          PropertyFactory<PollItemId> {
      private static final long serialVersionUID = 1L;
  
      public Factory() {
          super(POLL_ITEM_ID);
      }
  
      public PollItemId createProperty(final ParameterList parameters, final String value)
              throws IOException, URISyntaxException, ParseException {
        return new PollItemId(parameters, value);
      }
  
      public PollItemId createProperty() {
        return new PollItemId();
      }
  }
}
