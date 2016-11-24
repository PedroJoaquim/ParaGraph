package pt.ist.rc.paragraph.model;

import pt.ist.rc.paragraph.exceptions.DuplicateVertexIdException;

import java.util.*;
import java.util.stream.Collectors;

public class Graph<VV, EV> {
    private final List<Vertex<VV, EV>> vertices;
    private final boolean directed;
    private final String comment;

    private Graph(Builder<VV, EV> builder, List<Vertex<VV, EV>> vertices) {
        this.vertices = Collections.unmodifiableList(vertices);
        this.directed = builder.directed;
        this.comment = builder.comment;
    }

    public boolean isDirected() {
        return this.directed;
    }

    public String getComment() {
        return this.comment;
    }

    public Vertex<VV, EV> getVertex(int idx) { return this.vertices.get(idx); }

    public List<Vertex<VV, EV>> getVertices() { return this.vertices; }

    public Optional<Vertex<VV, EV>> getVertexById(String id) {
        return this.vertices.stream().filter(n -> n.getId().equals(id)).findAny();
    }

    public static abstract class Builder<VV, EV> {
        private boolean directed = true;
        private String comment;

        public Builder<VV, EV> directed(boolean directed) {
            this.directed = directed;
            return this;
        }

        public Builder<VV, EV> comment(String comment) {
            this.comment = comment;
            return this;
        }
    }

    public static class SimpleBuilder<VV, EV> extends Builder<VV, EV> {
        private final List<Vertex<VV, EV>> vertices;

        public SimpleBuilder() {
            this.vertices = new ArrayList<>();
        }

        public SimpleBuilder<VV, EV> addVertex(Vertex<VV, EV> vertex) {
            this.vertices.add(vertex);
            return this;
        }

        public SimpleBuilder<VV, EV> addVertices(Collection<Vertex<VV, EV>> moreVertices) {
            this.vertices.addAll(moreVertices);
            return this;
        }

        public SimpleBuilder<VV, EV> addPartialVertices(Collection<Vertex.Builder<VV, EV>> pVertices) {
            pVertices.stream().map(Vertex.Builder::build).forEachOrdered(this::addVertex);
            return this;
        }

        public Graph<VV, EV> build() {
            return new Graph<>(this, vertices);
        }
    }

    public static class BuilderEdges<VV, EV> extends Builder<VV, EV> {
        private final ArrayList<Vertex.Builder<VV, EV>> pVertices;
        private int maxToIdx;

        public BuilderEdges() {
            this.pVertices = new ArrayList<>();
        }

        public BuilderEdges(int nVertices) {
            this.pVertices = new ArrayList<>(nVertices);
        }

        public BuilderEdges<VV, EV> addEdge(int fromIdx, int toIdx) {
            this.maxToIdx = Math.max(this.maxToIdx, toIdx);
            fillUntilIdx(fromIdx); // TODO: compare performance of this vs fillUntilIdx(max(from, to)) ?

            Vertex.Builder<VV, EV> fromVertex = this.pVertices.get(fromIdx);

            Edge<EV> edge = new Edge.Builder<EV>().targetIdx(toIdx).build();

            fromVertex.addEdge(edge);

            return this;
        }

        public Graph<VV, EV> build() {
            fillUntilIdx(this.maxToIdx);

            List<Vertex<VV, EV>> vertices = pVertices.stream()
                    .map(Vertex.Builder::build)
                    .collect(Collectors.toList());

            return new Graph<>(this, vertices);
        }

        private void fillUntilIdx(int untilIdx) {
            this.pVertices.ensureCapacity(untilIdx + 1);

            while (untilIdx >= this.pVertices.size()) {
                this.pVertices.add(new Vertex.Builder<>());
            }
        }
    }

    public static class BuilderWithIds<VV, EV> extends Builder<VV, EV> {
        private final List<Vertex.Builder<VV, EV>> pVertices;
        private final Map<String, Integer> vertexIdx;

        public BuilderWithIds() {
            this.pVertices = new ArrayList<>();
            this.vertexIdx = new HashMap<>();
        }

        public BuilderWithIds<VV, EV> addVertex(Vertex.Builder<VV, EV> pVertex) throws DuplicateVertexIdException {
            String nodeId = pVertex.getId();
            if (vertexIdx.containsKey(nodeId)) {
                throw new DuplicateVertexIdException(nodeId);
            }

            vertexIdx.put(nodeId, this.pVertices.size());
            this.pVertices.add(pVertex);
            return this;
        }

        public BuilderWithIds<VV, EV> addEdge(Edge.Builder<EV> pEdge) {
            assert(vertexIdx.containsKey(pEdge.getSource()));
            assert(vertexIdx.containsKey(pEdge.getTarget()) || pEdge.hasTargetIdx());


            //TODO: duplicate and mirror edge if graph is not directed?

            if (!pEdge.hasTargetIdx()) {
                pEdge.targetIdx(vertexIdx.get(pEdge.getTarget()));
            }
            Edge<EV> edge = pEdge.build();

            Vertex.Builder<VV, EV> sourceNode = this.pVertices.get(vertexIdx.get(edge.getSource()));
            sourceNode.addEdge(edge);
            return this;
        }

        public Graph<VV, EV> build() {
            List<Vertex<VV, EV>> vertices = pVertices.stream().map(Vertex.Builder::build).collect(Collectors.toList());
            return new Graph<>(this, vertices);
        }
    }
}