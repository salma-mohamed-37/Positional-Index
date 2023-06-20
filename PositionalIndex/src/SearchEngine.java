import java.io.IOException;
import java.util.Scanner;

public class SearchEngine {

	public static void main(String[] args) throws IOException
	{
		String query;
		Collection c = new Collection ();
		int choice = 1;
		PositionalIndex in = new PositionalIndex ();
		in.documents = c.documents;
		in.buildIndex();
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Search Engine");
		do
		{
			System.out.println("The query must contain more than one word");
			System.out.print("Enter the query: ");
			query = input.nextLine();
			in.printResults(query);
			System.out.println();
			System.out.println("Do you want to enter another query? \nenter 1 for yes and 0 for no ");
			choice = input.nextInt();
			input. nextLine();
			System.out.println();
		}while(choice != 0);
		
		input.close();
	}
}
