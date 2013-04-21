package cdu.algorithm;

public class VertexDegree implements Comparable<VertexDegree> {
	int vertex;
	int degree;

	public VertexDegree(int vertex, int degree) {
		this.vertex = vertex;
		this.degree = degree;
	}

	public int getVertex() {
		return vertex;
	}

	public int getDegree() {
		return degree;
	}

	public int compareTo(VertexDegree arg0) {
		return this.getDegree() - arg0.getDegree();
	}

}
