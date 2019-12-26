DROP TABLE IF EXISTS Groups CASCADE;
DROP TABLE IF EXISTS Students CASCADE;
DROP TABLE IF EXISTS Courses CASCADE;
DROP TABLE IF EXISTS StudentCourses;
CREATE TABLE groups(
	group_id INT PRIMARY KEY,
    group_name VARCHAR(255)
);
CREATE TABLE students(
	student_id INT PRIMARY KEY,
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	group_id INT,
	FOREIGN KEY (group_id) REFERENCES groups(group_id)
);
CREATE TABLE courses(
	course_id INT PRIMARY KEY,
	course_name VARCHAR(255),
	course_description VARCHAR(255)
);
CREATE TABLE studentcourses(
student_id INT NOT NULL,
course_id INT NOT NULL,
FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
UNIQUE (student_id, course_id)
);
