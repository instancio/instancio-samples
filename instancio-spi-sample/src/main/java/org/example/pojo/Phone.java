package org.example.pojo;

import org.example.util.PrettyToString;

public class Phone {

    private final PhoneType phoneType;
    private final String countryCode;
    private final String number;

    private Phone(final Builder builder) {
        phoneType = builder.phoneType;
        countryCode = builder.countryCode;
        number = builder.number;
    }


    public PhoneType getPhoneType() {
        return phoneType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return PrettyToString.toPrettyString(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private PhoneType phoneType;
        private String countryCode;
        private String number;

        private Builder() {
        }

        public Builder phoneType(final PhoneType phoneType) {
            this.phoneType = phoneType;
            return this;
        }

        public Builder countryCode(final String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder number(final String number) {
            this.number = number;
            return this;
        }

        public Phone build() {
            return new Phone(this);
        }
    }
}
