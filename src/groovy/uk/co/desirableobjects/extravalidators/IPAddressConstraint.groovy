package uk.co.desirableobjects.extravalidators

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

class IPAddressConstraint extends AbstractConstraint {

    public static final String VALIDATION_DSL_NAME = 'ipAddress'
    public static final String DEFAULT_NOT_IP_ADDRESS_MESSAGE_CODE = 'default.not.ipAddress.message'

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        if (!validInternetProtocolAddress(constraintParameter, propertyValue)) {
            Object[] args = [constraintPropertyName, constraintOwningClass, propertyValue]
            super.rejectValue(target, errors, DEFAULT_NOT_IP_ADDRESS_MESSAGE_CODE,
                "not.${VALIDATION_DSL_NAME}", args)
        }
    }

    private boolean validInternetProtocolAddress(allowInternal, propertyValue) {
        String providedAddress = propertyValue as String
        Integer[] octets = splitOctets(providedAddress)

        if (!octets) {
            return false
        }

        if (octets.find { it > 255 }) {
            return false
        }

        if (octets[0] < 1) {
            return false
        }
        
        return true
    }

    Integer[] splitOctets(String address) {
        try {
            return address.split("\\.").collect { Integer.parseInt(it) }
        } catch (NumberFormatException nfe) {
            return null
        }
    }

    boolean supports(Class type) {
        return type == String.class
    }

    String getName() {
        return VALIDATION_DSL_NAME
    }
}
