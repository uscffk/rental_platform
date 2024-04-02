package com.ffk.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
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
public class RentOwnershipNFTContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506000600381905550336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610c76806100686000396000f3fe608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680636352211e1461007d57806369e9cae1146100f857806388ef3f7314610173578063a9467b0e14610293578063d082e38114610339578063e960554414610364575b600080fd5b34801561008957600080fd5b506100b6600480360360208110156100a057600080fd5b81019080803590602001909291905050506104d7565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561010457600080fd5b506101716004803603606081101561011b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061058e565b005b34801561017f57600080fd5b506101ac6004803603602081101561019657600080fd5b81019080803590602001909291905050506106bb565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156101f05780820151818401526020810190506101d5565b50505050905090810190601f16801561021d5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561025657808201518184015260208101905061023b565b50505050905090810190601f1680156102835780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561029f57600080fd5b506102e2600480360360208110156102b657600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506108a5565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561032557808201518184015260208101905061030a565b505050509050019250505060405180910390f35b34801561034557600080fd5b5061034e610a27565b6040518082815260200191505060405180910390f35b34801561037057600080fd5b506104c16004803603604081101561038757600080fd5b81019080803590602001906401000000008111156103a457600080fd5b8201836020820111156103b657600080fd5b803590602001918460018302840111640100000000831117156103d857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561043b57600080fd5b82018360208201111561044d57600080fd5b8035906020019184600183028401116401000000008311171561046f57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610a2d565b6040518082815260200191505060405180910390f35b60006003548211151515610553576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260098152602001807f4e6f7420666f756e64000000000000000000000000000000000000000000000081525060200191505060405180910390fd5b6001600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b8273ffffffffffffffffffffffffffffffffffffffff166001600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610664576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f4e6f207065726d697373696f6e7300000000000000000000000000000000000081525060200191505060405180910390fd5b816001600083815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050565b6060806003548311151515610738576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260098152602001807f4e6f7420666f756e64000000000000000000000000000000000000000000000081525060200191505060405180910390fd5b6002600084815260200190815260200160002060000160026000858152602001908152602001600020600101818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107f95780601f106107ce576101008083540402835291602001916107f9565b820191906000526020600020905b8154815290600101906020018083116107dc57829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108955780601f1061086a57610100808354040283529160200191610895565b820191906000526020600020905b81548152906001019060200180831161087857829003601f168201915b5050505050905091509150915091565b6060600080905060008090505b600354811015610939578373ffffffffffffffffffffffffffffffffffffffff166001600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141561092c5781806001019250505b80806001019150506108b2565b5060608160405190808252806020026020018201604052801561096b5781602001602082028038833980820191505090505b509050600080905060008090505b600354811015610a1b578573ffffffffffffffffffffffffffffffffffffffff166001600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610a0e578083838060010194508151811015156109ff57fe5b90602001906020020181815250505b8080600101915050610979565b50819350505050919050565b60035481565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610a8a57600080fd5b600060035490506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff166001600083815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610b0c610b8b565b604080519081016040528086815260200185815250905080600260008481526020019081526020016000206000820151816000019080519060200190610b53929190610ba5565b506020820151816001019080519060200190610b70929190610ba5565b50905050600160035401600381905550819250505092915050565b604080519081016040528060608152602001606081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610be657805160ff1916838001178555610c14565b82800160010185558215610c14579182015b82811115610c13578251825591602001919060010190610bf8565b5b509050610c219190610c25565b5090565b610c4791905b80821115610c43576000816000905550600101610c2b565b5090565b9056fea165627a7a72305820401df17faf06d112ed844ff0e953ef90e4f75565b13a694cf65a91b45a2a85aa0029";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_TRANSFERNFT = "transferNFT";

    public static final String FUNC_GETNFTMETADATA = "getNFTMetaData";

    public static final String FUNC_GETALLNFTBYADDRESS = "getAllNFTByAddress";

    public static final String FUNC_TOKENCOUNTER = "tokenCounter";

    public static final String FUNC_MINTNFT = "mintNFT";

    @Deprecated
    protected RentOwnershipNFTContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RentOwnershipNFTContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RentOwnershipNFTContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RentOwnershipNFTContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferNFT(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERNFT, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<String, String>> getNFTMetaData(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETNFTMETADATA, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getAllNFTByAddress(String manufacturer) {
        final Function function = new Function(FUNC_GETALLNFTBYADDRESS, 
                Arrays.<Type>asList(new Address(160, manufacturer)),
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

    public RemoteFunctionCall<BigInteger> tokenCounter() {
        final Function function = new Function(FUNC_TOKENCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mintNFT(String commodityName, String location) {
        final Function function = new Function(
                FUNC_MINTNFT, 
                Arrays.<Type>asList(new Utf8String(commodityName),
                new Utf8String(location)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static RentOwnershipNFTContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentOwnershipNFTContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RentOwnershipNFTContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RentOwnershipNFTContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RentOwnershipNFTContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RentOwnershipNFTContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RentOwnershipNFTContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RentOwnershipNFTContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RentOwnershipNFTContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RentOwnershipNFTContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<RentOwnershipNFTContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RentOwnershipNFTContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RentOwnershipNFTContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RentOwnershipNFTContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RentOwnershipNFTContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RentOwnershipNFTContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
