package cdu.algorithm;

import java.util.ArrayList;
import java.util.List;

public class State implements Cloneable {

	private List<String> ds = null;

	private List<String> cplDs = null;

	private List<String> prevDs = null;
	private List<String> prevCplDs = null;

	public void setDs(List<String> ds) {
		if (this.ds == null) {
			this.ds = new ArrayList<String>();
			this.ds.addAll(ds);
		} else {
			this.ds.clear();
			this.ds.addAll(ds);
		}
	}

	public void setCplDs(List<String> cplDs) {

		if (this.cplDs == null) {
			this.cplDs = new ArrayList<String>();
			this.cplDs.addAll(cplDs);
		} else {
			this.cplDs.clear();
			this.cplDs.addAll(cplDs);
		}
	}

	public void setPrevDs(List<String> prevDs) {
		if (this.prevDs == null) {
			this.prevDs = new ArrayList<String>();
			this.prevDs.addAll(prevDs);
		} else {
			this.prevDs.clear();
			this.prevDs.addAll(prevDs);
		}
	}

	public void setPrevCplDs(List<String> prevCplDs) {
		if (this.prevCplDs == null) {
			this.prevCplDs = new ArrayList<String>();
			this.prevCplDs.addAll(prevCplDs);
		} else {
			this.prevCplDs.clear();
			this.prevCplDs.addAll(prevCplDs);
		}
	}

	public List<String> getPrevDs() {
		return prevDs;
	}

	public List<String> getPrevCplDs() {
		return prevCplDs;
	}

	public State() {

	}

	public State(List<String> ds, List<String> cplDs) {
		this.ds = ds;
		this.cplDs = cplDs;

		if (this.prevDs == null) {
			this.prevDs = new ArrayList<String>();
			this.prevDs.addAll(ds);
		} else {
			this.prevDs.clear();
			this.prevDs.addAll(ds);
		}
		if (this.prevCplDs == null) {
			this.prevCplDs = new ArrayList<String>();
			this.prevCplDs.addAll(cplDs);
		} else {
			this.prevCplDs.clear();
			this.prevCplDs.addAll(cplDs);
		}
	}

	public State(List<String> ds, List<String> cplDs, List<String> prevDs,
			List<String> prevCplDs) {
		this.ds = ds;
		this.cplDs = cplDs;
		this.prevDs = prevDs;
		this.prevCplDs = prevCplDs;
	}

	public List<String> getDs() {
		return ds;
	}

	public List<String> getCplDs() {
		return this.cplDs;
	}

	public Object clone() {
		State copy = new State();
		copy.setDs(this.ds);
		copy.setCplDs(this.cplDs);
		copy.setPrevCplDs(this.prevCplDs);
		copy.setPrevDs(this.prevDs);

		return copy;
	}

}

