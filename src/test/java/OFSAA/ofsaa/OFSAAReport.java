package OFSAA.ofsaa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OFSAAReport {

    @JsonProperty
    private String entity;
    @JsonProperty
    private String type;
    @JsonProperty
    private String formInstance;
    @JsonProperty
    private String formInstanceRevision;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date referenceDate;

    public String getEntity() {
        return entity;
    }

    public String getType() {
        return type;
    }

    public String getFormInstance() {
        return formInstance;
    }

    public int getFormInstanceRevision() {
        return Integer.parseInt(formInstanceRevision);
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("entity", entity)
                .add("type", type)
                .add("formInstance", formInstance)
                .add("formInstanceRevision", formInstanceRevision)
                .add("referenceDate", referenceDate)
                .toString();
    }
}
