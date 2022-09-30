/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.List;
import java.util.Map;

public interface EdgeMethod<ScopeType extends EdgeScope, C extends EdgeClass, T extends EdgeType, FocusType>
        extends EdgeProperty<C, T> {
    void initialize(ScopeType parentScope, EdgeParameter... params);
    void initialize(ScopeType parentScope, Iterable<EdgeParameter> params);
    EdgeSignature getSignature();
    boolean matches(String name, List<EdgeArgument> args);
    T getValueType();
    List<EdgeParameter> getParameters();
    EdgeParameter getParameter(String externalName);
    void resolve(EdgeEvaluation evaluation, EdgeClass focusClass);
    Object evaluate(EdgeEvaluation evaluation, EvalScope scope, FocusType focus, Map<String, Object> args);
    void format(StringBuilder b, String indent, String nextIndent);
    EdgeScope getScope();
}
