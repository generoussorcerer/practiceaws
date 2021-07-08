package controller.dao;


import model.Folder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FolderDAO extends DAO
{
    public FolderDAO()
    {
        super();
    }

    public List<Folder> readFolders()
    {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Folder> query = cb.createQuery(Folder.class);
        Root<Folder> root = query.from(Folder.class);
        query.select(root);

        return getEntityManager().createQuery(query).getResultList();
    }

    public void createEntry(String folderName, String url)
    {
        getEntityManager().getTransaction().begin();

        Folder folder = new Folder();
        folder.setFolderName(folderName);
        folder.setUrl(url);

        getEntityManager().persist(folder);
        getEntityManager().getTransaction().commit();

        System.out.println("Successful insert");
    }
}
