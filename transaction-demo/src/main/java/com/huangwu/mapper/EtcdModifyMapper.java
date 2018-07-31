package com.huangwu.mapper;

import com.huangwu.domain.EtcdModify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package: com.huangwu.mapper
 * @Author: huangwu
 * @Date: 2018/5/19 21:32
 * @Description:
 * @LastModify:
 */
@Mapper
public interface EtcdModifyMapper {

    @Insert("INSERT INTO t_groot_etcd_modify (etcd_modify_addr, etcd_modify_key, etcd_modify_value, modify_userid, modified_time, modify_type, etcd_modify_ttl, is_dir)" +
            " VALUES (#{etcdModifyAddr}, #{etcdModifyKey}, #{etcdModifyValue}, #{modifyUserid}, now(), #{modifyType}, #{etcdModifyTtl}, #{isDir})")
    Integer insertOperation(EtcdModify data);
}
