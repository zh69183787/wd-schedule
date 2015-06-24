/**   
* @Title: ContractReviewServiceTest.java 
* @Package test 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月27日 下午5:06:57 
* @version V1.0   
*/
package test;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.contractReview.service.ContractReviewService;

/** 
 * @ClassName: ContractReviewServiceTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月27日 下午5:06:57 
 *  
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*Context*.xml")
@Transactional 
public class ContractReviewServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private ContractReviewService service;
	/**
	 * Test method for {@link com.wonders.task.contractReview.service.ContractReviewService#getInfo()}.
	 */
	@Test
	public void getTest() {
		System.out.println(this.service.getInfo().size());
	}


}
