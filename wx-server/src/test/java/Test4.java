import com.xuxue.dapp.red.packetes.util.G;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class Test4 {


    public static void main(String[] args)throws Exception{


        try(CloseableHttpClient client = HttpClients.createDefault()){

            HttpPost post = new HttpPost("http://localhost:8081/test");
            Map<String,Object> data = new HashMap<>();
            data.put("hehe","hehe");
            data.put("name","ddd");
            post.setEntity(new StringEntity(G.gson.toJson(data)));
            try(CloseableHttpResponse response = client.execute(post)){
                System.out.println(EntityUtils.toString(response.getEntity()));
            }

        }

    }

}
