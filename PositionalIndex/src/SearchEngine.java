import java.io.IOException;
import java.util.Scanner;

public class SearchEngine {

	public static void main(String[] args) throws IOException
	{
		String query;
		Collection c = new Collection ();
		
		Index in = new Index ();
		in.documents = c.documents;
		in.buildIndex();
		System.out.print("Enter the query: ");
		Scanner input = new Scanner(System.in);
		query = input.nextLine();
		in.printResults(query);
		input.close();
	}
}
