package com.backend.notifications.repository;

import com.backend.notifications.model.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, ObjectId> {

    List<Student> findByName(String name);

    // deleteById(id) ya viene incluido en MongoRepository, no hace falta declararlo

    boolean existsById(ObjectId id);

    Optional<Student> findById(ObjectId id);

    // Busca estudiantes cuyo nombre contenga la palabra clave (sin importar mayusculas/minusculas)
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Student> findByNameContaining(String keyword);
}
