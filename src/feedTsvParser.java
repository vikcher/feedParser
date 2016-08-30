/*
 * The class that is responsible for parsing the input TSV file
 */

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class feedTsvParser {
	
	public void parse() throws FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		TsvParserSettings settings = new TsvParserSettings();
		settings.getFormat().setLineSeparator("\n");
		settings.setHeaderExtractionEnabled(true);
		settings.setHeaders("Item Group ID", "Product ID", "GTIN", "Manufacturer Part Number", "Product Name", "Product Description", "Category", "Product Type", "Product URL", "Image URL", "Additional Image URL", "Brand", "MSRP Price", "Sale Price", "Stock Status", "Condition", "Color", "Gender", "Size", "Material", "Pattern", "Age Group", "Shipping Cost", "Shipping Weight" );
		settings.setNullValue("NULL");
		TsvParser parser = new TsvParser(settings);
		List<String[]> allRows = null;
		try {
			 allRows = parser.parseAll(getReader("/assets/products.tsv"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/products?" + "user=root&password=Ch@r@cter13");
			stmt = conn.prepareStatement("INSERT into products (item_group_id, product_id, gtin, manufacturer_part_number, product_name, product_desc, product_category, product_type, product_url, img_url, addl_img_url, brand, msrp_price, sale_price, stock_status, product_condition, color, gender, size, material, pattern, age_group, shipping_cost, shipping_weight) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			conn.setAutoCommit(false);

			for (String[] s : allRows)
			{
				stmt.setString(1,  s[0]);
				stmt.setString(2, s[1]);
				stmt.setString(3, s[2]);
				stmt.setString(4, s[3]);
				stmt.setString(5, s[4]);
				stmt.setString(6, s[5]);
				stmt.setString(7,  s[6]);
				stmt.setString(8, s[7]);
				stmt.setString(9, s[8]);
				stmt.setString(10, s[9]);
				stmt.setString(11, s[10]);
				stmt.setString(12, s[11]);
				stmt.setString(13, s[12]);
				stmt.setString(14, s[13]);
				stmt.setString(15, s[14]);
				stmt.setString(16, s[15]);
				stmt.setString(17, s[16]);
				stmt.setString(18, s[17]);
				stmt.setString(19,  s[18]);
				stmt.setString(20, s[19]);
				stmt.setString(21, s[20]);
				stmt.setString(22, s[21]);
				stmt.setString(23, s[22]);
				stmt.setString(24, s[23]);
				stmt.addBatch();
			}

			stmt.executeBatch();
			conn.commit();
		} finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
		}
	}
	
	public Reader getReader (String path) throws UnsupportedEncodingException {
		return new InputStreamReader(this.getClass().getResourceAsStream(path), "UTF-8");
	}
	
}
