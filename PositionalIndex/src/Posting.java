
import java.util.*;

public class Posting
{
	public Posting next = null;
	int docId;
	double dtf = 1.0; // document term frequency
	List <Integer>positions = new LinkedList<Integer>();
	
	Posting(int docId, int position)
	{
		this.docId= docId;
		this.positions.add(position);
	}
}
