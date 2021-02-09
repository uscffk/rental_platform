package com.ffk.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 房发科
 * @date 2021/1/31 11:40
 */
public class IpfsUtil {
    private static IPFS  ipfs;
    //private static String filePath;
    static {
        ipfs = new IPFS("127.0.0.1",5001);
    }

    /**
     * 添加文件
     * @param file
     * @throws IOException
     */
    public void add(File file) throws IOException {
        //上传文件
        NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(file);
        //返回哈希值
        MerkleNode result = ipfs.add(savefile).get(0);
        //返回结果里面获取保存文件的唯一hash，基于文件内容的 hash
        System.out.println(result.toJSONString());
    }

    /**
     *下载并保存文件
     * @param hash  //文件对应的hash值
     * @throws IOException
     */
    public  void download(String hash) throws IOException {
        //保存的文件路径
        String filename="D:/ipfstest.log";
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = ipfs.cat(filePointer);
        if(data != null){
            File file  = new File(filename);
            if(file.exists()){
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data,0,data.length);
            fos.flush();
            fos.close();
        }
    }

    /**
     * 删除文件
     * @param hash //文件对应的hash值
     * @throws IOException
     */
    public void delete(String hash) throws IOException {

        Multihash filePointer = Multihash.fromBase58(hash);
        List<Multihash> rm = ipfs.pin.rm(filePointer);
        System.out.println(rm.get(0));//返回结果文件内容的 hash
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
