package org.example;

import org.instancio.InstancioMetamodel;

/**
 * This class annotated with {@code InstancioMetamodel} tells Instancio
 * to generate metamodels for the specified classes.
 * <p>
 * Since we are using the same metamodels in multiple test classes,
 * we have this standalone config class containing the annotation.
 * <p>
 * Duplicating this annotation with the same 'classes' is not recommended
 * as it will result in metamodels being generated multiple times.
 */
@InstancioMetamodel(classes = {Person.class, Address.class, Phone.class})
interface ExampleMetamodel {
    // can be left blank
}
