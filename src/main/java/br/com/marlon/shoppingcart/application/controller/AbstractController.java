package br.com.marlon.shoppingcart.application.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

abstract public class AbstractController {

    protected Pageable getPageable(int page, int limit, String direction, String defaultPropertyOrder ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, limit, Sort.by(sortDirection, defaultPropertyOrder));
    }

    protected <T> ResponseEntity<?> getResponseEntity(Page<T> page, PagedResourcesAssembler<T> assembler) {
        return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);
    }

}
