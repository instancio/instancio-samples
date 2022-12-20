package org.example.generators;

import net.datafaker.Faker;
import org.example.pojo.Phone;
import org.example.pojo.PhoneType;
import org.instancio.Random;
import org.instancio.generator.Generator;

public class PhoneGenerator implements Generator<Phone> {

    @Override
    public Phone generate(final Random random) {
        Faker faker = new Faker(new java.util.Random(random.getSeed()));

        return Phone.builder()
                .phoneType(random.oneOf(PhoneType.values()))
                .countryCode("+" + random.digits(2))
                .number(faker.phoneNumber().cellPhone())
                .build();
    }
}
