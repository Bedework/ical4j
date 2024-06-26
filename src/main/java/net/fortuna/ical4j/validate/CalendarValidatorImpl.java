package net.fortuna.ical4j.validate;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Color;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Image;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Name;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RefreshInterval;
import net.fortuna.ical4j.model.property.Source;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.model.property.immutable.ImmutableMethod;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import net.fortuna.ical4j.util.CompatibilityHints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by fortuna on 13/09/15.
 */
public class CalendarValidatorImpl implements Validator<Calendar> {

    protected final List<Class<? extends Property>> calendarProperties = new ArrayList<>();

    private final List<ValidationRule> rules;

    public CalendarValidatorImpl(ValidationRule... rules) {
        this.rules = Arrays.asList(rules);

        Collections.addAll(calendarProperties, CalScale.class, Method.class, ProdId.class, Version.class,
                Uid.class, LastModified.class, Url.class, RefreshInterval.class, Source.class, Color.class,
                Name.class, Description.class, Categories.class, Image.class);
    }

    @Override
    public void validate(Calendar target) throws ValidationException {
        for (ValidationRule rule : rules) {
            if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)
                    && rule.isRelaxedModeSupported()) {
                continue;
            }

            switch (rule.getType()) {
                case None:
                    rule.getInstances().forEach(s -> PropertyValidator.assertNone(s,
                            target.getProperties()));
                    break;
                case One:
                    rule.getInstances().forEach(s -> PropertyValidator.assertOne(s,
                            target.getProperties()));
                    break;
                case OneOrLess:
                    rule.getInstances().forEach(s -> PropertyValidator.assertOneOrLess(s,
                            target.getProperties()));
                    break;
                case OneOrMore:
                    rule.getInstances().forEach(s -> PropertyValidator.assertOneOrMore(s,
                            target.getProperties()));
                    break;
            }
        }

        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
            // require VERSION:2.0 for RFC2445..
            if (!ImmutableVersion.VERSION_2_0.equals(target.getProperty(Property.VERSION))) {
                throw new ValidationException("Unsupported Version: " + target.getProperty(Property.VERSION).getValue());
            }
        }

        // must contain at least one component
        if (target.getComponents().isEmpty()) {
            throw new ValidationException(
                    "Calendar must contain at least one component");
        }

        // validate properties..
        for (final Property property : target.getProperties()) {
            boolean isCalendarProperty = calendarProperties.stream().filter(calProp -> calProp.isInstance(property)) != null;

            if (!(property instanceof XProperty) && !isCalendarProperty) {
                throw new ValidationException("Invalid property: " + property.getName());
            }
        }

//        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
        // validate method..
        final Method method = target.getProperty(Property.METHOD);
        if (ImmutableMethod.PUBLISH.equals(method)) {
            new PublishValidator().validate(target);
        }
        else if (ImmutableMethod.REQUEST.equals(target.getProperty(Property.METHOD))) {
            new RequestValidator().validate(target);
        }
        else if (ImmutableMethod.REPLY.equals(target.getProperty(Property.METHOD))) {
            new ReplyValidator().validate(target);
        }
        else if (ImmutableMethod.ADD.equals(target.getProperty(Property.METHOD))) {
            new AddValidator().validate(target);
        }
        else if (ImmutableMethod.CANCEL.equals(target.getProperty(Property.METHOD))) {
            new CancelValidator().validate(target);
        }
        else if (ImmutableMethod.REFRESH.equals(target.getProperty(Property.METHOD))) {
            new RefreshValidator().validate(target);
        }
        else if (ImmutableMethod.COUNTER.equals(target.getProperty(Property.METHOD))) {
            new CounterValidator().validate(target);
        }
        else if (ImmutableMethod.DECLINE_COUNTER.equals(target.getProperty(Property.METHOD))) {
            new DeclineCounterValidator().validate(target);
        }
//        }

        // perform ITIP validation on components..
        if (method != null) {
            for (CalendarComponent component : target.getComponents()) {
                component.validate(method);
            }
        }
    }

    public static class PublishValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());

                if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
                    ComponentValidator.assertNone(Component.VTODO, target.getComponents());
                }
            }
            else if (target.getComponent(Component.VFREEBUSY) != null) {
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, target.getComponents());
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
//                    ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                    ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
            }
//                else if (target.getComponent(Component.VJOURNAL) != null) {
//                    ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                    ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
//                    ComponentValidator.assertNone(Component.VTODO, target.getComponents());
//                }
        }
    }

    public static class RequestValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
            else if (target.getComponent(Component.VFREEBUSY) != null) {
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, target.getComponents());
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
//                  ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
            }
        }
    }

    public static class ReplyValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, target.getComponents());

                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
            else if (target.getComponent(Component.VFREEBUSY) != null) {
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, target.getComponents());
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, target.getComponents());

                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
//                  ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
            }
        }
    }

    public static class AddValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
            }
            else if (target.getComponent(Component.VJOURNAL) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, target.getComponents());

                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
//                  ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
        }
    }

    public static class CancelValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, target.getComponents());

                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
            }
            else if (target.getComponent(Component.VJOURNAL) != null) {
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
//                  ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
        }
    }

    public static class RefreshValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, target.getComponents());
            }
        }
    }

    public static class CounterValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, target.getComponents());

                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
            }
        }
    }

    public static class DeclineCounterValidator implements Validator<Calendar> {

        @Override
        public void validate(Calendar target) throws ValidationException {
            if (target.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
                ComponentValidator.assertNone(Component.VTODO, target.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, target.getComponents());
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
            }
            else if (target.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertNone(Component.VALARM, target.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, target.getComponents());
//                  ComponentValidator.assertNone(Component.VEVENT, target.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, target.getComponents());
            }
        }
    }
}
