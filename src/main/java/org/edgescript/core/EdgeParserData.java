/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public class EdgeParserData<EClass extends EdgeClass> {
    public EClass initClass;
    public EClass focusClass;

    public EdgeParserData() {
    }

    public EdgeParserData(EClass initClass, EClass focusClass) {
        this.initClass = initClass;
        this.focusClass = focusClass;
    }

    public EdgeParserData withInitClass(EdgeClass initClass) {
        return new EdgeParserData(initClass, initClass);
    }
}
