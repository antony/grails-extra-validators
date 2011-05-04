package uk.co.desirableobjects.extravalidators

import org.junit.Test
import org.springframework.validation.Errors
import org.springframework.validation.BeanPropertyBindingResult
import org.junit.Before

class ConfirmedPasswordConstraintTest {

    ConfirmedPasswordConstraint confirmedPasswordConstraint = new ConfirmedPasswordConstraint()

    String password = 'password'
    String passwordConfirmation = 'password'

    Errors errors

    @Before
    void "Configure validator"() {
        errors = new BeanPropertyBindingResult(this, 'password')
        confirmedPasswordConstraint.with {
            constraintPropertyName = 'password'
            constraintOwningClass = this.class
            constraintParameter = 'passwordConfirmation'
        }
    }

    @Test
    void "Test that a password matches the confirmation password value"() {

        confirmedPasswordConstraint.processValidate(this, 'password', errors)
        assert !errors.hasErrors()

    }

    @Test
    void "Test that a password does not match the confirmation password value"() {

        passwordConfirmation = 'drowssap'
        confirmedPasswordConstraint.processValidate(this, 'password', errors)
        assert errors.hasErrors()

    }

    @Test
    void "Test that this validator supports String"() {

        assert confirmedPasswordConstraint.supports(String.class)

    }

    @Test
    void "Test that this validator doesn't support a Random class"() {

        assert !confirmedPasswordConstraint.supports(Random.class)

    }

    @Test
    void "Test that the name of this validator is correct"() {

        assert confirmedPasswordConstraint.name == 'confirmedPassword'

    }

}
