/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeLine {
    String getScript();
    void getScript(StringBuilder b);
    void addUpdate(String text, String type);
    boolean isAltered();
}
