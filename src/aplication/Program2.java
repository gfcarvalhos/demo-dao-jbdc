package aplication;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		
		System.out.println("=== Test 1: Department insert ===");		
		Department dep = new Department(null, "Music");
		departmentDao.insert(dep);
		System.out.println("Inserted! New id = " + dep.getId());
		
		System.out.println("=== Test 2: Department findById ===");
		dep = departmentDao.findById(2);
		System.out.println(dep);
		
		
		System.out.println("=== Test 3: Department update ===");	
		dep = new Department();
		
		sc.close();
	}
}
