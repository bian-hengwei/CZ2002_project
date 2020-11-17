# CE/CZ2002: Object-Oriented Design & Programming
  
Back to [README.md](README.md)

## Program Requirements:
- Administrator mode
  - Create course
  - Create student record
- User mode
  - Add course
  - Drop course
  - Check/print courses registered
  - Check vacancies available
  - Change index number of course
  - Swap index number with another student
  
## Design pattern:
We plan to use 
[*MVC*](https://en.wikipedia.org/wiki/Model-view-controller) 
pattern in our design.
  
## Model Properties
- [Account (abstract)](Account.java):
  
- [Administrator](Admin.java):
  - Name
  - Nationality
  
- [User](Student.java):
  - Name
  - Nationality
  - Matric Number
  - Major
  - Year
  - Courses taken
  - Indexes taking
  - _Waitlists_
  - Current AU
  
- [Course](Course.java):
  - Indexes
  
- [Index](Index.java):
  - Course id
  - Course name
  - Index number
  - Vacancy count
  - Number of AU
  - School
  - Wait list queue
  - Registered students list
  - Lecture time
  - Lecture venue
  - (Optional) Exam time
  - (Optional) Exam venue
  - (Optional) Tutorial time
  - (Optional) Tutorial venue
  - (Optional) Lab time
  - (Optional) Lab venue

- [Main](Main.java):
  - Login periods
  - Courses
  - Indexes
  - Current account
  
## Main functions
- [x] Account (abstract):
  - [x] Read passwords
  - [x] Read personal information
  
- [ ] Administrator:
  - [ ] Login
    - Reject if:
      - Invalid password
      - Wrong account
  - [ ] Load personal information
  - [ ] Print personal information
  - [ ] Add a new course
    - Reject if:
      - Already exists
    - Else display all courses
  - [ ] Add a new student
    - Reject if:
      - Already exists
    - Else display all students
  - [ ] Update a course (code, school, indexes, vacancy)
  - [ ] Print student list by index number
  - [ ] Print student list by course
  - [ ] Check vacancies
  
- User:
  - [x] Login
    - Reject if:
      - Invalid password
      - Wrong account
      - _Illegal access period (after loading information)_
  - [x] Load personal information
  - [ ] Print personal information
  - [ ] Add course
    - Reject if:
      - Currently taking
      - Already taken
      - Exceeds max AU
      - Time clashes
    - Wait list if:
      - Index full
    - Else accept and display
  - [ ] Check for vacancies
  - [ ] Swap Index With Student
  - [ ] Display registered courses
  - [ ] Drop course
    - Register first student in wait list if any
    - _Check clash_
    - _Send notification to student (via email)_
  - [ ] Change course index
    - Reject if:
      - Time clashes
    - Wait list if:
      - Index full
    - Else accept and display
  
- [ ] Course:
  - [ ] _Seems like no special method needed_
  
- [ ] Index:
  - [ ] Methods used by above classes
  - [ ] Display methods
