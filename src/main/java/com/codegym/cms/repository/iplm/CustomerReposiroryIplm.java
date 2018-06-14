package com.codegym.cms.repository.iplm;

import com.codegym.cms.model.Customer;
import com.codegym.cms.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class CustomerReposiroryIplm implements CustomerRepository {

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.id = :id", Customer.class);
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void save(Customer customer) {
        if(customer.getId()!= null){
            em.merge(customer);
        }else {
            em.persist(customer);
        }
    }

    public void remove(Long id) {
        Customer customer = findById(id);
        if(customer != null){
            em.remove(customer);
        }
    }
}
