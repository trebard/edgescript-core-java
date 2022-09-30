/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

import java.util.List;

public interface EdgeAttributeData {
    String getName();
    List<EdgeArgument> getArguments();
}
