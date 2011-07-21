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
package gr.xe.conf.tree;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kkapelonis
 */
public abstract class ConfNode {
	private Map<String,ConfNode> children = new LinkedHashMap<String,ConfNode>();

	private ConfNode parent = null;

	private String comment = null;
	private String name = null;

	public ConfNode (ConfNode parent) {
		this.parent = parent;
	}

	public String getComment () {
		return comment;
	}

	public void setComment (String comment) {
		this.comment = comment;
	}

	public Map<String,ConfNode> getChildren () {
		return children;
	}

	public ConfNode getParent () {
		return parent;
	}
	
	
	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public abstract String getValue(); 
	
	
}
