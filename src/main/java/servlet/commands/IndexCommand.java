package servlet.commands;

import controller.dao.FolderDAO;
import model.Folder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.utils.IoUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class IndexCommand implements Command
{
    private static FolderDAO folderDAO = new FolderDAO();

    @Override
    public String getPattern()
    {
        return "index";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException
    {
        List<Folder> list = folderDAO.readFolders();
        request.setAttribute("downloadFiles", list);

        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/WEB-INF/view/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException
    {
//        String contentType = request.getContentType();
//
//        if ((contentType.indexOf("multipart/form-data") >= 0))
//        {
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            factory.setRepository(new File("c:\\temp"));
//
//            ServletFileUpload upload = new ServletFileUpload(factory);
//
//            try
//            {
//                List fileItems = upload.parseRequest(request);
//                String zipName = "archive" + (folderDAO.readFolders().size() + 1) + ".zip";
//
//                Iterator i = fileItems.iterator();
//
//                //zipping folder in one file
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
//
//                while (i.hasNext())
//                {
//                    FileItem fi = (FileItem)i.next();
//
//                    if (!fi.isFormField())
//                    {
//                        String fileName = fi.getName();
//
//                        ZipEntry zipEntry = new ZipEntry(fileName.substring( fileName.lastIndexOf("/") + 1));
//                        zipOutputStream.putNextEntry(zipEntry);
//
//                        byte[] buffer = new byte[fi.getInputStream().available()];
//                        fi.getInputStream().read(buffer);
//                        zipOutputStream.write(buffer);
//                        zipOutputStream.closeEntry();
//
//                    }
//                }
//
//                zipOutputStream.close();
//                bos.close();
//
//                //AWS S3 upload using presigned URL
//                String bucketName = "saudade0807";
//                String keyName =  zipName;
//                Region region = Region.US_EAST_2;
//
//                S3Presigner presigner = S3Presigner.builder()
//                        .region(region)
//                        .build();
//
//                signBucket(presigner, bucketName, keyName, bos.toByteArray());
//                presigner.close();
//
//                doGet(request, response, servletContext);
//            }
//            catch(Exception ex)
//            {
//                System.out.println(ex);
//            }
//        }

        String bucketName = "saudade0807";
        String keyName =  "archive" + (folderDAO.readFolders().size() + 1) + ".zip";
        System.out.println(keyName);
        Region region = Region.US_EAST_2;

        S3Presigner presigner = S3Presigner.builder()
                        .region(region)
                        .build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .contentType("application/zip")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(45))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);

        String content = presignedRequest.url().toString();

        response.setContentType("text/plain");

        OutputStream outStream = response.getOutputStream();
        outStream.write(content.getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }

    public static String putS3Object(S3Client s3,
                                     String bucketName,
                                     String objectKey,
                                     String objectPath)
    {

        try
        {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            PutObjectResponse response = s3.putObject(putOb,
                    RequestBody.fromBytes(getObjectFile(objectPath)));

            return response.eTag();

        }
        catch (S3Exception  e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return "";
    }

    private static byte[] getObjectFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

    public static void signBucket(S3Presigner presigner, String bucketName, String keyName, byte[] pic)
    {
        try
        {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(45))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);


            String myURL = presignedRequest.url().toString();
            System.out.println("Presigned URL to upload a file to: " +myURL);

            System.out.println("Which HTTP method needs to be used when uploading a file: " +
                    presignedRequest.httpRequest().method());

            URL url = presignedRequest.url();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.getOutputStream().write(pic);
            connection.getResponseCode();
            System.out.println("HTTP response code is " + connection.getResponseCode());

            folderDAO.createEntry(keyName, getPresignedUrl(presigner, bucketName, keyName));

        }
        catch (S3Exception e)
        {
            e.getStackTrace();
        }
        catch (IOException e)
        {
            e.getStackTrace();
        }
    }

    public static String getPresignedUrl(S3Presigner presigner, String bucketName, String keyName)
    {
        try
        {
            GetObjectRequest getObjectRequest =
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(45))
                    .getObjectRequest(getObjectRequest)
                    .build();

            // Generate the presigned request
            PresignedGetObjectRequest presignedGetObjectRequest =
                    presigner.presignGetObject(getObjectPresignRequest);

            return presignedGetObjectRequest.url().toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }

//                        String bucketName = "saudade0807";
//                        String keyName =  fileName;

//                        Region region = Region.US_EAST_2;
//                        S3Client s3 = S3Client.builder()
//                                .region(region)
//                                .build();
//
//                        String result = putS3Object(s3, bucketName, keyName, objectPath);
//                        System.out.println("Tag information: "+result);
//                        s3.close();
//
//                        S3Presigner presigner = S3Presigner.builder()
//                                .region(region)
//                                .build();
//
//                        signBucket(presigner, bucketName, keyName, FileUtils.readFileToByteArray(file));
//                        presigner.close();

}
