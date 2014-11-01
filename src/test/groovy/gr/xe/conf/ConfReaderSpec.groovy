
package gr.xe.conf;

import gr.xe.conf.ConfReader
import spock.lang.*

/**
 * Port of existing test into Spock
 * @author Kostis
 *
 */
class ConfReaderSpec extends spock.lang.Specification{
	
	def "Reading a simple entry"() {
		when: "A sample configuration file is loaded"
		def inputFile = getClass().getResourceAsStream("some_values.conf");
		
		def confReader = new ConfReader();
		confReader.readFile(inputFile);

		then: "Values of first level should be parsed correctly"
		confReader.readValue("auto_defaults.make") == "OTHER"
		confReader.readValue("moto.type") == "OTHER"
	}
}
