package com.wonders.task.asset.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wonders.task.asset.model.bo.DwAssetCodeInfo;
import com.wonders.task.asset.service.DwAssetCodeInfoService;
import com.wonders.task.asset.util.DateUtil;
import com.wonders.task.sample.ITaskService;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("dwAssetCodeInfoExecute")
public class DwAssetCodeInfoExecute implements ITaskService {
	private DwAssetCodeInfoService dwAssetCodeInfoService;

	public DwAssetCodeInfoService getDwAssetCodeInfoService() {
		return dwAssetCodeInfoService;
	}

	@Autowired(required = false)
	public void setDwAssetCodeInfoService(@Qualifier(value = "dwAssetCodeInfoService") DwAssetCodeInfoService dwAssetCodeInfoService) {
		this.dwAssetCodeInfoService = dwAssetCodeInfoService;
	}

	@Override
	public String exec(String param) {
		long value = 0;
		DwAssetCodeInfo dw = new DwAssetCodeInfo();
		List<DwAssetCodeInfo> listDwAssetCodeInfo = new ArrayList<DwAssetCodeInfo>();
		List<Object[]> listDwAsset = dwAssetCodeInfoService.findAllDwAsset();
		List<Object[]> listCount = dwAssetCodeInfoService.findAllCountByTypeidDm();
		for (int i = 0; i < listDwAsset.size(); i++) {
			for (int j = 0; j < listCount.size(); j++) {

				if (String.valueOf(listDwAsset.get(i)[1]).equals(String.valueOf(listCount.get(j)[0])) && String.valueOf(listDwAsset.get(i)[2]).equals(String.valueOf(listCount.get(j)[1]))) {
					String id = dwAssetCodeInfoService.findIdByTypeIdAndDm(String.valueOf(listDwAsset.get(i)[1]), String.valueOf(listDwAsset.get(i)[2]));
					dw = (DwAssetCodeInfo) dwAssetCodeInfoService.load(id, dw.getClass());

					if (listCount.get(j)[2] != null) {
						dw.setValue(Long.valueOf(String.valueOf(listCount.get(j)[2])));
					} else {
						dw.setValue(value);
					}
					dw.setUpdateTime(DateUtil.Date2Date(new Date()));

					listDwAssetCodeInfo.add(dw);
				}
			}
		}
		dwAssetCodeInfoService.saveOrUpdateAll(listDwAssetCodeInfo);
		return "";
	}
}
