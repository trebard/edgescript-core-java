/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import org.uninode.core.UniScope;

import java.util.List;

public interface EdgeScope extends UniScope<EdgeScope, EdgeClass> {
    EdgeVariable addVariable(EdgeVariable variable);
    boolean hasVariable(String name);
    EdgeVariable getVariable(String name);
    EdgeSpace getEdgeSpace(String name);
    EdgeType getType(EdgeTypeIdentifier type, boolean checkParents);
    EdgeClass getClass(EdgeTypeIdentifier typeIdentifier);
    EdgeProperty createProperty(String name, EdgeType valueType, EdgeElement initialization, boolean isInit, List<EdgeAttribute> attributes);
    void addEdgeSpace(EdgeSpace er);
}
