package com.gedcom.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by
 * ranjanr
 * 5/11/2017
 * GedcomParser
 */
public class NodeStack {

    private LinkedList<Node> nodeStack;

    public Node pop(){
        return this.nodeStack.removeLast();
    }

    public void push(Node node){
        if(this.nodeStack == null){
            this.nodeStack = new LinkedList<Node>();
        }
        this.nodeStack.add(node);
    }

    public Node peek(){
        return this.nodeStack.get(this.nodeStack.size()-1);
    }

    public boolean isEmpy(){
        return this.nodeStack == null ? true: this.nodeStack.isEmpty();
    }
}
