import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PositionalIndex 
{
	public HashMap<String, DictEntry> index = new HashMap<String, DictEntry> ();
	List <String> stopWords = new ArrayList<String>(Arrays.asList("too","with","the","a","to","i","and","this","so","then","or","on","of","no","in","for","only","from","but","by","at","as"));
	List <Document>documents = null;
	public void buildIndex() throws IOException
	{
		for(Document d: documents)
		{
			FileReader file = new FileReader(d.name);
			BufferedReader buffer = new BufferedReader(file);
			String terms [] = null;
			String s; //to hold each line
			int position =0;
			
			while ((s = buffer.readLine()) != null)
			{
				s=s.trim().replaceAll("[\\]\\[?:/,.;\"(\\)]", "");
		    	terms = s.split("\\s+");
				for(int i=0;i<terms.length;i++)
				{
					d.length ++;
					terms[i]=terms[i].toLowerCase(); //convert all terms to lower case
					position ++;
					if(stopWords.contains(terms[i]))
					{
						continue;
					}
					
					else
					{
						if(index.containsKey(terms[i])) 
						{
							index.get(terms[i]).term_freq++; 
							
							if(!index.get(terms[i]).is_exist(d.id,position)) 
							{
								index.get(terms[i]).doc_freq++; 
								index.get(terms[i]).addNewPosting(d.id,position); 
							}
						 }
					 	 else
						 {
							index.put(terms[i],new DictEntry()); //it is the first time for this term to be mentioned in the collection
							index.get(terms[i]).pList = new Posting(d.id,position); //create a new posting for the document 
							index.get(terms[i]).term_freq++; //increase the number of times the term mentioned
							index.get(terms[i]).doc_freq++;  //increase number of documents in which this term mentioned
						 }
				     }
				}
			}
			file.close();
			buffer.close();
		}
	}
	
	public void printResults(String query)
	{
		Set<Integer> docs = searchInIndex(query); //the documents that contains the whole query
		if(Objects.isNull(docs))
		{
			System.out.println("There is at least one term in the query is not in the index or you entered one word");
		}
		else
		{
			if(docs.size() == 0)
			{
				System.out.println("No documents contains the whole query with this order");
			}
			else
			{
				for (int d: docs)
				{
					System.out.println("Document "+d);
				}
			}
		}
		
	}
	
	public Set<Integer> searchInIndex(String query) //search in index for documents contain the whole query and return them in set
	{
		Set<Integer> result = null;
		List<String> queryTerms = Arrays.asList(query.split("\\s+"));
		for(int i =0 ;i<queryTerms.size()-1;i++)
		{
			if (!(index.containsKey(queryTerms.get(i))) || !(index.containsKey(queryTerms.get(i+1))))
			{
				return null ;
			}
			Posting p1 =index.get(queryTerms.get(i)).pList;
			Posting p2 =index.get(queryTerms.get(i+1)).pList;
			Set<Integer> docIds = new HashSet<Integer>();   // document ids contain every two terms
			while(p1 != null && p2 !=null)
			{
				if(p1.docId == p2.docId)  //the two terms in the same document
				{
					List<Integer> l1 = p1.positions;
					List<Integer>l2 = p2.positions;
					int x =0; int y =0;
					while (x < l1.size() && y <l2.size())
					{
						if (l2.get(y) - l1.get(x) == 1) //the second term is after the first term
						{
		                   docIds.add(p1.docId);
		                   x++;
		                   y++;
		                } 
						else if (l2.get(y) > l1.get(x))
		                  x++;
						
						else 
		                  y++;
		             
		             }
					p1 = p1.next;
					p2= p2.next;
				}
				else if(p1.docId < p2.docId)
					p1 = p1.next;
				
				else
					p2 = p2.next;
				
			}
			if (result == null) //first two terms
		        result = docIds;
		            
			else 
		       result.retainAll(docIds);	 // keep in the results the common values in the old results and docIds
		}
		return result;
	}
	
}	
