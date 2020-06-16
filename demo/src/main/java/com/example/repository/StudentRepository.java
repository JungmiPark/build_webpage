package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.entity.Student;

//CrudRepository<엔티티, ID(기본키)타입>
public interface StudentRepository extends CrudRepository<Student, Long> {
	List<Student> findByKorGreaterThan(int kor);
	
	Student findById(String id);
	
	@Query(value = "SELECT * FROM STUDENT WHERE ST_KOR >= :kor", nativeQuery = true)
	List<Student> selectStudentQuery(@Param("kor") int kor);
}
