package io.zhenglei.hadoop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

public class HadoopTest {
	//下载
	@Test
	public void down() {
		Configuration configuration = new Configuration();
		try {
			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.6.122:9000"),configuration);
			FSDataInputStream inputStream = fs.open(new Path("hdfs://192.168.6.122:9000/log/localhost_access_log.2017-06-19.txt"));
			FileOutputStream fos = new FileOutputStream(new File("d://hadooptest/ee.txt"));
			IOUtils.copyBytes(inputStream, fos, configuration);
			inputStream.close();
			fos.close();
			System.out.println("end");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void up() {//上传
		Configuration configuration = new Configuration();
		try {
			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.59.110:9000"),configuration);
			FSDataOutputStream outputStream = fs.create(new Path("hdfs://192.168.59.110:9000/zhenglei/13517568-2edda26680447bb2.png"));
			FileInputStream inputStream =new FileInputStream(new File("D:\\截图\\13517568-2edda26680447bb2.png"));
			
			
			IOUtils.copyBytes(inputStream, outputStream, configuration);
			inputStream.close();
			outputStream.close();
			System.out.println("end");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void delete() {//删除
		Configuration configuration = new Configuration();
		try {
			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.59.110:9000"),configuration);
			fs.delete(new Path("hdfs://192.168.59.110:9000/zhenglei"), true);
			System.out.println("end");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void up1() {
		Configuration configuration = new Configuration();
		try {
			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.44.132:9000"),configuration);
			fs.copyFromLocalFile(new Path("d://hadooptest/ee.txt"), new Path("hdfs://192.168.44.132:9000/test/ee2.txt"));
			System.out.println("end");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void open2() {
		Configuration configuration = new Configuration();
		try {
			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.44.132:9000"),configuration);
			fs.copyToLocalFile(new Path("d://hadooptest/ee2.txt"), new Path("hdfs://192.168.44.132:9000/test/ee2.txt"));
			System.out.println("end");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void list() {
		Configuration configuration = new Configuration();
		try {
			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.44.132:9000"),configuration);
			RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("hdfs://192.168.44.132:9000/"), true);
			while (listFiles.hasNext()) {
				LocatedFileStatus lfs = listFiles.next();
				System.out.println(lfs.getLen()+"   "+lfs.getOwner());
			}
			System.out.println("end");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
