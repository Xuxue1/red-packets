import com.xuxue.dapp.red.packetes.util.G;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.nebulas.account.AccountManager;
import rpcpb.AdminServiceGrpc;
import rpcpb.Rpc;

import java.util.Arrays;

public class Test2 {

    public static void main(String[] args)throws Exception{

        AccountManager accountManager = new AccountManager();
        String s = "{\"version\":3,\"id\":\"fd292156-966b-4410-b4a2-465f00b3a703\",\"address\":\"n1KeeRJJPMqRJTd2ecihzx2c5sX1zD3kvaH\",\"crypto\":{\"ciphertext\":\"3b2b43c4baa9ffe5f2cff9dd6728b34858b99876b81c6e636c9d14b16ffad592\",\"cipherparams\":{\"iv\":\"47b67ea02d1aedc46ea1f54c5dab7a26\"},\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"salt\":\"fe66381d6304467c780dca367e715394ed1b2816e285f68d8a3b800561710ccf\",\"n\":4096,\"r\":8,\"p\":1},\"mac\":\"58133ae56c0d8514d90019f8ec80bac39fc551bf56a374997f5c6aba41dd99df\",\"machash\":\"sha3256\"}}";
        String passwd = "ww7qq6998490";
        //accountManager.load(s.getBytes(),passwd.getBytes());

        ManagedChannel channel = ManagedChannelBuilder.forAddress("104.20.17.135", 80).usePlaintext().build();
        Rpc.TransactionRequest transactionRequest = Rpc.TransactionRequest.newBuilder()
                .setFrom("n1KeeRJJPMqRJTd2ecihzx2c5sX1zD3kvaH")
                .setTo("n1zoakZ1SEMpGdc6SuR1zFgD2qYcS6GUvqb")
                .setValue("0.01")
                .setGasLimit("200000")
                .setGasPrice("1000000")
                .setContract(
                        Rpc.ContractRequest.newBuilder()
                                .setArgs(G.gson.toJson(Arrays.asList("2","test2","xuxue","hahaha","2018-5-4")))
                            .setFunction("sent")
                            .build()
                )
                .build();

        Rpc.SendTransactionPassphraseRequest request = Rpc.SendTransactionPassphraseRequest
                .newBuilder()
                .setTransaction(transactionRequest)
                .setPassphrase("ww7qq6998490")
                .build();

        AdminServiceGrpc.AdminServiceBlockingStub apiServiceStub = AdminServiceGrpc.newBlockingStub(channel);
        Rpc.SendTransactionResponse response = apiServiceStub.sendTransactionWithPassphrase(request);
        System.out.println(response);


    }

}
