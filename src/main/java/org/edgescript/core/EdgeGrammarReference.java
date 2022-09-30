/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.Set;

public class EdgeGrammarReference<Parser extends EdgeParser> extends EdgeGrammarElement<Parser> {
    EdgeGrammarItem id;

    public EdgeGrammarReference(String name, boolean isValid, Whitespace whitespace, boolean isOptional) {
        super(name, isValid, whitespace, isOptional);
    }

    @Override
    public void validate(EdgeGrammarSequence sequence, EdgeGrammarData data) {
        if (data.getId(name, false) == null) {
            throw new RuntimeException("Missing " + data.grammarType + " reference " + name + " of " + sequence.name);
        }
    }

    @Override
    public void setupPaths(EdgeGrammarData data) {
        id = data.getId(name, true);
        id.setupPaths(data);
    }

    @Override
    public EdgeGrammarItem optimize(EdgeGrammarData data) {
        if (!data.getIdSet().contains(parserId)) {
            data.getIdSet().add(parserId);
            id = id.optimize(data);
            if (!replaceNextList) {
                return id;
            }
        }
        return this;
    }

    @Override
    public void setupConnections(EdgeGrammarData data) {
        addMerge(id);
        id.setupConnections(data);
    }

    public void grammarName(StringBuilder b, EdgeGrammarData data) {
        super.grammarName(b, data);
        b.append(name);
    }

    @Override
    public String parserType() {
        return "ref";
    }

    @Override
    public void parserContent(StringBuilder b, int indent, String indentString, Set<Integer> doneIds) {
        id.parserDoc(b, indent, indentString, doneIds, parserId + " ref");
    }
}
