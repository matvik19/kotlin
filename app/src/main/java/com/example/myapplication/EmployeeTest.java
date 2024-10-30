package com.example.myapplication;

import com.example.myapplication.domain.Admin;
import com.example.myapplication.domain.Director;
import com.example.myapplication.domain.Engineer;
import com.example.myapplication.domain.Manager;
import com.example.myapplication.domain.Employee;

public class EmployeeTest {
    public static void main(String[] args) {
        // Создание объектов
        Engineer eng = new Engineer(101, "Jane Smith", "012-34-5678", 120_345.27);
        Manager mgr = new Manager(207, "Barbara Johnson", "054-12-2367", 109_501.36, "US Marketing");
        Admin adm = new Admin(304, "Bill Munroe", "108-23-2367", 75_002.34);
        Director dir = new Director(12, "Susan Wheeler", "099-45-2340", 120_567.36, "Global Marketing", 1_000_000.00);

        // Отображение информации
        printEmployee(eng);
        printEmployee(mgr);
        printEmployee(adm);
        printEmployee(dir);
    }

    // Метод для отображения данных о сотруднике
    private static void printEmployee(Employee emp) {
        System.out.println("Employee ID: " + emp.getEmpId());
        System.out.println("Employee Name: " + emp.getName());
        System.out.println("Employee Soc Sec #: " + emp.getSsn());
        System.out.println("Employee salary: " + emp.getSalary());

        if (emp instanceof Manager) {
            System.out.println("Department Name: " + ((Manager) emp).getDeptName());
        }

        if (emp instanceof Director) {
            System.out.println("Budget: " + ((Director) emp).getBudget());
        }
        System.out.println();
    }
}
