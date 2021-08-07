package cz.grunwjir.csasdemo.branch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents physical Csas branch.
 * The object contains details about the branch and its Google Maps rating.
 *
 * @author Jiri Grunwald
 */
@Data
public class Branch {

    private Integer id;
    private String name;
    private Address address;
    private String manager;
    private List<String> phones;
    private String email;
    private List<String> equipments;
    private Float googleMapsRating;

    @Data
    @NoArgsConstructor
    public static class Address {
        private String street;
        private String city;
        private String postalCode;

        public Address(String street, String city, String postalCode) {
            this.street = street;
            this.city = city;
            this.postalCode = postalCode;
        }
    }
}
