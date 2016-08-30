import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class Parser {
	
	public static void main (String[] args)
	{
		feedTsvParser fs = new feedTsvParser();
		
		try {
			/*
			 * Calling the parse method of the feedTsvParser to parse the feed.
			 */
			fs.parse();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
