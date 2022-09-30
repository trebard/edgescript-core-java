/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.HashMap;
import java.util.Map;

public class EdgeEvaluation {
    private Map<String, Long> autoIncrement = new HashMap<>();

    public long getAutoIncrement(String type) {
        synchronized (autoIncrement) {
            Long i = autoIncrement.get(type);
            if (i == null) {
                i = 1L;
            } else {
                i += 1;
            }
            autoIncrement.put(type, i);
            return i;
        }
    }

    public EdgeSource getSource() {
        return null;
    }
}
