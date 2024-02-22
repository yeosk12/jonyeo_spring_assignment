use TESTDB;

CREATE TABLE topic (
	id INT PRIMARY KEY IDENTITY,
	name nvarchar(30), 
	description nvarchar(50)
);

CREATE TABLE Lesson (
    id INT PRIMARY KEY IDENTITY,
    name VARCHAR(30),
    topic_id INT,
    FOREIGN KEY (topic_id) REFERENCES Topic(id)
);

select * from topic;
select * from lesson;