package uk.co.desirableobjects.extravalidators

import org.junit.Test
import org.junit.Before
import static uk.co.desirableobjects.extravalidators.PostalCountry.*

public class PostalCodeConstraintTest {

    PostalCodeConstraint postalCodeConstraint = new PostalCodeConstraint()

    @Before
    void "Configure the validator"() {
        postalCodeConstraint.parameter = UK
    }
    
    @Test
    void "Validate a valid UK postcode"() {

        assert postalCodeConstraint.validPostalCode(UK, 'SE16 7TL')

    }

    @Test
    void "Validate an invalid UK postcode"() {

        assert !postalCodeConstraint.validPostalCode(UK, 'abyz dee')

    }

    @Test
    void "Validate a valid CA postcode"() {

        assert postalCodeConstraint.validPostalCode(CA, 'A1A 3G3')

    }

    @Test
    void "Validate an invalid CA postcode"() {

        assert !postalCodeConstraint.validPostalCode(CA, 'A1A Beachfront Avenue')

    }

    @Test
    void "Validate a valid US postcode"() {

        assert postalCodeConstraint.validPostalCode(US, '19945')

    }

    @Test
    void "Validate an invalid US postcode"() {

        assert !postalCodeConstraint.validPostalCode(US, 'A1234')

    }

    @Test(expected = IllegalArgumentException)
    void "Validate an invalid country"() {

        postalCodeConstraint.parameter = 'GB'

    }

    @Test
    void "Validate that the name of this constraint is correct"() {

        assert postalCodeConstraint.name == 'postalCode'

    }


}
