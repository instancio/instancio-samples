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
package org.example.typeresolver;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.instancio.spi.InstancioServiceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of {@link InstancioServiceProvider} that provides
 * a custom {@link TypeResolver}.
 *
 * <p>For this class to be loaded, its fully-qualified name must be specified in:
 * {@code /META-INF/services/org.instancio.spi.InstancioServiceProvider}
 */
public class SampleProvider implements InstancioServiceProvider {

    @Override
    public TypeResolver getTypeResolver() {
        return new CustomTypeResolver();
    }

    /**
     * Custom {@code TypeResolver} implementation.
     * Uses ClassGraph to find implementations of interfaces by scanning the classpath.
     */
    private static class CustomTypeResolver implements TypeResolver {
        private static final String PACKAGE = "org.example.domain";
        private final Map<Class<?>, Class<?>> subtypes = new HashMap<>();

        CustomTypeResolver() {
            try (ScanResult result = new ClassGraph().acceptPackages(PACKAGE).scan()) {
                result.getAllInterfaces()
                        .directOnly()
                        .forEach(this::mapInterfaceToImplementation);
            }
        }

        @Override
        public Class<?> getSubtype(final Class<?> type) {
            return subtypes.get(type);
        }

        private void mapInterfaceToImplementation(final ClassInfo interfaceClassInfo) {
            final List<Class<?>> implementations = interfaceClassInfo.getClassesImplementing()
                    .directOnly()
                    .loadClasses();

            if (implementations.size() == 1) {
                final Class<?> implementation = implementations.iterator().next();
                subtypes.put(interfaceClassInfo.loadClass(), implementation);
                return;
            }

            System.out.println("Could not resolve implementation class for interface '" + interfaceClassInfo.getName()
                    + "'. Expected to find exactly one implementor, but found: " + implementations);
        }
    }

}
