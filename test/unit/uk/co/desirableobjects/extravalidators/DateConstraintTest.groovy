package uk.co.desirableobjects.extravalidators

import org.junit.Test
import org.junit.Before
import org.springframework.validation.Errors
import org.springframework.validation.BeanPropertyBindingResult

class DateConstraintTest {

    DateConstraint dateConstraint = new DateConstraint()
    Errors errors
    String dateOfBirth

    @Before
    void "Configure the validator"() {
        errors = new BeanPropertyBindingResult(this, 'dateOfBirth')
        dateConstraint.with {
            propertyName = 'dateOfBirth'
            owningClass = this.class
        }
    }

    @Test
    void "Check validator dsl name is correct"() {
        assert "date" == dateConstraint.name
    }

    @Test
    void "Supports String type"() {
        assert dateConstraint.supports(String.class)
    }

    @Test
    void "Does not support non-String type"() {
        assert !dateConstraint.supports(Integer.class)
    }

    @Test
    void "Validate date in standard UK format"() {

        dateConstraint.parameter = 'dd/MM/yyyy'
        dateConstraint.processValidate(this, '12/08/1992', errors)
        assert !errors.hasErrors()
    }

    @Test
    void "Validate date in standard US format"() {
        dateConstraint.parameter = 'MM/dd/yyyy'
        dateConstraint.processValidate(this, '08/20/1992', errors)
        assert !errors.hasErrors()
    }

    @Test
    void "Validate invalid date"() {
        dateConstraint.parameter = 'mm/DD/yyyy'
        dateConstraint.processValidate(this, 'ablobbobble', errors)
        assert errors.hasErrors()
    }

    @Test(expected=IllegalArgumentException.class)
    void "Invalid parameter format"() {
        dateConstraint.parameter = 5
    }

}
