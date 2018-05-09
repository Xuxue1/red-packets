import com.xuxue.dapp.red.packetes.model.AdminTransaction;
import com.xuxue.dapp.red.packetes.model.Contract;
import com.xuxue.dapp.red.packetes.model.Transaction;
import com.xuxue.dapp.red.packetes.util.G;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;

public class Test {


    public static void main(String[] args)throws Exception{

        AdminTransaction adminTransaction = new AdminTransaction();
        adminTransaction.setPassphrase("ww7qq6998490");
        Transaction transaction = new Transaction();
        transaction.setFrom("n1KeeRJJPMqRJTd2ecihzx2c5sX1zD3kvaH");
        transaction.setTo("n1zoakZ1SEMpGdc6SuR1zFgD2qYcS6GUvqb");
        transaction.setValue("0.01");
        transaction.setGasLimit("200000");
        transaction.setGasPrice("1000000");
        Contract contract = new Contract();
        contract.setFunction("sent");
        contract.setArgs(G.gson.toJson(Arrays.asList("2","test2","xuxue","hahaha","2018-5-4")));
        transaction.setContract(contract);
        adminTransaction.setTransaction(transaction);

        System.out.println(G.gson.toJson(adminTransaction));

        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpUriRequest request = RequestBuilder.post("https://testnet.nebulas.io/v1/admin/transactionWithPassphrase")
                    .setEntity(new StringEntity(G.gson.toJson(adminTransaction)))
                    .addHeader("Accept","application/json")
                    .build();
            try(CloseableHttpResponse response = client.execute(request)){
                System.out.println(EntityUtils.toString(response.getEntity()));
            }


        }

    }

}
