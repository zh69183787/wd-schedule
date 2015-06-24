package com.wonders.task.assetPortal.util;

import java.util.Map;

import com.wonders.task.assetPortal.model.bo.DwAssetType;

public class SetDwAssetType {
	private long parseStringToLong(String value){
		if(value==null){
			return 0l;
		}else{
			return Long.parseLong(value.split("\\|")[1]);
		}
	}
	
	private double parseStringToDouble(String value){
		if(value==null){
			return 0d;
		}else{
			return Double.parseDouble(value.split("\\|")[0]);
		}
	}
	
	private double parseStringToDouble2(String value){
		if(value==null){
			return 0d;
		}else{
			return Double.parseDouble(value.split("\\|")[2]);
		}
	}
	
	public DwAssetType setLineType(DwAssetType dwAssetType,Map<String,String> lineMap,String line){
		dwAssetType.setQuantityType1(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE1)));
		dwAssetType.setQuantityType2(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE2)));
		dwAssetType.setQuantityType3(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE3)));
		dwAssetType.setQuantityType4(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE4)));
		dwAssetType.setQuantityType5(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE5)));
		dwAssetType.setQuantityType6(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE6)));
		dwAssetType.setQuantityType7(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE7)));
		dwAssetType.setQuantityType8(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE8)));
		dwAssetType.setQuantityType9(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE9)));
		dwAssetType.setQuantityType10(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE10)));
		dwAssetType.setQuantityType11(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE11)));
		dwAssetType.setQuantityType12(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE12)));
		dwAssetType.setQuantityType13(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE13)));
		dwAssetType.setQuantityType14(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE14)));
		dwAssetType.setQuantityType15(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE15)));
		dwAssetType.setQuantityType16(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE16)));
		dwAssetType.setQuantityType17(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE17)));
		dwAssetType.setQuantityType18(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE18)));
		dwAssetType.setQuantityType19(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE19)));
		dwAssetType.setQuantityType20(parseStringToLong(lineMap.get(line+"|"+TypeUtil.TYPE20)));
		dwAssetType.setValueType1(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE1)));
		dwAssetType.setValueType2(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE2)));
		dwAssetType.setValueType3(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE3)));
		dwAssetType.setValueType4(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE4)));
		dwAssetType.setValueType5(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE5)));
		dwAssetType.setValueType6(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE6)));
		dwAssetType.setValueType7(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE7)));
		dwAssetType.setValueType8(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE8)));
		dwAssetType.setValueType9(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE9)));
		dwAssetType.setValueType10(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE10)));
		dwAssetType.setValueType11(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE11)));
		dwAssetType.setValueType12(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE12)));
		dwAssetType.setValueType13(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE13)));
		dwAssetType.setValueType14(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE14)));
		dwAssetType.setValueType15(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE15)));
		dwAssetType.setValueType16(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE16)));
		dwAssetType.setValueType17(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE17)));
		dwAssetType.setValueType18(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE18)));
		dwAssetType.setValueType19(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE19)));
		dwAssetType.setValueType20(parseStringToDouble(lineMap.get(line+"|"+TypeUtil.TYPE20)));
		
		return dwAssetType;
	}
	
	public DwAssetType setStationType(DwAssetType dwAssetType,Map<String,String> stationMap,String line,String station){
		dwAssetType.setQuantityType1(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE1)));
		dwAssetType.setQuantityType2(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE2)));
		dwAssetType.setQuantityType3(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE3)));
		dwAssetType.setQuantityType4(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE4)));
		dwAssetType.setQuantityType5(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE5)));
		dwAssetType.setQuantityType6(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE6)));
		dwAssetType.setQuantityType7(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE7)));
		dwAssetType.setQuantityType8(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE8)));
		dwAssetType.setQuantityType9(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE9)));
		dwAssetType.setQuantityType10(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE10)));
		dwAssetType.setQuantityType11(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE11)));
		dwAssetType.setQuantityType12(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE12)));
		dwAssetType.setQuantityType13(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE13)));
		dwAssetType.setQuantityType14(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE14)));
		dwAssetType.setQuantityType15(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE15)));
		dwAssetType.setQuantityType16(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE16)));
		dwAssetType.setQuantityType17(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE17)));
		dwAssetType.setQuantityType18(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE18)));
		dwAssetType.setQuantityType19(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE19)));
		dwAssetType.setQuantityType20(parseStringToLong(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE20)));
		dwAssetType.setValueType1(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE1)));
		dwAssetType.setValueType2(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE2)));
		dwAssetType.setValueType3(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE3)));
		dwAssetType.setValueType4(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE4)));
		dwAssetType.setValueType5(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE5)));
		dwAssetType.setValueType6(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE6)));
		dwAssetType.setValueType7(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE7)));
		dwAssetType.setValueType8(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE8)));
		dwAssetType.setValueType9(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE9)));
		dwAssetType.setValueType10(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE10)));
		dwAssetType.setValueType11(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE11)));
		dwAssetType.setValueType12(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE12)));
		dwAssetType.setValueType13(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE13)));
		dwAssetType.setValueType14(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE14)));
		dwAssetType.setValueType15(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE15)));
		dwAssetType.setValueType16(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE16)));
		dwAssetType.setValueType17(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE17)));
		dwAssetType.setValueType18(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE18)));
		dwAssetType.setValueType19(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE19)));
		dwAssetType.setValueType20(parseStringToDouble(stationMap.get(line+"|"+station+"|"+TypeUtil.TYPE20)));
		
		return dwAssetType;
	}
	
	public DwAssetType setCompanyType(DwAssetType dwAssetType,Map<String,String> companyMap,String line,String company){
		dwAssetType.setValueType1(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE1)));
		dwAssetType.setValueType2(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE2)));
		dwAssetType.setValueType3(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE3)));
		dwAssetType.setValueType4(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE4)));
		dwAssetType.setValueType5(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE5)));
		dwAssetType.setValueType6(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE6)));
		dwAssetType.setValueType7(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE7)));
		dwAssetType.setValueType8(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE8)));
		dwAssetType.setValueType9(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE9)));
		dwAssetType.setValueType10(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE10)));
		dwAssetType.setValueType11(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE11)));
		dwAssetType.setValueType12(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE12)));
		dwAssetType.setValueType13(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE13)));
		dwAssetType.setValueType14(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE14)));
		dwAssetType.setValueType15(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE15)));
		dwAssetType.setValueType16(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE16)));
		dwAssetType.setValueType17(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE17)));
		dwAssetType.setValueType18(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE18)));
		dwAssetType.setValueType19(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE19)));
		dwAssetType.setValueType20(parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE20)));
		
		double allValue = parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE1))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE2))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE3))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE4))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE5))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE6))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE7))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE8))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE9))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE10))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE11))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE12))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE13))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE14))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE15))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE16))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE17))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE18))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE19))+
						parseStringToDouble(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE20));
		double nowValue = parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE1))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE2))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE3))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE4))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE5))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE6))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE7))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE8))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE9))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE10))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE11))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE12))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE13))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE14))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE15))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE16))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE17))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE18))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE19))+
						parseStringToDouble2(companyMap.get(line+"|"+company+"|"+TypeUtil.TYPE20));
		
		dwAssetType.setAllValue(allValue);
		dwAssetType.setNowValue(nowValue);
		return dwAssetType;
	}
}
