CREATE TABLE IF NOT EXISTS QUIZ
(
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    TITLE VARCHAR(50) NOT NULL,
    COURSE_ID BIGINT NOT NULL,
    FOREIGN KEY (COURSE_ID) REFERENCES COURSE(ID)
);