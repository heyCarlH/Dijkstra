package A6_Dijkstra;

public class EntryPair implements EntryPair_Interface {
	public String value;
	public long priority;

	public EntryPair(String aValue, long dist) {
		this.value = aValue;
		this.priority = dist;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public long getPriority() {
		return this.priority;
	}
}
