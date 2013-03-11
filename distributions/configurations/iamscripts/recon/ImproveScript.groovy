import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVParser;
public class ImproveScript implements org.openiam.idm.srvc.recon.service.CSVImproveScript {
	public int execute(String path){
		// FIELD NAME TO FIX
		String formattedName = "FORMATTED_NM";
		// TRY TO OPEN FILE
		File file = new File(path);
		// IF FILE NOT EXIST - VERY BAD. RETURN -1
		if (!file.exists()) {
			return -1;
		}
		//PARSE FILE AS CSV
		CSVParser parser = new CSVParser(new FileReader(file));
		String[][] fromParse = parser.getAllValues();

		//CSV ROWS LIST. EXAMPLE : {1,4,5,6,6\n}
		List<String> rows = new ArrayList<String>();
		//NUMBER OF COLUMS FOR FIXING
		int col = 0;
		// FLAG TO MARK "IS formattedName FIELD EXIST IN CSV FILE"
		boolean isFind = false;
		//Check is already fixed CSV?
		int fields;
		for (String names : fromParse[0]) {
			if (names.equals(formattedName) || names.equals(formattedName+"_2") ||  names.equals(formattedName+"_3") ){
				fields++;
			}
			if (fields==3)
				return 0;
		}
		//END CHECK-------------------------------------------------------

		// Fix header
		StringBuilder nameStr = new StringBuilder();
		for (String names : fromParse[0]) {
			nameStr.append(names);
			nameStr.append(',');
			if (names.equals(formattedName)) {
				nameStr.append(names + "_2");
				nameStr.append(',');
				nameStr.append(names + "_3");
				nameStr.append(',');
				isFind = true;
			}
			if (!isFind)
				col++;

		}
		nameStr.deleteCharAt(nameStr.length() - 1);
		nameStr.append('\n');
		rows.add(nameStr.toString());
		for (int i = 1; i < fromParse.length; i++) {
			nameStr = new StringBuilder();
			for (int j = 0; j < fromParse[i].length; j++) {
				if (j == col) {
					String[] names = fromParse[i][j].split(",");
					if (names.length == 2) {
						nameStr.append(names[0].trim());
						nameStr.append(',');
						String[] sec_name = names[1].trim().split(" ");
						if (sec_name.length == 2) {
							nameStr.append(sec_name[0].trim());
							nameStr.append(',');
							nameStr.append(sec_name[1].trim());
						} else {
							nameStr.append(names[1].trim());
							nameStr.append(',');
						}
					} else {
						nameStr.append(fromParse[i][j]);
						nameStr.append(',');
					}
					nameStr.append(',');
				} else {
					nameStr.append(fromParse[i][j].replace(",", " "));
					nameStr.append(',');
				}
			}
			nameStr.deleteCharAt(nameStr.length() - 1);
			nameStr.append('\n');
			rows.add(nameStr.toString());
		}

		//OVERWRITE FILE
		StringBuilder fileStr = new StringBuilder();
		for (String row : rows) {
			fileStr.append(row);
		}
		FileWriter fw = new FileWriter(path);
		fw.write(fileStr.toString());
		fw.close();
		//---------------------------------
		return 0;
	}
}

