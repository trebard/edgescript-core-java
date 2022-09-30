/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeParser<Parser extends EdgeParser> {
    Parser optimize(EdgeGrammarData<Parser> data);
    boolean finalizeParser(Parser parent);
    void setMatch(String match);
    void setReplaceNextList(boolean replaceNextList);
    void addMerge(Parser merge);
    void addNext(Parser next);
}