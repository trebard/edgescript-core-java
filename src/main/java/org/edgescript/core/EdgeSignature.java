/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EdgeSignature {
    private final static Map<String, EdgeSignature> signatures =  new HashMap<>();
    private String signature;

    private EdgeSignature(String signature) {
        this.signature = signature;
    }

    public String asString() {
        return signature;
    }

    public final static EdgeSignature getSignature(String name, String... args) {
        if (args.length == 0) {
            EdgeSignature s = signatures.get(name);
            if (s == null) {
                s = new EdgeSignature(name);
                signatures.put(name, s);
            }
            return s;
        } else {
            StringBuilder b = new StringBuilder();
            b.append(name + ":");
            for (String arg: args) {
                b.append(arg + ":");
            }
            return getSignature(b.toString());
        }
    }

    public final static EdgeSignature getParameterSignature(String name, List<EdgeParameter> params) {
        if (params == null || params.isEmpty()) {
            return getSignature(name);
        } else {
            return getSignature(name + ":" + params.stream()
                    .map(EdgeParameter::signatureString)
                    .collect(Collectors.joining("")));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeSignature that = (EdgeSignature) o;
        return Objects.equals(signature, that.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signature);
    }

    @Override
    public String toString() {
        return signature;
    }
}
