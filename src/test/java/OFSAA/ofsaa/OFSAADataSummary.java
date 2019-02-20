package OFSAA.ofsaa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OFSAADataSummary {

    @JsonProperty
    private String controlFileVersion;

    @JsonProperty
    private String dataFilename;

    @JsonProperty
    private String schema;

    @JsonProperty
    private String tableSpace;

    @JsonProperty
    private String lrmProductNameVersion;


    @JsonProperty
    private String agileREPORTERVersion;

    @JsonProperty
    private String OFSAAVersion;

    @JsonProperty
    private String configPackageName;

    @JsonProperty
    private List<OFSAAReport> reports;

    @JsonProperty
    private String configPackagePrefix;

    public String getControlFileVersion() {
        return controlFileVersion;
    }

    public String getDataFilename() {
        return dataFilename;
    }

    public String getSchema() {
        return schema;
    }

    public String getTableSpace() {
        return tableSpace;
    }

    public String getLrmProductNameVersion() {
        return lrmProductNameVersion;
    }

    public String getOFSAAVersion() {
        return OFSAAVersion;
    }

    public String getConfigPackageName() {
        return configPackageName;
    }

    public List<OFSAAReport> getReports() {
        return reports;
    }

    public String getConfigPackagePrefix() {
        return configPackagePrefix;
    }

    public void setConfigPackagePrefix(final String configPackagePrefix) {
        this.configPackagePrefix = configPackagePrefix;
    }

    public String getAgileREPORTERVersion() {
        return agileREPORTERVersion;
    }

    public void setAgileREPORTERVersion(final String agileREPORTERVersion) {
        this.agileREPORTERVersion = agileREPORTERVersion;
    }

    public void setConfigPackageName(String configPackageName) {
        this.configPackageName = configPackageName;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this));
        }
        catch (com.fasterxml.jackson.core.JsonProcessingException je) {
            return ("Error parsing object:" + je);
        }

    }
}
