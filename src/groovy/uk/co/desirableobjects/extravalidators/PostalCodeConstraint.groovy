package uk.co.desirableobjects.extravalidators

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

class PostalCodeConstraint extends AbstractConstraint {
 
    private static final String DEFAULT_NOT_POSTAL_CODE_MESSAGE_CODE = "default.not.postalCode.message"
    public static final String POSTAL_CODE_CONSTRAINT = "postalCode"
 
    private boolean postalCode
 
    private US = { postalCode->
        postalCode ==~ /\d{5}/
    }
 
    private CA = { postalCode->
        postalCode ==~ /[A-Z]\d[A-Z] \d[A-Z]\d/
    }

    private UK = { postalCode ->
        postalCode ==~ /(GIR 0AA)|(((A[BL]|B[ABDHLNRSTX]?|C[ABFHMORTVW]|D[ADEGHLNTY]|E[HNX]?|F[KY]|G[LUY]?|H[ADGPRSUX]|I[GMPV]|JE|K[ATWY]|L[ADELNSU]?|M[EKL]?|N[EGNPRW]?|O[LX]|P[AEHLOR]|R[GHM]|S[AEGKLMNOPRSTY]?|T[ADFNQRSW]|UB|W[ADFNRSV]|YO|ZE)[1-9]?[0-9]|((E|N|NW|SE|SW|W)1|EC[1-4]|WC[12])[A-HJKMNPR-Y]|(SW|W)([2-9]|[1-9][0-9])|EC[1-9][0-9]) [0-9][ABD-HJLNP-UW-Z]{2})/
    }

    @Override
    public void setParameter(Object constraintParameter) {
        if(!(constraintParameter instanceof Boolean))
        throw new IllegalArgumentException("""Parameter for constraint [${POSTAL_CODE_CONSTRAINT}] of property
            [${constraintPropertyName}] of class [${constraintOwningClass}] must be a boolean value""")
 
        this.postalCode = constraintParameter as Boolean
        super.setParameter(constraintParameter)
    }

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        if (!validPostalCode(target, propertyValue)) {
            Object[] args = [constraintPropertyName, constraintOwningClass, propertyValue]
            super.rejectValue(target, errors, DEFAULT_NOT_POSTAL_CODE_MESSAGE_CODE, 
                "not.${POSTAL_CODE_CONSTRAINT}", args)
        }
    }

    @Override
    boolean supports(Class type) {
        return type != null && String.class.isAssignableFrom(type)
    }

    @Override
    String getName() {
        return POSTAL_CODE_CONSTRAINT
    }

    boolean validPostalCode(target, propertyValue) {

        String country = target.metaClass.hasMetaProperty("country") ? target.country : PostalCountry.UK
        if (this.hasProperty(country)) {
            return this."${country}"(propertyValue)
        } else {
            throw new IllegalArgumentException("Postal codes for ${country} are not currently validatable. Try one of ${PostalCountry.values()} instead")
        }

    }
}