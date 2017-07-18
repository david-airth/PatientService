package com.flex.dhp.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Created by david.airth on 7/11/17.
 */
public abstract class AbstractRestController<T> {

    protected abstract T doGet(Long patientId, long id);

    protected abstract Collection<T> doGetList(Long patientId);

    protected abstract T doCreate(Long patientId, T entity);

    protected abstract T doUpdate(Long patientId, T entity);

    protected abstract void doDelete(Long patientId, long id);

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    T get(@PathVariable(required = false) Long patientId, @PathVariable long id) {

        Assert.isTrue(id > 0, "Id is required");

        return doGet(patientId, id);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<T> getList(@PathVariable(required = false) Long patientId) {

        return doGetList(patientId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<T> add(@PathVariable(required = false) Long patientId, @RequestBody T entity) {

        Assert.notNull(entity, "Entity to update is required");

        T createdEntity = doCreate(patientId, entity);

        return new ResponseEntity<T>(createdEntity, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<T> update(@PathVariable(required = false) Long patientId, @RequestBody T entity) {

        Assert.notNull(entity, "Entity to update is required");

        T updatedEntity = doUpdate(patientId, entity);

        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    ResponseEntity<?> delete(@PathVariable(required = false) Long patientId, @PathVariable long id) {

        Assert.isTrue(id > 0, "id is required");

        doDelete(patientId, id);

        return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
    }
}
