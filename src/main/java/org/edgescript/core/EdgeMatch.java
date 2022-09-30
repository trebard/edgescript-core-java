/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public class EdgeMatch<T extends EdgeNode> {
    private EdgeReader reader;
    public T match;

    public void replaceMatch(T e) {
        match = e;
    }

    public void updateWith(EdgeReader r, T e) {
        if (r != null && (reader == null || r.compareTo(reader) > 0)) {
            reader = r;
            match = e;
        }
    }

    public boolean updateWith(EdgeMatch<T> m) {
        if (m.reader != null && (reader == null || m.reader.compareTo(reader) > 0)) {
            reader = m.reader;
            match = m.match;
            return true;
        } else {
            return false;
        }
    }

    public boolean updateWith(EdgeMatch m, T e) {
        if (m.match != null && (reader == null || m.reader.compareTo(reader) > 0)) {
            reader = m.reader;
            match = e;
            return true;
        } else {
            return false;
        }
    }

    public boolean isAcceptedBy(EdgeReader target) {
        if (reader != null) {
            target.accept(reader);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (reader != null) {
            return match + " " + reader.currentLocation();
        }
        return "<none>";
    }
}
