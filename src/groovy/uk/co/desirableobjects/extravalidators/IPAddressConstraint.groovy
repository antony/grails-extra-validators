package uk.co.desirableobjects.extravalidators

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

class IPAddressConstraint extends AbstractConstraint {

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    boolean supports(Class type) {
        return false  //To change body of implemented methods use File | Settings | File Templates.
    }

    String getName() {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
