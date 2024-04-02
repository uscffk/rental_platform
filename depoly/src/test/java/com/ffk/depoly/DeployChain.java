package com.ffk.depoly;

import com.ffk.contract.DepositContract;
import com.ffk.contract.PlatformContract;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.*;
//import com.ffk.contract.service.PlatFormContractService;
import org.junit.Test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author 房发科
 * @date 2021/3/23 22:28
 */
public class DeployChain {

    @Test
    public void test1() throws Exception {


        //这四个值都把它打印出来
        //部署平台合约  给这个合约打钱部署会比较好点
        String platFormContractAddress = PlatformContractService.deploy(BigInteger.valueOf(10000));
        System.out.println(platFormContractAddress);
        //部署租赁权合约
        String rentOwnershipNFTAddress = RentOwnershipNFTContractService.deploy();
        System.out.println(rentOwnershipNFTAddress);
        //部署押金合约
        String depositAddress = DepositContractService.deploy();
        System.out.println(depositAddress);
        //部署商品合约
        String commodityAddress = CommodityContractService.deploy();
        System.out.println(commodityAddress);
        //拍卖合约 用户合约 商家合约不需要提前部署 具体在哪里部署去看业务逻辑即可


//
//        String platFormContractAddress = PlatFormContractService.deploy();
//        System.out.println(platFormContractAddress);
//        System.out.println(PlatformContract.deploy(Environment.getWeb3j(),
//                Environment.getCredential(),
//                new DefaultGasProvider(),
//                BigInteger.valueOf(10000)).send().getContractAddress());
//        String deploy = UserContractService.deploy();
//        System.out.println(deploy);

//        String deploy = ManufacturerContractService.deploy();
//        System.out.println(deploy);

//        System.out.println(Platform.deploy(Environment.getWeb3j(),
//                Environment.getCredential(),
//                new DefaultGasProvider(),
//                BigInteger.ZERO).send().getContractAddress());

//        System.out.println(DepositContractService.deploy());
//        System.out.println(RentOwnershipNFTContractService.deploy());

//        System.out.println(CommodityContractService.deploy());

//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());
//        System.out.println(UserContractService.deploy());

//        PlatformContract load = PlatformContractService.load("0xb045ab28f9705b78852c124ccdf5a781d3e452d6");
//        load.recharge("0x5e16c8c1ebcabe916dee1c3253b630fbf1e5722e",BigInteger.valueOf(500)).send();

//        System.out.println(ManufacturerContractService.load("0xa692a719bc0a3fdf571f680d42e7e8eddd277396").getBalance().send().intValue());

//        System.out.println(UserContractService.deploy());
//        UserContractService.load("0x5e16c8c1ebcabe916dee1c3253b630fbf1e5722e").transfer("0xb045ab28f9705b78852c124ccdf5a781d3e452d6",
//                BigInteger.valueOf(20)).send();

//        System.out.println(RentOwnershipNFTContractService.deploy());
//        RentOwnershipNFTContract load = RentOwnershipNFTContractService.load("0x846c0311c895ee705f1dc54cb4857c5d03ecc290");
//        System.out.println(load.ownerOf(BigInteger.valueOf(1)).send());
        //部署拍卖合约
//        String deploy = AuctionContractService.deploy(10);
//        AuctionContract load = AuctionContractService.load(deploy);
//        System.out.println(load.getContractAddress());
//        load.setAuctionParameter(BigInteger.valueOf(10000),"0xc9B639F309229122685A6fEaD51845B8cF12b930",BigInteger.valueOf(0),
//                "0x846c0311c895ee705f1dc54cb4857c5d03ecc290").send();
//        String s = "";
//        s = "dfas";
//        System.out.println(s);
        //获取系统时间
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = df.parse(df.format(new Date()));
//        long time = date.getTime();
//        System.out.println(time);
//        String s = new String("fsed");
//        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.replace()

//        CommodityContractService.transferUseRight("0x5e16c8c1ebcabe916dee1c3253b630fbf1e5722e",
//                "0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65",5,
//                CommodityContractService.load("0xd8826f81d68493832602f3d62e9db0624b089469"));

//        System.out.println(CommodityContractService.ownerofUseright(5, CommodityContractService.load("0xd8826f81d68493832602f3d62e9db0624b089469")));

//        System.out.println(UserContractService.getAllOrdersId(UserContractService.load("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65")));
//        System.out.println(UserContractService.getAllGoodsId(UserContractService.load("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65")));
//        UserContractService.addOrder(BigInteger.valueOf(1),BigInteger.valueOf(5),UserContractService.load("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65"));

//        System.out.println(DepositContractService.getAllNFTByAddress("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65", DepositContractService
//                .load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49")));

//        DepositContractService.mintDepositNFT("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65",BigInteger.valueOf(5),BigInteger.valueOf(1),DepositContractService
//                .load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49"));
//                System.out.println(DepositContractService.ownerOf(1, DepositContractService
//                .load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49")));
//        System.out.println(DepositContractService.getNFTMetaData(5, DepositContractService
//                .load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49")));

//        DepositContractService.backDeposit("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65",BigInteger.valueOf(5),DepositContractService.load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49"));

//        DepositContract depositContract = DepositContractService.load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49");
//        System.out.println(depositContract.backDeposit("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65", BigInteger.valueOf(5)).send().isStatusOK());

        //        System.out.println(CommodityContractService.ownerofOwnership(26, CommodityContractService.load("0xd8826f81d68493832602f3d62e9db0624b089469")));
//        System.out.println(DepositContractService.getBalance(DepositContractService.load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49")).intValue());
//        System.out.println(CommodityContractService.getCommodityComment(5, CommodityContractService.load("0xd8826f81d68493832602f3d62e9db0624b089469")));
//        DepositContractService.load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49").backDeposit("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65",)

//        System.out.println(DepositContractService.getAllNFTByAddress("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65", DepositContractService.load("0xd1cfa502c85d9dbf8103954d3587e84cac82ba49")));

//        UserContract userContract = UserContractService.load("0x2eba669ebad4a875f5e02c4f4840c6727a9f1b65");
//        System.out.println(userContract.addCredit(BigInteger.valueOf(50)).send().isStatusOK());

//        String deploy = PlatformContractService.deploy(BigInteger.valueOf(100));
//        System.out.println(deploy);
//        PlatformContract platformContract = PlatformContractService.load("0xdc9e5440a085230dd9bd3e829c42496c23d3286a");
//        int balance = PlatformContractService.getBalance(platformContract).intValue();
//        System.out.println(balance);
//
//        //转出来
//        PlatformContractService.transferToAccount("0xc9B639F309229122685A6fEaD51845B8cF12b930",platformContract);
//        int balance1 = PlatformContractService.getBalance(platformContract).intValue();
//        System.out.println(balance1);
    }

