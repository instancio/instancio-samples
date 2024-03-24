package org.example.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Denotes that a map should contain given keys.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MapWithKeys {

    String[] value();
}
