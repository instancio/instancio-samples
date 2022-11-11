package org.example;

import org.example.avro.AddressAvro;
import org.example.avro.ApplicantAvro;
import org.example.dto.Address;
import org.example.dto.Applicant;
import org.example.dto.Grade;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Selector;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

@ExtendWith(InstancioExtension.class)
class ApplicantToAvroMapperTest {

    private final ApplicantToAvroMapper mapper = new ApplicantToAvroMapper();

    private static Model<Applicant> createValidApplicantModel() {
        return Instancio.of(Applicant.class)
                .withNullable(all(
                        field("middleName"),
                        field(Address.class, "postalCode")))
                .generate(field("age"), gen -> gen.ints().range(18, 25))
                .generate(all(Grade.class), gen -> gen.oneOf(Grade.A, Grade.B))
                .toModel();
    }

    @Test
    @DisplayName("Valid applicant should be successfully converted to Avro")
    void verifyAvroMapping() {
        final Applicant applicant = Instancio.create(createValidApplicantModel());
        final Optional<ApplicantAvro> result = mapper.toAvro(applicant);
        assertThat(result).isPresent();

        final ApplicantAvro applicantAvro = result.get();
        assertThat(applicantAvro.getFirstName()).isEqualTo(applicant.getFirstName());
        assertThat(applicantAvro.getMiddleName()).isEqualTo(applicant.getMiddleName());
        assertThat(applicantAvro.getLastName()).isEqualTo(applicant.getLastName());
        assertThat(applicantAvro.getAddress()).isNotNull();

        final Address address = applicant.getAddress();
        final AddressAvro addressAvro = applicantAvro.getAddress();
        assertThat(addressAvro.getStreet()).isEqualTo(address.getStreet());
        assertThat(addressAvro.getCity()).isEqualTo(address.getCity());
        assertThat(addressAvro.getCountry()).isEqualTo(address.getCountry());
        assertThat(addressAvro.getPostalCode()).isEqualTo(address.getPostalCode());
    }

    @Test
    @DisplayName("Validation should fail if applicant is under 18 or over 25")
    void applicantAgeValidation() {
        Applicant applicant = Instancio.of(createValidApplicantModel())
                .generate(field("age"), gen -> gen.oneOf(17, 26))
                .create();

        assertThatThrownBy(() -> mapper.toAvro(applicant))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Applicant must be between 18 and 25 years of age");
    }

    @Test
    @DisplayName("Validation should fail if applicant's grade is lower than B")
    void applicantGradeValidation() {
        Applicant applicant = Instancio.of(createValidApplicantModel())
                .generate(all(Grade.class), gen -> gen.oneOf(Grade.C, Grade.D, Grade.F))
                .create();

        assertThatThrownBy(() -> mapper.toAvro(applicant))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Applicant's grade must be either A or B");
    }

    @Test
    @DisplayName("Should return an empty Optional if any of the required fields is null")
    void shouldReturnEmptyResultIfRequiredDataIsMissing() {
        Selector[] requiredFields = {
                field(Applicant.class, "firstName"),
                field(Applicant.class, "lastName"),
                field(Address.class, "street"),
                field(Address.class, "city"),
                field(Address.class, "country")
        };

        // Set each of these to null individually, so that only one required field is null at a time
        Arrays.stream(requiredFields).forEach(selector -> {
            // Given
            Applicant applicant = Instancio.of(createValidApplicantModel())
                    .set(selector, null)
                    .create();

            // When
            Optional<ApplicantAvro> result = mapper.toAvro(applicant);

            // Then
            assertThat(result).as("Expected %s to be required", selector).isNotPresent();
        });
    }
}
