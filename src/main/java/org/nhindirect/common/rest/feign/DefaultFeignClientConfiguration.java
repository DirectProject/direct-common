package org.nhindirect.common.rest.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;

import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;

public class DefaultFeignClientConfiguration
{
	@Autowired
	protected Environment env;
	
	@Value("${direct.webservices.retry.backoff.multiplier:3}")
	protected double backoffMultiplier;
	
	@Value("${direct.webservices.retry.backoff.initialBackoffInterval:100}")
	protected long initialBackoffInterval;	
	
	@Value("${direct.webservices.retry.backoff.maxInterval:20000}")
	protected long maxInterval;		
	
	@Bean
	public ErrorDecoder feignClientErrorDecoder()
	{
		return new DefaultErrorDecoder();
	}
	
    @Bean
    @ConditionalOnProperty(name="direct.webservices.security.basic.user.name", matchIfMissing=false)
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() 
    {
    	final String user = env.getProperty("direct.webservices.security.basic.user.name");
    	final String pass = env.getProperty("direct.webservices.security.basic.user.password");
    	
        return new BasicAuthRequestInterceptor(user, pass);
    }	
    
    @Bean
    public LoadBalancedRetryFactory backOffPolciyFactory()
    {
    	return new LoadBalancedRetryFactory() 
    	{
            @Override
            public BackOffPolicy createBackOffPolicy(String service) 
            {
            	final ExponentialBackOffPolicy backoffPolicy = new ExponentialBackOffPolicy();
            	backoffPolicy.setMultiplier(backoffMultiplier);
            	backoffPolicy.setInitialInterval(initialBackoffInterval);
            	backoffPolicy.setMaxInterval(maxInterval);
            	
                return backoffPolicy;
            }
        };    	
    }
    
    @Bean
    public Retryer retryer() 
    {
    	/*
    	 * Default retryer config
    	 */
        return new Retryer.Default(200, 1000, 5);
    }
}
