package org.example.generators;

import net.datafaker.Faker;
import org.example.pojo.Address;
import org.instancio.Random;
import org.instancio.generator.Generator;

public class AddressGenerator implements Generator<Address> {

    @Override
    public Address generate(final Random random) {
        Faker faker = new Faker(new java.util.Random(random.getSeed()));

        return Address.builder()
                .street(faker.address().streetAddress())
                .city(faker.address().city())
                .build();
    }
}
