package pt.ist.rc.paragraph.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Vertex<VV,EV> {
    /**
     * Vertex property associated to the graph
     */
    private final VV value;
    private final String id;
    private final String label;
    private final List<Edge<EV>> outEdges;

    private Vertex(Builder<VV,EV> builder) {
        this.value = builder.value;

        this.id = builder.id;
        this.label = builder.label;
        this.outEdges = Collections.unmodifiableList(builder.edges);
    }

    public VV getValue() { return this.value; }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<Edge<EV>> getOutEdges() {
        return this.outEdges;
    }

    public static class Builder<VV,EV> {
        private VV value;
        private String id;
        private String label;
        private final List<Edge<EV>> edges;

        public Builder() {
            edges = new ArrayList<>();
        }

        public Builder<VV,EV> value(VV value) {
            this.value = value;
            return this;
        }

        public Builder<VV, EV> id(String id) {
            this.id = id;
            return this;
        }

        public Builder<VV,EV> label(String label) {
            this.label = label;
            return this;
        }

        public Builder<VV,EV> addEdge(Edge<EV> edge) {
            edges.add(edge);
            return this;
        }

        public Builder<VV, EV> addAllEdges(Collection<Edge<EV>> edges) {
            edges.forEach(this::addEdge);
            return this;
        }

        public Vertex<VV,EV> build() { return new Vertex<>(this); }

        public String getId() {
            return this.id;
        }
    }
}
