/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import org.uninode.core.UniContainer;

public interface EdgeNode<ScopeType extends EdgeScope, C extends EdgeClass> extends UniContainer {
    void setEdgeClass(C edgeClass);
    EdgeClass getEdgeClass(ScopeType scope);
    EdgeType getEdgeType(ScopeType scope);
    void format(StringBuilder b, String indent, String nextIndent);
}
