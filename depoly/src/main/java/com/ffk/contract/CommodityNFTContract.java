package com.ffk.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class CommodityNFTContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50600060038190555033600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506112f0806100696000396000f3fe6080604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806354ba0f27146100b45780636ef5f17e146100f157806380f90a4d1461012e578063862b092b1461016b5780638f3897471461019657806391de45e6146101d3578063922454a414610210578063962bf8a41461024d578063bcae34f81461028a578063d082e381146102c7578063e7796312146102f2575b600080fd5b3480156100c057600080fd5b506100db60048036036100d69190810190610e80565b61032f565b6040516100e8919061115b565b60405180910390f35b3480156100fd57600080fd5b5061011860048036036101139190810190610ef8565b610484565b60405161012591906110de565b60405180910390f35b34801561013a57600080fd5b5061015560048036036101509190810190610f4c565b61055b565b60405161016291906110a1565b60405180910390f35b34801561017757600080fd5b506101806105de565b60405161018d91906110f9565b60405180910390f35b3480156101a257600080fd5b506101bd60048036036101b89190810190610e80565b61061b565b6040516101ca91906110bc565b60405180910390f35b3480156101df57600080fd5b506101fa60048036036101f59190810190610f4c565b61079d565b60405161020791906110a1565b60405180910390f35b34801561021c57600080fd5b5061023760048036036102329190810190610e80565b610821565b60405161024491906110bc565b60405180910390f35b34801561025957600080fd5b50610274600480360361026f9190810190610f4c565b6109a1565b60405161028191906110f9565b60405180910390f35b34801561029657600080fd5b506102b160048036036102ac9190810190610ea9565b610a9d565b6040516102be91906110de565b60405180910390f35b3480156102d357600080fd5b506102dc610bfb565b6040516102e9919061115b565b60405180910390f35b3480156102fe57600080fd5b5061031960048036036103149190810190610ea9565b610c01565b60405161032691906110de565b60405180910390f35b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561038d57600080fd5b81600080600354815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508160016000600354815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020604051908101604052806000815250600260006003548152602001908152602001600020908051906020019061046d929190610d5d565b506001600354016003819055506003549050919050565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156104e257600080fd5b6003548211151515610529576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105209061111b565b60405180910390fd5b82600260008481526020019081526020016000209080519060200190610550929190610d5d565b506001905092915050565b600060035482111515156105a4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161059b9061111b565b60405180910390fd5b60008083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b60606040805190810160405280601f81526020017f436f6d6d6f646974794e4654204e6f6e2d46756e6769626c6520546f6b656e00815250905090565b6060600080905060008090505b6003548110156106af578373ffffffffffffffffffffffffffffffffffffffff166001600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156106a25781806001019250505b8080600101915050610628565b506060816040519080825280602002602001820160405280156106e15781602001602082028038833980820191505090505b509050600080905060008090505b600354811015610791578573ffffffffffffffffffffffffffffffffffffffff166001600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156107845780838380600101945081518110151561077557fe5b90602001906020020181815250505b80806001019150506106ef565b50819350505050919050565b600060035482111515156107e6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107dd9061111b565b60405180910390fd5b6001600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6060600080905060008090505b6003548110156108b4578373ffffffffffffffffffffffffffffffffffffffff1660008083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156108a75781806001019250505b808060010191505061082e565b506060816040519080825280602002602001820160405280156108e65781602001602082028038833980820191505090505b509050600080905060008090505b600354811015610995578573ffffffffffffffffffffffffffffffffffffffff1660008083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156109885780838380600101945081518110151561097957fe5b90602001906020020181815250505b80806001019150506108f4565b50819350505050919050565b606060035482111515156109ea576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109e19061111b565b60405180910390fd5b600260008381526020019081526020016000208054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610a915780601f10610a6657610100808354040283529160200191610a91565b820191906000526020600020905b815481529060010190602001808311610a7457829003601f168201915b50505050509050919050565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610afb57600080fd5b8373ffffffffffffffffffffffffffffffffffffffff166001600084815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610b9e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b959061113b565b60405180910390fd5b826001600084815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600190509392505050565b60035481565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610c5f57600080fd5b8373ffffffffffffffffffffffffffffffffffffffff1660008084815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610d01576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610cf89061113b565b60405180910390fd5b8260008084815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600190509392505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d9e57805160ff1916838001178555610dcc565b82800160010185558215610dcc579182015b82811115610dcb578251825591602001919060010190610db0565b5b509050610dd99190610ddd565b5090565b610dff91905b80821115610dfb576000816000905550600101610de3565b5090565b90565b6000610e0e8235611247565b905092915050565b600082601f8301121515610e2957600080fd5b8135610e3c610e37826111a3565b611176565b91508082526020830160208301858383011115610e5857600080fd5b610e63838284611263565b50505092915050565b6000610e788235611259565b905092915050565b600060208284031215610e9257600080fd5b6000610ea084828501610e02565b91505092915050565b600080600060608486031215610ebe57600080fd5b6000610ecc86828701610e02565b9350506020610edd86828701610e02565b9250506040610eee86828701610e6c565b9150509250925092565b60008060408385031215610f0b57600080fd5b600083013567ffffffffffffffff811115610f2557600080fd5b610f3185828601610e16565b9250506020610f4285828601610e6c565b9150509250929050565b600060208284031215610f5e57600080fd5b6000610f6c84828501610e6c565b91505092915050565b610f7e816111ff565b82525050565b6000610f8f826111dc565b808452602084019350610fa1836111cf565b60005b82811015610fd357610fb7868351611092565b610fc0826111f2565b9150602086019550600181019050610fa4565b50849250505092915050565b610fe881611211565b82525050565b6000610ff9826111e7565b80845261100d816020860160208601611272565b611016816112a5565b602085010191505092915050565b6000600982527f4e6f7420466f756e6400000000000000000000000000000000000000000000006020830152604082019050919050565b6000600d82527f4e6f207065726d697373696f6e000000000000000000000000000000000000006020830152604082019050919050565b61109b8161123d565b82525050565b60006020820190506110b66000830184610f75565b92915050565b600060208201905081810360008301526110d68184610f84565b905092915050565b60006020820190506110f36000830184610fdf565b92915050565b600060208201905081810360008301526111138184610fee565b905092915050565b6000602082019050818103600083015261113481611024565b9050919050565b600060208201905081810360008301526111548161105b565b9050919050565b60006020820190506111706000830184611092565b92915050565b6000604051905081810181811067ffffffffffffffff8211171561119957600080fd5b8060405250919050565b600067ffffffffffffffff8211156111ba57600080fd5b601f19601f8301169050602081019050919050565b6000602082019050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600061120a8261121d565b9050919050565b60008115159050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b60006112528261121d565b9050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015611290578082015181840152602081019050611275565b8381111561129f576000848401525b50505050565b6000601f19601f830116905091905056fea265627a7a72305820644a19416352fda98ae1f126cd79aa62994c185d703222055a0546255fa02a2d6c6578706572696d656e74616cf50037\r\n";

    public static final String FUNC_MINTNFT = "mintNFT";

    public static final String FUNC_SETCOMMODITYCOMMENT = "setCommodityComment";

    public static final String FUNC_OWNEROFUSERIGHT = "ownerofUseright";

    public static final String FUNC_GETTOKENNAME = "getTokenName";

    public static final String FUNC_GETALLOWNERSHIPNFTBYADDRESS = "getAllOwnershipNFTByAddress";

    public static final String FUNC_OWNEROFOWNERSHIP = "ownerofOwnership";

    public static final String FUNC_GETALLUSERIGHTNFTBYADDRESS = "getAllUseRightNFTByAddress";

    public static final String FUNC_GETCOMMODITYCOMMENT = "getCommodityComment";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnerShip";

    public static final String FUNC_TOKENCOUNTER = "tokenCounter";

    public static final String FUNC_TRANSFERUSERIGHT = "transferUseRight";

    @Deprecated
    protected CommodityNFTContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CommodityNFTContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CommodityNFTContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CommodityNFTContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> mintNFT(String manufacturer) {
        final Function function = new Function(
                FUNC_MINTNFT, 
                Arrays.<Type>asList(new Address(160, manufacturer)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setCommodityComment(String commentIpfs, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_SETCOMMODITYCOMMENT, 
                Arrays.<Type>asList(new Utf8String(commentIpfs),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> ownerofUseright(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROFUSERIGHT, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getTokenName() {
        final Function function = new Function(FUNC_GETTOKENNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getAllOwnershipNFTByAddress(String userAddress) {
        final Function function = new Function(FUNC_GETALLOWNERSHIPNFTBYADDRESS, 
                Arrays.<Type>asList(new Address(160, userAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> ownerofOwnership(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROFOWNERSHIP, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getAllUseRightNFTByAddress(String userAddress) {
        final Function function = new Function(FUNC_GETALLUSERIGHTNFTBYADDRESS, 
                Arrays.<Type>asList(new Address(160, userAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> getCommodityComment(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETCOMMODITYCOMMENT, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnerShip(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> tokenCounter() {
        final Function function = new Function(FUNC_TOKENCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferUseRight(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERUSERIGHT, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CommodityNFTContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CommodityNFTContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CommodityNFTContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CommodityNFTContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CommodityNFTContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CommodityNFTContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CommodityNFTContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CommodityNFTContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CommodityNFTContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CommodityNFTContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<CommodityNFTContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CommodityNFTContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CommodityNFTContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CommodityNFTContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CommodityNFTContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CommodityNFTContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
