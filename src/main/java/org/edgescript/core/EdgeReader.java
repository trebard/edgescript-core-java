/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeReader<R extends EdgeReader>{
    interface ParsedElement {
        EdgeElement getElement();
        EdgeReader getEndReader();
    }
    void skipWhitespace();
    boolean hasWhitespace();
    String useWhitespace();
    boolean skipMatch(String match, boolean isRequired);
    boolean skipMatch(String match, boolean isRequired, boolean trailingSpace);
    EdgeLine getLine();
    EdgeReader cloneReader();
    boolean atEnd();
    char nextChar();
    void next();
    int compareTo(R e);
    void accept(R r);
    String currentLocation();
    String fullLocation();
    ParsedElement getParsed(Class parserClass);
    void addParsed(Class parserClass, EdgeNode element, EdgeReader endReader);
    void addFailed(Class parserClass);
    ParsedElement createParsedElement(EdgeElement element, EdgeReader endReader);
}
