package pt.ist.rc.paragraph.model;

public class Edge<EV> {

    /*
     * Edge target vertex
     * (source vertex implicit by edge position on the edges array)
     */
    private int target;

    /*
     * Edge property
     */
    private EV value;

    public Edge(int target, EV value) {
        this.target = target;
        this.value = value;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public EV getValue() {
        return value;
    }

    public void setValue(EV value) {
        this.value = value;
    }
}
