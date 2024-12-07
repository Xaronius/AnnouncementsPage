package commons.models;
import java.io.Serializable;
public interface BaseDataObj extends Serializable {

    Long getId();
    BaseDataObj copyObj();
}
