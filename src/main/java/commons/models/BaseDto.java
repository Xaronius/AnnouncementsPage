package commons.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true, value = "selectOneMenuLabel")
public interface BaseDto extends BaseDataObj{

    default Long getIdOrNull() { return getId() == null || getId() <= 0 ? null : getId(); }
    default BaseDataObj copyObj() { return null; }

    @Override
    Long getId();

}
