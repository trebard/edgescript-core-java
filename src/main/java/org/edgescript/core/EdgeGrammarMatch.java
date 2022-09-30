/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import org.uninode.core.UniEncode;

public class EdgeGrammarMatch<Parser extends EdgeParser> extends EdgeGrammarElement<Parser> {
    public EdgeGrammarMatch(String match, boolean isValid, boolean isOptional) {
        super(match, isValid, isOptional);
    }

    public EdgeGrammarMatch(String match, boolean isValid, Whitespace whitespace, boolean isOptional) {
        super(match, isValid, whitespace, isOptional);
    }

    @Override
    public Parser createParser(EdgeGrammarData<Parser> data) {
        Parser node = data.getRecursionMap().get(parserId);
        if (node == null) {
            String escapedName = "\"" + UniEncode.escapeString(name) + "\"";
            node = data.createParser(escapedName, this);
            node.setMatch(name);
            node.setReplaceNextList(replaceNextList);
            data.getRecursionMap().put(parserId, node);
            for (EdgeGrammarItem<Parser> merge : merges) {
                node.addMerge(merge.createParser(data));
            }
            if (nexts != null) {
                for (EdgeGrammarItem<Parser> next : nexts) {
                    node.addNext(next.createParser(data));
                }
            }
            data.getRecursionMap().remove(parserId);
        }
        return node;
    }

    @Override
    public String parserDescription() {
        return (isOptional ? "(opt) " : "") + "\"" + UniEncode.escapeString(name) + "\"";
    }

    public void grammarName(StringBuilder b, EdgeGrammarData data) {
        b.append(parserDescription());
    }

    @Override
    public String parserType() {
        return "match";
    }
}
