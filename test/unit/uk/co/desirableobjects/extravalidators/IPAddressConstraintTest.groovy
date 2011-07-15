package uk.co.desirableobjects.extravalidators

import org.junit.Before
import org.junit.Test
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Errors

class IPAddressConstraintTest {

    static final String VALIDATOR_NAME = 'ipAddress'
    IPAddressConstraint ipAddressConstraint = new IPAddressConstraint()
    Errors errors
    String ipAddress

    @Before
    void "Configure the validator"() {
        errors = new BeanPropertyBindingResult(this, VALIDATOR_NAME)
        ipAddressConstraint.with {
            propertyName = VALIDATOR_NAME
            parameter = true
            owningClass = this.class
        }
    }

    @Test
    void "Check the validator name"() {
        assert VALIDATOR_NAME == ipAddressConstraint.name
    }

    @Test
    void "Check that the validator only supports Strings"() {
        assert !ipAddressConstraint.supports(Number.class)
        assert !ipAddressConstraint.supports(Object.class)
        assert ipAddressConstraint.supports(String.class)
    }

    @Test
    void "Validate a valid IP address"() {

        ipAddressConstraint.processValidate(this, '1.2.3.4', errors)
        assert !errors.hasErrors()

    }

    @Test
    void "Invalidate an IP which has a non numeric octet"() {

        ipAddressConstraint.processValidate(this, '1.2.do.4', errors)
        assert errors.hasErrors()

    }

    @Test
    void "Invalidate an IP which has more than one non numeric octet"() {

        ipAddressConstraint.processValidate(this, 're.2.do.4', errors)
        assert errors.hasErrors()

    }

    @Test
    void "Invalidate a String which doesn't represent an ip"() {

        ipAddressConstraint.processValidate(this, 'abcdefghj', errors)
        assert errors.hasErrors()

    }

    @Test
    void "Invalidate an IP which has one octet too high"() {

        ipAddressConstraint.processValidate(this, '12.34.56.256', errors)
        assert errors.hasErrors()

    }

    @Test
    void "Validate a netmask"() {

        ipAddressConstraint.processValidate(this, '255.255.255.255', errors)
        assert !errors.hasErrors()

    }

    @Test
    void "Invalidate an IP which has an invalid initial octet"() {

        ipAddressConstraint.processValidate(this, '0.34.3.78', errors)
        assert errors.hasErrors()

    }

    @Test
    void "Validate an IP which has a zero in it"() {

        ipAddressConstraint.processValidate(this, '1.2.0.4', errors)
        assert !errors.hasErrors()

    }

    @Test
    void "Invalidate an internal 10. IP when the allowInternal parameter is set to false"() {

        ipAddressConstraint.parameter = false
        ipAddressConstraint.processValidate(this, '10.243.20.40', errors)
        assert errors.hasErrors()

    }

    @Test
    void "Invalidate an internal 172. IP when the allowInternal parameter is set to false"() {

        ipAddressConstraint.parameter = false
        ipAddressConstraint.processValidate(this, '172.16.0.0', errors)
        assert !errors.hasErrors()

    }

    @Test
    void "Invalidate the latter end of the 172 internal range"() {

        ipAddressConstraint.parameter = false
        ipAddressConstraint.processValidate(this, '172.31.255.255', errors)
        assert !errors.hasErrors()

    }

    @Test
    void "Validate a 172. ip address which is just outside the disallowed range"() {

        ipAddressConstraint.parameter = false
        ipAddressConstraint.processValidate(this, '172.32.0.0', errors)
        assert !errors.hasErrors()

    }

    @Test
    void "Invalidate an internal 192.168. IP when the allowInternal parameter is set to false"() {

        ipAddressConstraint.parameter = false
        ipAddressConstraint.processValidate(this, '10.243.20.40', errors)
        assert errors.hasErrors()

    }

}