    @Test
    public void test2() throws Exception {



//        UserContract load1 = UserContractService.load("0x6bd23Fb5281CC5EDb69CEc923A62fE1A9E7Fa158");
//        BigInteger balance = UserContractService.getBalance(load1);
//        System.out.println(balance.intValue());

//        PlatformContract load = PlatFormContractService.load("0xABEdE1886BeD8623cD26a8A3C7Fe940DBb78d36f");
//        System.out.println(load.getContractAddress());
//        System.out.println(load.getBalance().send().intValue());
//        RemoteFunctionCall<TransactionReceipt> transactionReceiptRemoteFunctionCall = load.transferToAccount("0x653104B6FDd18cb9b151926E5036bA89E5dc0C48");
//        transactionReceiptRemoteFunctionCall.send();

//        System.out.println(load.getBalance().send().intValue());
//        System.out.println(load.recharge("0x6ee921357DdA218165Ca41381A9802a992471980",BigInteger.valueOf(10)).send().booleanValue());
//        int i = balance.send().intValue();
//        System.out.println(i);
//        测试加载合约
//        PlatFormContract platFormContract = PlatFormContractService.load("0x8284de246d7ba5076f47a79f78c3a780c39ba09c");
        //余额查询
//        System.out.println(PlatFormContractService.getBalance(platFormContract));
        //
        //ManufacturerContractService.load()
//        0x8284de246d7ba5076f47a79f78c3a780c39ba09c
//        System.out.println(PlatFormContractService.deploy(BigInteger.valueOf(88888888)));
        //测试用户合约
//        UserContract load = UserContractService.load("0xa44a24006f8381f80221db93a7d0935f115c0b3c");
//        System.out.println(UserContractService.getBalance(load));

        //信用积分初始化
//        UserContract load = UserContractService.load("0xa44a24006f8381f80221db93a7d0935f115c0b3c");
//        UserContractService.initCredit(1,load);
//        System.out.println("0xa44a24006f8381f80221db93a7d0935f115c0b3c".length());
//        System.out.println(UserContractService.getCredit(load));
//        load.initCredit(BigInteger.valueOf(1));


    }


}
