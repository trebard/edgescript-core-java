/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeAttribute<FocusClass> {
    void evaluateAttribute(EdgeEvaluation evaluation, EvalScope scope, FocusClass focusClass, EdgeAttributeData attribute);
}
