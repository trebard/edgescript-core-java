/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class EdgeGrammarItem<Parser extends EdgeParser> {
    public enum Whitespace {
        NONE, OPTIONAL, REQUIRED
    }
    private static int nextId = 1;
    protected int parserId = nextId++;
    public String name;
    protected boolean isValid;
    public boolean replaceNextList;
    public Whitespace whitespace = Whitespace.OPTIONAL;
    protected List<EdgeGrammarItem<Parser>> merges = new ArrayList<>();
    protected List<EdgeGrammarItem> nexts;
    public EdgeGrammarItem(String name, boolean isValid) {
        this.name = name;
        this.isValid = isValid;
    }

    public EdgeGrammarItem(String name, boolean isValid, Whitespace whitespace) {
        this(name, isValid);
        this.whitespace = whitespace;
    }

    public boolean isValid() {
        return isValid;
    }

    public void addMerge(EdgeGrammarItem merge) {
        for (EdgeGrammarItem p: merges) {
            if (p == merge) {
                return;
            }
        }
        this.merges.add(merge);
    }

    public void addNext(EdgeGrammarItem next) {
        if (nexts == null) {
            nexts = new ArrayList<>();
        } else {
            for (EdgeGrammarItem p : nexts) {
                if (next == p) {
                    return;
                }
            }
        }
        this.nexts.add(next);
    }

    protected EdgeGrammarId registerId(String n, EdgeGrammarData data) {
        EdgeGrammarId id = data.getId(n, true);
        id.whitespace = whitespace;
        id.addOption(this);
        return id;
    }

    public void registerIds(EdgeGrammarData data) {
        registerId(name, data);
    }

    public Parser getParser(String name, EdgeGrammarData<Parser> data) {
        EdgeGrammarId<Parser> id = data.getId(name, false);
        return id.createParser(data);
    }

    public void trim() {

    }

    public void validate(EdgeGrammarData data) {
    }

    public void validate(EdgeGrammarSequence sequence, EdgeGrammarData data) {
    }

    public Parser createParser(EdgeGrammarData<Parser> data) {
        Parser node = data.getRecursionMap().get(parserId);
        if (node == null) {
            node = data.createParser(toString(), this);
            node.setReplaceNextList(replaceNextList);
            data.getRecursionMap().put(parserId, node);
            for (EdgeGrammarItem merge: merges) {
                node.addMerge(merge.createParser(data));
            }
            if (nexts != null) {
                for (EdgeGrammarItem next : nexts) {
                    node.addNext(next.createParser(data));
                }
            }
//            node.setComplete();
        }
        return node;
    }

    public void setupPaths(EdgeGrammarData data) {
    }

    public void setupConnections(EdgeGrammarData data) {
    }

    public List<EdgeGrammarItem> optimize(EdgeGrammarData data, List<EdgeGrammarItem> list) {
        List<EdgeGrammarItem> es = new ArrayList<>();
        for (EdgeGrammarItem e : list) {
            es.add(e.optimize(data));
        }
        return es;
    }

    public EdgeGrammarItem<Parser> optimize(EdgeGrammarData<Parser> data) {
        return this;
    }

    public EdgeGrammarItem setupParser(EdgeGrammarData data) {
        setupPaths(data);
        data.clearIdSets();
        // Add merges and nexts before optimizing to avoid sequence overoptimizing
        setupConnections(data);
        data.clearIdSets();
        EdgeGrammarItem parser = optimize(data);
        data.clearIdSets();
//        parser.setupConnections(data);
//        data.clearIdSets();
        return parser;
    }

    public Parser createNode(EdgeGrammarData<Parser> data) {
        Parser node = createParser(data);
        node = (Parser)node.optimize(data);
        node.finalizeParser(null);
        return node;
    }

    public String parserType() {
        return "?";
    }

    public static String indentation(int indent) {
        if (indent % 3 == 0) {
            return ". ";
        } else {
            return "  ";
        }
    }

    public void parserContent(StringBuilder b, int indent, String indentString, Set<Integer> doneIds) {
        for (EdgeGrammarItem merge: merges) {
            merge.parserDoc(b, indent, indentString, doneIds, parserId + " merge with");
        }
        if (nexts != null) {
            for (EdgeGrammarItem next : nexts) {
                next.parserDoc(b, indent, indentString, doneIds, parserId + " next");
            }
        }
    }

    public String parserDescription() {
        return name;
    }

    public void addParserDescription(StringBuilder b, String indentString, String prefix, boolean isReference) {
        b.append(indentString);
        if (prefix != null && !prefix.isEmpty()) {
            b.append(prefix + " ");
        }
        b.append(parserDescription() + " [" + parserType() + " " + parserId + "]");
        if (replaceNextList) {
            b.append(" NEXT");
        }
        if (isReference) {
            b.append(" ^^");
        }
        bnfImplementation(b);
        b.append("\n");
    }

    public void parserDoc(StringBuilder b, int indent, String indentString, Set<Integer> doneIds, String prefix) {
        if (doneIds.contains(parserId)) {
            addParserDescription(b, indentString, prefix, true);
        } else {
            doneIds.add(parserId);
            addParserDescription(b, indentString, prefix, false);
            indent++;
            indentString += indentation(indent);
            parserContent(b, indent, indentString, doneIds);
        }
    }

    public void parserDoc(StringBuilder b) {
        parserDoc(b, 0, "", new HashSet<>(), "Root");
    }

    public void grammarName(StringBuilder b, EdgeGrammarData data) {
    }

    public void bnfDocumentation(StringBuilder b, EdgeGrammarData data) {
    }

    public String bnfImplementationName() {
        return null;
    }

    public void bnfImplementation(StringBuilder b) {
    }

    public void toDoc(StringBuilder b, EdgeGrammarData data) {
        b.append(parserDescription() + " -> ");
        bnfDocumentation(b, data);
        bnfImplementation(b);
        b.append("\n");
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        bnfDocumentation(b, new EdgeGrammarData());
        return parserType() + " " + parserDescription() + " -> " + b;
    }
}
