package com.ffk.constract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class HelloContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610347806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80635a9b0b891461003b5780638262963b1461005a575b600080fd5b61004361006f565b604051610051929190610265565b60405180910390f35b61006d6100683660046101be565b61010b565b005b6060600080600154818054610083906102c0565b80601f01602080910402602001604051908101604052809291908181526020018280546100af906102c0565b80156100fc5780601f106100d1576101008083540402835291602001916100fc565b820191906000526020600020905b8154815290600101906020018083116100df57829003601f168201915b50505050509150915091509091565b815161011e906000906020850190610125565b5060015550565b828054610131906102c0565b90600052602060002090601f0160209004810192826101535760008555610199565b82601f1061016c57805160ff1916838001178555610199565b82800160010185558215610199579182015b8281111561019957825182559160200191906001019061017e565b506101a59291506101a9565b5090565b5b808211156101a557600081556001016101aa565b600080604083850312156101d0578182fd5b823567ffffffffffffffff808211156101e7578384fd5b818501915085601f8301126101fa578384fd5b813560208282111561020e5761020e6102fb565b604051601f8301601f1916810182018481118282101715610231576102316102fb565b6040528281528483018201891015610247578687fd5b82828601838301379182018101959095529694909301359450505050565b6000604082528351806040840152815b818110156102925760208187018101516060868401015201610275565b818111156102a35782606083860101525b50602083019390935250601f91909101601f191601606001919050565b6002810460018216806102d457607f821691505b602082108114156102f557634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052604160045260246000fdfea2646970667358221220b065e19e322424a62f63ff9dbc80a91adf660b4582eaaee3a7bbebed53fcbb8864736f6c63430008000033";

    public static final String FUNC_GETINFO = "getInfo";

    public static final String FUNC_SETINFO = "setInfo";

    protected HelloContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HelloContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> getInfo() {
        final Function function = new Function(
                FUNC_GETINFO, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setInfo(String _name, BigInteger _age) {
        final Function function = new Function(
                FUNC_SETINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_age)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<HelloContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HelloContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<HelloContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HelloContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static HelloContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HelloContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static HelloContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HelloContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
