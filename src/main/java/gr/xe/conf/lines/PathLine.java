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
package gr.xe.conf.lines;

import gr.xe.conf.tree.ConfNode;
import gr.xe.conf.tree.PathNode;

import org.apache.commons.lang.StringUtils;

/**
 * @author kkapelonis
 */
public class PathLine implements LineReader{
	
	private ConfNode now = null;
	private String line = null;

	@Override
	public boolean matches (String line) {
		return line.trim().startsWith("<");
	}

	@Override
	public ConfNode process (ConfNode where, String textline) {
		this.line = textline.trim();
		now = where;
		if(line.startsWith("</"))
		{
			return closeTag();
		}
		else // <
		{
			return openTag();
		}
	}
	
	private ConfNode openTag()
	{
		String name = StringUtils.substringBetween(line,"<", ">");
		if(now.getChildren().containsKey(name))
		{
			return now.getChildren().get(name);
		}
		else
		{
			PathNode newNode = new PathNode(now);
			newNode.setName(name);
			now.getChildren().put(name,newNode);
			return newNode;
		}
		
		
	}
	
	private ConfNode closeTag()
	{
		//TODO maybe check that the node closed was the one opened
		return now.getParent();
	}
	

}
