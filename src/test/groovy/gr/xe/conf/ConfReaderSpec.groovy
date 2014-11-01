
package gr.xe.conf;

import gr.xe.conf.ConfReader
import spock.lang.*

/**
 * Port of existing test into Spock
 * @author Kostis
 *
 */
class ConfReaderSpec extends spock.lang.Specification{
	
	def "Reading a simple entry - happy path"() {
		when: "A sample configuration file is loaded"
		def inputFile = getClass().getResourceAsStream("some_values.conf");
		
		def confReader = new ConfReader();
		confReader.readFile(inputFile);

		then: "Value of first level should be parsed correctly"
		confReader.readValue("moto.type") == "OTHER"
	}
	
	def "Reading entries that do not exist"() {
		when: "A sample configuration file is loaded"
		def inputFile = getClass().getResourceAsStream("some_values.conf");
		
		def confReader = new ConfReader();
		confReader.readFile(inputFile);

		then: "Values that do not exist should return null"
		confReader.readValue(configurationKey) == configurationValue
		
		where:
		configurationKey | configurationValue
		"auto_defaults.make" | "OTHER"
		"moto.type" | "OTHER"
		"nothing.nothing" | null
		"auto_defaults.whatever" | null
		
	}
	
	def "Reading all keys from the configuration file"() {
		when: "A sample configuration file is loaded"
		def inputFile = getClass().getResourceAsStream("product_templates.conf");
		
		def confReader = new ConfReader();
		confReader.readFile(inputFile);

		then: "we expect 3 keys"
		def result = confReader.readKeys("templates")
		result.size() == 3
		
		and: "we expect the correct values"
		result.contains("first")
		result.contains("second")
		result.contains("third")
	}
	
	def "Having duplicate configuration blocks"() {
		when: "A sample configuration file is loaded that contains duplicate blocks"
		def inputFile = getClass().getResourceAsStream("primary_mapping.conf");
		
		def confReader = new ConfReader();
		confReader.readFile(inputFile);

		then: "all configuration entries should be merged"
		def result = confReader.readKeys("primary_mapping")
		result.size() == 3
		confReader.readValue(configurationKey) == configurationValue
		
		where:
		configurationKey | configurationValue
		"primary_mapping.first" | "value1"
		"primary_mapping.second" | "value2"
		"primary_mapping.third" | "value3"
	}
	
	def "Having duplicate configuration blocks should not result in extra keys"() {
		when: "A sample configuration file is loaded that contains duplicate blocks"
		def inputFile = getClass().getResourceAsStream("primary_mapping.conf");
		
		def confReader = new ConfReader();
		confReader.readFile(inputFile);

		then: "all configuration entries should be merged"
		confReader.keyExists("primary_mapping.first")
		confReader.keyExists("primary_mapping.second")
		confReader.keyExists("primary_mapping.third")
		confReader.keyExists("primary_mapping")
		confReader.keyExists("primary_mapping.whatever") == false
		confReader.keyExists("whatever") == false
	}
}
