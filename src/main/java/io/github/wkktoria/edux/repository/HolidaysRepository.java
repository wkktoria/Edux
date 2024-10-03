package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepository extends CrudRepository<Holiday, String> {

}
