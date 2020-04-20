package com.study.spring.transcation.base.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class BaseRepositoryImpl<T, ID extends Serializable> extends
        SimpleJpaRepository<T, Serializable> implements BaseRepository<T, Serializable> {


    private final EntityManager em;
    private final Class<T> domainClass;
    private static Boolean hasJoin;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.domainClass = domainClass;
        this.em = em;
    }

    @Override
    public String toString() {
        return "BaseRepositoryImpl [em=" + this.em + ", domainClass=" + this.domainClass
                + "]";
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        TypedQuery<T> query = this.getQuery(spec, pageable);
        return pageable == null ? new PageImpl<T>(query.getResultList())
                : this.readPage(query, this.getDomainClass(), pageable, spec);

    }

    @Override
    protected <S extends T> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<S> root = this.applySpecificationToCriteria(spec, domainClass, query);

        if (!CollectionUtils.isEmpty(root.getJoins())) {
            this.hasJoin = true;
        } else {
            this.hasJoin = false;
        }

        if (this.hasJoin) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }
        //query.select(builder.countDistinct(root));
        query.orderBy(Collections.emptyList());
        return this.em.createQuery(query);
    }

    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass, CriteriaQuery<S> query) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");
        Root<U> root = query.from(domainClass);
        if (spec == null) {
            return root;
        } else {
            CriteriaBuilder builder = this.em.getCriteriaBuilder();
            Predicate predicate = spec.toPredicate(root, query, builder);
            if (predicate != null) {
                query.where(predicate);
            }

            return root;
        }
    }

    private static Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        if (hasJoin) {
            return null == totals ? 0 : (long) totals.size();
        }

        Long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }


}


