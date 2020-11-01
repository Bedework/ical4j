package net.fortuna.ical4j.validate.component;

import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

import static net.fortuna.ical4j.model.Component.VALARM;
import static net.fortuna.ical4j.model.Property.*;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.One;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;

public class VEventValidator extends ComponentValidator<VEvent> {

    private final Validator<VAlarm> itipValidator = new ComponentValidator<>(new ValidationRule(One, ACTION, TRIGGER),
            new ValidationRule(OneOrLess, DESCRIPTION, DURATION, REPEAT, SUMMARY));

    private final boolean alarmsAllowed;

    public VEventValidator(ValidationRule... rules) {
        this(true, rules);
    }

    public VEventValidator(boolean alarmsAllowed, ValidationRule... rules) {
        super(rules);
        this.alarmsAllowed = alarmsAllowed;
    }

    @Override
    public void validate(VEvent target) throws ValidationException {
        super.validate(target);

        if (alarmsAllowed) {
            target.getAlarms().forEach(itipValidator::validate);
        } else {
            ComponentValidator.assertNone(VALARM, target.getAlarms());
        }
    }
}
