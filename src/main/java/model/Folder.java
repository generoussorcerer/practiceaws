package model;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "folder")

public class Folder
{
    @Id
    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "aws_key")
    private String url;

    @Column(name = "is_finished")
    private String isFinished;

    public Folder() {}

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public String getUrl()
    {
        return url;

//        //AWS S3 upload using presigned URL
//        String bucketName = "saudade0807";
//        String keyName =  folderName;
//        Region region = Region.US_EAST_2;
//
//        S3Presigner presigner = S3Presigner.builder()
//                .region(region)
//                .build();
//        GetObjectRequest getObjectRequest =
//                GetObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(keyName)
//                        .build();
//
//        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
//                .signatureDuration(Duration.ofHours(24))
//                .getObjectRequest(getObjectRequest)
//                .build();
//
//        // Generate the presigned request
//        PresignedGetObjectRequest presignedGetObjectRequest =
//                presigner.presignGetObject(getObjectPresignRequest);
//
//        System.out.println(presignedGetObjectRequest.url().toString());
//
//        return presignedGetObjectRequest.url().toString();
    }

    @Override
    public String toString()
    {
        return "\nFolder #" + folderName + " with presignedURL: " + url;
    }
}
