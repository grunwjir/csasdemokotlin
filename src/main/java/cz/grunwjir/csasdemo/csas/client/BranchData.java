package cz.grunwjir.csasdemo.csas.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Data object that represents Csas branch.
 *
 * @author Jiri Grunwald
 */
@Data
public class BranchData {
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String postCode;
    private String managerName;
    private List<String> phones;
    private String email;
    @JsonProperty("equipment")
    private List<Equipment> equipments;

    @Data
    public static class Equipment {
        private Integer id;
        private String name;
        private Integer count;
    }
}