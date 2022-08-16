package com.comeongroup.assignment.service;

import com.comeongroup.assignment.data.model.IdentifiableEntity;
import com.comeongroup.assignment.exception.ElementNotFoundException;
import com.comeongroup.assignment.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class CrudService<E extends IdentifiableEntity, R extends JpaRepository<E, Long>, CD, UD, D, M extends BaseMapper<CD, UD, D, E>> {

    protected final R repository;
    protected final M mapper;

    @Transactional
    public D create(CD createDto) {
        E entity = mapper.fromCreateDTO(createDto);
        preCreate(entity);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    protected void preCreate(E entity) {
    }

    @Transactional
    public D update(UD updateDto) {
        E entity = mapper.fromUpdateDTO(updateDto);
        validateAndGet(entity.getId());
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public D findOne(Long id) {
        return mapper.toDto(validateAndGet(id));
    }

    @Transactional
    public void delete(Long id) {
        E entity = validateAndGet(id);
        preDelete(id);
        repository.delete(entity);
    }

    protected void preDelete(Long id) {
    }

    @Transactional(readOnly = true)
    public Page<D> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public E validateAndGet(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Entity Id InCorrect"));
    }
}