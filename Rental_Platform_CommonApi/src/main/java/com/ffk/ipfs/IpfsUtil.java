package com.ffk.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.List;

/**
 * @author 房发科
 * @date 2021/1/31 11:40
 */
@Component
public class IpfsUtil {

    private static IPFS  ipfs;
    static {
        ipfs = new IPFS("127.0.0.1",5001);
    }

    /**
     * 添加评论到ipfs
     * @param comment
     * @throws IOException
     * 返回一个哈希值
     */
    public String add(String comment) throws IOException {
        NamedStreamable.ByteArrayWrapper byteArray = new NamedStreamable.ByteArrayWrapper(comment.getBytes());
        MerkleNode addResult = ipfs.add(byteArray).get(0);
        return addResult.hash.toString();
    }

    /**
     *查询ipfs里面的评论
     * @param hash  //文件对应的hash值
     * @throws IOException
     */
    public String query(String hash) throws IOException {
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = ipfs.cat(filePointer);
        String str = new String(data);
        return str;
    }

    /**
     * 添加新节点
     */
    public void addPeers()throws  IOException{
        MultiAddress multiAddress = new MultiAddress("/ip4/192.168.91.140/tcp/4001/ipfs/QmQJPAqVaURJbSnFLaiA65Zm6fYePFTR7AkWKT2LANp7X5");
        List<MultiAddress> peers = ipfs.bootstrap.add(multiAddress);
        System.out.println(peers.get(0).toString());
    }

    /**
     * 删除节点
     * @throws IOException
     */
    public void rmPeers()throws IOException{
        MultiAddress multiAddress = new MultiAddress("/ip4/192.168.91.140/tcp/4001/ipfs/QmQJPAqVaURJbSnFLaiA65Zm6fYePFTR7AkWKT2LANp7X5");
        List<MultiAddress> rm = ipfs.bootstrap.rm(multiAddress);
        System.out.println(rm.get(0).toString());
    }
}
