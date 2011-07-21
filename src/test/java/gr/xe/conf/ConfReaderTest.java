/*
 * Copyright 2011 Kapelonis Kostis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gr.xe.conf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.junit.Test;


/**
 * @author kkapelonis
 */
public class ConfReaderTest {
	
	@Test
	public void simpleEntry() throws IOException
	{
		InputStream in = getClass().getResourceAsStream("some_values.conf");
		
		ConfReader loader = new ConfReader();
		loader.readFile(in);
		
		assertEquals("Expected an entry","OTHER",loader.readValue("auto_defaults.make"));
		assertEquals("Expected an entry","OTHER",loader.readValue("moto.type"));
	}
	
	@Test
	public void unknownPath()  throws IOException
	{
		InputStream in = getClass().getResourceAsStream("some_values.conf");
		
		ConfReader loader = new ConfReader();
		loader.readFile(in);
		
		assertNull("Expected an entry",loader.readValue("nothing.nothing"));
		
	}
	
	@Test
	public void unknownchild()  throws IOException
	{
		InputStream in = getClass().getResourceAsStream("some_values.conf");
		
		ConfReader loader = new ConfReader();
		loader.readFile(in);
		
		assertNull("Expected an entry",loader.readValue("auto_defaults.whatever"));
	}
	
	@Test
	public void allKeys()  throws IOException
	{
		InputStream in = getClass().getResourceAsStream("product_templates.conf");
		
		ConfReader loader = new ConfReader();
		loader.readFile(in);
		
		Set<String> keys = loader.readKeys("templates");
		assertEquals("Expected 3 keys", 3,keys.size());
		assertTrue("Expected key",keys.contains("first"));
		assertTrue("Expected key",keys.contains("second"));
		assertTrue("Expected key",keys.contains("third"));
	}
	
	@Test
	public void concat()  throws IOException
	{
		InputStream in = getClass().getResourceAsStream("primary_mapping.conf");
		
		ConfReader loader = new ConfReader();
		loader.readFile(in);
		
		Set<String> keys = loader.readKeys("primary_mapping");
		assertEquals("Expected 3 entries", 3,keys.size());
		assertEquals("Expected correct entry", "value1",loader.readValue("primary_mapping.first"));
		assertEquals("Expected correct entry", "value2",loader.readValue("primary_mapping.second"));
		assertEquals("Expected correct entry", "value3",loader.readValue("primary_mapping.third"));
	}
	
	@Test
	public void keyExists()  throws IOException
	{
		InputStream in = getClass().getResourceAsStream("primary_mapping.conf");
		
		ConfReader loader = new ConfReader();
		loader.readFile(in);
		
		assertTrue("Expected key",loader.keyExists("primary_mapping.first"));
		assertTrue("Expected key",loader.keyExists("primary_mapping.second"));
		assertTrue("Expected key",loader.keyExists("primary_mapping.third"));
		assertTrue("Expected key",loader.keyExists("primary_mapping"));
		assertFalse("Expected nothing",loader.keyExists("primary_mapping.whatever"));
		assertFalse("Expected nothing",loader.keyExists("whatever"));
	}
	
	
	
	
	
	
	
	
	
	
	
}
