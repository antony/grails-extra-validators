package uk.co.desirableobjects.extravalidators

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

class ConfirmedPasswordConstraint extends AbstractConstraint {

    String PASSWORD_CONFIRMATION_CONSTRAINT = 'confirmedPassword'
    String DEFAULT_NOT_PASSWORD_CONFIRMATION_MESSAGE_CODE = 'default.not.confirmedPassword.message'

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        String confirmationValue = target."${constraintPropertyName}Confirmation"

        if (!validatePasswordConfirmation(target, confirmationValue, propertyValue)) {
            Object[] args = [constraintPropertyName, constraintOwningClass, confirmationValue]
            super.rejectValue(target, errors, DEFAULT_NOT_PASSWORD_CONFIRMATION_MESSAGE_CODE,
                "not.${PASSWORD_CONFIRMATION_CONSTRAINT}", args)
        }
    }

    boolean validatePasswordConfirmation(target, confirmationValue, propertyValue) {
        return confirmationValue == propertyValue
    }

    @Override
    boolean supports(Class type) {
        return type != null && String.class.isAssignableFrom(type)
    }

    @Override
    String getName() {
        return PASSWORD_CONFIRMATION_CONSTRAINT
    }
}
