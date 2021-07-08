package model.dao;

import model.entity.Booking;
import model.entity.Client;
import model.entity.Client_;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Client DAO: implementing all methods from interface
 */

public class ClientDAO extends DAO
{
    public ClientDAO()
    {
        super();
    }

    public boolean signUpClient(String name, String password, int role)
    {
        try
        {
            getEntityManager().getTransaction().begin();

            Client client = new Client();
            client.setId(10 + (int)(Math.random()*100));
            client.setName(name);
            client.setPassword(password);
            client.setRole(role);

            getEntityManager().persist(client);
            getEntityManager().getTransaction().commit();


            System.out.println("Successful registration");
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Client getClientRole(String name, String password)
    {
        Client client;

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> raceRoot = query.from(Client.class);
        query.where(cb.equal(raceRoot.get(Client_.name), name) ,
                cb.equal(raceRoot.get(Client_.password), password));

        try
        {
            client = getEntityManager().createQuery(query).getSingleResult();
        }
        catch (NoResultException ex)
        {
            client  = null;
        }

        return client;
    }

}
