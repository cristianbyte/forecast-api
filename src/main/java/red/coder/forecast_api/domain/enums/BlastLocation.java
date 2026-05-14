package red.coder.forecast_api.domain.enums;

public enum BlastLocation {
    HN("HATILLO NORTE"),
    HS("HATILLO SUR");

    private final String label;

    BlastLocation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
