import org.codehaus.groovy.grails.validation.ConstrainedProperty

import uk.co.desirableobjects.extravalidators.PostalCodeConstraint
import uk.co.desirableobjects.extravalidators.ConfirmedPasswordConstraint
import uk.co.desirableobjects.extravalidators.DateConstraint
import uk.co.desirableobjects.extravalidators.IPAddressConstraint

class ExtraValidatorsGrailsPlugin {
    // the plugin version
    def version = "0.3"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Antony Jones"
    def authorEmail = "aj@desirableobjects.co.uk"
    def title = "Grails Extra Validators"
    def description = '''Provides a bundle of extra validators for grails'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/grails-extra-validators"

	def loadAfter = ['controllers']

    def doWithSpring = {

        [
         PostalCodeConstraint.class,
         ConfirmedPasswordConstraint.class,
         DateConstraint.class,
         IPAddressConstraint.class
        ].each { Class constraintClass ->

            ConstrainedProperty.registerNewConstraint(
                constraintClass.VALIDATION_DSL_NAME,
                constraintClass
            )
            
        }
    }
    
}
