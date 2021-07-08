package controller.dao;

import controller.JPAManager;

import javax.persistence.EntityManager;

abstract class DAO
{
    private EntityManager conn;

    public DAO()
    {
        conn = JPAManager.getInstance();
    }

    public EntityManager getEntityManager()
    {
        return conn;
    }
}
