/**
 *
 */
package com.wonders.task.contractReview.dao;

        import com.wonders.task.contractReview.model.bo.PContract;

        import java.util.List;

/**
 * @ClassName: PContractDao
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2014年1月21日 下午3:22:18
 *
 */
public interface PContractDao {
    public void saveBatch(List<?> c);
    public void save(Object entity);
    public  List<PContract>  findBySelfNo(String selfNo);
}
