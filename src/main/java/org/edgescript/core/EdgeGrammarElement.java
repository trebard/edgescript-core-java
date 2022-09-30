/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public abstract class EdgeGrammarElement<Parser extends EdgeParser> extends EdgeGrammarItem<Parser> {
    protected boolean isOptional;
    public EdgeGrammarElement(String name, boolean isValid, boolean isOptional) {
        super(name, isValid);
        this.isOptional = isOptional;
    }

    public EdgeGrammarElement(String name, boolean isValid, Whitespace whitespace, boolean isOptional) {
        super(name, isValid, whitespace);
        this.isOptional = isOptional;
    }

    public String parserDescription() {
        return (isOptional ? "(opt) " : "") + name;
    }

    public void grammarName(StringBuilder b, EdgeGrammarData data) {
        if (isOptional) {
            b.append("(opt)");
        }
    }
}