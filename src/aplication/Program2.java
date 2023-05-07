package aplication;

import java.util.List;
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
		
		System.out.println("\n=== Test 2: Department findById ===");
		dep = departmentDao.findById(2);
		System.out.println(dep);
		
		
		System.out.println("\n=== Test 3: Department findAll ===");	
		List<Department> depList = departmentDao.findAll();
		for (Department obj : depList) {
			System.out.println(obj);
		}
		
		sc.close();
		
		System.out.println("\n=== Test 4: Department update ===");	
		dep = departmentDao.findById(7);
		dep.setName("Tecnology");
		departmentDao.update(dep);
		System.out.println("Update completed!");
	}
}
