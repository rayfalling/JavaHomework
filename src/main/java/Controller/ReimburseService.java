package Controller;


import Model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报销服务类
 *
 * @author 海蔚、
 * @version 0.1
 */
public class ReimburseService {

    /**
     * 预结算计算
     *
     * @param Id 病人编号
     */
    public static PreSettleResult preSettle(String Id) {
        double allValue = 0;  //总费用
        double allTotalReimburseValue = 0;  //总报销金额
        double selfFundedAmount = 0;  //总自费金额
        double firstRange = 0;  //第一阶段自费
        double secondRange = 0;  //第二阶段自费
        double thirdRange = 0;   //第三阶段自费
        List<PersonnelVisit> personnelVisitArrayList = new PersonnelVisitManager().getFromFile().stream().filter(p -> p.getId().equals(Id)).collect(Collectors.toList());
        if (personnelVisitArrayList.isEmpty()) return null;
        double max = 200000;
        String personType = personnelVisitArrayList.get(0).getCategory();
        switch (personType) {
            case "在职职工":
                max = 200000;
                break;
            case "退休人员":
                max = 150000;
                break;
            case "享受最低保障的在职人员":
                max = 120000;
                break;
            case "享受最低保障的退休人员":
                max = 100000;
                break;
        }
        PreSettleResult result = new PreSettleResult();
        List<PreSettleResult> newList = new PreSettleResultManager().getFromFile() == null ? new ArrayList<>() : new PreSettleResultManager().getFromFile().stream().filter(p -> p.getId().equals(Id)).collect(Collectors.toList());
        //上一次报销得到的年度报销总额
        double yearAmount = 0;
        //如果在这一年，本次报销前存在报销记录，则获取上次报销统计的年度报销金额
        if (!newList.isEmpty()) {
            yearAmount = newList.get(newList.size() - 1).getYearTotalReimbursementAmount();
            //超过最大限额，则不报销
            if (yearAmount > max) {
                result = newList.get(newList.size() - 1);
                result.setReimbursementAmount(0);
                result.setFirstRangeAmount(0);
                result.setSecondRangeAmount(0);
                result.setThirdRangeAmount(0);
                result.setSelfFundedAmount(allValue);
                result.setTotalExpenses(allValue);
                result.setYearTotalReimbursementAmount(yearAmount);
                return result;
            }
        }
        //遍历所有处方
        ArrayList<Prescription> list = (ArrayList<Prescription>) new PrescriptionManager().getFromFile().stream().filter((item) -> personnelVisitArrayList.stream().anyMatch(personnelVisit -> personnelVisit.getClinicNumber().equals(item.getClinicNumber()))).collect(Collectors.toList());
        if (list.isEmpty()) return null;
        //增加总费用
        /*如果一次报销总和小于100，则不报销*/
        for (Prescription details : list) {
            allValue += details.getTotalPrice();
        }
        if (allValue < 100) {
            selfFundedAmount += allValue;
        }

        //一次报销申请的总报销
        double totalReimburseValue = 0;
        //一次报销申请中未缩减比例的总报销
        double totalHalfReimburseValue = 0;
        //遍历一次报销的所有明细
        for (Prescription p : list) {
            //如果是药品，按药品算
            MedicineInfo m = new MedicineInfoManager().getFromFile().stream().filter((medicineInfo) -> medicineInfo.getDrugCode().equals(p.getCode())).findFirst().get();
            //返回为0，则为自费
            double v = calculateMedical(p, m);
            if (v == -1) {
                return null;
            }
            if (v == 0) {
                selfFundedAmount += p.getTotalPrice();
            }
            totalHalfReimburseValue += v;
            double[][] r = calculateRange(totalHalfReimburseValue);
            //增加三个阶段自费金额
            firstRange += r[0][0];
            secondRange += r[0][1];
            thirdRange += r[0][2];
            //增加一次报销的报销总金额
            totalReimburseValue += r[1][0];
            totalReimburseValue += r[1][1];
            totalReimburseValue += r[1][2];
            allTotalReimburseValue += totalReimburseValue;
        }
        selfFundedAmount += firstRange + secondRange + thirdRange;
        //这一次报销后的年度报销金额
        double newYearAmount = allTotalReimburseValue + yearAmount;
        //超过，则年度总报销金额等于最大金额，本次报销金只取最后抵达上限部分
        if (newYearAmount > max) {
            allTotalReimburseValue = max - yearAmount;
            selfFundedAmount += newYearAmount - max;
        }
        result.setTotalExpenses(allValue);
        result.setClinicNumber(personnelVisitArrayList.get(0).getClinicNumber());
        result.setFirstRangeAmount(firstRange);
        result.setSecondRangeAmount(secondRange);
        result.setThirdRangeAmount(thirdRange);
        result.setSelfFundedAmount(selfFundedAmount);
        result.setReimbursementAmount(allTotalReimburseValue);
        result.setId(Id);
        result.setYearTotalReimbursementAmount(newYearAmount);
        return result;
    }

    /**
     * 对药品计算
     * 预结算报销金额
     */
    private static double calculateMedical(Prescription p, MedicineInfo m) {
        double reimburseValue = p.getTotalPrice();
        if (p.getItemPrice() > m.getPriceLimit()) {
            reimburseValue = m.getPriceLimit() * p.getCount();
        }
        if (m.getChargeItemLevel() == ChargeItemLevel.ClassB) {
            return reimburseValue / 2;
        } else if (m.getChargeItemLevel() == ChargeItemLevel.ClassC) {
            return 0;
        } else return reimburseValue;
    }

