package com.airtribe.studentmanagement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.airtribe.studentmanagement.entity.Course;
import com.airtribe.studentmanagement.entity.Enrollment;
import com.airtribe.studentmanagement.entity.Student;
import com.airtribe.studentmanagement.exception.InvalidDataException;
import com.airtribe.studentmanagement.services.CourseService;
import com.airtribe.studentmanagement.services.EnrollmentService;
import com.airtribe.studentmanagement.services.StudentService;
import com.airtribe.studentmanagement.util.InputValidator;

public class Main {
	private static final StudentService studentService = StudentService.getInstance();
	private static final CourseService courseService = CourseService.getInstance();
	private static final EnrollmentService enrollmentService = EnrollmentService.getInstance();

	public static void main(String[] args) {
		studentService.load();
		courseService.load();
		enrollmentService.load();

		Scanner sc = new Scanner(System.in);
		boolean running = true;

		System.out.println("=== Student Management System (CLI) ===");

		while (running) {
			printMenu();
			System.out.print("> ");
			String choice = sc.nextLine().trim();

			try {
				switch (choice) {
				case "1":
					addStudent(sc);
					break;
				case "2":
					listStudents();
					break;
				case "3":
					findStudent(sc);
					break;
				case "4":
					deleteStudent(sc);
					break;
				case "5":
					addCourse(sc);
					break;
				case "6":
					listCourses();
					break;
				case "7":
					enrollStudent(sc);
					break;
				case "8":
					listEnrollmentsForStudent(sc);
					break;
				case "9":
					dropEnrollment(sc);
					break;
				case "s":
					saveAll();
					break;
				case "q":
					running = false;
					saveAll();
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Unknown option.");
				}
			} catch (InvalidDataException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Unexpected error: " + e.getMessage());
				e.printStackTrace();
			}
		}

		sc.close();
	}

	private static void printMenu() {
		System.out.println("\nMenu:");
		System.out.println("1) Add Student");
		System.out.println("2) List Students");
		System.out.println("3) Find Student by ID");
		System.out.println("4) Delete Student");
		System.out.println("5) Add Course");
		System.out.println("6) List Courses");
		System.out.println("7) Enroll Student in Course");
		System.out.println("8) List Enrollments for Student");
		System.out.println("9) Drop Enrollment");
		System.out.println("s) Save");
		System.out.println("q) Save & Quit");
	}

	private static void addStudent(Scanner sc) {
		System.out.println("Enter firstName,lastName,email,rollNumber,dob(yyyy-MM-dd),year,program");
		String line = sc.nextLine();
		String[] parts = line.split(",");
		if (parts.length < 7) {
			System.out.println("Invalid input: need 7 comma-separated values.");
			return;
		}
		String first = parts[0].trim();
		String last = parts[1].trim();
		String email = parts[2].trim();
		String roll = parts[3].trim();
		String dobS = parts[4].trim();
		String yearS = parts[5].trim();
		String program = parts[6].trim();

		InputValidator.validateEmail(email);
		LocalDate dob = InputValidator.parseDate(dobS);
		int year = InputValidator.parseInt(yearS, "year");

		Student s = new Student(first, last, email, roll, dob, year, program);
		studentService.createStudent(s);
		System.out.println("Created student with id: " + s.getId());
	}

	private static void listStudents() {
		List<Student> list = studentService.listAll();
		if (list.isEmpty()) {
			System.out.println("No students.");
			return;
		}
		list.forEach(System.out::println);
	}

	private static void findStudent(Scanner sc) {
		System.out.print("Enter student id: ");
		String id = sc.nextLine().trim();
		Optional<Student> s = studentService.findById(id);
		s.ifPresentOrElse(System.out::println, () -> System.out.println("Student not found."));
	}

	private static void deleteStudent(Scanner sc) {
		System.out.print("Enter student id to delete: ");
		String id = sc.nextLine().trim();
		studentService.deleteStudent(id);
		System.out.println("Deleted if existed.");
	}

	private static void addCourse(Scanner sc) {
		System.out.println("Enter code,title,credits,maxSeats");
		String line = sc.nextLine();
		String[] p = line.split(",");
		if (p.length < 4) {
			System.out.println("Need 4 values.");
			return;
		}
		String code = p[0].trim();
		String title = p[1].trim();
		int credits = InputValidator.parseInt(p[2].trim(), "credits");
		int maxSeats = InputValidator.parseInt(p[3].trim(), "maxSeats");
		Course c = new Course(code, title, credits, maxSeats);
		courseService.createCourse(c);
		System.out.println("Created course id: " + c.getId());
	}

	private static void listCourses() {
		List<Course> list = courseService.listAll();
		if (list.isEmpty()) {
			System.out.println("No courses.");
			return;
		}
		list.forEach(System.out::println);
	}

	private static void enrollStudent(Scanner sc) {
		System.out.print("Enter studentId: ");
		String sid = sc.nextLine().trim();
		System.out.print("Enter courseId: ");
		String cid = sc.nextLine().trim();
		Enrollment e = enrollmentService.enroll(sid, cid);
		System.out.println("Enrolled. enrollmentId: " + e.getId());
	}

	private static void listEnrollmentsForStudent(Scanner sc) {
		System.out.print("Enter studentId: ");
		String sid = sc.nextLine().trim();
		List<Enrollment> list = enrollmentService.listByStudent(sid);
		if (list.isEmpty()) {
			System.out.println("No enrollments.");
			return;
		}
		list.forEach(System.out::println);
	}

	private static void dropEnrollment(Scanner sc) {
		System.out.print("Enter enrollment id to drop: ");
		String id = sc.nextLine().trim();
		enrollmentService.dropEnrollment(id);
		System.out.println("Dropped if existed.");
	}

	private static void saveAll() {
		studentService.save();
		courseService.save();
		enrollmentService.save();
		System.out.println("Saved data.");
	}
}