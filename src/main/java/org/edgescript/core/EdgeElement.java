/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeElement extends EdgeNode<EdgeScope, EdgeClass> {
    EdgeElement resolve(EdgeEvaluation evaluation, EdgeScope scope, EdgeClass initClass, EdgeClass focusClass);
    Object evaluate(Object focus, EvalScope scope, EdgeEvaluation evaluation);
    int getPrecedence();
    EdgeElement resolvePrecedence(EdgeElement parent, EdgeElement left, EdgeElement test);
    EdgeElement switchPrecedence(EdgeElement right);
}
