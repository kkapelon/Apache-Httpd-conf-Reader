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

import gr.xe.conf.lines.CommentLine;
import gr.xe.conf.lines.LineReader;
import gr.xe.conf.lines.PathLine;
import gr.xe.conf.lines.ValueLine;
import gr.xe.conf.tree.ConfNode;
import gr.xe.conf.tree.PathNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kkapelonis
 */
public class ConfReader {
	private static final Logger logger = LoggerFactory.getLogger(ConfReader.class);
	
	private ConfNode rootNode = null;
	private List<LineReader> readers = null;
	
	/**
	 * Creates a new instance of ConfReader
	 */
	public ConfReader()
	{
		rootNode = new PathNode(null);
		readers = new ArrayList<LineReader>();
		readers.add(new CommentLine());
		readers.add(new PathLine());
		readers.add(new ValueLine());
	}
	
	/**
	 * Reads a legacy .conf Perl file
	 * @param in the file
	 * @throws IOException something was wrong during reading.
	 */
	public void readFile(InputStream in) throws IOException
	{
		List<String> lines = IOUtils.readLines(in);
		
		for(String line:lines)
		{
			if(StringUtils.isBlank(line))
			{
				continue;
			}
			boolean ok = process(line);
			if(!ok)
			{
				logger.warn("Unknown line {}",line);
			}
		}
		
	}
	
	private boolean process(String line)
	{
		for(LineReader reader:readers)
		{
			if(reader.matches(line))
			{
				rootNode = reader.process(rootNode, line);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds a value in the configuration file
	 * @param path a path using full stops
	 * @return the value or null if nothing was found
	 */
	public String readValue(String path)
	{
		ConfNode node = followPath(path);
		if(node !=null)
		{
			return node.getValue();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Finds all available keys that hang from this path
	 * 
	 * @param path a path using full stops
	 * @return the values found
	 */
	public Set<String> readKeys(String path)
	{
		ConfNode node = followPath(path);
		if(node !=null)
		{
			return node.getChildren().keySet();
		}
		else
		{
			return new HashSet<String>();
		}
	}
	
	/**
	 * Checks if a key is present or not
	 * 
	 * @param path a path using full stops
	 * @return true if the key exists
	 */
	public boolean keyExists(String path)
	{
		return followPath(path)!=null;
	}
	
	private ConfNode followPath(String path)
	{
		String[] paths = path.split("\\.");
		ConfNode start = rootNode;
		for(String pathName:paths)
		{
			if(!start.getChildren().containsKey(pathName))
			{
				return null;
			}
			start = start.getChildren().get(pathName);
		}
		return start;
	}

}
