/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EvalScope {
    void setProperty(String name, Object value);
    EdgeScope getScope();
    int getScopeDepth();
    boolean hasProperty(String name);
    Object getProperty(String name);
    void setVariable(EdgeVariable variable, Object value);
}