    /**
     * 计算
     * 预结算报销范围
     */
    private static double[][] calculateRange(double halfValue) {
        double firstSelfRange = 0;
        double secondSelfRange = 0;
        double thirdSelfRange = 0;
        double firstReimburseRange = 0;
        double secondReimburseRange = 0;
        double thirdReimburseRange = 0;
        if (halfValue <= 10000 && halfValue > 100) {
            firstSelfRange = (halfValue - 100) * 0.2;
            firstReimburseRange = (halfValue - 100) * (1 - 0.2);
        } else if (halfValue <= 20000 && halfValue > 10000) {
            firstSelfRange = (10000 - 100) * 0.2;
            firstReimburseRange = (10000 - 100) * (1 - 0.2);
            secondSelfRange = (halfValue - 10000) * 0.1;
            secondReimburseRange = (halfValue - 10000) * (1 - 0.1);
        } else if (halfValue > 20000) {
            firstSelfRange = (10000 - 100) * 0.2;
            firstReimburseRange = (10000 - 100) * (1 - 0.2);
            secondSelfRange = (20000 - 10000) * 0.1;
            secondReimburseRange = (20000 - 10000) * (1 - 0.1);
            thirdSelfRange = (halfValue - 20000) * 0.05;
            thirdReimburseRange = (halfValue - 20000) * (1 - 0.05);
        }
        double[][] result = new double[2][3];
        result[0][0] = firstSelfRange;
        result[0][1] = secondSelfRange;
        result[0][2] = thirdSelfRange;
        result[1][0] = firstReimburseRange;
        result[1][1] = secondReimburseRange;
        result[1][2] = thirdReimburseRange;
        return result;
    }

    /**
     * 报销结算方法，对预报销进行年度结算
     *
     * @param Id 用于搜索预结算清单及人员信息
     * @return 结算结果数据对象
     */
    public static SettleResult Settle(String Id) throws IOException {
        if (Id == null || Id.equals("")) return null;
        SettleResult settleResult = new SettleResult();
        //预结算信息
        ArrayList<PreSettleResult> results = new ArrayList<>();
        double totalExpenses = 0;  //年度费用总额
        double selfFundedAmount = 0;//年度自费金额
        double totalReimbursementAmount = 0; //年度累计报销金额
        //从文件中查询预结算信息
        PreSettleResult result = new PreSettleResultManager().getFromFile().stream().filter(preSettleResult -> preSettleResult.getId().equals(Id)).findFirst().get();
        results.add(result);
        totalExpenses += result.getTotalExpenses();
        selfFundedAmount += result.getSelfFundedAmount();
        //传入为预结算信息为null或者为空，则返回null
        if (results.isEmpty()) return null;
        //人员id
        settleResult.setId(Id);
        //就诊日期段
        ArrayList<String> medicalTimeRange = new ArrayList<>();
        //根据人员id得到本年度所有就诊信息
        List<PersonnelVisit> information = new PersonnelVisitManager().getFromFile().stream().filter(personnelVisit -> personnelVisit.getId().equals(Id)).collect(Collectors.toList());
        for (int i = 0; i < information.size(); i++)
            medicalTimeRange.add("第" + (i + 1) + "次报销：" + information.get(i).getAdmissionDate() + " to " + information.get(i).getLeaveDate());
        //根据就诊信息得到所有处方信息
        List<Prescription> detailsArrayList = new PrescriptionManager().getFromFile().stream().filter(prescription -> information.stream().anyMatch(personnelVisit -> prescription.getClinicNumber().equals(personnelVisit.getClinicNumber()))).collect(Collectors.toList());
        //乙类项目
        ArrayList<String> bClassItem = new ArrayList<>();
        //自费项目
        ArrayList<String> cClassItem = new ArrayList<>();
        //遍历处方信息，获取乙类和丙类项目
        for (Prescription details : detailsArrayList) {
            MedicineInfo medical = new MedicineInfoManager().getFromFile().stream().filter(medicineInfo -> medicineInfo.getDrugCode().equals(details.getCode())).findFirst().get();
            if (medical.getChargeItemLevel() == ChargeItemLevel.ClassB) {
                boolean flag = true;
                for (String s : bClassItem) {
                    if (s.equals(medical.getName())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) bClassItem.add(medical.getName());
            }
            if (medical.getChargeItemLevel() == ChargeItemLevel.ClassC) {
                //丙类为自费项目
                boolean flag = true;
                for (String s : cClassItem) {
                    if (s.equals(medical.getName())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) cClassItem.add(medical.getName());
            }
        }
        //起付标准
        double standard = 100;
        settleResult.setName(new PersonManager().getFromFile().stream().filter(person -> person.getId().equals(Id)).findFirst().get().getName());
        settleResult.setSelfFundedAmount(selfFundedAmount);
        settleResult.setbClassItem(bClassItem);
        settleResult.setCategory(information.get(0).getCategory());
        settleResult.setTotalExpenses(totalExpenses);
        settleResult.setInHospitalTime(results.size());
        settleResult.setMedicalTimeRange(medicalTimeRange);
        settleResult.setSettleDate(LocalDate.now().toString());
        settleResult.setStartStandardAmount(standard);
        settleResult.setServiceExpenses(totalReimbursementAmount);
        settleResult.setcClassItem(cClassItem);
        return settleResult;
    }
}
