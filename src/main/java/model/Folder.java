package model;

import javax.persistence.*;

@Entity
@Table(name = "folder")

public class Folder
{
    @Id
    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "url")
    private String url;

    public Folder() {}

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public String getUrl()
    {
        return url;
    }

    @Override
    public String toString()
    {
        return "\nFolder #" + folderName + " with presignedURL: " + url;
    }
}
