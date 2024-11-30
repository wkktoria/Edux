package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.EduxClass;
import io.github.wkktoria.edux.repository.EduxClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EduxClassService {
    private final EduxClassRepository eduxClassRepository;

    @Autowired
    public EduxClassService(final EduxClassRepository eduxClassRepository) {
        this.eduxClassRepository = eduxClassRepository;
    }

    public List<EduxClass> findAll() {
        return eduxClassRepository.findAll();
    }

    public boolean saveClass(final EduxClass eduxClass) {
        boolean isSaved = false;
        final EduxClass savedEduxClass = eduxClassRepository.save(eduxClass);

        if (savedEduxClass != null && savedEduxClass.getClassId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }

    public Optional<EduxClass> findClassWithId(final int classId) {
        return eduxClassRepository.findById(classId);
    }

    public void deleteClassWithId(final int classId) {
        eduxClassRepository.deleteById(classId);
    }

    public EduxClass updateClass(EduxClass eduxClass) {
        return eduxClassRepository.save(eduxClass);
    }
}
