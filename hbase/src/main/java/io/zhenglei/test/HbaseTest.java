package io.zhenglei.test;

import java.util.List;

import org.junit.Test;

import io.zhenglei.domain.Student;
import io.zhenglei.hbase.StudentDao;

public class HbaseTest {
	@Test
	public void testAdd() {
		Student student = new Student("lisi", "nan");
		StudentDao dao = new StudentDao();
		dao.add(student);
		System.out.println("end");
	}
	@Test
	public void testUpdate() {
		Student student = new Student("lisi", "nan");
		student.setStuKey("2017-12-28_2291620499");
		StudentDao dao = new StudentDao();
		dao.update(student);
		System.out.println("end");
	}
	@Test
	public void testScan() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.scan();
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	@Test
	public void testGet() {
		StudentDao dao = new StudentDao();
		Student stu = dao.get("2017-12-27_2635571623");
		System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		System.out.println("end");
	}
	@Test
	public void testDelete() {
		StudentDao dao = new StudentDao();
		dao.delete("2017-12-27_2635571623");
		System.out.println("end");
	}
	@Test
	public void testFileList() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.prefixFile("2017-12-27");
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	@Test
	public void rowFile() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.rowFile("263557");
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	@Test
	public void rowFileTest() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.rowFile("2635571623");
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	@Test
	public void singleColumnTest() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.singleColumn("lisi");
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	@Test
	public void randomTest() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.random();
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	@Test
	public void valueFilterTest() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.valueFilter("lisi");
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	@Test
	public void moreFilterTest() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.moreFilter();
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
	
	@Test
	public void rkRangeTest() {
		StudentDao dao = new StudentDao();
		List<Student> list = dao.rkRange2("2017-12-28_229162038","2017-12-28_2291620389");
		for (Student stu : list) {
			System.out.println(stu.getStuKey()+"\t"+stu.getStuName()+"\t"+stu.getStuSex());
		}
		System.out.println("end");
	}
}
