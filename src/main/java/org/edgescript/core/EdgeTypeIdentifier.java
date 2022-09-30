/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.List;

public interface EdgeTypeIdentifier {
    String getName();
    List<EdgeClassArgument> getArguments();
}
