package io.zhenglei.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.InclusiveStopFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueExcludeFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;

import io.zhenglei.constants.DateFarmate;
import io.zhenglei.constants.HbaseConstants;
import io.zhenglei.domain.Student;

public class StudentDao extends HbaseDao {
	/**
	 * 多条件查询
	 */
	public List<Student> moreFilter(){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			FilterList filterList = new FilterList(Operator.MUST_PASS_ONE);
			
			SingleColumnValueFilter columnValueFilter1 = new SingleColumnValueFilter(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME), CompareOp.EQUAL, toByte("lisi"));
			SingleColumnValueFilter columnValueFilter2 = new SingleColumnValueFilter(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX), CompareOp.EQUAL, toByte("nv"));
			
			filterList.addFilter(columnValueFilter1);
			filterList.addFilter(columnValueFilter2);
			scan.setFilter(filterList);
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * valueFilter
	 */
	public List<Student> valueFilter(String key){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			ValueFilter valueFilter = new ValueFilter(CompareOp.EQUAL, new SubstringComparator(key));
			scan.setFilter(valueFilter);
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 随机抽取
	 */
	public List<Student> random(){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			RandomRowFilter randomRowFilter = new RandomRowFilter(0.5f);
			scan.setFilter(randomRowFilter);
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 根据列值
	 * @param key 列值
	 * @return
	 */
	public List<Student> singleColumn(String key){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		SingleColumnValueFilter columnValueFilter = new SingleColumnValueFilter(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME), CompareOp.EQUAL, toByte(key));
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			scan.setFilter(columnValueFilter);
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public List<Student> rowFile(String key){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			RowFilter rowFilter = new RowFilter(CompareOp.EQUAL, new RegexStringComparator("^.*("+key+")$"));
			//RowFilter rowFilter = new RowFilter(CompareOp.EQUAL, new SubstringComparator(key));
			scan.setFilter(rowFilter);
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public List<Student> prefixFile(String prefix){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			PrefixFilter fileFilter = new PrefixFilter(toByte(prefix));
			scan.setFilter(fileFilter);
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 包括start也包括end
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Student> rkRange2(String start,String end){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			scan.setStartRow(toByte(start));
//			scan.setStopRow(toByte(end));
			
			InclusiveStopFilter inclusiveStopFilter = new InclusiveStopFilter(toByte(end));
			scan.setFilter(inclusiveStopFilter);
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 包括start不包括end
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Student> rkRange(String start,String end){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			scan.setStartRow(toByte(start));
			scan.setStopRow(toByte(end));
			ResultScanner rs = table.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				Student student = new Student();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public void add(Student stu){
		HTable table = null;
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			String date = DateFarmate.format();
			crc32.update(toByte(String.valueOf(date)));
			Put put = new Put(toByte(date+"_"+crc32.getValue()));
			put.addColumn(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME), toByte(stu.getStuName()));
			put.addColumn(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX), toByte(stu.getStuSex()));
			table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void update(Student stu){
		HTable table = null;
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Put put = new Put(toByte(stu.getStuKey()));
			put.addColumn(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME), toByte(stu.getStuName()));
			put.addColumn(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX), toByte(stu.getStuSex()));
			table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Student> scan(){
		HTable table = null;
		List<Student> list = new ArrayList<>();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Scan scan = new Scan();
			ResultScanner sc = table.getScanner(scan);
			Iterator<Result> iterator = sc.iterator();
			while(iterator.hasNext()){
				Student student = new Student();
				Result result = iterator.next();
				student.setStuKey(toString(result.getRow()));
				student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
				student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
				list.add(student);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public Student get(String key){
		HTable table = null;
		Student student = new Student();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Get get = new Get(toByte(key));
			Result result = table.get(get);
			
			student.setStuKey(toString(result.getRow()));
			student.setStuName(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_NAME))));
			student.setStuSex(toString(result.getValue(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.HBASE_SEX))));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return student;
	}
	public Student delete(String key){
		HTable table = null;
		Student student = new Student();
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Delete delete = new Delete(toByte(key));
//			delete.addColumn(family, qualifier);	//删除列
//			delete.addFamily(family);				//删除列族
			table.delete(delete);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return student;
	}
	
	
}
