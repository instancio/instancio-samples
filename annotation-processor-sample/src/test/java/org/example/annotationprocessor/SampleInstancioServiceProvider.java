/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example.annotationprocessor;

import org.example.domain.Hex;
import org.example.domain.MapWithKeys;
import org.instancio.generator.specs.MapGeneratorSpec;
import org.instancio.generator.specs.StringGeneratorSpec;
import org.instancio.spi.InstancioServiceProvider;

/**
 * An implementation of {@link InstancioServiceProvider} with
 * a custom {@link AnnotationProcessor}.
 *
 * <p>For this class to be loaded, its fully-qualified name must be specified in:
 * {@code /META-INF/services/org.instancio.spi.InstancioServiceProvider}
 */
public class SampleInstancioServiceProvider implements InstancioServiceProvider {

    @Override
    public AnnotationProcessor getAnnotationProcessor() {
        return new AnnotationProcessorImpl();
    }

    private static class AnnotationProcessorImpl implements AnnotationProcessor {

        @AnnotationHandler
        void withKeys(MapWithKeys annotation, MapGeneratorSpec<String, ?> mapSpec) {
            mapSpec.withKeys(annotation.value());
        }

        @AnnotationHandler
        void hexString(Hex annotation, StringGeneratorSpec stringSpec) {
            stringSpec.hex().length(annotation.length());
        }
    }
}
