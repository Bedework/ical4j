package net.fortuna.ical4j.validate.component;

import net.fortuna.ical4j.model.component.VPoll;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.ValidationRule;

public class VPollValidator extends ComponentValidator<VPoll> {
    public VPollValidator(ValidationRule... rules) {
        super(rules);
    }
}
