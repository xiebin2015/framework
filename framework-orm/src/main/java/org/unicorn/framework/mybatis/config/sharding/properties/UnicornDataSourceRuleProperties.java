package org.unicorn.framework.mybatis.config.sharding.properties;

import com.google.common.collect.Lists;
import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author  xiebin
 */
@Data
@ConfigurationProperties(prefix = "unicorn.jdbc.datasource.rule")
public class UnicornDataSourceRuleProperties {
    /**
     * 主从规则配置
     */
    private List<MasterSlaveRuleConfiguration> masterSlaveRule= Lists.newArrayList();
    /**
     * 分片规则配置
     */
    private ShardingRuleConfiguration shardingRule=new ShardingRuleConfiguration();
}
