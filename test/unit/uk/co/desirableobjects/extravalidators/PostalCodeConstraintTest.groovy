package uk.co.desirableobjects.extravalidators

import org.junit.Test

public class PostalCodeConstraintTest {

    String country = PostalCountry.UK
    PostalCodeConstraint postalCodeConstraint = new PostalCodeConstraint()

    @Test
    void "Validate a valid UK postcode"() {

        assert postalCodeConstraint.validPostalCode(this, 'SE16 7TL')

    }

    @Test
    void "Validate an invalid UK postcode"() {

        assert !postalCodeConstraint.validPostalCode(this, 'abyz dee')

    }

    @Test
    void "Validate a valid CA postcode"() {

        country = PostalCountry.CA
        assert postalCodeConstraint.validPostalCode(this, 'A1A 3G3')

    }

    @Test
    void "Validate an invalid CA postcode"() {

        country = PostalCountry.CA
        assert !postalCodeConstraint.validPostalCode(this, 'A1A Beachfront Avenue')

    }

    @Test
    void "Validate a validUS postcode"() {

        country = PostalCountry.US
        assert postalCodeConstraint.validPostalCode(this, '19945')

    }

    @Test
    void "Validate an invalid US postcode"() {

        country = PostalCountry.US
        assert !postalCodeConstraint.validPostalCode(this, 'A1234')

    }

    @Test(expected = IllegalArgumentException)
    void "Validate an invalid country"() {

        country = 'GB'
        assert !postalCodeConstraint.validPostalCode(this, 'SK7 3AL')

    }

    @Test
    void "Validate that the name of this constraint is correct"() {

        assert postalCodeConstraint.name == 'postalCode'

    }


}
