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
        try {
            Integer[] octets = splitOctets(propertyValue as String)
            boolean validatesNumerically = (octets.findAll { it > 255 }.isEmpty() && octets[0] > 0)
            boolean inAllowedRange = allowInternal ? true : !isInternal(octets)
            return validatesNumerically && inAllowedRange
        } catch (NumberFormatException nfe) {
            return false
        }
    }

    private boolean isInternal(Integer[] octets) {
        switch (octets[0]) {
            case 10:
                return true
            case 192:
                return octets[1] == 168
            case 172:
                return (16..32).containsWithinBounds(octets[2])
            default:
                return false
        }
    }

    private Integer[] splitOctets(String address) {
        return address.split("\\.").collect { Integer.parseInt(it) }
    }

    boolean supports(Class type) {
        return type == String.class
    }

    String getName() {
        return VALIDATION_DSL_NAME
    }
}
