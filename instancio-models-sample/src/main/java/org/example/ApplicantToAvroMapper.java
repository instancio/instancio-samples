package org.example;

import org.apache.avro.AvroRuntimeException;
import org.apache.commons.lang3.Validate;
import org.example.avro.AddressAvro;
import org.example.avro.ApplicantAvro;
import org.example.dto.Address;
import org.example.dto.Applicant;
import org.example.dto.Grade;

import java.util.Optional;

public class ApplicantToAvroMapper {

    /**
     * Maps an {@link Applicant} to {@link ApplicantAvro}.
     * <p>
     * Rejects applicants who are not 18-25 years old or with grades lower than B.
     *
     * @param applicant to convert to Avro
     * @return an optional containing the Avro object, or an empty result if any required data is missing.
     */
    public Optional<ApplicantAvro> toAvro(final Applicant applicant) {
        Validate.isTrue(applicant.getAge() >= 18 && applicant.getAge() <= 25,
                "Applicant must be between 18 and 25 years of age");

        Validate.isTrue(applicant.getGrade() == Grade.A || applicant.getGrade() == Grade.B,
                "Applicant's grade must be either A or B");

        try {
            final ApplicantAvro applicantAvro = ApplicantAvro.newBuilder()
                    .setFirstName(applicant.getFirstName())
                    .setMiddleName(applicant.getMiddleName())
                    .setLastName(applicant.getLastName())
                    .setAddress(addressToAvro(applicant.getAddress()))
                    .build();

            return Optional.of(applicantAvro);
        } catch (AvroRuntimeException ex) { // log
            System.out.printf("Error converting person id=%d to Avro. Cause: '%s'%n", applicant.getId(), ex.getMessage());
            return Optional.empty();
        }
    }

    private static AddressAvro addressToAvro(final Address address) {
        return address == null ? null : AddressAvro.newBuilder()
                .setStreet(address.getStreet())
                .setCity(address.getCity())
                .setPostalCode(address.getPostalCode())
                .setCountry(address.getCountry())
                .build();
    }

}
