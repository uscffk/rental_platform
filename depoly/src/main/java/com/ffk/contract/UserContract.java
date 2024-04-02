package com.ffk.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
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
public class UserContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600060018190555042600381905550610a9f8061006f6000396000f3fe6080604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063017e4fa2146100b157806312065fe01461010057806324c09be81461012b57806359296e7b1461017a578063597ea83a146101a55780636d3dd259146101e05780639e0acf8f1461024c578063a9059cbb146102b8578063f28db6c41461032b578063f3961c1314610388578063f5f5ba721461039f575b005b3480156100bd57600080fd5b506100ea600480360360208110156100d457600080fd5b810190808035906020019092919050505061042f565b6040518082815260200191505060405180910390f35b34801561010c57600080fd5b506101156104d8565b6040518082815260200191505060405180910390f35b34801561013757600080fd5b506101646004803603602081101561014e57600080fd5b81019080803590602001909291905050506104f7565b6040518082815260200191505060405180910390f35b34801561018657600080fd5b5061018f610562565b6040518082815260200191505060405180910390f35b3480156101b157600080fd5b506101de600480360360208110156101c857600080fd5b810190808035906020019092919050505061056c565b005b3480156101ec57600080fd5b506101f561060e565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561023857808201518184015260208101905061021d565b505050509050019250505060405180910390f35b34801561025857600080fd5b506102616106d8565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156102a4578082015181840152602081019050610289565b505050509050019250505060405180910390f35b3480156102c457600080fd5b50610311600480360360408110156102db57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506107fe565b604051808215151515815260200191505060405180910390f35b34801561033757600080fd5b5061036e6004803603604081101561034e57600080fd5b8101908080359060200190929190803590602001909291905050506108c5565b604051808215151515815260200191505060405180910390f35b34801561039457600080fd5b5061039d6109ac565b005b3480156103ab57600080fd5b506103b4610a1c565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103f45780820151818401526020810190506103d9565b50505050905090810190601f1680156104215780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b600080600090505b6002805490508110156104ae5761044c610a59565b60028281548110151561045b57fe5b906000526020600020906002020160408051908101604052908160008201548152602001600182015481525050905083816000015114156104a05781925050506104d3565b508080600101915050610437565b507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff90505b919050565b60003073ffffffffffffffffffffffffffffffffffffffff1631905090565b6000806105038361042f565b90506000811215156105385760028181548110151561051e57fe5b90600052602060002090600202016001015491505061055d565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff9150505b919050565b6000600154905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156105c757600080fd5b60006001549050600060035490506000620151808242038115156105e757fe5b04600a858115156105f457fe5b040290508083016001819055504260038190555050505050565b6060806002805490506040519080825280602002602001820160405280156106455781602001602082028038833980820191505090505b50905060008090505b6002805490508110156106d057610663610a59565b60028281548110151561067257fe5b9060005260206000209060020201604080519081016040529081600082015481526020016001820154815250509050806020015183838151811015156106b457fe5b906020019060200201818152505050808060010191505061064e565b508091505090565b60606000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561073557600080fd5b606060028054905060405190808252806020026020018201604052801561076b5781602001602082028038833980820191505090505b50905060008090505b6002805490508110156107f657610789610a59565b60028281548110151561079857fe5b9060005260206000209060020201604080519081016040529081600082015481526020016001820154815250509050806000015183838151811015156107da57fe5b9060200190602002018181525050508080600101915050610774565b508091505090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561085b57600080fd5b816108646104d8565b11156108ba578273ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f193505050501580156108b0573d6000803e3d6000fd5b50600190506108bf565b600090505b92915050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561092257600080fd5b600061092d8461042f565b12156109a15761093b610a59565b604080519081016040528085815260200184815250905060028190806001815401808255809150509060018203906000526020600020906002020160009091929091909150600082015181600001556020820151816001015550505060019150506109a6565b600090505b92915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610a0757600080fd5b60016000815480929190600190039190505550565b60606040805190810160405280600d81526020017f557365722d436f6e747261637400000000000000000000000000000000000000815250905090565b60408051908101604052806000815260200160008152509056fea165627a7a723058200cbe58312e254c7c6cc619f7a8680aea4c51cb792a352e4d2f648e104fa310510029\r\n";

    public static final String FUNC_GETINDEXOFORDERID = "getIndexofOrderId";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETGOODIDBYORDERID = "getGoodIdByOrderId";

    public static final String FUNC_GETCREDIT = "getCredit";

    public static final String FUNC_ADDCREDIT = "addCredit";

    public static final String FUNC_GETGOODIDS = "getGoodIds";

    public static final String FUNC_GETORDERIDS = "getOrderIds";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_ADDORDER = "addOrder";

    public static final String FUNC_DECREASECREDIT = "decreaseCredit";

    public static final String FUNC_GETCONTRACTNAME = "getContractName";

    @Deprecated
    protected UserContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UserContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UserContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UserContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> getIndexofOrderId(BigInteger id) {
        final Function function = new Function(FUNC_GETINDEXOFORDERID, 
                Arrays.<Type>asList(new Uint256(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getGoodIdByOrderId(BigInteger id) {
        final Function function = new Function(FUNC_GETGOODIDBYORDERID, 
                Arrays.<Type>asList(new Uint256(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getCredit() {
        final Function function = new Function(FUNC_GETCREDIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addCredit(BigInteger amount) {
        final Function function = new Function(
                FUNC_ADDCREDIT, 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getGoodIds() {
        final Function function = new Function(FUNC_GETGOODIDS, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<List> getOrderIds() {
        final Function function = new Function(FUNC_GETORDERIDS, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<TransactionReceipt> transfer(String userAddress, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, userAddress), 
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addOrder(BigInteger orderId, BigInteger goodId) {
        final Function function = new Function(
                FUNC_ADDORDER, 
                Arrays.<Type>asList(new Uint256(orderId),
                new Uint256(goodId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> decreaseCredit() {
        final Function function = new Function(
                FUNC_DECREASECREDIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getContractName() {
        final Function function = new Function(FUNC_GETCONTRACTNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static UserContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UserContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UserContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UserContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UserContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UserContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UserContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<UserContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
