/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import org.uninode.core.UniProperty;

import java.util.List;

public interface EdgeProperty<C extends EdgeClass, T extends EdgeType>
        extends UniProperty<C, T> {
    boolean isInit();
    boolean isState();
    void setState(boolean isState);
    EdgeElement getInitialization();
    void format(StringBuilder b, String indent, String nextIndent);
    void setAttributes(List<EdgeAttribute> attributes);
}
