/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeSpace extends EdgeElement {
    String getName();
    EdgeClass getFocusClass();
    boolean isRoot();
}
