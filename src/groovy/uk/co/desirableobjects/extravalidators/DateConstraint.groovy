package uk.co.desirableobjects.extravalidators

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors
import java.text.SimpleDateFormat
import java.text.ParseException

class DateConstraint extends AbstractConstraint {

    private static final String DEFAULT_NOT_DATE_MESSAGE_CODE = "default.not.date.message"
    public static final String VALIDATION_DSL_NAME = "date"

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {

        try {
            Date.parse(constraintParameter as String, propertyValue as String)
        } catch (ParseException pe) {
            Object[] args = [constraintPropertyName, constraintOwningClass, constraintParameter]
            super.rejectValue(target, errors, DEFAULT_NOT_DATE_MESSAGE_CODE,
                "not.${VALIDATION_DSL_NAME}", args)
        }

    }

    @Override
    public void setParameter(Object constraintParameter) {
        if(constraintParameter instanceof String) {
            super.setParameter(constraintParameter)
        } else {
            throw new IllegalArgumentException("""Parameter for constraint [${VALIDATION_DSL_NAME}] of property
                [${constraintPropertyName}] of class [${constraintOwningClass}] must be a date-formatting String
                 (see http://download.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)""")
        }
    }

    @Override
    boolean supports(Class type) {
        return type == String.class || type.isAssignableFrom(String.class)
    }

    @Override
    String getName() {
        return VALIDATION_DSL_NAME
    }

}
