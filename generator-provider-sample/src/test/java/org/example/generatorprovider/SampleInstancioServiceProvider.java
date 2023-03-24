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
package org.example.generatorprovider;

import org.example.domain.Location;
import org.example.domain.PointOfInterest;
import org.instancio.Node;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorSpec;
import org.instancio.generators.Generators;
import org.instancio.spi.InstancioServiceProvider;

import java.lang.reflect.Field;

/**
 * An implementation of {@link InstancioServiceProvider} with
 * a custom {@link GeneratorProvider}.
 *
 * <p>For this class to be loaded, its fully-qualified name must be specified in:
 * {@code /META-INF/services/org.instancio.spi.InstancioServiceProvider}
 */
public class SampleInstancioServiceProvider implements InstancioServiceProvider {

    @Override
    public GeneratorProvider getGeneratorProvider() {
        return new GeneratorProviderImpl();
    }

    private static class GeneratorProviderImpl implements GeneratorProvider {

        @Override
        public GeneratorSpec<?> getGenerator(final Node node, final Generators generators) {
            if (node.getTargetClass() == Location.class) {
                return locationGenerator();
            }

            final Field field = node.getField();
            if (field != null
                    && "description".equals(field.getName())
                    && field.getDeclaringClass() == PointOfInterest.class) {

                return descriptionGenerator();
            }

            return null;
        }
    }

    private static Generator<String> descriptionGenerator() {
        return random -> "some description";
    }

    private static Generator<Location> locationGenerator() {
        return random -> new Location(
                random.doubleRange(-90, 90),
                random.doubleRange(-180, 180));
    }
}
