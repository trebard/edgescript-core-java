/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EdgeGrammarSequence<Parser extends EdgeParser> extends EdgeGrammarItem<Parser> {
    List<EdgeGrammarElement> sequence = new ArrayList<>();

    public EdgeGrammarSequence(String name, boolean isValid) {
        super(name, isValid);
    }

    public EdgeGrammarSequence(String name) {
        super(name, true);
    }

    public EdgeGrammarSequence(String name, boolean isValid, String... refs) {
        super(name, isValid);
        addReferences(refs);
    }

    public EdgeGrammarSequence(String name, String... refs) {
        super(name, true);
        addReferences(refs);
    }

    public EdgeGrammarSequence(String name, boolean isValid, Whitespace whitespace, String... refs) {
        super(name, isValid, whitespace);
        addReferences(refs);
    }

    public EdgeGrammarSequence(String name, Whitespace whitespace, String... refs) {
        super(name, true, whitespace);
        addReferences(refs);
    }

    public EdgeGrammarSequence addReferences(String... refs) {
        for (String name: refs) {
            sequence.add(new EdgeGrammarReference(name, true, whitespace, false));
        }
        return this;
    }

    public EdgeGrammarSequence addOptional(String name) {
        sequence.add(new EdgeGrammarReference(name, true, whitespace, true));
        return this;
    }

    public EdgeGrammarSequence addOptional(String name, Whitespace whitespace) {
        sequence.add(new EdgeGrammarReference(name, true, whitespace, true));
        return this;
    }

    public EdgeGrammarSequence addMatch(String match) {
        sequence.add(new EdgeGrammarMatch(match, true, whitespace, false));
        return this;
    }

    public EdgeGrammarSequence addOptMatch(EdgeGrammarElement next, String match) {
        sequence.add(new EdgeGrammarMatch(match, true, whitespace, true));
        return this;
    }

    @Override
    public void trim() {
        List<EdgeGrammarElement> list = sequence;
        sequence = new ArrayList<>();
        for (EdgeGrammarElement p: list) {
            if (p.isValid) {
                sequence.add(p);
                p.trim();
            }
        }
    }

    public void validate(EdgeGrammarData data) {
        for (EdgeGrammarItem e: sequence) {
            e.validate(this, data);
        }
    }

    @Override
    public void setupPaths(EdgeGrammarData data) {
        for (EdgeGrammarItem e: sequence) {
            e.setupPaths(data);
        }
    }

    @Override
    public EdgeGrammarItem<Parser> optimize(EdgeGrammarData<Parser> data) {
        if (!data.getIdSet().contains(parserId)) {
            data.getIdSet().add(parserId);
            List<EdgeGrammarItem<Parser>> newMerges = new ArrayList<>();
            for (EdgeGrammarItem e : merges) {
                newMerges.add(e.optimize(data));
            }
            merges = newMerges;
            if (!replaceNextList && merges.size() == 1 && nexts == null) {
                // Always merged with first element
                return merges.get(0);
            }
//            data.getIdSet().remove(parserId);
        }
        return this;
    }

    @Override
    public void setupConnections(EdgeGrammarData data) {
        if (!data.getIdSet().contains(parserId)) {
            data.getIdSet().add(parserId);
            List<List<Integer>> followers = new ArrayList<>();
            Set<Integer> previous = new HashSet<>();
            boolean isEntry = true;
            for (int i = 0; i < sequence.size(); i++) {
                EdgeGrammarElement e = sequence.get(i);
                if (isEntry) {
                    addMerge(e);
                    isEntry &= e.isOptional;
                }
                List<Integer> f = new ArrayList<>();
                followers.add(f);
                for (Integer p : previous) {
                    followers.get(p).add(i);
                }
                if (!e.isOptional) {
                    previous.clear();
                }
                previous.add(i);
            }
            for (int i = sequence.size() - 1; i >= 0; i--) {
                EdgeGrammarElement e = sequence.get(i);
                List<Integer> fs = followers.get(i);
                for (Integer follower : fs) {
                    // This has nexts in sequence
                    e.addNext(sequence.get(follower));
                }
                e.replaceNextList = true;
            }
            for (EdgeGrammarItem e : sequence) {
                e.setupConnections(data);
            }
            data.getIdSet().remove(parserId);
        }
    }

    @Override
    public String parserType() {
        return "seq";
    }

    @Override
    public void parserContent(StringBuilder b, int indent, String indentString, Set<Integer> doneIds) {
        int order = 1;
        int sIndent = indent;
        String sString = indentString;
        for (EdgeGrammarItem element : sequence) {
            element.parserDoc(b, sIndent, sString, doneIds, parserId + "." + order + ".");
            sIndent++;
            sString += EdgeGrammarItem.indentation(sIndent);
            order++;
        }
        super.parserContent(b, indent, indentString, doneIds);
    }

    @Override
    public void grammarName(StringBuilder b, EdgeGrammarData data) {
        super.grammarName(b, data);
        b.append(name);
    }

    @Override
    public void bnfDocumentation(StringBuilder b, EdgeGrammarData data) {
        if (data.getIdSet().contains(parserId)) {
            b.append(name + "...");
        } else {
            data.getIdSet().add(parserId);
            for (EdgeGrammarItem e : sequence) {
                e.grammarName(b, data);
                b.append(" ");
            }
        }
    }
}