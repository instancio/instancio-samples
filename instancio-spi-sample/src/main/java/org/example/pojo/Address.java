package org.example.pojo;

import org.example.util.PrettyToString;

import java.util.List;

public class Address {
    private final String street;
    private final String city;
    private final String country;
    private final List<Phone> phoneNumbers;

    private Address(final Builder builder) {
        street = builder.street;
        city = builder.city;
        country = builder.country;
        phoneNumbers = builder.phoneNumbers;
    }


    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public List<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }

    @Override
    public String toString() {
        return PrettyToString.toPrettyString(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String street;
        private String city;
        private String country;
        private List<Phone> phoneNumbers;

        private Builder() {
        }

        public Builder street(final String street) {
            this.street = street;
            return this;
        }

        public Builder city(final String city) {
            this.city = city;
            return this;
        }

        public Builder country(final String country) {
            this.country = country;
            return this;
        }

        public Builder phoneNumbers(final List<Phone> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
