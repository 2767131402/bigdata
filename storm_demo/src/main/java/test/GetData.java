package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class GetData {
	public static void main(String[] args) {
		String name = "www.baidu.com";
		String[] session = {"SDKVNDJKBNDJ28978","DKNVJKDNVJ34UT834UH","NVDIUSHRIUURVREE8HVID8989","KVJDINSJBNSJBNJ939302","DNBVKJNBJFNHDBJHSDNKJI889"};
		String[] time = {"2018-01-05 10:10:10","2018-01-05 10:13:10","2018-01-05 10:15:10","2018-01-05 10:17:10","2018-01-05 10:19:10"};
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 50; i++) {
			String message = name+"\t"+session[random.nextInt(session.length)]+"\t"+time[random.nextInt(time.length)];
			sb.append(message+"\n");
		}
		File file = new File("data.log");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			byte[] b = sb.toString().getBytes();
			out.write(b, 0, b.length);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
