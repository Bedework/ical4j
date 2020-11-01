/* ********************************************************************
    Appropriate copyright notice
*/
package net.fortuna.ical4j.model;

import net.fortuna.ical4j.validate.ValidationException;

/**
 * User: mike Date: 10/30/20 Time: 21:57
 */
public class TestComponent extends Component{
  protected TestComponent(final String s) {
    super(s);
  }

  @Override
  public ComponentList<Component> getComponents() {
    return null;
  }

  @Override
  public void validate(final boolean recurse)
          throws ValidationException {

  }
}
