/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import org.uninode.core.UniClass;

import java.util.List;

public interface EdgeClass<ScopeType extends EdgeScope, Property extends EdgeProperty>
        extends UniClass<Property>, EdgeType, EdgeStructure {
    Class getJavaClass();
    Class getClassJavaClass();
    EdgeClass<ScopeType, Property> getClassClass();
    void addSubClass(EdgeClass c);
    void addSuperClass(EdgeClass c);
    EdgeAttribute getAttribute(String name, List<EdgeArgument> args);
    void addProperty(Property p);
    EdgeClass<ScopeType, Property> addMethod(EdgeMethod fn, EdgeParameter... params);
    EdgeClass<ScopeType, Property> addMethod(EdgeMethod fn, List<EdgeParameter> params);
    EdgeMethod getMethod(String name, List<EdgeArgument> args);
    EdgeMethod getMethod(EdgeSignature sign);
    void addInitializer(EdgeInitializer fn, EdgeParameter... params);
    void addInitializer(EdgeInitializer fn, List<EdgeParameter> params);
    EdgeInitializer getInitializer(List<EdgeArgument> args);
    EdgeInitializer getSuperInitializer(List<EdgeArgument> args);
    EdgeOperator getOperator(EdgeOperator.OpType type, EdgeSignature signature);
    Object createInstance(EdgeEvaluation evaluation, EvalScope scope, List<EdgeArgument> argList);
    void resolveClass(boolean reset);
    EdgeScope getScope();
}

