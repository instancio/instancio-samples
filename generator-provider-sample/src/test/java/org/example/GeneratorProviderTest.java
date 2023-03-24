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

import org.example.domain.Location;
import org.example.domain.PointOfInterest;
import org.example.generatorprovider.SampleInstancioServiceProvider;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(InstancioExtension.class)
class GeneratorProviderTest {

    /**
     * Should use custom generators specified by {@link SampleInstancioServiceProvider}.
     */
    @Test
    void pointOfInterest() {
        final PointOfInterest result = Instancio.create(PointOfInterest.class);

        assertThat(result.getDescription()).isEqualTo("some description");

        Location location = result.getLocation();
        assertThat(location.getLat()).isBetween(-90d, 90d);
        assertThat(location.getLon()).isBetween(-180d, 180d);
    }

    /**
     * Override generators from {@link SampleInstancioServiceProvider} using the API.
     */
    @Test
    void overrideCustomGenerators() {
        final String description = "new description";

        final PointOfInterest result = Instancio.of(PointOfInterest.class)
                .set(field(PointOfInterest::getDescription), description)
                .set(field(Location::getLat), 0d)
                .create();

        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getLocation().getLat()).isZero();
    }
}
