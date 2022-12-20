package org.example.generators;

import net.datafaker.Faker;
import org.example.pojo.Person;
import org.instancio.Random;
import org.instancio.generator.Generator;

public class PersonGenerator implements Generator<Person> {

    @Override
    public Person generate(final Random random) {
        Faker faker = new Faker(new java.util.Random(random.getSeed()));

        // Populate only a couple of fields
        return Person.builder()
                .name(faker.name().fullName())
                .passportNumber(random.upperCaseAlphabetic(2) + random.digits(6))
                .build();
    }
}
