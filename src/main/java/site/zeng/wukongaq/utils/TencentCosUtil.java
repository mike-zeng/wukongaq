package site.zeng.wukongaq.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeng
 */
public class TencentCosUtil {
    private static final Logger logger= LoggerFactory.getLogger(TencentCosUtil.class);
    private static final String SECRETID = "AKIDHWcgptGqmstxXFg0zUuPrRGFCD6ssfgi";
    private static final String SECRETKEY = "HcccYgls2Eew7tdQxNk3KcIA1iJza1XR";
    private static COSCredentials cred = new BasicCOSCredentials(SECRETID, SECRETKEY);
    private static ClientConfig clientConfig;
    private static ObjectMetadata objectMetadata=new ObjectMetadata();
    private static String bucketName="wukongaq-1257009269";

    static {
        Region region=new Region("ap-chengdu");
        clientConfig = new ClientConfig(region);
    }

    public static boolean put(String key, InputStream input){
        COSClient cosClient = new COSClient(cred, clientConfig);
        cosClient.deleteObject(bucketName,key);
        PutObjectRequest putObjectRequest= new PutObjectRequest(bucketName, key,input,objectMetadata);
        try {
           cosClient.putObject(putObjectRequest);
        }catch (Exception e){
            logger.warn("文件上传失败");
            return false;
        }finally {
            cosClient.shutdown();
        }
        return true;
    }
    public static boolean put(List<String> keys, MultipartFile[] multipartFiles){
        List<InputStream> list=new ArrayList<>();
        try {
            for (MultipartFile multipartFile:multipartFiles){
                if (multipartFile.getInputStream()==null){
                    throw new IOException("文件流异常");
                }
                list.add(multipartFile.getInputStream());
            }
            for (int i=0;i<keys.size();i++){
                put(keys.get(i),list.get(i));
            }
            return true;
        }catch (IOException e){
            logger.error("文件上传失败");
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
