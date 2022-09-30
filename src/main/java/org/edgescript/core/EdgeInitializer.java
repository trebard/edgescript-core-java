/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeInitializer<ScopeType extends EdgeScope, C extends EdgeClass, T extends EdgeType, FocusType>
        extends EdgeMethod<ScopeType, C, T, FocusType> {
}
