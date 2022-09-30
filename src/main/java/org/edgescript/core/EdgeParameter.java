/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeParameter<C extends EdgeClass, T extends EdgeType>
        extends EdgeElement, EdgeVariable<C, T> {
    String getExternalName();
    String signatureString();
    boolean isDynamicArray();
    EdgeElement getDefaultValue();

    EdgeClass getFocusType(EdgeScope scope, EdgeClass currentFocusClass);
}
