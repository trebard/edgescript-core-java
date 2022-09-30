/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.*;

public class EdgeGrammarData<Parser extends EdgeParser> {
    private EdgeParserFactory<Parser> factory;
    public String grammarType;
    private Map<String, EdgeGrammarId<Parser>> ids = new HashMap<>();
    public Set<String> usedIds = new HashSet<>();
    private Map<Integer, Parser> recursionNodes = new HashMap<>();
    private Set<Integer> idSet = new HashSet<>();

    public EdgeGrammarData(String grammarType, EdgeParserFactory<Parser> factory) {
        this.grammarType = grammarType;
        this.factory = factory;
    }

    public EdgeGrammarData() {
        this("", null);
    }

    public Parser createParser(String description, EdgeGrammarItem grammar) {
        return factory.createParser(description, grammar);
    }

    public Collection<EdgeGrammarId<Parser>> getIdValues() {
        return ids.values();
    }

    public Set<String> getIdKeys() {
        return ids.keySet();
    }
    public Set<Integer> getIdSet() {
        return idSet;
    }

    public void clearIdSets() {
        idSet.clear();
    }

    public EdgeGrammarId getId(String name, boolean create) {
        EdgeGrammarId id = ids.get(name);
        if (id == null && create) {
            id = new EdgeGrammarId(name, true);
            ids.put(name, id);
        }
        return id;
    }

    public Map<Integer, Parser> getRecursionMap() {
        return recursionNodes;
    }

    public Parser createParser(String id) {
        EdgeGrammarId<Parser> bnfId = getId(id, false);
        if (bnfId == null) {
            throw new RuntimeException("Missing id " + id);
        }
        return (Parser)bnfId.optimize(this).createParser(this).optimize(this);
    }
}