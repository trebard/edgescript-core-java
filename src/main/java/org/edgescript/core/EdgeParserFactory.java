/*
 * Copyright (c) 2022 Demagic AB.
 * All rights reserved.
 */

package org.edgescript.core;

public interface EdgeParserFactory<Parser extends EdgeParser> {
    Parser createParser(String description, EdgeGrammarItem grammar);
}
