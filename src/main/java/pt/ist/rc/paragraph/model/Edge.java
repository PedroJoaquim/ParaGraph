package pt.ist.rc.paragraph.model;

public final class Edge<EV> {
    private final int targetIdx;
    private final String source;
    private final String target;
    private final String label;
    /*
     * Edge property
     */
    private final EV value;

    private Edge(Builder<EV> builder) {
        this.targetIdx = builder.targetIdx;
        this.value = builder.value;

        this.source = builder.source;
        this.target = builder.target;
        this.label = builder.label;
    }

    public int getTargetIdx() {
        return this.targetIdx;
    }

    public EV getValue() {
        return this.value;
    }

    public String getSource() {
        return this.source;
    }

    public static class Builder<EV> {
        private int targetIdx = -1;
        private EV value;
        private String source;
        private String target;
        private String label;

        public Builder<EV> value(EV value) {
            this.value = value;
            return this;
        }

        public Builder<EV> source(String source) {
            this.source = source;
            return this;
        }

        public Builder<EV> target(String target) {
            this.target = target;
            return this;
        }

        public Builder<EV> label(String label) {
            this.label = label;
            return this;
        }

        public Builder<EV> targetIdx(int targetIdx) {
            this.targetIdx = targetIdx;
            return this;
        }

        public String getSource() {
            return this.source;
        }

        public String getTarget() {
            return this.target;
        }

        public int getTargetIdx() { return this.targetIdx; }

        public boolean hasTargetIdx() { return this.targetIdx != -1; }

        public Edge<EV> build() {
            return new Edge<>(this);
        }
    }
}
