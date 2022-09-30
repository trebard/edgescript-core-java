/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EdgeGrammarId<Parser extends EdgeParser> extends EdgeGrammarItem<Parser> {
    boolean isSetup = false;

    public EdgeGrammarId(String name, boolean isValid) {
        super(name, isValid);
    }

    public EdgeGrammarId(String name, boolean isValid, Whitespace whitespace) {
        super(name, isValid, whitespace);
    }

    public void addOption(EdgeGrammarItem option) {
        merges.add(option);
    }

    public void validate(EdgeGrammarData data) {
        if (!data.getIdSet().contains(parserId)) {
            data.getIdSet().add(parserId);
            for (EdgeGrammarItem option : merges) {
                option.validate(data);
            }
            data.getIdSet().remove(parserId);
        }
    }

    @Override
    public void setupPaths(EdgeGrammarData data) {
        if (!isSetup) {
            isSetup = true;
            data.usedIds.add(name);
            for (EdgeGrammarItem option : merges) {
                option.setupPaths(data);
            }
        }
    }

    @Override
    public EdgeGrammarItem<Parser> optimize(EdgeGrammarData<Parser> data) {
        if (!data.getIdSet().contains(parserId)) {
            data.getIdSet().add(parserId);
            List<EdgeGrammarItem<Parser>> optMerges = new ArrayList<>();
            for (EdgeGrammarItem merge : merges) {
                optMerges.add(merge.optimize(data));
            }
            merges = optMerges;
            if (!replaceNextList && merges.size() == 1 && nexts == null) {
                return merges.get(0);
            }
        }
        return this;
    }

    @Override
    public void setupConnections(EdgeGrammarData data) {
        if (!data.getIdSet().contains(parserId)) {
            data.getIdSet().add(parserId);
            for (EdgeGrammarItem option : merges) {
                option.setupConnections(data);
            }
            data.getIdSet().remove(parserId);
        }
    }

    @Override
    public String parserType() {
        return "id";
    }

    @Override
    public void parserContent(StringBuilder b, int indent, String indentString, Set<Integer> doneIds) {
        for (EdgeGrammarItem option: merges) {
            option.parserDoc(b, indent, indentString, doneIds, parserId + " option");
        }
    }

    @Override
    public void grammarName(StringBuilder b, EdgeGrammarData data) {
        super.grammarName(b, data);
        b.append(name);
    }
}