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
package org.example;

import org.example.annotationprocessor.SampleInstancioServiceProvider;
import org.example.domain.Pojo;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class AnnotationProcessorTest {

    /**
     * Should use custom annotations specified by {@link SampleInstancioServiceProvider}.
     */
    @Test
    void shouldCreateMapBasedOnAnnotations() {
        final Pojo result = Instancio.create(Pojo.class);

        // Sample output:
        // Pojo[map={bar=2F5E92847B, NGJKQBQ=25F845DB67, foo=824D732CAA, ODDVXUPESM=2EDB5EB46A}]

        assertThat(result.getMap())
                .hasSizeGreaterThanOrEqualTo(2)
                .containsKeys("foo", "bar");

        assertThat(result.getMap().values()).allSatisfy(value ->
                assertThat(value).as("Hex value of length 10").matches("^[0-9A-F]{10}$"));
    }
}
