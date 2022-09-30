/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeVariable<C extends EdgeClass, T extends EdgeType> {
    T getEvalType();
    C getEdgeClass(EdgeScope scope);
    boolean isState();
    String getName();
}
