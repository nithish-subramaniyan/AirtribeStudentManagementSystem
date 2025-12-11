# Student Management System (Java CLI Application)

A simple, extensible **Student Management System** built using **Core Java**.  
The application allows users to manage students, courses, and enrollments through a menu-driven command-line interface.  
Data is stored in **JSON files** using Gson, ensuring easy readability and persistence.

## Features

### Student Management
- Add new students  
- List all students  
- Find a student by ID  
- Delete a student  

### Course Management
- Add new courses  
- List all courses  

### Enrollment Management
- Enroll a student into a course  
- List enrollments for a student  
- Drop an enrollment  

### Additional Technical Features
- JSON-based file storage  
- Input validation  
- Singleton service layer  
- Custom exception handling  

## Technologies Used
- Java (11–25)  
- Maven  
- Gson  
- Java Time API  

## Project Structure
```
src/main/java/com/airtripe/studentmanagement/
├── Main.java
├── entity/
│   ├── Person.java
│   ├── Student.java
│   ├── Course.java
│   └── Enrollment.java
├── service/
│   ├── StudentService.java
│   ├── CourseService.java
│   └── EnrollmentService.java
├── util/
│   ├── InputValidator.java
│   └── JsonUtil.java
├── exception/
│   ├── StudentNotFoundException.java
│   └── InvalidDataException.java
└── interfacex/
    └── Searchable.java
```

## How to Run

### 1. Clone the repository
```bash
git clone https://github.com/<your-username>/<repo-name>.git
cd <repo-name>
```

### 2. Build the JAR
```bash
mvn package
```

### 3. Run the application
```bash
java -jar target/student-management-system-1.0-SNAPSHOT.jar
```

JSON data files will appear automatically:
```
students.json
courses.json
enrollments.json
```

## CLI Menu Overview
```
1) Add Student
2) List Students
3) Find Student by ID
4) Delete Student
5) Add Course
6) List Courses
7) Enroll Student in Course
8) List Enrollments for Student
9) Drop Enrollment
s) Save
q) Save & Quit
```
