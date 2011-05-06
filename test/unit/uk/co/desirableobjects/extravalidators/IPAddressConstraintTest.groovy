package uk.co.desirableobjects.extravalidators

import org.junit.Before
import org.junit.Test

class IPAddressConstraintTest {

    IPAddressConstraint ipAddressConstraint = new IPAddressConstraint()

    @Before
    void "Configure the validator"() {
        ipAddressConstraint.parameter = true
    }

    @Test
    void "Check the validator name"() {
        'ipAddress' == ipAddressConstraint.name
    }

    @Test
    void "Validate a valid IP address"() {

        assert ipAddressConstraint.

    }

}
