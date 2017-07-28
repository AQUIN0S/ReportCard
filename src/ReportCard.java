import java.util.*;

/**
 * This class is a project for the Udacity course Android Basics Nanodegree. As the name suggests, this is a simple
 * class made to represent the properties and methods of a typical school report card class in programming. Variables
 * that this class should contain include the student's name, their classes and corresponding grades, their teacher's
 * name and their school name. There are more that could be included but those are the ones I can think of at this
 * moment.
 *
 * Created by Daniel Schimanski on 28/07/17
 */
public class ReportCard {

    private final double maxPercentage = 100.0;
    private final double minPercentage = 0.0;

    private String studentName;
    private String teacherName;
    private String schoolName;

    // This value can be set via either the setOverallGrade() method or the setCoursesAndGrades() method, whichever is
    // called last.
    private double overallGrade;

    // Because each grade (represented by doubles to show percentages), apart from overallGrade, is associated with a
    // class that the student takes, a HashMap is used to represent the concept.
    private HashMap<String, Double> coursesAndGrades = new HashMap<>();

    /**
     * Create a new instance of a Student Report class using the individual grades
     * @param studentName       string containing the student's name.
     * @param teacherName       string containing the teacher's name.
     * @param schoolName        string containing the school's name.
     * @param coursesAndGrades  a HashMap representing each class/course the student has taken, and each corresponding
     *                          grade.
     */
    public ReportCard(String studentName, String teacherName,
                      String schoolName, HashMap<String, Double> coursesAndGrades) {
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.schoolName = schoolName;
        setCoursesAndGrades(coursesAndGrades);
    }

    public ReportCard(String studentName, String teacherName, String schoolName, double overallGrade) {
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.schoolName = schoolName;
        this.overallGrade = overallGrade;
    }

    /**
     * Set the individual grade for each class that the student takes. This method also sets the overallGrade variable,
     * making it equal to the average of all of the class grades.
     *
     * @param classesAndGrades  a HashMap with keys containing a string with the class/course name, and a double with
     *                          the corresponding grade of that class/course.
     */
    public void setCoursesAndGrades(HashMap<String, Double> classesAndGrades) {
        Iterator iterator = classesAndGrades.entrySet().iterator();

        // While there's still a set/entry that contains the student's grade and class, add that to HashMap
        // this.coursesAndGrades. If the user entered an invalid grade, prompt them to enter a correct grade and
        // add that new grade with the class instead.
        while (iterator.hasNext()) {
            Map.Entry nextCourseAndGrade = (Map.Entry) iterator.next();

            if ((double) nextCourseAndGrade.getValue() < minPercentage ||
                    (double) nextCourseAndGrade.getValue() > maxPercentage) {
                double newGrade = promptUserForValidGrade((String) nextCourseAndGrade.getKey(),
                        (double) nextCourseAndGrade.getValue());
                this.coursesAndGrades.put((String) nextCourseAndGrade.getKey(), newGrade);
            } else {
                this.coursesAndGrades.put((String) nextCourseAndGrade.getKey(),
                        (double) nextCourseAndGrade.getValue());
            }

            iterator.remove();
        }

        // Set overallGrade to be the average of the individual grades.
        double totalOfGrades = 0.0;

        for (double individualGrade : this.coursesAndGrades.values()) {
            totalOfGrades += individualGrade;
        }

        overallGrade = totalOfGrades / this.coursesAndGrades.size();
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void addCourseAndGrade(String courseName, double courseGrade) {

        if (courseGrade > maxPercentage || courseGrade < minPercentage) {
            double newGrade = promptUserForValidGrade(courseName, courseGrade);
            coursesAndGrades.put(courseName, newGrade);
        } else {
            coursesAndGrades.put(courseName, courseGrade);
        }

    }

    public double getCourseGrade(String courseName) {
        if (coursesAndGrades.get(courseName) == null) {
            System.out.println("This student is not associated with that course.");
            return minPercentage;
        } else {
            return coursesAndGrades.get(courseName);
        }
    }

    /**
     * Don't let any student access this method! You've been warned...
     *
     * @param overallGrade      the student's overall grade, usually set when intantiating this class or when
     *                          setting the student's individual grades.
     */
    public void setOverallGrade(double overallGrade) {
        this.overallGrade = overallGrade;
    }

    public HashMap<String, Double> getCoursesAndGrades() {
        return coursesAndGrades;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public double getOverallGrade() {
        return overallGrade;
    }

    @Override
    public String toString() {
        String initialReport = "Student: " + studentName + '\n' +
                "Teacher: " + teacherName + '\n' +
                "School: " + schoolName + '\n';

        // A StringBuilder is used instead of just concatenating the string because of the increased performance
        // (from what I understood, concatenating a string means copying the entire string each time).
        StringBuilder fullReport = new StringBuilder(initialReport);

        if (!coursesAndGrades.isEmpty()) {
            fullReport.append("Courses:\n");
            for (Map.Entry classAndGrade : coursesAndGrades.entrySet()) {
                fullReport.append('\t');
                fullReport.append(classAndGrade.getKey());
                fullReport.append(" - ");
                fullReport.append(classAndGrade.getValue());
                fullReport.append("%\n");
            }
        }

        fullReport.append("Overall Grade: ");
        fullReport.append(overallGrade);
        fullReport.append("%\n");
        return fullReport.toString();
    }

    private double promptUserForValidGrade(String className, double currentEnteredGrade) {
        double newGrade = currentEnteredGrade;
        while (newGrade > 100.0 || newGrade < 0.0) {
            System.out.print("You entered an invalid percentage grade for " + studentName + "'s class ");
            System.out.print(className + ". (" + newGrade);
            System.out.print(") Please enter a grade between 0 and 100: ");
            newGrade = new Scanner(System.in).nextDouble();
        }
        return newGrade;
    }

    /**
     * An example main method to show some functionalities of this class.
     */
    public static void main(String[] args) {

        HashMap<String, Double> emilysGrades = new HashMap<>();
        emilysGrades.put("History", 56.0);
        emilysGrades.put("Mathematics", 64.7);
        emilysGrades.put("Physics", 104.0);

        ReportCard emilysReport = new ReportCard("Emily Webb", "J. Whittaker",
                "Victoria University", emilysGrades);
        emilysReport.addCourseAndGrade("Art", 74.3);
        System.out.println(emilysReport.getCourseGrade("Mathematics"));
        System.out.println(emilysReport);

        double joshesOverallGrade = 75.4;
        ReportCard joshesReport = new ReportCard("Josh Smith", "T. Tucker",
                "Otago University", joshesOverallGrade);
        System.out.println(joshesReport);
    }

}
