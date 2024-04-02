package com.ffk.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

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
public class DepositContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506000600181905550336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610b01806100686000396000f3fe60806040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806312065fe01461009057806326dbe56b146100bb5780633c168eab146101165780636352211e14610178578063862b092b146101f357806388ef3f7314610283578063a9467b0e146102e4578063d082e3811461038a575b005b34801561009c57600080fd5b506100a56103b5565b6040518082815260200191505060405180910390f35b3480156100c757600080fd5b50610114600480360360408110156100de57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506103d4565b005b6101626004803603604081101561012c57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506105a2565b6040518082815260200191505060405180910390f35b34801561018457600080fd5b506101b16004803603602081101561019b57600080fd5b810190808035906020019092919050505061074b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101ff57600080fd5b506102086107f8565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561024857808201518184015260208101905061022d565b50505050905090810190601f1680156102755780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561028f57600080fd5b506102bc600480360360208110156102a657600080fd5b8101908080359060200190929190505050610835565b6040518084815260200183151515158152602001828152602001935050505060405180910390f35b3480156102f057600080fd5b506103336004803603602081101561030757600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610929565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561037657808201518184015260208101905061035b565b505050509050019250505060405180910390f35b34801561039657600080fd5b5061039f610aab565b6040518082815260200191505060405180910390f35b60003073ffffffffffffffffffffffffffffffffffffffff1631905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610498576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f4e6f207065726d697373696f6e0000000000000000000000000000000000000081525060200191505060405180910390fd5b6104a0610ab1565b6002600083815260200190815260200160002060606040519081016040529081600082015481526020016001820160009054906101000a900460ff1615151515815260200160028201548152505090506000816000015190508373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f1935050505015801561053f573d6000803e3d6000fd5b50600082602001901515908115158152505081600260008581526020019081526020016000206000820151816000015560208201518160010160006101000a81548160ff0219169083151502179055506040820151816002015590505050505050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610668576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f4e6f207065726d697373696f6e0000000000000000000000000000000000000081525060200191505060405180910390fd5b60006001549050610677610ab1565b60606040519081016040528034815260200160011515815260200185815250905080600260008481526020019081526020016000206000820151816000015560208201518160010160006101000a81548160ff02191690831515021790555060408201518160020155905050846003600084815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001805401600181905550819250505092915050565b6000610755610ab1565b6002600084815260200190815260200160002060606040519081016040529081600082015481526020016001820160009054906101000a900460ff16151515158152602001600282015481525050905060011515816020015115151415156107bc57600080fd5b6003600084815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050919050565b60606040805190810160405280601a81526020017f4465706f736974204e6f6e2d46756e6769626c6520546f6b656e000000000000815250905090565b600080600060015484111515156108b4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260098152602001807f4e6f7420666f756e64000000000000000000000000000000000000000000000081525060200191505060405180910390fd5b6108bc610ab1565b6002600086815260200190815260200160002060606040519081016040529081600082015481526020016001820160009054906101000a900460ff161515151581526020016002820154815250509050806000015181602001518260400151935093509350509193909250565b6060600080905060008090505b6001548110156109bd578373ffffffffffffffffffffffffffffffffffffffff166003600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156109b05781806001019250505b8080600101915050610936565b506060816040519080825280602002602001820160405280156109ef5781602001602082028038833980820191505090505b509050600080905060008090505b600154811015610a9f578573ffffffffffffffffffffffffffffffffffffffff166003600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610a9257808383806001019450815181101515610a8357fe5b90602001906020020181815250505b80806001019150506109fd565b50819350505050919050565b60015481565b6060604051908101604052806000815260200160001515815260200160008152509056fea165627a7a72305820a5d9dfeea36dccb2e6502b09ecef1ab7e97c2b1f631330ab1949640c4cc5ed330029\r\n";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_BACKDEPOSIT = "backDeposit";

    public static final String FUNC_MINTNFT = "mintNFT";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_GETTOKENNAME = "getTokenName";

    public static final String FUNC_GETNFTMETADATA = "getNFTMetaData";

    public static final String FUNC_GETALLNFTBYADDRESS = "getAllNFTByAddress";

    public static final String FUNC_TOKENCOUNTER = "tokenCounter";

    @Deprecated
    protected DepositContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DepositContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DepositContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DepositContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> backDeposit(String user, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_BACKDEPOSIT, 
                Arrays.<Type>asList(new Address(160, user),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mintNFT(String userAddress, BigInteger commodityNFTTokenId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_MINTNFT, 
                Arrays.<Type>asList(new Address(160, userAddress),
                new Uint256(commodityNFTTokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROF, 
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

    public RemoteFunctionCall<Tuple3<BigInteger, Boolean, BigInteger>> getNFTMetaData(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETNFTMETADATA, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, Boolean, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, Boolean, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, Boolean, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getAllNFTByAddress(String userAddress) {
        final Function function = new Function(FUNC_GETALLNFTBYADDRESS, 
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

    public RemoteFunctionCall<BigInteger> tokenCounter() {
        final Function function = new Function(FUNC_TOKENCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static DepositContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DepositContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DepositContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DepositContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DepositContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DepositContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DepositContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DepositContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DepositContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DepositContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<DepositContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DepositContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DepositContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DepositContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DepositContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DepositContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
