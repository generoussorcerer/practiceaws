package model;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Folder.class)
public class Folder_
{
    public static volatile SingularAttribute<Folder, String> folderName;
    public static volatile SingularAttribute<Folder, String> url;
}
