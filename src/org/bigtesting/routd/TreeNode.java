/*
 * Copyright (C) 2014 BigTesting.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bigtesting.routd;

import static org.bigtesting.routd.RouteHelper.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @author Luis Antunes
 */
public class TreeNode {
    
    private final List<TreeNode> children = new ArrayList<TreeNode>();
    
    /*
     * From the Java API documentation for the Pattern class:
     * Instances of this (Pattern) class are immutable and are safe for use by 
     * multiple concurrent threads. Instances of the Matcher class are not 
     * safe for such use.
     */
    private final Pattern pattern;
    
    private final PathElement pathElement;
    
    private Route route;
    
    private final TreeNodeComparator treeNodeComparator = new TreeNodeComparator();
    
    public TreeNode(PathElement elem) {
        
        this.pattern = compilePattern(elem);
        this.pathElement = elem;
    }
    
    private Pattern compilePattern(PathElement elem) {
        
        StringBuilder routeRegex = new StringBuilder("^");
        
        if (elem instanceof NamedParameterElement) {
            
            NamedParameterElement namedElem = (NamedParameterElement)elem;
            if (namedElem.hasRegex()) {
                routeRegex.append("(").append(namedElem.regex()).append(")");
            } else {
                routeRegex.append("([^").append(PATH_ELEMENT_SEPARATOR).append("]+)");
            }
            
        } else if (elem instanceof SplatParameterElement) {
            
            routeRegex.append("(.*)");
            
        } else {
            
            routeRegex.append(escapeNonCustomRegex(elem.name()));
        }
        
        routeRegex.append("$");
        return Pattern.compile(routeRegex.toString());
    }
    
    public boolean matches(String token) {
        
        return pattern().matcher(token).find();
    }
    
    public boolean matches(PathElement elem) {
        
        if (pathElement != null) {
            return pathElement.equals(elem);
        }
        
        return false;
    }
    
    public void addChild(TreeNode node) {
        
        children.add(node);
        Collections.sort(children, treeNodeComparator);
    }
    
    public List<TreeNode> getChildren() {
        
        return new ArrayList<TreeNode>(children);
    }
    
    public TreeNode getMatchingChild(PathElement elem) {
        
        for (TreeNode node : children) {
            if (node.matches(elem)) return node;
        }
        return null;
    }

    public List<TreeNode> getMatchingChildren(String token) {

        List<TreeNode> matchingChildren = new ArrayList<TreeNode>();
        for (TreeNode node : children) {
            if (node.matches(token)) matchingChildren.add(node);
        }
        return matchingChildren;
    }
    
    public boolean hasChildren() {
        return !children.isEmpty();
    }
    
    public boolean containsSplatChild() {
        return getSplatChild() != null;
    }
    
    public TreeNode getSplatChild() {
        
        for (TreeNode child : children) {
            if (child.pathElement instanceof SplatParameterElement) {
                return child;
            }
        }
        return null;
    }
    
    public Pattern pattern() {
        
        return pattern;
    }
    
    public boolean isSplat() {
        return pathElement instanceof SplatParameterElement;
    }
    
    public Route getRoute() {
        
        return route;
    }
    
    public void setRoute(Route route) {
        
        this.route = route;
    }
    
    public boolean hasRoute() {
        return this.route != null;
    }
    
    public String toString() {
        
        return pattern.toString();
    }
    
    private static class TreeNodeComparator implements Comparator<TreeNode> {
        
        public int compare(TreeNode node1, TreeNode node2) {
        
            String r1Elem = getElem(node1.pathElement);
            String r2Elem = getElem(node2.pathElement);
            
            return new PathElementComparator().compare(r1Elem, r2Elem);
        }
        
        private String getElem(PathElement element) {
            String elem = element.name();
            if (element instanceof NamedParameterElement) {
                elem = PARAM_PREFIX + elem;
            }
            return elem;
        }
    }
}
